package com.yimayhd.sellerAdmin.service.item.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.PropertyType;
import com.yimayhd.ic.client.model.param.item.line.LinePubAddDTO;
import com.yimayhd.ic.client.model.param.item.line.LinePubUpdateDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.LineConverter;
import com.yimayhd.sellerAdmin.converter.PictureTextConverter;
import com.yimayhd.sellerAdmin.converter.TagConverter;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.repo.CategoryRepo;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.repo.PictureTextRepo;
import com.yimayhd.sellerAdmin.service.item.LineService;
import com.yimayhd.user.client.dto.CityDTO;

public class LineServiceImpl implements LineService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private LineRepo lineRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private PictureTextRepo pictureTextRepo;

	@Override
	public WebResult<LineVO> getByItemId(long itemId) {
		try {
			if (itemId <= 0) {
				log.warn("无效 ID:" + itemId);
				return WebResult.failure(WebReturnCode.PARAM_ERROR);
			}
			LineResult lineResult = lineRepo.getLineByItemId(itemId);
			if (lineResult == null) {
				log.warn("数据未找到 ID:" + itemId);
				return WebResult.failure(WebReturnCode.DATA_NOT_FOUND);
			}
			// 主题
			List<ComTagDO> themeTags = commentRepo.getTagsByOutId(itemId, TagType.LINETAG);
			List<TagDTO> themes = TagConverter.toTagDTO(themeTags);
			// 出发地
			boolean allDeparts = false;
			List<ComTagDO> departTags = commentRepo.getTagsByOutId(itemId, TagType.DEPARTPLACE);
			if (CollectionUtils.isNotEmpty(departTags)) {
				for (ComTagDO comTagDO : departTags) {
					String name = comTagDO.getName();
					if (Constant.ALL_PLACE_CODE.equalsIgnoreCase(name)) {
						departTags = new ArrayList<ComTagDO>();
						allDeparts = true;
					}
				}
			}
			// 目的地
			List<ComTagDO> destTags = commentRepo.getTagsByOutId(itemId, TagType.DESTPLACE);
			// 图文详情
			PicTextResult picTextResult = pictureTextRepo.getPictureText(itemId, PictureText.ITEM);
			LineVO lineVO = LineConverter.toLineVO(lineResult, picTextResult, themes, toCityVO(departTags),
					toCityVO(destTags));
			lineVO.getBaseInfo().setAllDeparts(allDeparts);
			return WebResult.success(lineVO);
		} catch (Exception e) {
			log.error("Params: id=" + itemId);
			log.error("LineService.getById error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public WebResult<PageVO<LineDO>> pageQueryLine(LinePageQuery query) {
		try {
			if (query == null) {
				log.warn("无效参数: query=" + JSON.toJSONString(query));
				return WebResult.failure(WebReturnCode.PARAM_ERROR);
			}
			PageVO<LineDO> pageQueryLine = lineRepo.pageQueryLine(query);
			return WebResult.success(pageQueryLine);
		} catch (Exception e) {
			log.error("Params: query=" + JSON.toJSONString(query));
			log.error("LineService.pageQueryLine error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public WebResult<List<ComTagDO>> getAllLineThemes() {
		try {
			List<ComTagDO> tagsByTagType = commentRepo.getTagsByTagType(TagType.LINETAG);
			return WebResult.success(tagsByTagType);
		} catch (Exception e) {
			log.error("LineService.getAllLineThemes error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public WebResult<List<CityVO>> getAllLineDeparts() {
		try {
			List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DEPARTPLACE);
			return WebResult.success(toCityVO(comTagDOs));
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "系统错误 ");
		}
	}

	private List<CityVO> toCityVO(List<ComTagDO> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return new ArrayList<CityVO>(0);
		}
		List<String> codes = new ArrayList<String>();
		for (ComTagDO comTagDO : tags) {
			codes.add(comTagDO.getName());
		}
		Map<String, CityDTO> citiesByCodes = cityRepo.getCitiesByCodes(codes);
		List<CityVO> departs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : tags) {
			if (citiesByCodes.containsKey(comTagDO.getName())) {
				CityDTO cityDTO = citiesByCodes.get(comTagDO.getName());
				departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
			}
		}
		return departs;
	}

	@Override
	public WebResult<List<CityVO>> getAllLineDests() {
		try {
			List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DESTPLACE);
			return WebResult.success(toCityVO(comTagDOs));
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public WebResult<LinePropertyConfig> getLinePropertyConfig(long categoryId) {
		try {
			if (categoryId <= 0) {
				log.warn("无效参数: categoryId=" + categoryId);
				return WebResult.failure(WebReturnCode.PARAM_ERROR);
			}
			LinePropertyConfig lineConfig = new LinePropertyConfig();
			List<CategoryValueDO> persionPropertyValues = new ArrayList<CategoryValueDO>();
			CategoryDO category = categoryRepo.getCategoryById(categoryId);
			if (category == null) {
				log.warn("数据未找到 ID:" + categoryId);
				return WebResult.failure(WebReturnCode.DATA_NOT_FOUND);
			}
			List<CategoryPropertyValueDO> propertyDOs = category.getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getCategoryPropertyDO().getType() == PropertyType.PERSON_TYPE.getType()) {
						lineConfig.setPersionProperty(propertyDO.getCategoryPropertyDO());
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								persionPropertyValues.add(categoryValueDO);
							}
						}
					} else if (propertyDO.getCategoryPropertyDO().getType() == PropertyType.LINE_PACKAGE.getType()) {
						lineConfig.setPackageProperty(propertyDO.getCategoryPropertyDO());
					} else if (propertyDO.getCategoryPropertyDO().getType() == PropertyType.START_DATE.getType()) {
						lineConfig.setDateProperty(propertyDO.getCategoryPropertyDO());
					}
				}
			}
			lineConfig.setPersionPropertyValues(persionPropertyValues);
			return WebResult.success(lineConfig);
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public WebOperateResult update(long sellerId, LineVO line) {
		try {
			if (sellerId <= 0 || line == null) {
				log.warn("Param sellerId=" + sellerId);
				log.warn("Param line=" + JSON.toJSONString(line));
				log.warn("LineService.update param error");
				return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
			}
			long itemId = line.getBaseInfo().getItemId();
			LinePubUpdateDTO linePublishDTOForUpdate = LineConverter.toLinePublishDTOForUpdate(sellerId, line);
			LinePublishResult publishLine = lineRepo.updateLine(linePublishDTOForUpdate);
			if (publishLine.isSuccess() && itemId > 0) {
				BaseInfoVO baseInfo = line.getBaseInfo();
				List<Long> themeIds = baseInfo.getThemes();
				commentRepo.saveTagRelation(itemId, TagType.LINETAG, themeIds);
				List<Long> departIds = new ArrayList<Long>();
				if (baseInfo.isAllDeparts()) {
					ComTagDO tagByName = commentRepo.getTagByName(TagType.DEPARTPLACE, Constant.ALL_PLACE_CODE);
					if (tagByName != null) {
						departIds.add(tagByName.getId());
						commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
					}
				} else {
					List<CityVO> departs = baseInfo.getDeparts();
					for (TagDTO tagDTO : departs) {
						departIds.add(tagDTO.getId());
					}
					commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
				}
				List<CityVO> dests = baseInfo.getDests();
				List<Long> destIds = new ArrayList<Long>();
				for (TagDTO tagDTO : dests) {
					destIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DESTPLACE, destIds);

				ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemId, PictureText.ITEM,
						line.getPictureText());
				pictureTextRepo.editPictureText(comentEditDTO);
			} else {
				return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, "更新线路失败 ");
			}
			return WebOperateResult.success();
		} catch (Exception e) {
			log.error("Param sellerId=" + sellerId);
			log.error("Param line=" + JSON.toJSONString(line));
			log.error("更新线路失败", e);
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, "更新线路失败 ");
		}
	}

	@Override
	public WebResult<Long> save(long sellerId, LineVO line) {
		try {
			if (sellerId <= 0 || line == null) {
				log.warn("Param sellerId=" + sellerId);
				log.warn("Param line=" + JSON.toJSONString(line));
				log.warn("LineService.save param error");
				return WebResult.failure(WebReturnCode.PARAM_ERROR);
			}
			LinePubAddDTO linePublishDTOForSave = LineConverter.toLinePublishDTOForSave(sellerId, line);
			LinePublishResult publishLine = lineRepo.saveLine(linePublishDTOForSave);
			long itemId = publishLine.getItemId();
			if (itemId > 0) {
				BaseInfoVO baseInfo = line.getBaseInfo();
				List<Long> themeIds = baseInfo.getThemes();
				commentRepo.saveTagRelation(itemId, TagType.LINETAG, themeIds);
				List<Long> departIds = new ArrayList<Long>();
				if (baseInfo.isAllDeparts()) {
					ComTagDO tagByName = commentRepo.getTagByName(TagType.DEPARTPLACE, Constant.ALL_PLACE_CODE);
					if (tagByName != null) {
						departIds.add(tagByName.getId());
						commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
					}
				} else {
					List<CityVO> departs = baseInfo.getDeparts();
					for (TagDTO tagDTO : departs) {
						departIds.add(tagDTO.getId());
					}
					commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
				}
				List<CityVO> dests = baseInfo.getDests();
				List<Long> destIds = new ArrayList<Long>();
				for (TagDTO tagDTO : dests) {
					destIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DESTPLACE, destIds);
				ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemId, PictureText.ITEM,
						line.getPictureText());
				pictureTextRepo.editPictureText(comentEditDTO);
			} else {
				return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "保存线路失败 ");
			}
			return WebResult.success(itemId);
		} catch (Exception e) {
			log.error("Param sellerId=" + sellerId);
			log.error("Param line=" + JSON.toJSONString(line));
			log.error("保存线路失败", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "保存线路失败 ");
		}
	}
}

package com.yimayhd.sellerAdmin.service.item.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
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
import com.yimayhd.sellerAdmin.converter.LineConverter;
import com.yimayhd.sellerAdmin.converter.TagConverter;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LineCategoryConfig;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.repo.CategoryRepo;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.repo.LineRepo;
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

	@Override
	public WebResult<LineVO> getById(long id) {
		try {
			if (id <= 0) {
				log.warn("无效 ID:" + id);
				return WebResult.failure(WebReturnCode.PARAM_ERROR);
			}
			LineResult lineResult = lineRepo.getLineById(id);
			if (lineResult == null) {
				log.warn("数据未找到 ID:" + id);
				return WebResult.failure(WebReturnCode.DATA_NOT_FOUND);
			}
			// 主题
			List<ComTagDO> themeTags = commentRepo.getTagsByOutId(id, TagType.LINETAG);
			List<TagDTO> themes = TagConverter.toTagDTO(themeTags);
			// 出发地
			List<ComTagDO> departTags = commentRepo.getTagsByOutId(id, TagType.DEPARTPLACE);
			List<CityVO> departs = new ArrayList<CityVO>();
			for (ComTagDO comTagDO : departTags) {
				CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
				departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
			}
			// 目的地
			List<ComTagDO> destTags = commentRepo.getTagsByOutId(id, TagType.DESRTPLACE);
			List<CityVO> dests = new ArrayList<CityVO>();
			for (ComTagDO comTagDO : destTags) {
				CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
				dests.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
			}
			LineVO lineVO = LineConverter.toLineVO(lineResult, themes, departs, dests);
			return WebResult.success(lineVO);
		} catch (Exception e) {
			log.error("Params: id=" + id);
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
	public WebResult<LineCategoryConfig> getLineCategoryConfig() {
		try {
			LineCategoryConfig lineConfig = new LineCategoryConfig();
			// TODO YEBIN 待开发
			return WebResult.success(lineConfig);
		} catch (Exception e) {
			log.error("LineService.getLineCategoryConfig error", e);
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
			List<CityVO> cityVOs = new ArrayList<CityVO>();
			if (CollectionUtils.isNotEmpty(comTagDOs)) {
				for (ComTagDO comTagDO : comTagDOs) {
					CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
					cityVOs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
				}
			}
			return WebResult.success(cityVOs);
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "系统错误 ");
		}
	}

	@Override
	public WebResult<List<CityVO>> getAllLineDests() {
		try {
			List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DESRTPLACE);
			List<CityVO> cityVOs = new ArrayList<CityVO>();
			if (CollectionUtils.isNotEmpty(comTagDOs)) {
				for (ComTagDO comTagDO : comTagDOs) {
					CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
					cityVOs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
				}
			}
			return WebResult.success(cityVOs);
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
				List<CityVO> departs = baseInfo.getDeparts();
				List<Long> departIds = new ArrayList<Long>();
				for (TagDTO tagDTO : departs) {
					departIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
				List<CityVO> dests = baseInfo.getDests();
				List<Long> destIds = new ArrayList<Long>();
				for (TagDTO tagDTO : dests) {
					destIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DESRTPLACE, destIds);
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
			long itemId = publishLine.getLineId();
			if (itemId > 0) {
				BaseInfoVO baseInfo = line.getBaseInfo();
				List<Long> themeIds = baseInfo.getThemes();
				commentRepo.saveTagRelation(itemId, TagType.LINETAG, themeIds);
				List<CityVO> departs = baseInfo.getDeparts();
				List<Long> departIds = new ArrayList<Long>();
				for (TagDTO tagDTO : departs) {
					departIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DEPARTPLACE, departIds);
				List<CityVO> dests = baseInfo.getDests();
				List<Long> destIds = new ArrayList<Long>();
				for (TagDTO tagDTO : dests) {
					destIds.add(tagDTO.getId());
				}
				commentRepo.saveTagRelation(itemId, TagType.DESRTPLACE, destIds);
			}else {
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

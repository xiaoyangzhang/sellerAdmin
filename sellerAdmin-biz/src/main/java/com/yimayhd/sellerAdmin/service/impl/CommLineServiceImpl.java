package com.yimayhd.sellerAdmin.service.impl;

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
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.param.item.line.LinePubAddDTO;
import com.yimayhd.ic.client.model.param.item.line.LinePubUpdateDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
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
import com.yimayhd.sellerAdmin.service.CommLineService;
import com.yimayhd.user.client.dto.CityDTO;

public class CommLineServiceImpl implements CommLineService {
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
	public LineVO getById(long id) {
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = lineRepo.getLineById(id);
		// 主题
		List<ComTagDO> themeTags = commentRepo.getTags(id, TagType.LINETAG);
		List<TagDTO> themes = TagConverter.toTagDTO(themeTags);
		// 出发地
		List<ComTagDO> departTags = commentRepo.getTags(id, TagType.DEPARTPLACE);
		List<CityVO> departs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : departTags) {
			CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
			departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
		}
		// 目的地
		List<ComTagDO> destTags = commentRepo.getTags(id, TagType.DESRTPLACE);
		List<CityVO> dests = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : destTags) {
			CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
			dests.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
		}
		return LineConverter.toLineVO(lineResult, themes, departs, dests);
	}

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		return lineRepo.pageQueryLine(query);
	}

	@Override
	public LineCategoryConfig getLineCategoryConfig() {
		LineCategoryConfig lineConfig = new LineCategoryConfig();

		return lineConfig;
	}

	@Override
	public List<ComTagDO> getAllLineThemes() {
		return commentRepo.getTagsByTagType(TagType.LINETAG);
	}

	@Override
	public List<CityVO> getAllLineDeparts() {
		List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DEPARTPLACE);
		List<CityVO> cityVOs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : comTagDOs) {
			CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
			cityVOs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
		}
		return cityVOs;
	}

	@Override
	public List<CityVO> getAllLineDests() {
		List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DESRTPLACE);
		List<CityVO> cityVOs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : comTagDOs) {
			CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
			cityVOs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
		}
		return cityVOs;
	}

	@Override
	public LinePropertyConfig getLinePropertyConfig(long categoryId) {
		LinePropertyConfig lineConfig = new LinePropertyConfig();
		List<CategoryValueDO> persionPropertyValues = new ArrayList<CategoryValueDO>();
		CategoryDO category = categoryRepo.getCategoryById(categoryId);
		if (category != null) {
			List<CategoryPropertyValueDO> propertyDOs = category.getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				lineConfig.setOptions(1);
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.PERSON.getType()) {
						lineConfig.setPersionProperty(propertyDO.getCategoryPropertyDO());
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								persionPropertyValues.add(categoryValueDO);
							}
						}
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.TRAVEL_PACKAGE
							.getType()) {
						lineConfig.setPackageProperty(propertyDO.getCategoryPropertyDO());
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.DEPART_DATE.getType()) {
						lineConfig.setDateProperty(propertyDO.getCategoryPropertyDO());
					}
				}
			} else {
				lineConfig.setOptions(0);
			}
		}
		lineConfig.setPersionPropertyValues(persionPropertyValues);
		return lineConfig;
	}

	@Override
	public void update(long sellerId, LineVO line) {
		try {
			long itemId = line.getBaseInfo().getItemId();
			if (itemId < 0) {
				log.error("线路商品ID: " + itemId);
				throw new BaseException("无效线路商品ID");
			}
			LinePubUpdateDTO linePublishDTOForUpdate = LineConverter.toLinePublishDTOForUpdate(sellerId, line);
			LinePublishResult publishLine = lineRepo.updateLine(linePublishDTOForUpdate);
			if (publishLine.getLineId() > 0) {
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
			}
		} catch (Exception e) {
			log.error("更新线路失败: " + JSON.toJSONString(line), e);
			throw new BaseException("更新线路失败");
		}
	}

	@Override
	public long save(long sellerId, LineVO line) {
		try {
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
			}
			return publishLine.getLineId();
		} catch (Exception e) {
			log.error("保存线路失败: " + JSON.toJSONString(line), e);
			throw new BaseException("更新线路失败");
		}
	}
}

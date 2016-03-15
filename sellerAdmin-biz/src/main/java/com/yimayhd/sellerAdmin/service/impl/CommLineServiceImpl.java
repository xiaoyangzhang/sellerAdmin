package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.converter.LineConverter;
import com.yimayhd.sellerAdmin.model.line.LineContextConfig;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.repo.CategoryRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.repo.PictureRepo;
import com.yimayhd.sellerAdmin.service.CommLineService;

public class CommLineServiceImpl implements CommLineService {
	private static final int PICTURE_TOP_SIZE = 6;
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private LineRepo lineRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private PictureRepo pictureRepo;

	@Override
	public LineVO getById(long id) {
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = lineRepo.getLineById(id);
		List<ComTagDO> tags = commentRepo.findAllTag(id, TagType.LINETAG);
		return LineConverter.toLineVO(lineResult, tags);
	}

	@Override
	public long publishLine(LineVO line) {
		LinePublishResult publishLine = null;
		long lineId = line.getBaseInfo().getLineId();
		if (lineId > 0) {
			/*
			 * CheckResult checkForSave = LineChecker.checkForUpdate(line); if
			 * (checkForSave.isSuccess()) {
			 */
			LineResult lineResult = lineRepo.getLineById(lineId);
			LinePublishDTO linePublishDTOForUpdate = LineConverter.toLinePublishDTOForUpdate(line, lineResult);
			publishLine = lineRepo.updateLine(linePublishDTOForUpdate);
			/*
			 * } else { throw new BaseException(checkForSave.getMsg()); }
			 */
		} else {
			/*
			 * CheckResult checkForUpdate = LineChecker.checkForSave(line); if
			 * (checkForUpdate.isSuccess()) {
			 */
			LinePublishDTO linePublishDTOForSave = LineConverter.toLinePublishDTOForSave(line);
			publishLine = lineRepo.saveLine(linePublishDTOForSave);
			/*
			 * } else { throw new BaseException(checkForUpdate.getMsg()); }
			 */
		}
		if (publishLine.getLineId() > 0) {
			List<Long> themes = line.getBaseInfo().getThemes();
			commentRepo.addTagRelation(publishLine.getLineId(), TagType.LINETAG, themes, publishLine.getCreateTime());
		}
		return publishLine.getLineId();
	}

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		return lineRepo.pageQueryLine(query);
	}

	@Override
	public LineContextConfig getLineContextConfig() {
		LineContextConfig lineConfig = new LineContextConfig();
		List<ComTagDO> allLineThemes = commentRepo.getAllLineThemes();
		lineConfig.setThemes(allLineThemes);
		return lineConfig;
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
}

package com.yimayhd.sellerAdmin.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.line.tour.TripTraffic;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.enums.LineOwnerType;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.RegionService;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.resourcecenter.model.enums.RegionType;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseTravelController extends BaseController {
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	protected RegionService regionService;
	@Resource
	protected CategoryService categoryService;
	@Autowired
	protected UserRPCService userService;

	protected void initBaseInfo() throws BaseException {
		put("PT_DEFAULT", LineOwnerType.DEFAULT.getType());
		put("PT_MASTER", LineOwnerType.MASTER.getType());
		RepoUtils.requestLog(log, "comCenterServiceRef.selectTagListByTagType", TagType.LINETAG.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.LINETAG.name());
		RepoUtils.resultLog(log, "comCenterServiceRef.selectTagListByTagType", tagResult);
		put("themes", tagResult.getValue());
		put("departRegions", regionService.getRegions(RegionType.DEPART_REGION));
		put("descRegions", regionService.getRegions(RegionType.DESC_REGION));
		put("ways", TripTraffic.ways());
		put("officialPublisher", userService.getUserById(Constant.YIMAY_OFFICIAL_ID));
	}

	protected void initLinePropertyTypes(long categoryId) throws Exception {
		put("categoryId", categoryId);
		List<CategoryValueDO> persionPropertyValues = new ArrayList<CategoryValueDO>();
		CategoryDO category = categoryService.getCategoryDOById(categoryId);
		if (category != null) {
			List<CategoryPropertyValueDO> propertyDOs = category.getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				put("options", 1);
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.PERSON.getType()) {
						put("persionProperty", propertyDO.getCategoryPropertyDO());
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								persionPropertyValues.add(categoryValueDO);
							}
						}
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.TRAVEL_PACKAGE
							.getType()) {
						put("packageProperty", propertyDO.getCategoryPropertyDO());
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.DEPART_DATE.getType()) {
						put("dateProperty", propertyDO.getCategoryPropertyDO());
					}
				}
			} else {
				put("options", 0);
			}
		}
		put("persionPropertyValues", persionPropertyValues);
	}
}

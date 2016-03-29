package com.yimayhd.sellerAdmin.base;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.tour.TripTraffic;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.CommLineService;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseTravelController extends BaseController {
	@Resource
	protected CommLineService commLineService;
	@Autowired
	protected CategoryService categoryService;

	protected void initBaseInfo() throws BaseException {
		put("themes", commLineService.getAllLineThemes());
		put("ways", TripTraffic.ways());
	}

	protected void initLinePropertyTypes(long categoryId) throws Exception {
		put("categoryId", categoryId);
		CategoryDO categoryDO = categoryService.getCategoryDOById(categoryId);
		ItemType itemType = ItemType.get(categoryDO.getCategoryFeature().getItemType());
		put("itemType", itemType);
		LinePropertyConfig lineConfig = commLineService.getLinePropertyConfig(categoryId);
		put("options", lineConfig.getOptions());
		put("persionProperty", lineConfig.getPersionProperty());
		put("packageProperty", lineConfig.getPackageProperty());
		put("dateProperty", lineConfig.getDateProperty());
		put("persionPropertyValues", lineConfig.getPersionPropertyValues());
	}
}

package com.yimayhd.sellerAdmin.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.tour.TripTraffic;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.item.LineService;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseLineController extends BaseController {
	@Resource
	protected LineService commLineService;
	@Autowired
	protected CategoryService categoryService;

	protected void initBaseInfo() throws BaseException {
		WebResult<List<ComTagDO>> allLineThemes = commLineService.getAllLineThemes();
		if (allLineThemes.isSuccess()) {
			put("themes", allLineThemes.getValue());
		}
		put("ways", TripTraffic.ways());
	}

	protected void initLinePropertyTypes(long categoryId) throws Exception {
		CategoryDO categoryDO = categoryService.getCategoryDOById(categoryId);
		ItemType itemType = ItemType.get(categoryDO.getCategoryFeature().getItemType());
		put("itemType", itemType);
		WebResult<LinePropertyConfig> result = commLineService.getLinePropertyConfig(categoryId);
		if (result.isSuccess()) {
			LinePropertyConfig lineConfig = result.getValue();
			put("persionProperty", lineConfig.getPersionProperty());
			put("packageProperty", lineConfig.getPackageProperty());
			put("dateProperty", lineConfig.getDateProperty());
			put("persionPropertyValues", lineConfig.getPersionPropertyValues());
		} else {
			throw new BaseException("获取线路属性配置失败");
		}

	}
}

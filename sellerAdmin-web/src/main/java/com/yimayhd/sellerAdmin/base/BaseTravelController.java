package com.yimayhd.sellerAdmin.base;

import javax.annotation.Resource;

import com.yimayhd.sellerAdmin.model.line.LineContextConfig;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
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

	protected void initBaseInfo() throws BaseException {
		LineContextConfig lineConfig = commLineService.getLineContextConfig();
		put("themes", lineConfig.getThemes());
	}

	protected void initLinePropertyTypes(long categoryId) {
		put("categoryId", categoryId);
		LinePropertyConfig lineConfig = commLineService.getLinePropertyConfig(categoryId);
		put("options", lineConfig.getOptions());
		put("persionProperty", lineConfig.getPersionProperty());
		put("packageProperty", lineConfig.getPackageProperty());
		put("dateProperty", lineConfig.getDateProperty());
		put("persionPropertyValues", lineConfig.getPersionPropertyValues());
	}
}

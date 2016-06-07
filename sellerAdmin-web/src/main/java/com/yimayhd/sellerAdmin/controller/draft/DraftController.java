package com.yimayhd.sellerAdmin.controller.draft;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.model.query.DraftListQuery;

/**
 * 草稿箱
 *
 * @author xiemingna
 *
 */
@Controller
@RequestMapping("/draft")
public class DraftController extends BaseController {

	@RequestMapping(value = "/list")
	public String list(DraftListQuery query) throws Exception {
		// long sellerId = getCurrentUserId();
		// if (sellerId <= 0) {
		// log.warn("未登录");
		// throw new BaseException("请登陆后重试");
		// }
		// WebResult<PageVO<ItemListItemVO>> result =
		// itemService.getItemList(sellerId, query);
		// if (!result.isSuccess()) {
		// throw new BaseException(result.getResultMsg());
		// }
		// put("pageVo", result.getValue());
		// put("itemTypeList", BizItemType.values());
		// put("itemStatusList", BizItemStatus.values());
		// put("query", query);
		return "/system/draft/list";
	}
}

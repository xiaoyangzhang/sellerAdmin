package com.yimayhd.sellerAdmin.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.service.item.ItemService;

/**
 * 商品管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
	@Autowired
	private ItemService itemService;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ItemListQuery query) throws Exception {
		long sellerId = getCurrentUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			throw new BaseException("请登陆后重试");
		}
		WebResult<PageVO<ItemListItemVO>> result = itemService.getItemList(sellerId, query);
		if (!result.isSuccess()) {
			throw new BaseException(result.getResultMsg());
		}
		put("pageVo", result.getValue());
		put("itemTypes", ItemType.values());
		put("query", query);
		return "/system/comm/itemList";
	}
}

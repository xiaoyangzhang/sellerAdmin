package com.yimayhd.sellerAdmin.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
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
		long sellerId = 100;
		PageVO<ItemListItemVO> pageVO = itemService.getItemList(sellerId, query);
		put("pageVo", pageVO);
		put("itemTypes", ItemType.values());
		put("query", query);
		return "/system/comm/list";
	}
}

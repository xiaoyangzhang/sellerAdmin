package com.yimayhd.sellerAdmin.controller.comm;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.service.ItemService;

/**
 * 商品管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
	private ItemService itemService;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ItemListQuery query) throws Exception {
		PageVO<ItemVO> pageVO = itemService.getItemList(query);
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		put("pageVo", pageVO);
		put("itemTypeList", itemTypeList);
		put("commodityList", pageVO.getResultList());
		put("query", query);
		return "/system/comm/list";
	}
}

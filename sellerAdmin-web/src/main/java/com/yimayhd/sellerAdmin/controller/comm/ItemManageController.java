package com.yimayhd.sellerAdmin.controller.comm;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;

/**
 * 商品管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/commodityManage")
public class ItemManageController {

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, CommodityListQuery commodityListQuery) throws Exception {
		PageVO<ItemVO> pageVO = null;// commodityService.getList(commodityListQuery);
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("itemTypeList", itemTypeList);
		model.addAttribute("commodityList", pageVO.getItemList());
		model.addAttribute("commodityListQuery", commodityListQuery);
		return "/system/comm/list";
	}
}

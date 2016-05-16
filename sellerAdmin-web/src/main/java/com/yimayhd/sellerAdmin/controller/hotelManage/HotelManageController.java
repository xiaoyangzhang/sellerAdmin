package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.yimayhd.sellerAdmin.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 酒店管理
 * 
 * @author wangdi
 *
 */
@Controller
@RequestMapping("/hotel")
public class HotelManageController extends BaseController {

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String queryHotelManageList(Model model) throws Exception {
		System.out.println("ddddd");
		return "/system/comm/hotelManage/choicehotel";
	}


}
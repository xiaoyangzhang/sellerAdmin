package com.yimayhd.sellerAdmin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.user.entity.Merchant;
/**
 * 美食
 * 
 * @author zhangxy
 *
 */
@Controller
@RequestMapping("deliciousFood")
public class DeliciousFoodController extends BaseController {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="toDeliciousFoodForm",method=RequestMethod.GET)
	public String toDeliciousFoodForm(Model model) {
		return "system/deliciousFood/addfoodcustom";
		
	}
	
	@RequestMapping(value="saveDeliciousFood",method=RequestMethod.POST)
	public WebResult<String> saveDeliciousFoodForm(Merchant merchant) {
		WebResult<String> result = new WebResult<String>();
		
		return result;
		
	}
}

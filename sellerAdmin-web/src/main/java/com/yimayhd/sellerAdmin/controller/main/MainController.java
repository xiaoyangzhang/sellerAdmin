package com.yimayhd.sellerAdmin.controller.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.user.session.manager.SessionManager;

@RestController
public class MainController {
	@Autowired
	private SessionManager sessionManager;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Model model){
//		UserDO user = sessionManager.getUser(request);
//		long option = user.getOptions() ;
//		boolean isTalent = UserOptions.USER_TALENT.has(option) ;
//		boolean isMerchant = UserOptions.COMMERCIAL_TENANT.has(option) ;
//		if( !isTalent && !isMerchant ){
//			//不是达人、也不是商户
//			return new ModelAndView("redirect:/merchant/toChoosePage");
//		}
		
		return new ModelAndView("/system/home/home");
	}
	
	
}

package com.yimayhd.sellerAdmin.controller.main;

import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.enums.UserOptions;
import com.yimayhd.user.session.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger("MainController");
	@Autowired
	private SessionManager sessionManager;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Boolean home, Model model){

		if(  home != null && home ){
			return new ModelAndView("/system/home/home");
		}
		
		UserDO user = sessionManager.getUser(request);
		long option = user.getOptions() ;
		boolean isTalent = UserOptions.CERTIFICATED.has(option) ;
		boolean isMerchant = UserOptions.COMMERCIAL_TENANT.has(option) ;
		if( !isTalent && !isMerchant ){
			//不是达人、也不是商户
			return new ModelAndView("redirect:/apply/toChoosePage");
		}else if(isTalent){
			return new ModelAndView("redirect:/basicInfo/talent/toAddTalentInfo");
		}else if(isMerchant){
			return new ModelAndView("redirect:/basicInfo/merchant/toAddBasicPage");
		}
		
		return new ModelAndView("/system/home/home");
	}
	
	
}

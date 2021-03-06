package com.yimayhd.sellerAdmin.controller.about;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.sellerAdmin.base.BaseController;

/**
 * 
 * @author wzf
 *
 */
@RestController
@RequestMapping("/about")
public class AboutController extends BaseController {

	
	@RequestMapping(value = "/service", method = RequestMethod.GET) 
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/service");
	}
	
}

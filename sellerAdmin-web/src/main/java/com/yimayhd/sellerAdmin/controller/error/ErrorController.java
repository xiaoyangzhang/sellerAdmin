package com.yimayhd.sellerAdmin.controller.error;


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
@RequestMapping("/error")
public class ErrorController extends BaseController {


	@RequestMapping(value = "/lackPermission", method = RequestMethod.GET) 
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/error/lackPermission");
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET) 
	public ModelAndView notFound(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("/system/error/404");
	}
	
}

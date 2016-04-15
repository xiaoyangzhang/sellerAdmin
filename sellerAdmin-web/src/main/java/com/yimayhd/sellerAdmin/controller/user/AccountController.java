package com.yimayhd.sellerAdmin.controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.UserBiz;
import com.yimayhd.sellerAdmin.checker.UserChecker;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.model.vo.user.ModifyPasswordVo;
import com.yimayhd.sellerAdmin.url.UrlHelper;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.annot.SessionChecker;

/**
 * 
 * @author wzf
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

	@Autowired
	private UserBiz userBiz;
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET) 
	public ModelAndView modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/modifyPassword");
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> modifyPassword(HttpServletRequest request, ModifyPasswordVo modifyPasswordVo) {
		WebResult<String> result = new WebResult<String>();
		WebResultSupport checkResult = UserChecker.checkModifyPasswordPassword(modifyPasswordVo);
		if ( !checkResult.isSuccess() ) {
			result.setWebReturnCode(checkResult.getWebReturnCode());
			return result ;
		}
		long userId = sessionManager.getUserId(request);
		ChangePasswordDTO changePasswordDTO = UserConverter.toModifyPasswordDTO(modifyPasswordVo, userId);
		
		WebResultSupport modifyResult = userBiz.modifyPassword(changePasswordDTO);
		if( modifyResult == null || !modifyResult.isSuccess() ){
			if( modifyResult == null ){
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			}else{
				result.setWebReturnCode(modifyResult.getWebReturnCode());
			}
			return result ;
		}
		String url = UrlHelper.getUrl(rootPath, "/user/modifyPasswordSuccess");
		result.setValue(url);
		return result;
	}
	
	
	@RequestMapping(value = "/modifyPasswordSuccess", method = RequestMethod.GET) 
	public ModelAndView modifyPasswordSuccess(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/modifyPasswordSuccess");
	}
	
}

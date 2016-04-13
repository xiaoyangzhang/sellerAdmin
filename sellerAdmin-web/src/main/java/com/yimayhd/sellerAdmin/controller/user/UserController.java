package com.yimayhd.sellerAdmin.controller.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
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
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.model.User;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.ModifyPasswordVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.url.UrlHelper;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.VerifyCodeManager;
import com.yimayhd.user.session.manager.annot.SessionChecker;

/**
 * 
 * ClassName: LoginController <br/>
 * Description: 用户相关 . <br/>
 * date: 2016年2月15日 下午2:14:54 <br/>
 * 
 * @author zhangjian
 * @version
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	// private static String COOKIE_USER_NAME = "loginName";

	@Autowired
	private UserBiz userBiz;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private VerifyCodeManager verifyCodeManager;
	@Autowired
	private UserService userService;
	
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	
	
//
//	
//
//	@RequestMapping("/toWinLogin")
//	public ModelAndView toWinLogin() {
//		ModelAndView modelAndView = new ModelAndView("/system/user/winLogin");
//
//		return modelAndView;
//
//	}
//
//	
//
//	@RequestMapping("/toRetrieveSuccess")
//	public ModelAndView toRetrieveSuccess() {
//		ModelAndView modelAndView = new ModelAndView("/system/user/retrieveSuccess");
//
//		return modelAndView;
//	}
	

	@RequestMapping(value = "/register", method =RequestMethod.GET)
	public ModelAndView toRegister() {
		ModelAndView modelAndView = new ModelAndView("/system/user/register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public WebResult<String> register(RegisterVo registerVo, HttpServletResponse response) {
		WebResult<String> rs = new WebResult<String>();
		WebResultSupport checkResult = UserChecker.checkRegisterVo(registerVo);
		if ( !checkResult.isSuccess() ) {
			rs.setWebReturnCode(checkResult.getWebReturnCode());
			return rs ;
		}

		RegisterDTO registerDTO = UserConverter.toRegisterDTO(registerVo);
		WebResult<UserDO> registeResult = userBiz.register(registerDTO);
		if( registeResult == null || !registeResult.isSuccess() ){
			rs.setWebReturnCode(registeResult.getWebReturnCode());
			return rs ;
		}
		LoginDTO loginDTO = new LoginDTO() ;
		loginDTO.setMobile(registerVo.getUsername());
		loginDTO.setPassword(registerVo.getPassword());
		loginDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		WebResult<LoginResult> loginResult = userBiz.login(loginDTO);
		if( loginResult == null || !loginResult.isSuccess() || loginResult.getValue() == null || StringUtils.isBlank(loginResult.getValue().getToken()) ){
			rs.setWebReturnCode(loginResult.getWebReturnCode());
			return rs ;
		}
		String token = loginResult.getValue().getToken() ; 
		String url = UrlHelper.getUrl(rootPath, "/user/registerSuccess") ;
		// 登录成功后跳转
		rs.setValue(url);
		// token放到cookie中
		SessionHelper.setCookies(response, token);

		return rs;
	}
	
	@RequestMapping(value="/registerSuccess", method = RequestMethod.GET)
	public ModelAndView toRegisterSuccess(Model model) {
		String loginUrl = UrlHelper.getUrl(rootPath, "/home");
		model.addAttribute("loginUrl", loginUrl);
		ModelAndView modelAndView = new ModelAndView("/system/user/regsuccess");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	@RequestMapping(value = "/login", headers = "Request-Channel=https")
	public ModelAndView toLogin(Model model, String callback) {
		model.addAttribute("callback", callback);
		ModelAndView modelAndView = new ModelAndView("/system/user/login");
		return modelAndView;
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> login(LoginVo loginVo, String callback, HttpServletResponse response) {
		WebResult<String> result = new WebResult<String>();
		WebResultSupport checkResult = UserChecker.checkLogin(loginVo);
		if( !checkResult.isSuccess() ){
			result.setWebReturnCode(checkResult.getWebReturnCode());
			return result ;
		}
		LoginDTO loginDTO = UserConverter.toLoginDTO(loginVo);
		WebResult<LoginResult> loginResult = userBiz.login(loginDTO);
		if( loginResult == null || !loginResult.isSuccess() || loginResult.getValue() == null){
			if( loginResult == null ){
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			}else{
				result.setWebReturnCode(loginResult.getWebReturnCode());
			}
			return result ;
		}
		String token = loginResult.getValue().getToken();
		SessionHelper.setCookies(response, token);
		
		String targetUrl = null ;
//		String returnUrl = get("callback");
		String returnUrl = callback;
		if( StringUtils.isNotBlank(returnUrl) ){
			targetUrl = returnUrl ;
		}else{
			//判断用户身份，进入申请认证页面
			targetUrl = UrlHelper.getUrl(rootPath, "/home");
		}
		result.setValue(targetUrl);
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		sessionManager.removeToken(request);
		SessionHelper.cleanCookies(response);
		String url = UrlHelper.getUrl(true, rootPath, "/user/login") ;
		return new ModelAndView(url);
	}

	@RequestMapping(value = "/sendRegisterVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport sendRegisterVerifyCode(String username, String imageCode) {
		WebResultSupport result = new WebResultSupport();

		if (StringUtils.isBlank(imageCode) || !verifyCodeManager.checkVerifyCode(imageCode)) {
			result.setWebReturnCode(WebReturnCode.IMAGE_VERIFY_CODE_ERROR);
			return result;
		}
		result = userBiz.sendRegisterVerifyCode(username);
		return result;
	}

	@RequestMapping(value = "/sendRetrievePasswordVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport sendRetrievePasswordVerifyCode(String username, String imageCode) {
		WebResultSupport result = new WebResultSupport();
//		if (!isTest()) { // 压力测试不校验
			if (StringUtils.isBlank(imageCode) || !verifyCodeManager.checkVerifyCode(imageCode)) {
				result.setWebReturnCode(WebReturnCode.IMAGE_VERIFY_CODE_ERROR);
				return result;
			}
//		}
		result = userBiz.sendRetrievePasswordVerifyCode(username);
		return result;
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET) 
	@SessionChecker
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
	@SessionChecker
	public ModelAndView modifyPasswordSuccess(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/modifyPasswordSuccess");
	}
	

	@RequestMapping(value="/retrievePassword", method = RequestMethod.GET)
	public ModelAndView toLostPassword() {
		ModelAndView modelAndView = new ModelAndView("/system/user/retrievePassword");
		return modelAndView;
	}
	
	@RequestMapping(value = "/retrievePassword", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> retrievePassword(RetrievePasswordVo retrievePasswordVo) {
		WebResult<String> result = new WebResult<String>();
		WebResultSupport checkFeedBack = UserChecker.checkRetrievePassword(retrievePasswordVo);
		if (checkFeedBack == null || !checkFeedBack.isSuccess()) {
			result.setWebReturnCode(checkFeedBack.getWebReturnCode());
			return result;
		}
		
		RevivePasswordDTO revivePasswordDTO = UserConverter.toRevivePasswordDTO(retrievePasswordVo);
		WebResultSupport retrieveResult = userBiz.retrievePassword(revivePasswordDTO);
		if( retrieveResult == null ){
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}else if( !retrieveResult.isSuccess() ){
			result.setWebReturnCode(retrieveResult.getWebReturnCode());
		}else{
			String url =  UrlHelper.getUrl(rootPath, "/user/retrievePasswordSuccess") ;
			result.setValue(url);
		}
		return result;
	}
	
	@RequestMapping(value="/retrievePasswordSuccess", method = RequestMethod.GET)
	public ModelAndView toRetrievePasswordSuccess(Model model) {
		String loginUrl = UrlHelper.getUrl(rootPath, "/user/login");
		model.addAttribute("loginUrl", loginUrl);
		ModelAndView modelAndView = new ModelAndView("/system/user/retrievePasswordSuccess");
		return modelAndView;
	}

	@RequestMapping(value = "/getImgCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) {
		verifyCodeManager.writeVerifyCode(request, response);
	}
	
	/**
	 * 判断用户昵称是否存在
	 * @return
	 */
	@RequestMapping(value= "/chargeUserNickName")
	public WebResultSupport chargeUserNickName(String nickName){
		WebResultSupport webResult = new WebResultSupport();
			
	    BaseResult<List<UserDO>> result = userService.getUserByNickname(nickName.trim());
	   
	    if(result.isSuccess()){
	    	if(result.getValue().size()<1){
	    		webResult.isSuccess();
	    	}else{
	    		webResult.setWebReturnCode(WebReturnCode.USER_NICKNAME_EXIT);
	    	}
		}else{
			webResult.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return webResult;
	}
	
	@RequestMapping(value = "/getUserByMobile", method = RequestMethod.GET)
	@ResponseBody
	public WebResult<UserDO> getUserByMobile(String mobile) {
		UserDO userDO = userBiz.getUserByMobile(mobile);
		WebResult<UserDO> result = new WebResult<UserDO>() ;
		result.setValue(userDO);
		return result;
	}

}

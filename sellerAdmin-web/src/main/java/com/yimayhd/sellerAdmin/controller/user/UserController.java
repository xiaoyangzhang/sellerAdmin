package com.yimayhd.sellerAdmin.controller.user;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.UserBiz;
import com.yimayhd.sellerAdmin.checker.UserChecker;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;

import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;

import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.LoginDTO;
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
	
	

	@RequestMapping(value = "/toLogin")
//	@RequestMapping(value = "/toLogin", headers = "Request-Channel=https")
	public ModelAndView toLogin() {
		ModelAndView modelAndView = new ModelAndView("/system/user/login");
		return modelAndView;
	}

	@RequestMapping("/toRegister")
	public ModelAndView toRegister() {
		ModelAndView modelAndView = new ModelAndView("/system/user/register");

		return modelAndView;

	}

	@RequestMapping("/toWinLogin")
	public ModelAndView toWinLogin() {
		ModelAndView modelAndView = new ModelAndView("/system/user/winLogin");

		return modelAndView;

	}

	@RequestMapping("/toRetrievePassword")
	public ModelAndView toLostPassword() {
		ModelAndView modelAndView = new ModelAndView("/system/user/retrievePassword");

		return modelAndView;

	}

	@RequestMapping("/toRegisterSuccess")
	public ModelAndView toRegisterSuccess() {
		ModelAndView modelAndView = new ModelAndView("/system/user/regsuccess");

		return modelAndView;
	}

	@RequestMapping("/toRetrieveSuccess")
	public ModelAndView toRetrieveSuccess() {
		ModelAndView modelAndView = new ModelAndView("/system/user/retrieveSuccess");

		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public WebResult<String> register(RegisterVo registerVo, HttpServletResponse response) {
		WebResult<String> rs = new WebResult<String>();

		WebResult<UserDO> registeResult = userBiz.register(registerVo);
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
		// 登录成功后跳转
		rs.setValue("/user/toRegisterSuccess");
		// token放到cookie中
		SessionHelper.setCookies(response, token);

		return rs;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> login(LoginVo loginVo, HttpServletResponse response) {
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
		String returnUrl = get("callback");
		if( StringUtils.isNotBlank(returnUrl) ){
			targetUrl = returnUrl ;
		}else{
			//判断用户身份，进入申请认证页面
			targetUrl = "http://localhost:8080/sellerAdmin/main";
		}
		result.setValue(targetUrl);
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		sessionManager.removeToken(request);
		SessionHelper.cleanCookies(response);
		return new ModelAndView("redirect:" + Constant.LOGIN_URL);
	}

	@RequestMapping(value = "/sendRegisterVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport sendRegisterVerifyCode(String username, String imageCode) {
		WebResultSupport result = new WebResultSupport();

//		if ( !isTest() ) { // 压力测试不校验
			if (StringUtils.isBlank(imageCode) || !verifyCodeManager.checkVerifyCode(imageCode)) {
				result.setWebReturnCode(WebReturnCode.IMAGE_VERIFY_CODE_ERROR);
				return result;
			}
//		}
		result = userBiz.sendRegisterVerifyCode(username);
		return result;
	}

	@RequestMapping(value = "/sendRetrievePasswordVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport sendRetrievePasswordVerifyCode(String username, String imageCode) {
		WebResultSupport result = new WebResultSupport();
		if (!isTest()) { // 压力测试不校验
			if (StringUtils.isBlank(imageCode) || !verifyCodeManager.checkVerifyCode(imageCode)) {
				result.setWebReturnCode(WebReturnCode.IMAGE_VERIFY_CODE_ERROR);
				return result;
			}
		}
		result = userBiz.sendRetrievePasswordVerifyCode(username);
		return result;
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET) 
	@SessionChecker
	public ModelAndView modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		UserDO user = sessionManager.getUser(request);
		request.setAttribute("user", user);
		return new ModelAndView("/system/user/modifyPassword");
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport modifyPassword(String username, String imageCode) {
		WebResultSupport result = new WebResultSupport();
		result = userBiz.sendRetrievePasswordVerifyCode(username);
		return result;
	}
//
//
//	@RequestMapping(value = "/sendRetrievePasswordVerifyCode", method = RequestMethod.POST)
//	public WebResult<String> sendRetrievePasswordVerifyCode(RetrievePasswordVo retrievePasswordVo, String imageCode) {
//		WebResult<String> result = new WebResult<String>();
//		try {
//			if (!(AppMode.STEST.equals(webConfig.getMode()))) {
//				if (StringUtils.isBlank(imageCode) || verifyCodeManager.checkVerifyCode(imageCode) == false) {
//					result.initFailure(WebErrorCode.InvalidImageCode);
//					return result;
//				}
//			}
//
//			userService.sendRetrievePasswordVerifyCode(retrievePasswordVo);
//			result.initSuccess();
//		} catch (WebException e) {
//			log.error(e.getMessage(), e);
//			result.initFailure(e);
//		} catch (Exception e) {
//			log.error("发送短信验证码失败");
//			log.error(e.getMessage(), e);
//			result.initFailure(WebErrorCode.Error, e.getMessage());
//		}
//
//		return result;
//	}
//
	@RequestMapping(value = "/retrievePassword", method = RequestMethod.POST)
	@ResponseBody
	public WebResultSupport retrievePassword(RetrievePasswordVo retrievePasswordVo) {
		WebResultSupport checkFeedBack = UserChecker.checkRetrievePassword(retrievePasswordVo);
		if (checkFeedBack == null || !checkFeedBack.isSuccess()) {
			return checkFeedBack;
		}
		
		RevivePasswordDTO revivePasswordDTO = UserConverter.toRevivePasswordDTO(retrievePasswordVo);
		WebResultSupport result = userBiz.retrievePassword(revivePasswordDTO);
		return result;
	}
//
//	@RequestMapping(value = "/isExistedMobile/{mobile}", method = RequestMethod.POST)
//	public WebResultSupport isExistedMobile(String mobile) {
//		try {
//			UserVo userVo = userService.getUserByMobile(mobile);
//			if (userVo != null) {
//				return success("用户已经存在");
//			}
//		} catch (WebException e) {
//			log.error(e.getMessage(), e);
//			return error(e, e.getMessage());
//		} catch (Exception e) {
//			log.error("查询用户失败");
//			log.error(e.getMessage(), e);
//			return error("查询用户失败");
//		}
//
//		return error("用户不存在");
//	}
//
	@RequestMapping(value = "/getImgCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) {
		verifyCodeManager.writeVerifyCode(request, response);
	}
//
//	/**
//	 * @RequestMapping("/validateImgCode") public WebResult
//	 * <String> validateImgCode(HttpServletRequest request) {
//	 * 
//	 * String vericode = request.getParameter("param"); log.info(
//	 * "get the imgcode input :" + vericode);
//	 * 
//	 * WebResult<String> verifyResult = new WebResult<String>(); if
//	 * (!StringUtils.isBlank(vericode) &&
//	 * verifyCodeManager.checkVerifyCode(vericode)) {
//	 * verifyResult.initSuccess();
//	 * 
//	 * } else { verifyResult.initFailure(WebErrorCode.InvalidImageCode); }
//	 * 
//	 * return verifyResult; }
//	 */
//	private void setCookies(HttpServletResponse response, String token) {
//		if (StringUtils.isBlank(token)) {
//			return;
//		}
//		Cookie cookie = new Cookie(Constant.TOKEN_SERVER, token);
////		cookie.setDomain(WebResourceConfigUtil.getDomain());
//		cookie.setHttpOnly(true);
//		cookie.setPath("/");
//
//		String token2 = UUID.randomUUID().toString();
//		Cookie cookie2 = new Cookie(Constant.TOKEN_CLIENT, token2);
////		cookie2.setDomain(WebResourceConfigUtil.getDomain());
//		cookie2.setPath("/");
//
//		response.addCookie(cookie);
//		response.addCookie(cookie2);
//	}
//
//	private void cleanCookies(HttpServletResponse response) {
//		Cookie cookie = new Cookie(Constant.TOKEN_SERVER, null);
//		cookie.setDomain(WebResourceConfigUtil.getDomain());
//		cookie.setMaxAge(0);
//		cookie.setPath("/");
//
//		Cookie cookie2 = new Cookie(Constant.TOKEN_CLIENT, null);
//		cookie2.setDomain(WebResourceConfigUtil.getDomain());
//<<<<<<< HEAD
//		cookie2.setPath("/");
//
//		response.addCookie(cookie);
//		response.addCookie(cookie2);
//	}
//
//	private void cleanCookies(HttpServletResponse response) {
//		Cookie cookie = new Cookie(Constant.TOKEN_SERVER, null);
//		cookie.setDomain(WebResourceConfigUtil.getDomain());
//		cookie.setMaxAge(0);
//		cookie.setPath("/");
//
//		Cookie cookie2 = new Cookie(Constant.TOKEN_CLIENT, null);
//		cookie2.setDomain(WebResourceConfigUtil.getDomain());
//		cookie2.setMaxAge(0);
//		cookie2.setPath("/");
//
//		// Cookie usernameCookie = new Cookie(COOKIE_USER_NAME, null);
//		// cookie2.setMaxAge(0);
//		// cookie2.setPath("/");
//
//		response.addCookie(cookie);
//		response.addCookie(cookie2);
//		// response.addCookie(usernameCookie);
//
//	}
	
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
//=======
//		cookie2.setMaxAge(0);
//		cookie2.setPath("/");
//
//		// Cookie usernameCookie = new Cookie(COOKIE_USER_NAME, null);
//		// cookie2.setMaxAge(0);
//		// cookie2.setPath("/");
//
//		response.addCookie(cookie);
//		response.addCookie(cookie2);
//		// response.addCookie(usernameCookie);
//
//	}
//>>>>>>> 8564a582abb0c4b4d0087e8b3179c6c20b755105

}

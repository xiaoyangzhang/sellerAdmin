package com.yimayhd.sellerAdmin.controller.user;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.validate.CodeUtil;
import com.yimayhd.sellerAdmin.validate.IPUtil;
import com.yimayhd.sellerAdmin.validate.ValidateCode;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.constant.SessionConstant;
import com.yimayhd.user.session.manager.enums.TokenType;
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
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.VerifyCodeManager;

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
	@Autowired
	private CacheManager cacheManager ;

	@Value("${sellerAdmin.rootPath}")
	private String rootPath;


	@RequestMapping(value = "/register", method =RequestMethod.GET)
	public ModelAndView toRegister() {
		ModelAndView modelAndView = new ModelAndView("/system/user/register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public WebResult<String> register(RegisterVo registerVo, HttpServletRequest request, HttpServletResponse response) {
		WebResult<String> rs = new WebResult<String>();
		//选中会员
		if (registerVo.getVIP()) {

			String url = UrlHelper.getUrl(rootPath, "/user/developing") ;
//			try {
//				response.sendRedirect(url);
//			} catch (IOException e) {
//				log.error("redirect error");
//				url = UrlHelper.getUrl(rootPath, "user/register") ;
//				rs.setValue(url);
//				return rs;
//			}
			rs.setValue(url);
			return rs;
		}
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
		/*SessionHelper.setCookies(response, token);*/
		setCookies(response,request, token);
		return rs;
	}

	@RequestMapping(value="/registerSuccess", method = RequestMethod.GET)
	public ModelAndView toRegisterSuccess(Model model) {
		String loginUrl = UrlHelper.getUrl(rootPath, "/home");
		model.addAttribute("loginUrl", loginUrl);
		ModelAndView modelAndView = new ModelAndView("/system/user/regsuccess");
		return modelAndView;
	}
	@RequestMapping(value="/developing", method = RequestMethod.GET)
	public ModelAndView teveloping(Model model) {
//		String loginUrl = UrlHelper.getUrl(rootPath, "/home");
//		model.addAttribute("loginUrl", loginUrl);
		ModelAndView modelAndView = new ModelAndView("/system/user/developing");
		return modelAndView;
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	@RequestMapping(value = "/login", headers = "Request-Channel=https")
	public ModelAndView toLogin(Model model, String callback,HttpServletRequest request) {
		model.addAttribute("callback", callback);
		model.addAttribute("isPop", String.valueOf(checkPopVerifyCode(request)));
		ModelAndView modelAndView = new ModelAndView("/system/user/login");
		return modelAndView;
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> login(LoginVo loginVo, String callback, HttpServletResponse response, HttpServletRequest request) {
		WebResult<String> result = new WebResult<String>();
		Map<String,String> map = new HashMap<String,String>();
		WebResultSupport checkResult = UserChecker.checkLogin(loginVo);
		if( !checkResult.isSuccess() ){
			result.setWebReturnCode(checkResult.getWebReturnCode());
			return result ;
		}
		/**需要验证码验证**/
		if(checkPopVerifyCode(request)&&!getVerifyCode(request).equals(loginVo.getImageCode())){
			result.setWebReturnCode(WebReturnCode.IMAGE_VERIFY_CODE_ERROR);
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
			failUserLogin(request);//登录失败记录,记录次数
			result.setValue(String.valueOf(checkPopVerifyCode(request)));
			return result ;
		}

		LoginResult loginResultValue = loginResult.getValue();
		long userId = loginResultValue.getValue().getId();
		String token = loginResultValue.getToken();
		//SessionHelper.setCookies(response, token);
		setCookies(response,request, token);
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
		//result.setValue(JSON.toJSONString(map));
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		sessionManager.removeToken(request);
		cleanCookies(response);
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


//	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
//	@ResponseBody
//	public WebResult<String> modifyPassword(HttpServletRequest request, ModifyPasswordVo modifyPasswordVo) {
//		WebResult<String> result = new WebResult<String>();
//		WebResultSupport checkResult = UserChecker.checkModifyPasswordPassword(modifyPasswordVo);
//		if ( !checkResult.isSuccess() ) {
//			result.setWebReturnCode(checkResult.getWebReturnCode());
//			return result ;
//		}
//		long userId = sessionManager.getUserId(request);
//		ChangePasswordDTO changePasswordDTO = UserConverter.toModifyPasswordDTO(modifyPasswordVo, userId);
//
//		WebResultSupport modifyResult = userBiz.modifyPassword(changePasswordDTO);
//		if( modifyResult == null || !modifyResult.isSuccess() ){
//			if( modifyResult == null ){
//				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
//			}else{
//				result.setWebReturnCode(modifyResult.getWebReturnCode());
//			}
//			return result ;
//		}
//		String url = UrlHelper.getUrl(rootPath, "/user/modifyPasswordSuccess");
//		result.setValue(url);
//		return result;
//	}
//	@RequestMapping(value = "/modifyPasswordSuccess", method = RequestMethod.GET)
//	@SessionChecker
//	public ModelAndView modifyPasswordSuccess(HttpServletRequest request, HttpServletResponse response) {
//		return new ModelAndView("/system/user/modifyPasswordSuccess");
//	}
//

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

	@RequestMapping(value = "/getPerfectImgCode")
	public void getPerfectImgCode(HttpServletRequest request, HttpServletResponse response) {
		writeVerifyCode(request, response);
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

	/**
	 * @param mobile
	 * 此接口已移到 palace 项目下 UserController
	 * @author hongfei.guo 	2016-07-11
	 * @return
	 */
	/**
	@RequestMapping(value = "/getUserByMobile", method = RequestMethod.GET)
	@ResponseBody
	public WebResult<UserDO> getUserByMobile(String mobile) {
		UserDO userDO = userBiz.getUserByMobile(mobile);
		WebResult<UserDO> result = new WebResult<UserDO>() ;
		result.setValue(userDO);
		return result;
	}
	**/

	private static final String TOKEN_SERVER = "token";
	private static final String TOKEN_CLIENT = "token2";

	private void setCookies(HttpServletResponse response, HttpServletRequest request, String token) {
		if (StringUtils.isBlank(token)) {
			return;
		}
		Cookie cookie = new Cookie(TOKEN_SERVER, token);
		String domain = WebResourceConfigUtil.getDomain() ;
		String serverName = request.getServerName();
		if( StringUtils.isNoneBlank(domain) && serverName.contains(domain) ){
			cookie.setDomain(domain);
		}
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		String token2 = UUID.randomUUID().toString();
		Cookie cookie2 = new Cookie(TOKEN_CLIENT, token2);
		if( StringUtils.isNoneBlank(domain) && serverName.contains(domain) ){
			cookie2.setDomain(domain);
		}
		cookie2.setPath("/");

		response.addCookie(cookie);
		response.addCookie(cookie2);
	}

	private void cleanCookies(HttpServletResponse response) {
		Cookie cookie = new Cookie(TOKEN_SERVER, null);
		cookie.setDomain(WebResourceConfigUtil.getDomain());
		cookie.setMaxAge(0);
		cookie.setPath("/");

		Cookie cookie2 = new Cookie(TOKEN_CLIENT, null);
		cookie2.setDomain(WebResourceConfigUtil.getDomain());
		cookie2.setMaxAge(0);
		cookie2.setPath("/");

		// Cookie usernameCookie = new Cookie(COOKIE_USER_NAME, null);
		// cookie2.setMaxAge(0);
		// cookie2.setPath("/");

		response.addCookie(cookie);
		response.addCookie(cookie2);
		// response.addCookie(usernameCookie);

	}

	/**
	 * 验证频繁登录 (限制ip恶意访问)
	 * @param request
	 * @return true:频繁登录;false 登录状态正常
     */
	public boolean checkFrequentLgoinIP(HttpServletRequest request){
		int max_nm = Integer.valueOf(WebResourceConfigUtil.getLoginCheckIPCount()) ;
		String srcIp = request.getHeader(Constant.CDN_SRC_IP);//访问者ip
		if(StringUtils.isBlank(srcIp)){
			srcIp = request.getRemoteAddr();
		}
		Object obj = cacheManager.getFormTair(Constant.LOGIN_FQ_IP_KEY_+srcIp);
		int count = obj==null?0:(Integer)obj;
		//失效时间默认十分钟,再次频繁登录时间为-1
		cacheManager.incr(Constant.LOGIN_FQ_IP_KEY_+srcIp, 1 , count==0?Constant.LOGIN_COUNT_EXPIRETIME:-1);
		log.info("ip={},count={}",srcIp,count);
		if(count>max_nm){
			log.info("此机器登录过于频繁,ip={},count={}",srcIp,count);
			return true;
		}
		return false;
	}


	/**
	 * 登录失败,记录次数,失效时间3小时
	 * @param request
     */
	public void failUserLogin(HttpServletRequest request){
		String ip_key = getLoginFqIpKey(request);
		int exp_time =setUserLoginIPTime(request);
		int num= cacheManager.incr(ip_key, 1 , exp_time);
		log.info("ip_key={},num={},exp_time={}",ip_key,num,exp_time);
	}


	/**
	 * 登录IP 打标时间戳
	 * @param request
	 * @return
     */
	public int setUserLoginIPTime(HttpServletRequest request){
		String key = getLoginIpTimeTempKey(request);
		Object obj = cacheManager.getFormTair(key);
		if(obj==null){
			int exp_time = DateUtil.getCurrentTimeStamp()+Constant.LOGIN_COUNT_EXPIRETIME;
			cacheManager.addToTair(key,exp_time,exp_time);
			return exp_time;
		}

		return (Integer)obj;
	}

	/**
	 * 正确登录删除时间戳
	 *
	 * @param request
     */
	public void delUserLoginIPTime(HttpServletRequest request){
		String key = getLoginIpTimeTempKey(request);
		int time = DateUtil.getCurrentTimeStamp();
		cacheManager.deleteFromTair(key);
	}


	/**
	 * 是否弹出验证码 true:弹出;false 不弹出
	 * @param request
	 * @return
     */
	public boolean checkPopVerifyCode(HttpServletRequest request){
		int max_nm = 0 ;
		try{
			max_nm=Integer.valueOf(WebResourceConfigUtil.getLoginCheckIPCount());
		}catch (Exception e){
			max_nm=0;
		}
		String ip_key = getLoginFqIpKey(request);
		Object obj = cacheManager.getFormTair(ip_key);
		int count = obj==null?0:(Integer)obj;
		log.info("ip_key={},count={}",ip_key,count);
		if(count>=max_nm){
			log.info("此机器登录过于频繁,ip_key={},count={}",ip_key,count);
			return true;
		}
		return false;
	}

	/**
	 * 限制用户登录最大次数
	 * @param userId
	 * @param request
     * @return
     */
	public boolean checkFrequentLgoinUserId(long userId ,HttpServletRequest request){
		int max_nm = Integer.valueOf(WebResourceConfigUtil.getLoginCheckUserCount()) ;
		Object obj = cacheManager.getFormTair(Constant.LOGIN_FQ_USER_KEY_+userId);
		int count = obj==null?0:(Integer)obj;
		//失效时间默认十分钟,再次频繁登录时间为-1
		cacheManager.incrFree(Constant.LOGIN_FQ_USER_KEY_+userId, 1 , 0,count==0?Constant.LOGIN_COUNT_EXPIRETIME:-1);
		log.info("userId={},count={}",userId,count);
		if(count>max_nm){
			log.info("此用户登录过于频繁,userId={},count={}",userId,count);
			return true;
		}
		return false;
	}




	/**
	 * 频繁登录,验证验证码是否一致
	 * @param verifyCode
	 * @param request
     * @return
     */
	public boolean checkVerifyCode(String verifyCode,HttpServletRequest request){
		if(StringUtils.isBlank(verifyCode)){
			log.error("verifyCode is null");
			return false;
		}
		String token = SessionHelper.getTokenFromCookie(request, TokenType.VERIFY_CODE) ;
		String key = getVerifyCodeKey(token);
		Object obj = cacheManager.getFormTair(key);
		String cache_verifyCode = obj==null?"":(String)obj;
		if(!cache_verifyCode.equals(verifyCode)){
			log.error("check code error ,verifyCode={},cache_verifyCode={}",verifyCode,cache_verifyCode);
			return false;
		}
		return true;
	}




	/**
	 * 输出验证码
	 */
	public void writeVerifyCode(HttpServletRequest request, HttpServletResponse reponse){
		// 设置响应的类型格式为图片格式
		reponse.setContentType("image/jpeg");
		// 禁止图像缓存。
		reponse.setHeader("Pragma", "no-cache");
		reponse.setHeader("Cache-Control", "no-cache");
		reponse.setDateHeader("Expires", 0);

		String code = CodeUtil.getNumberCode(4);
		ValidateCode validateCode = new ValidateCode(code);
		String token = SessionHelper.getTokenFromCookie(request, TokenType.VERIFY_CODE) ;
		if( token == null ){
			token = CodeUtil.getToken();
			Cookie cookie = new Cookie(TokenType.VERIFY_CODE.getKey(), token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(Constant.TOKEN_EXPIRE_TIME);
			reponse.addCookie(cookie);
		}
		addVerifyCode(token, code);
		try {
			validateCode.write(reponse.getOutputStream());
		} catch (IOException e) {
			log.error("writ verify code failed!  code={}, token={}",code, token, e);
		}
	}

	public boolean addVerifyCode(String token, String verifyCode) {
		String key = getVerifyCodeKey(token);
		boolean addResult = cacheManager.addToTair(key, verifyCode, Constant.TOKEN_EXPIRE_TIME);
		return addResult;
	}

	/**
	 * 获取验证码
	 * @param request
	 * @return
     */
	public String getVerifyCode(HttpServletRequest request){
		String token = SessionHelper.getTokenFromCookie(request, TokenType.VERIFY_CODE) ;
		String key = getVerifyCodeKey(token);
		Object obj = cacheManager.getFormTair(key);
		if(obj==null){
			return "";
		}
		return (String)obj;
	}

	/**
	 * 验证码key
	 * @param token
	 * @return
     */
	private String getVerifyCodeKey(String token) {
		return SessionConstant.SESSION_KEY_PREFIX + "code_"+ token;
	}

	/**
	 * 获取当前登录用户Ip key
	 * @param request
	 * @return
	 */
	public  String getLoginFqIpKey(HttpServletRequest request)  {
		String srcIp="";
		try{
			srcIp = request.getHeader(Constant.CDN_SRC_IP);//访问者ip
			log.info("srcIp1="+srcIp);
			if(StringUtils.isBlank(srcIp)){
				//srcIp = request.getRemoteAddr();
				srcIp=IPUtil.getIpAddr(request);

			}
			log.info("登录ip={}",srcIp);

		}catch (Exception e){
			log.error("获取IP信息异常,",e);
		}
		return Constant.LOGIN_FQ_IP_KEY_+srcIp;
	}

	/**
	 * 登录时间戳 key
	 * @param request
	 * @return
     */
	public  String getLoginIpTimeTempKey(HttpServletRequest request){
		String srcIp = request.getHeader(Constant.CDN_SRC_IP);//访问者ip
		if(StringUtils.isBlank(srcIp)){
			srcIp = request.getRemoteAddr();
		}
		log.info("登录ip={}",srcIp);
		return Constant.LOGIN_IP_TIME_TEMP_+srcIp;
	}



}

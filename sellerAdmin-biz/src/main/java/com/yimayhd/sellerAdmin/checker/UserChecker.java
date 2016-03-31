package com.yimayhd.sellerAdmin.checker;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.util.CheckUtils;

/**  
 * 
 * @author wzf
 *
 */
public class UserChecker {
	
	public static WebResultSupport checkLogin(LoginVo loginVo){
		WebResultSupport result = new WebResultSupport() ;
		if( loginVo == null || StringUtils.isBlank(loginVo.getUsername()) ){
			result.setWebReturnCode(WebReturnCode.USERNAME_EMPTY);
			return result;
		}else if( StringUtils.isBlank(loginVo.getPassword()) ){
			result.setWebReturnCode(WebReturnCode.PASSWORD_EMPTY);
			return result;
		}
		return result;
	} 
//	public static CheckResult checkLoginVo(LoginVo loginVo){
//		
//		if(loginVo.getUsername() == null || StringUtils.isBlank(loginVo.getUsername())){
//			return CheckResult.error("手机号码不能为空");
//		}
//		return CheckResult.success();
//	} 
	
	public static WebCheckResult checkRegisterVo(RegisterVo registerVo){
		if(registerVo.getUsername() == null || StringUtils.isBlank(registerVo.getUsername())){
			return WebCheckResult.error("手机号码不能为空");
		}
		
		if(registerVo.getPassword() == null || StringUtils.isBlank(registerVo.getPassword())){
			return WebCheckResult.error("密码不能为空");
		}
		
		if(!CheckUtils.isMobileNO(registerVo.getUsername())){
			return WebCheckResult.error("手机号码格式不正确");
		}
		
		
		return WebCheckResult.success();
	}

	public static WebResultSupport checkRetrievePassword(RetrievePasswordVo retrievePasswordVo){
		WebResultSupport result = new WebResultSupport() ;
		if( retrievePasswordVo == null || StringUtils.isBlank(retrievePasswordVo.getUsername()) ){
			result.setWebReturnCode(WebReturnCode.USERNAME_EMPTY);
			return result;
		}else if( !CheckUtils.isMobileNO(retrievePasswordVo.getUsername()) ){
			result.setWebReturnCode(WebReturnCode.MOBILE_FORMAT_ERROR);
			return result;
		}else if( StringUtils.isBlank(retrievePasswordVo.getPassword()) ){
			result.setWebReturnCode(WebReturnCode.PASSWORD_EMPTY);
			return result;
		}
		return result;
	}
	public static WebCheckResult checkRetrievePasswordVo(RetrievePasswordVo retrievePasswordVo){
		if(retrievePasswordVo.getUsername() == null || StringUtils.isBlank(retrievePasswordVo.getUsername())){
			return WebCheckResult.error("手机号码不能为空");
		}
		
		if(!CheckUtils.isMobileNO(retrievePasswordVo.getUsername())){
			return WebCheckResult.error("手机号码格式不正确");
		}
		
		return WebCheckResult.success();
	}
}
  

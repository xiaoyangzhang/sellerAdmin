package com.yimayhd.sellerAdmin.checker;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.ModifyPasswordVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.util.CheckUtils;
import com.yimayhd.sellerAdmin.util.PhoneUtil;

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
	
	public static WebResultSupport checkRegisterVo(RegisterVo registerVo){
		WebResultSupport result = new WebResultSupport() ;
		if(registerVo == null){
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result ;
		}
		
		String mobile = registerVo.getUsername() ;
		if(StringUtils.isBlank( mobile )){
			result.setWebReturnCode(WebReturnCode.USERNAME_EMPTY);
			return result ;
		}
		if( !PhoneUtil.isMobileNumber(mobile) ){
			result.setWebReturnCode(WebReturnCode.MOBILE_FORMAT_ERROR);
			return result ;
		}
		
		String password = registerVo.getPassword() ;
		if( StringUtils.isBlank(password)){
			result.setWebReturnCode(WebReturnCode.PASSWORD_EMPTY);
			return result ;
		}
		return result;
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
	public static WebResultSupport checkModifyPasswordPassword(ModifyPasswordVo modifyPasswordVo){
		WebResultSupport result = new WebResultSupport() ;
		if( modifyPasswordVo == null || StringUtils.isBlank(modifyPasswordVo.getOldPassword()) ){
			result.setWebReturnCode(WebReturnCode.OLD_PASSWORD_EMPTY);
			return result;
		}else if( StringUtils.isBlank(modifyPasswordVo.getNewPassword() ) ){
			result.setWebReturnCode(WebReturnCode.NEW_PASSWORD_EMPTY);
			return result;
		}else if( modifyPasswordVo.getNewPassword().equals(modifyPasswordVo.getOldPassword()) ){
			result.setWebReturnCode(WebReturnCode.NEW_OLD_PASSWORD_EQUAL);
			return result;
		}
		return result;
	}
}
  

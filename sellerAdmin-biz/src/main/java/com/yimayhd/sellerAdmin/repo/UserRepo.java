package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.dto.VerifyCodeDTO;
import com.yimayhd.user.client.enums.security.RevivePasswordStep;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.ResultSupport;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserSecurityService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.entity.UserContact;
import com.yimayhd.user.errorcode.UserServiceHttpCode;

/**
 * 
 * @author wzf
 *
 */
public class UserRepo {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private UserService userServiceRef;

	@Resource
	private UserSecurityService userSecurityServiceRef;
	

	/**
	 * 通过ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@MethodLogger
	public UserDO getUser(long userId) {
		if (userId <= 0) {
			return null;
		}
		return userServiceRef.getUserDOById(userId);
	}

	/**
	 * 获取联系人
	 * 
	 * @param userId
	 * @return
	 */
	@MethodLogger(isPrintResult=false)
	public WebResult<List<UserContact>> getUserContactsById(long userId){
		WebResult<List<UserContact>> webResult = new WebResult<List<UserContact>>() ;
		if (userId <= 0) {
			webResult.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return webResult ;
		}
		BaseResult<List<UserContact>> result = userServiceRef.getUserContactsById(userId);
		if( result == null || !result.isSuccess() ){
			webResult.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return webResult ;
		}
		webResult.setValue(result.getValue());
		return webResult;
	}

	/**
	 * 
	 * @param loginDTO
	 * @return
	 */
	@MethodLogger
	public WebResult<LoginResult> login(LoginDTO loginDTO) {
		WebResult<LoginResult> result = new WebResult<LoginResult>();
		LoginResult loginResult = userServiceRef.loginV3(loginDTO);
		if( loginResult == null || !loginResult.isSuccess() ){
			log.error("login failed!  LoginDTO={}, LoginResult={}", JSON.toJSONString(loginDTO), JSON.toJSONString(loginResult) );
			if( loginResult == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = loginResult.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.USER_LOCKED_TO_MANY_TIMES_FAILED.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.USER_LOCKED_TO_MANY_TIMES_FAILED);
				}else if( UserServiceHttpCode.USERNAME_OR_PASSWORD_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.USERNAME_OR_PASSWORD_ERROR);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}
		result.setValue(loginResult);
		return result;
	}

	/**
	 * 
	 * @param registerDTO
	 * @return
	 */
	@MethodLogger
	public WebResult<UserDO> register(RegisterDTO registerDTO){
		WebResult<UserDO> result = new WebResult<UserDO>();
		
		BaseResult<UserDO> registerResult = userServiceRef.register(registerDTO);
		if( registerResult == null || !registerResult.isSuccess() ){
			log.error("register failed!  RegisterDTO={}, Result={}", JSON.toJSONString(registerDTO), JSON.toJSONString(registerResult) );
			if( registerResult == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = registerResult.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.MOBILE_REGISTED.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.MOBILE_REGISTED);
				}else if( UserServiceHttpCode.UNSUPPORTED_PHONE_NUM.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.MOBILE_NUM_ERROR);
				}else if( UserServiceHttpCode.SMS_ALREADY_SEND.getCode() == code || UserServiceHttpCode.SMS_SEND_ING.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.SMS_ALREADY_SEND);
				}else if( UserServiceHttpCode.SMS_VERIFY_CODE_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.SMS_VERIFY_CODE_ERROR);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}
		result.setValue(registerResult.getValue());
		return result;
	}

	/**
	 * 找回密码 - 验证短信验证码
	 * @param revivePasswordDTO
	 * @return
	 */
	@MethodLogger
	public WebResultSupport retrievePasswordVerify(RevivePasswordDTO revivePasswordDTO){
		WebResultSupport result = new WebResultSupport() ;
		if( revivePasswordDTO == null || revivePasswordDTO.getDomainId() <=0 || StringUtils.isBlank(revivePasswordDTO.getMobile()) ){
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result ;
		}
		
		// 首先校验短信验证码是否正确
		revivePasswordDTO.setStep(RevivePasswordStep.VERIFY_CODE);
		ResultSupport verifyResult = userSecurityServiceRef.retrievePassword(revivePasswordDTO);
		if(verifyResult == null || !verifyResult.isSuccess() ){
			log.error("retrievePassword  verfiy code failed!  RevivePasswordDTO={}, Result={}", JSON.toJSONString(revivePasswordDTO), JSON.toJSONString(verifyResult) );
			if( verifyResult == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = verifyResult.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.SMS_VERIFY_CODE_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.SMS_VERIFY_CODE_ERROR);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}
		return result;
	}
	

	/**
	 * 找回密码  - 修改密码
	 * @param revivePasswordDTO
	 * @return
	 */
	@MethodLogger
	public WebResultSupport retrievePasswordUpdate(RevivePasswordDTO revivePasswordDTO){
		WebResultSupport result = new WebResultSupport() ;
		if( revivePasswordDTO == null || revivePasswordDTO.getDomainId() <=0 || StringUtils.isBlank(revivePasswordDTO.getMobile()) ){
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result ;
		}
		
		// 更改密码
		revivePasswordDTO.setStep(RevivePasswordStep.UPDATE_PASSWORD);
		ResultSupport updatePassowrdResult = userSecurityServiceRef.retrievePassword(revivePasswordDTO);
		if(updatePassowrdResult == null || !updatePassowrdResult.isSuccess() ){
			log.error("retrievePassword  udpate password failed!  RevivePasswordDTO={}, Result={}", JSON.toJSONString(revivePasswordDTO), JSON.toJSONString(updatePassowrdResult) );
			if( updatePassowrdResult == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = updatePassowrdResult.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.MODIFY_DISABLE.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.MODIFY_DISABLE);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}

		return result;
	}

	/**
	 * 
	 * @param registerDTO
	 * @return
	 */
	@MethodLogger
	public WebResultSupport sendRegisterVerifyCode(VerifyCodeDTO verifyCodeDTO){
		WebResultSupport result = new WebResultSupport() ;
		ResultSupport rs = userSecurityServiceRef.sendSmsVerfiyCode(verifyCodeDTO);
		if(rs == null || !rs.isSuccess() ){
			log.error("sendSmsVerfiyCode  failed!  verifyCodeDTO={}, Result={}", JSON.toJSONString(verifyCodeDTO), JSON.toJSONString(rs) );
			if( rs == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = rs.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.UNSUPPORTED_PHONE_NUM.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.MOBILE_NUM_ERROR);
				}else if( UserServiceHttpCode.MOBILE_NOT_REGIST.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.USER_NOT_FOUND);
				}else if( UserServiceHttpCode.MOBILE_REGISTED.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.MOBILE_REGISTED);
				}else if( UserServiceHttpCode.SMS_SEND_ING.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.SMS_ALREADY_SEND);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}
		return result;
	}
	@MethodLogger
	public WebResultSupport changePassword(ChangePasswordDTO changePasswordDTO){
		WebResultSupport result = new WebResultSupport() ;
		if (changePasswordDTO == null || changePasswordDTO.getUserId() <= 0
				|| StringUtils.isBlank(changePasswordDTO.getNewPassword())
				|| StringUtils.isBlank(changePasswordDTO.getOldPassword()) ) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result ;
		}
		ResultSupport rs = userSecurityServiceRef.changePassword(changePasswordDTO);
		if(rs == null || !rs.isSuccess() ){
			log.error("changePassword  failed!  changePasswordDTO={}, Result={}", JSON.toJSONString(changePasswordDTO), JSON.toJSONString(rs) );
			if( rs == null ){
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result ;
			}else{
				int code = rs.getErrorCode();
				if( UserServiceHttpCode.PARAMETER_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
				}else if( UserServiceHttpCode.USER_NOT_FOUND.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.USER_NOT_FOUND);
				}else if( UserServiceHttpCode.PASSWORD_ERROR.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.PASSWORD_ERROR);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result ;
		}
		return result;
	}


	/**
	 * 
	 * getUserByMobile:(根据手机号码查询用户). <br/>
	 * 
	 * @author zhangjian
	 * @param mobile
	 * @return
	 * @throws BizException
	 */
	public UserDO getUserByMobile(String mobile) {
		BaseResult<UserDO> userDOResult = userServiceRef.getUserByMobile(mobile);
		if( userDOResult == null || !userDOResult.isSuccess() ){
			return null;
		}
		return userDOResult.getValue();
	}

}

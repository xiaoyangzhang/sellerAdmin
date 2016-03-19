package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.repo.UserRepo;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.dto.VerifyCodeDTO;
import com.yimayhd.user.client.enums.SmsType;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
 * @author wzf
 *
 */
public class UserBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(UserBiz.class);
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private SessionManager sessionManager;

	public WebResult<LoginResult> login(LoginDTO loginDTO) {
//		CheckResult checkFeedBack = UserChecker.checkLoginVo(loginVo);
//		LoginDTO loginDTO = UserConverter.toLoginDTO(loginVo);
		WebResult<LoginResult> result = userRepo.login(loginDTO);
		return result;
	}

//	@Override
//	public boolean register(RegisterVo registerVo) throws WebException {
//		LOGGER.debug("registerVo={}", JSONObject.toJSONString(registerVo));
//		CheckResult checkFeedBack = UserChecker.checkRegisterVo(registerVo);
//		if (!checkFeedBack.isSuccess()) {
//			LOGGER.warn(checkFeedBack.getResultMsg());
//			throw new WebException(checkFeedBack);
//		}
//
//		RegisterDTO registerDTO = UserConverter.toRegisterDTO(registerVo);
//		registerDTO.setStep(RegisterStep.VERIFY_CODE);
//
//		BaseResult<UserDO> registerResult = userRepo.register(registerDTO);
//		if (registerResult.isSuccess() == false) {
//			LOGGER.error("registerResult={}", registerResult);
//
//			int errorCode = registerResult.getErrorCode();
//			switch (errorCode) {
//			case UserServiceHttpCode.C_MOBILE_REGISTED:
//				throw new WebException(WebErrorCode.UserExisted);
//			case UserServiceHttpCode.C_SMS_VERIFY_CODE_ERROR:
//				// 验证码错误
//				throw new WebException(WebErrorCode.InvalidVerifyCode);
//			case UserServiceHttpCode.C_PARAMETER_ERROR:
//				// 参数错误
//				throw new WebException(WebErrorCode.ParametersValidateError);
//			case UserServiceHttpCode.C_UNSUPPORTED_PHONE_NUM:
//				throw new WebException(WebErrorCode.NotSupportedMobile);
//			default:
//				throw new WebException(WebErrorCode.Error);
//
//			}
//		}
//
//		return true;
//
//	}
//
	@MethodLogger
	public WebResultSupport retrievePassword(RevivePasswordDTO revivePasswordDTO) {
		WebResultSupport vefifyResult =  userRepo.retrievePasswordVerify(revivePasswordDTO);
		if( vefifyResult == null || !vefifyResult.isSuccess() ){
			return vefifyResult ;
		}
		
		WebResultSupport result =  userRepo.retrievePasswordUpdate(revivePasswordDTO);
		if( result == null || !result.isSuccess() ){
			return result ;
		}
		
		return result;
	}

	public WebResultSupport sendRegisterVerifyCode(String mobile) {

		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO() ;
		verifyCodeDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		verifyCodeDTO.setMobile(mobile);
		verifyCodeDTO.setSmsType(SmsType.REGISTER);
		WebResultSupport result= userRepo.sendRegisterVerifyCode(verifyCodeDTO);
		return result;
	}
	public WebResultSupport sendRetrievePasswordVerifyCode(String mobile) {
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO() ;
		verifyCodeDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		verifyCodeDTO.setMobile(mobile);
		verifyCodeDTO.setSmsType(SmsType.RETRIVE_PASSWORD);
		WebResultSupport result= userRepo.sendRegisterVerifyCode(verifyCodeDTO);
		return result;
	}

//	@Override
//	public Long getSessionUserId() {
//
//		long userId = sessionManager.getUserId();
//		LOGGER.debug("userId={}", userId);
//
//		if (userId > 0) {
//			return userId;
//		}
//
//		return null;
//	}
//
//	@Override
//	public UserVo getSessionUser() {
//
//		UserDO userDO = sessionManager.getUser();
//
//		LOGGER.debug("userDO={}", JSONObject.toJSONString(userDO));
//		return UserConverter.toUserVo(userDO);
//	}
//
//	@Override
//	public UserVo getUserByMobile(String mobile) throws WebException {
//		LOGGER.debug("mobile={}", mobile);
//
//		try {
//			UserDO userDO = userRepo.getUserByMobile(mobile);
//			return UserConverter.toUserVo(userDO);
//		} catch (Exception e) {
//			LOGGER.error("mobile={}", JSONObject.toJSON(mobile), e);
//			throw new WebException(WebErrorCode.Error, e.getMessage());
//		}
//	}

}

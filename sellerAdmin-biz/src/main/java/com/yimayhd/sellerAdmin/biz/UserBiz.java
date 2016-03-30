package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.UserChecker;
import com.yimayhd.sellerAdmin.checker.result.CheckResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.repo.UserRepo;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.dto.VerifyCodeDTO;
import com.yimayhd.user.client.enums.SmsType;
import com.yimayhd.user.client.enums.security.RegisterStep;
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
	
	@Autowired
	private MenuBiz menuBiz ;

	public WebResult<LoginResult> login(LoginDTO loginDTO) {
//		CheckResult checkFeedBack = UserChecker.checkLoginVo(loginVo);
//		LoginDTO loginDTO = UserConverter.toLoginDTO(loginVo);
		WebResult<LoginResult> result = userRepo.login(loginDTO);
		
		//加载菜单信息，并缓存
		if (result != null && result.isSuccess() 
				&& result.getValue() != null
				&& result.getValue().getValue() != null) {
			long userId = result.getValue().getValue().getId();
			menuBiz.cacheUserMenus2Tair(userId);
		}
		return result;
	}

	public WebResult<UserDO> register(RegisterVo registerVo) {
		WebResult<UserDO> result = new WebResult<UserDO>() ;
		LOGGER.debug("registerVo={}", JSONObject.toJSONString(registerVo));
		CheckResult checkFeedBack = UserChecker.checkRegisterVo(registerVo);
		if (!checkFeedBack.isSuccess()) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result ;
		}

		RegisterDTO registerDTO = UserConverter.toRegisterDTO(registerVo);
		registerDTO.setStep(RegisterStep.VERIFY_CODE);

		result = userRepo.register(registerDTO);
		
		
		
		
		return result ;

	}
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
	public WebResultSupport modifyPassword(long userId, String password, String oldPassword) {
		ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO() ;
		changePasswordDTO.setUserId(userId);
		changePasswordDTO.setNewPassword(password);
		changePasswordDTO.setOldPassword(oldPassword);
		WebResultSupport result= userRepo.changePassword(changePasswordDTO);
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

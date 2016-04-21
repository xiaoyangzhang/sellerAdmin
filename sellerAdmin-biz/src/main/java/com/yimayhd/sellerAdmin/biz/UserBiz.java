package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.constant.Constant;
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

	@MethodLogger
	public WebResult<LoginResult> login(LoginDTO loginDTO) {
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

	@MethodLogger
	public WebResult<UserDO> register(RegisterDTO registerDTO) {
		registerDTO.setStep(RegisterStep.VERIFY_CODE);
		WebResult<UserDO> result = userRepo.register(registerDTO);
		return result ;

	}
	
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
		WebResultSupport result= userRepo.sendSmsVerifyCode(verifyCodeDTO);
		return result;
	}
	public WebResultSupport sendRetrievePasswordVerifyCode(String mobile) {
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO() ;
		verifyCodeDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		verifyCodeDTO.setMobile(mobile);
		verifyCodeDTO.setSmsType(SmsType.RETRIVE_PASSWORD);
		WebResultSupport result= userRepo.sendSmsVerifyCode(verifyCodeDTO);
		return result;
	}
	public WebResultSupport modifyPassword(ChangePasswordDTO changePasswordDTO) {
		WebResultSupport result= userRepo.changePassword(changePasswordDTO);
		return result;
	}
	
	public UserDO getUserByMobile(String mobile){
		return userRepo.getUserByMobile(mobile);
	}

}

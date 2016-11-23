package com.yimayhd.sellerAdmin.biz;

import java.util.UUID;

import com.yimayhd.sellerAdmin.repo.MenuRepo;
import com.yimayhd.sellerAdmin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.repo.UserRepo;
import com.yimayhd.sellerAdmin.util.HttpRequestUtil;
import com.yimayhd.sellerAdmin.util.HttpUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.dto.VerifyCodeDTO;
import com.yimayhd.user.client.enums.SmsType;
import com.yimayhd.user.client.enums.security.RegisterStep;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.session.manager.SessionHelper;
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
	private MenuRepo menuRepo;
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
			//menuBiz.cacheUserMenus2Tair(userId);
			//权限整合
			try {
				menuRepo.cacheMenuListByUserId(result.getValue().getToken());
				menuBiz.cacheAllMenusToTair();
			} catch (Exception e) {
				LOGGER.error("cacheMenuListByUserId  getUser(token) failed  token={}",result);
			}
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
		String ip = HttpUtil.getIpAddr(SessionHelper.getRequest());
		StringBuilder sb = new StringBuilder();
		sb.append("UUID=").append(UUID.randomUUID()).append("  sendRegisterVerifyCode   ip=").append(ip).append("  mobile=").append(mobile) ;
		LOGGER.info(sb.toString());
		
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO() ;
		verifyCodeDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		verifyCodeDTO.setMobile(mobile);
		verifyCodeDTO.setSmsType(SmsType.REGISTER);
		
		
		WebResultSupport result= userRepo.sendSmsVerifyCode(verifyCodeDTO);
		LOGGER.info(sb.append("  result=").append(JSON.toJSONString(result)).toString());
		
		
		return result;
	}
	public WebResultSupport sendRetrievePasswordVerifyCode(String mobile) {
		String ip = HttpUtil.getIpAddr(SessionHelper.getRequest());
		StringBuilder sb = new StringBuilder();
		sb.append("UUID=").append(UUID.randomUUID()).append("  sendRegisterVerifyCode   ip=").append(ip).append("  mobile=").append(mobile) ;
		LOGGER.info(sb.toString());
		
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO() ;
		verifyCodeDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		verifyCodeDTO.setMobile(mobile);
		verifyCodeDTO.setSmsType(SmsType.RETRIVE_PASSWORD);
		WebResultSupport result= userRepo.sendSmsVerifyCode(verifyCodeDTO);
		
		LOGGER.info(sb.append("  result=").append(JSON.toJSONString(result)).toString());
		
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

/**  
 * Project Name:b2cpc-web  
 * File Name:UserConverter.java  
 * Package Name:com.yimayhd.b2cpc.web.converter  
 * Date:2016年2月15日下午3:22:22    
 *  
*/

package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.model.vo.user.UserVo;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;

/**
 * ClassName:UserConverter <br/>
 * Date: 2016年2月15日 下午3:22:22 <br/>
 * 
 * @author zhangjian
 * @version
 * @see
 */
public class UserConverter {
	public static LoginDTO toLoginDTO(LoginVo loginVo) {
		if (loginVo == null) {
			return null;
		}

		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setMobile(loginVo.getUsername());
		loginDTO.setPassword(loginVo.getPassword());
		loginDTO.setDomainId(Constant.DOMAIN_JIUXIU);

		return loginDTO;
	}

	public static RegisterDTO toRegisterDTO(RegisterVo registerVo) {
		if (registerVo == null) {
			return null;
		}

		RegisterDTO registerDTO = new RegisterDTO();

		registerDTO.setMobile(registerVo.getUsername());
		registerDTO.setNickName(registerVo.getNickname());
		UserDO userDO = new UserDO();
		userDO.setNickname(registerVo.getNickname());
		userDO.setPassword(registerVo.getPassword());
		userDO.setMobileNo(registerVo.getUsername());
		registerDTO.setUserDO(userDO);
		registerDTO.setVerifyCode(registerVo.getVerifyCode());
		registerDTO.setDomainId(Constant.DOMAIN_JIUXIU);

		return registerDTO;

	}

	public static RevivePasswordDTO toRevivePasswordDTO(RetrievePasswordVo revivePasswordVo) {
		if (revivePasswordVo == null) {
			return null;
		}

		RevivePasswordDTO revivePasswordDTO = new RevivePasswordDTO();
		revivePasswordDTO.setMobile(revivePasswordVo.getUsername());
		revivePasswordDTO.setPassword(revivePasswordVo.getPassword());
		revivePasswordDTO.setVerifyCode(revivePasswordVo.getVerifyCode());
		revivePasswordDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		// revivePasswordDTO.setStep(RevivePasswordStep.VERIFY_CODE);

		return revivePasswordDTO;
	}

	public static UserVo toUserVo(UserDO userDO) {
		if (userDO == null) {
			return null;
		}

		UserVo userVo = new UserVo();
		userVo.setUserId(userDO.getId());
		userVo.setNickname(userDO.getNickname());
		userVo.setHeadImg(userDO.getAvatar());
		userVo.setSignature(userDO.getSignature());

		return userVo;
	}
}

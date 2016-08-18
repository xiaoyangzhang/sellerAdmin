package com.yimayhd.sellerAdmin.register;

import javax.annotation.Resource;
import javax.imageio.spi.RegisterableService;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.enums.security.RegisterStep;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;

public class TestRegister extends BaseTest {

	@Resource
	private UserService userServiceRef;
	
	@Test
	public void testReg() {
		register();
	}

	private void register() {

		RegisterDTO registerDTO = new RegisterDTO();

		registerDTO.setMobile("张晓阳");
		registerDTO.setNickName("zhangxiaoyang");
		UserDO userDO = new UserDO();
		userDO.setPassword("123456a");
		userDO.setMobileNo("张晓阳");
		registerDTO.setUserDO(userDO);
		registerDTO.setVerifyCode("");
		registerDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		registerDTO.setStep(RegisterStep.VERIFY_CODE);
		//昵称自动生成
		registerDTO.setNicknameAutoGen(true);;
		registerDTO.setApplicationId(Constant.APPID_JIUXIU);
		BaseResult<UserDO> registerResult = userServiceRef.register(registerDTO);
		System.out.println("----------------------------"+JSON.toJSONString(registerResult));
	}
}

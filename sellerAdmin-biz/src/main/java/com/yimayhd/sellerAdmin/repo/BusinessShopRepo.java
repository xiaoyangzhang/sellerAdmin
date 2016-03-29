package com.yimayhd.sellerAdmin.repo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;

public class BusinessShopRepo {
	private static Logger LOGGER = LoggerFactory.getLogger(BusinessShopRepo.class);
	
	@Resource
	private MerchantService merchantService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 更新用户信息
	 * @param userDTO
	 * @return
	 */
	public WebResultSupport updateUser (UserDTO userDTO){
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<UserDO> result = userService.updateUser(userDTO);
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.UPDATE_USER_ERROR);
		}
		return webResult;
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	public WebResultSupport saveMerchant(MerchantDO merchantDO) {
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<MerchantDO> result = merchantService.saveMerchant(merchantDO);
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.BUSINESS_BASIC_SAVE_FAILURE);
		}
		return webResult;
	}

}

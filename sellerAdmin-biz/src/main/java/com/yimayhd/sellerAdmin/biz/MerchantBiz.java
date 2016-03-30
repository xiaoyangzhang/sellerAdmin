package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.repo.MerchantRepo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;

public class MerchantBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(MerchantBiz.class);
	
	@Autowired
	private MerchantRepo merchantRepo;
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	public WebResultSupport saveMerchant(MerchantDO merchantDO){
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<MerchantDO> result = merchantRepo.saveMerchant(merchantDO);
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			WebReturnCode code =new WebReturnCode(result.getErrorCode(), result.getErrorMsg());
			webResult.setWebReturnCode(code);
		}
		return webResult;
	}
	/**
	 * 修改用户信息
	 * @param userDTO
	 * @return
	 */
	public WebResultSupport updateUser(UserDTO userDTO){
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<UserDO> result = merchantRepo.updateUser(userDTO);
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			WebReturnCode code =new WebReturnCode(result.getErrorCode(), result.getErrorMsg());
			webResult.setWebReturnCode(code);
		}
		return webResult;
	}
	
	public WebResultSupport updateMerchantInfo(MerchantDTO merchantDTO){
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<Boolean> result = merchantRepo.updateMerchantInfo(merchantDTO);
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			WebReturnCode code =new WebReturnCode(result.getErrorCode(), result.getErrorMsg());
			webResult.setWebReturnCode(code);
		}
		return webResult;
	}
}

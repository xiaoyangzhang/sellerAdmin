package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.repo.BusinessShopRepo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.UserDTO;

public class BusinessShopBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(BusinessShopBiz.class);
	
	@Autowired
	private BusinessShopRepo businessShopRepo;
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	public WebResultSupport saveMerchant(MerchantDO merchantDO){
		return businessShopRepo.saveMerchant(merchantDO);
	}
	/**
	 * 修改用户信息
	 * @param userDTO
	 * @return
	 */
	public WebResultSupport updateUser(UserDTO userDTO){
		return businessShopRepo.updateUser(userDTO);
	}
	
}

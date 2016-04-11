package com.yimayhd.sellerAdmin.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.entity.Merchant;

/**
 * 
 * @author zhangxy
 *
 */
public class FoodRepo {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MerchantService merchantService;
	public WebResultSupport saveDeliciousFood(Merchant merchant) {
		WebResultSupport resultSupport = new WebResultSupport();
		//merchantService.
		return resultSupport;
		
	}
}

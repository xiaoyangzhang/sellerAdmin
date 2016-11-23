package com.yimayhd.sellerAdmin.biz.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.enums.MerchantItemCategoryStatus;

public class MerchantItemCategoryHelper {
	public static List<Long> getItemCategoryIds(List<MerchantItemCategoryDO> merchantItemCategoryDOs){
		if( CollectionUtils.isEmpty(merchantItemCategoryDOs) ){
			return null ;
		}
		List<Long> itemCategoryIds = new ArrayList<>();
		for( MerchantItemCategoryDO merchantItemCategoryDO : merchantItemCategoryDOs ){
			itemCategoryIds.add(merchantItemCategoryDO.getItemCategoryId());
		}
		return itemCategoryIds ;
	}
	public static List<MerchantItemCategoryDO> getActiveItemCategorys(List<MerchantItemCategoryDO> merchantItemCategoryDOs){
		if( CollectionUtils.isEmpty(merchantItemCategoryDOs) ){
			return null ;
		}
		List<MerchantItemCategoryDO> result = new ArrayList<>();
		for( MerchantItemCategoryDO merchantItemCategoryDO : merchantItemCategoryDOs ){
			if( MerchantItemCategoryStatus.ACTIVE.getStatus() == merchantItemCategoryDO.getStatus() ){
				result.add(merchantItemCategoryDO);
			}
		}
		return result ;
	}
}

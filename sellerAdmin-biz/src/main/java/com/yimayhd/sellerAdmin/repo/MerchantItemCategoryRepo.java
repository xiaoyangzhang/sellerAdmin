package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;

public class MerchantItemCategoryRepo {
	private static final Logger logger = LoggerFactory.getLogger("MerchantItemCategoryRepo");
	@Autowired
	private MerchantItemCategoryService merchantItemCategoryService;

	@MethodLogger(isPrintResult=false)
	public List<MerchantItemCategoryDO> getMerchantItemCaetgories(int domainId, long sellerId) {

		MemResult<List<MerchantItemCategoryDO>> queryResult = merchantItemCategoryService.findMerchantItemCategoriesBySellerId(domainId, sellerId);
		if (queryResult == null || !queryResult.isSuccess()) {
			logger.error("findMerchantItemCategoriesBySellerId  domainId={} sellerId={}, result={}", domainId, sellerId, JSON.toJSONString(queryResult));
			return null;
		}
		return queryResult.getValue();
	}
}

package com.yimayhd.sellerAdmin.examine;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.api.MerchantInfoApi;
import org.yimayhd.sellerAdmin.entity.merchant.HomePage;
import org.yimayhd.sellerAdmin.entity.merchant.Qualification;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;

public class ExamineTest extends BaseTest {

	@Autowired
	private MerchantBiz merchantBiz;
	@Autowired
	private MerchantInfoApi merchantInfoApi;
	@Test
	public void test() {
		//testQueryMerchantInfoResult();
//		testHomePage();
		testQualification();
	}

	private void testQueryMerchantInfoResult() {
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(1200);
		info.setSellerId(0);
		MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
		System.out.println(merchantInfoResult);
	}
	
	private void testHomePage() {
		//HomePage queryHomePage = merchantInfoApi.queryHomePage(0, 1200, 0, 1338820, 0,0);
		//System.out.println("------------------------"+JSON.toJSONString(queryHomePage));
	}
	
	private void testQualification() {
		Qualification queryMerchantQualification = merchantInfoApi.queryMerchantQualification(0, 1200, 0, 1338820, 0,0);
		System.out.println("======================"+JSON.toJSONString(queryMerchantQualification));
	}
}

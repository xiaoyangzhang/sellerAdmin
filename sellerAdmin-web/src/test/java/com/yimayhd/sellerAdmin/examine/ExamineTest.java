package com.yimayhd.sellerAdmin.examine;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;

public class ExamineTest extends BaseTest {

	@Autowired
	private MerchantBiz merchantBiz;

	@Test
	public void test() {
		testQueryMerchantInfoResult();
	}

	private void testQueryMerchantInfoResult() {
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(1200);
		info.setSellerId(0);
		MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
		System.out.println(merchantInfoResult);
	}
}

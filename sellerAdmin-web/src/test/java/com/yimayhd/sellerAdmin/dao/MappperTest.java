package com.yimayhd.sellerAdmin.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.client.model.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.mapper.OrderOperationLogMapper;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDO;

public class MappperTest extends BaseTest {
	@Autowired
	private OrderOperationLogMapper orderOperationLogMapper;

	@Test
	public void test() {
		process();
	}

	private void process() {
		try {
			queryOrderOperationLogDOList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println();
	}

	public void queryOrderOperationLogDOList() {
		System.err.println();
		OrderOperationLogQuery query = new OrderOperationLogQuery();
		query.setBizNo("/%");
//		query.setBizNo("_");
		List<OrderOperationLogDO> rs = orderOperationLogMapper.queryOrderOperationLogDOList(query);
		printResult(rs, "queryOrderOperationLogDOList");
		System.err.println();
	}
}

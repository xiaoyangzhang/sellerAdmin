package com.yimayhd.sellerAdmin.model.vo;

import com.yimayhd.sellerAdmin.model.Order;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;

/**
 * Created by Administrator on 2015/10/29.
 */
public class OrderVO extends Order {
	private OrderListQuery orderListQuery;//查询条件

	public OrderListQuery getOrderListQuery() {
		return orderListQuery;
	}

	public void setOrderListQuery(OrderListQuery orderListQuery) {
		this.orderListQuery = orderListQuery;
	}
	
}

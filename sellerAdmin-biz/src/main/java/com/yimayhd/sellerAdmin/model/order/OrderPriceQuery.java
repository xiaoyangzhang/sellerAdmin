package com.yimayhd.sellerAdmin.model.order;

import com.yimayhd.sellerAdmin.base.BaseQuery;


/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderPriceQuery extends BaseQuery {
	private static final long serialVersionUID = 3445511042180124468L;
	private String orderJson;// 订单json

	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}
}

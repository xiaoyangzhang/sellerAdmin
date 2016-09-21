package com.yimayhd.sellerAdmin.model.order;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderPrizeDTO implements Serializable {
	private static final long serialVersionUID = 2238635288629917984L;
	private String orderJson;// 订单json

	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}
}

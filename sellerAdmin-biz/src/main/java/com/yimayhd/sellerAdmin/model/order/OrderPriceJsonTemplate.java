package com.yimayhd.sellerAdmin.model.order;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderPriceJsonTemplate implements Serializable {
	private static final long serialVersionUID = 8796262401543744153L;
	private Long mainOrderId;//主订单id
	private BigDecimal mainOrderPrize;// 主订单实付金额
	private OrderSonModel[] orderSonModel;// 子订单列表


	public Long getMainOrderId() {
		return mainOrderId;
	}

	public void setMainOrderId(Long mainOrderId) {
		this.mainOrderId = mainOrderId;
	}

	public BigDecimal getMainOrderPrize() {
		return mainOrderPrize;
	}

	public void setMainOrderPrize(BigDecimal mainOrderPrize) {
		this.mainOrderPrize = mainOrderPrize;
	}

	public OrderSonModel[] getOrderSonModel() {
		return orderSonModel;
	}

	public void setOrderSonModel(OrderSonModel[] orderSonModel) {
		this.orderSonModel = orderSonModel;
	}
}

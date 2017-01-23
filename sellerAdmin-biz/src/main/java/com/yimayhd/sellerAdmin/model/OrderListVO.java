package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;

public class OrderListVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String itemTitle;

	private String orderStatus;

	private long sellerId;

	private long bizOrderId;

	private long buyerId;

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

}

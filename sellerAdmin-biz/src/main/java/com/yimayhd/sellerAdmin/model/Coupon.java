package com.yimayhd.sellerAdmin.model;

import java.math.BigDecimal;

/**
 * 优惠券
 * 
 * @author yebin
 *
 */
public class Coupon {
	private BigDecimal price;// 价格
	private Integer count;// 数量

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

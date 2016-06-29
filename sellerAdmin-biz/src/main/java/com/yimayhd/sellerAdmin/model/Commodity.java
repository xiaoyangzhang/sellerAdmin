package com.yimayhd.sellerAdmin.model;

import java.math.BigDecimal;

/**
 * 商品信息
 * 
 * @author yebin
 *
 */
public class Commodity {
	private String imageUrl;// 图片
	private String description;// 描述
	private BigDecimal price;// 单价
	private Integer amount;// 数量
	private Integer tradeState;// 交易状态
	private String discount;// 优惠

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getTradeState() {
		return tradeState;
	}

	public void setTradeState(Integer tradeState) {
		this.tradeState = tradeState;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}

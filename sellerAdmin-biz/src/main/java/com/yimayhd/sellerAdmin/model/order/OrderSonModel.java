package com.yimayhd.sellerAdmin.model.order;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderSonModel implements Serializable {
	private static final long serialVersionUID = -4778021633230936657L;
	private Long sonOrderId;// 子订单ID
	private BigDecimal sonOrderPrice;//子订单实际价格
	private Long sonOrderNumber;//购买数量

	public Long getSonOrderId() {
		return sonOrderId;
	}

	public void setSonOrderId(Long sonOrderId) {
		this.sonOrderId = sonOrderId;
	}

	public BigDecimal getSonOrderPrice() {
		return sonOrderPrice==null?BigDecimal.ZERO:sonOrderPrice;
	}

	public void setSonOrderPrice(BigDecimal sonOrderPrice) {
		this.sonOrderPrice = sonOrderPrice;
	}

	public Long getSonOrderNumber() {
		return sonOrderNumber;
	}

	public void setSonOrderNumber(Long sonOrderNumber) {
		this.sonOrderNumber = sonOrderNumber;
	}
}

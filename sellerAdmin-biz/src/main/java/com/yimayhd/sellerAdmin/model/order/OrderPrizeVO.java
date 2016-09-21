package com.yimayhd.sellerAdmin.model.order;


import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderPrizeVO implements Serializable {

	private static final long serialVersionUID = 5238604953120233016L;
	private long bizOrderId;// 主订单id
	private long afterAdjustActualTotalFee;// 主订单实际金额
	private Map<Long, Long> resultOrderChangePriceMap;//子订单 id,金额

	public long getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public long getAfterAdjustActualTotalFee() {
		return afterAdjustActualTotalFee;
	}

	public void setAfterAdjustActualTotalFee(long afterAdjustActualTotalFee) {
		this.afterAdjustActualTotalFee = afterAdjustActualTotalFee;
	}

	public Map<Long, Long> getResultOrderChangePriceMap() {
		return resultOrderChangePriceMap;
	}

	public void setResultOrderChangePriceMap(Map<Long, Long> resultOrderChangePriceMap) {
		this.resultOrderChangePriceMap = resultOrderChangePriceMap;
	}
}

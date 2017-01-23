package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.tradecenter.client.model.enums.BizOrderStatus;
import com.yimayhd.tradecenter.client.model.enums.LogisticsStatus;
import com.yimayhd.tradecenter.client.model.enums.PayStatus;

/**
 * @author: wuzhengfei@pajk.cn
 * @date: 2014年12月27日 下午3:33:22
 * 
 */
public enum BizOrderStatusConvert {
	WAITING_PAY(
			BizOrderStatus.WAITING_PAY,
			PayStatus.NOT_PAY,
			LogisticsStatus.UNCONSIGNED,
			"未付款，未发货"),
	WAITING_PAY_NO_LG_ORDER(
			BizOrderStatus.WAITING_PAY,
			PayStatus.NOT_PAY,
			LogisticsStatus.NO_LG_ORDER,
			"未付款，没有外部物流单"),
	WAITING_DELIVERY(
			BizOrderStatus.WAITING_DELIVERY,
			PayStatus.PAID,
			LogisticsStatus.UNCONSIGNED,
			"已付款，未发货"),
	WAITING_DELIVERY_NO_LG_ORDER(
			BizOrderStatus.WAITING_DELIVERY,
			PayStatus.PAID,
			LogisticsStatus.NO_LG_ORDER,
			"已付款，没有外部物流单"),
	SHIPPING(
			BizOrderStatus.SHIPPING,
			PayStatus.PAID,
			LogisticsStatus.CONSIGNED,
			"已付款，已收货"),
	CLOSED(
			BizOrderStatus.CLOSED,
			PayStatus.REFUNDED,
			LogisticsStatus.NO_LG_ORDER,
			"已退款订单关闭，没有外部物流单"),
	CANCEL(
			BizOrderStatus.CANCEL,
			PayStatus.NOT_PAY_CLOSE,
			LogisticsStatus.NO_LG_ORDER,
			"未付款订单关闭，没有外部物流单"),
	FINISH(
			BizOrderStatus.FINISH,
			PayStatus.SUCCESS,
			LogisticsStatus.DELIVERED,
			"交易成功，已收货"),
	CONFIRMED_CLOSE(
			BizOrderStatus.CONFIRMED_CLOSE,
			PayStatus.CONFIRMED_CLOSE,
			LogisticsStatus.RETURNED,
			"交易成功，已收货")
	;
	private BizOrderStatus bizOrderStatus;
	private PayStatus payStatus;
	private LogisticsStatus logisticsStatus ;
	private String desc ;

	private BizOrderStatusConvert(BizOrderStatus bizOrderStatus, PayStatus payStatus,
								  LogisticsStatus logisticsStatus, String desc
	) {
		this.payStatus = payStatus;
		this.logisticsStatus = logisticsStatus;
		this.bizOrderStatus = bizOrderStatus;
		this.desc = desc ;
	}
	
	public static BizOrderStatus getBizOrderStatus(LogisticsStatus logisticsStatus, PayStatus payStatus){
		if( logisticsStatus == null || payStatus == null ){
			return null;
		}
		for( BizOrderStatusConvert orderStatusConvert : BizOrderStatusConvert.values() ){
			LogisticsStatus lgStatus = orderStatusConvert.getLogisticsStatus();
			PayStatus pStatus = orderStatusConvert.getPayStatus() ;
			if( logisticsStatus == lgStatus && payStatus == pStatus ){
				return orderStatusConvert.bizOrderStatus ;
			}
		}
		return null ;
	}

	public static BizOrderStatus getBizOrderStatus(int logisticsStatus, int payStatus){
		return getBizOrderStatus(LogisticsStatus.getByStatus(logisticsStatus), PayStatus.getByStatus(payStatus));
	}

	public BizOrderStatus getBizOrderStatus() {
		return bizOrderStatus;
	}

	public void setBizOrderStatus(BizOrderStatus bizOrderStatus) {
		this.bizOrderStatus = bizOrderStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public LogisticsStatus getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(LogisticsStatus logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

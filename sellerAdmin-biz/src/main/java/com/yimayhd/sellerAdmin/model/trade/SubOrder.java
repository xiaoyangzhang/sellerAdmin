package com.yimayhd.sellerAdmin.model.trade;

import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class SubOrder {
    private TcDetailOrder tcDetailOrder;
    private long startTime;
    private long endTime;
    private long executeTime;
    private String activityTime;
    private String vTxt;
    //优惠后的金额(单价)
    private long itemPrice_;

    private long subOrderTotalFee;//子订单原价

    public SubOrder(){}

    public SubOrder(TcDetailOrder tcDetailOrder) {
        this.tcDetailOrder = tcDetailOrder;
    }

    public SubOrder(TcDetailOrder tcDetailOrder, long startTime, long endTime) {
        this.tcDetailOrder = tcDetailOrder;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SubOrder(TcDetailOrder tcDetailOrder, long executeTime) {
        this.tcDetailOrder = tcDetailOrder;
        this.executeTime = executeTime;
    }

    public TcDetailOrder getTcDetailOrder() {
        return tcDetailOrder;
    }

    public void setTcDetailOrder(TcDetailOrder tcDetailOrder) {
        this.tcDetailOrder = tcDetailOrder;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getvTxt() {
        return vTxt;
    }

    public void setvTxt(String vTxt) {
        this.vTxt = vTxt;
    }

	public long getItemPrice_() {
		return itemPrice_;
	}

	public void setItemPrice_(long itemPrice_) {
		this.itemPrice_ = itemPrice_;
	}

    public long getSubOrderTotalFee() {
        return subOrderTotalFee;
    }

    public void setSubOrderTotalFee(long subOrderTotalFee) {
        this.subOrderTotalFee = subOrderTotalFee;
    }
}

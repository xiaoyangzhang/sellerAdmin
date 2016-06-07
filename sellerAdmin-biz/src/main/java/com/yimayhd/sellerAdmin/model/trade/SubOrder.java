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
}

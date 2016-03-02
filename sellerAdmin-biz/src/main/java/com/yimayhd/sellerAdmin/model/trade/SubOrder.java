package com.yimayhd.sellerAdmin.model.trade;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class SubOrder {
    private BizOrderDO bizOrderDO;
    private long startTime;
    private long endTime;
    private long executeTime;
    private String vTxt;

    public SubOrder(){}

    public SubOrder(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public SubOrder(BizOrderDO bizOrderDO, long startTime, long endTime) {
        this.bizOrderDO = bizOrderDO;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SubOrder(BizOrderDO bizOrderDO, long executeTime) {
        this.bizOrderDO = bizOrderDO;
        this.executeTime = executeTime;
    }

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
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

    public String getvTxt() {
        return vTxt;
    }

    public void setvTxt(String vTxt) {
        this.vTxt = vTxt;
    }
}

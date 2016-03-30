package com.yimayhd.sellerAdmin.model.trade;

import java.util.List;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.user.client.domain.UserDO;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class MainOrder {
    private BizOrderDO bizOrderDO;

    private LogisticsOrderDO logisticsOrderDO;

    private List<SubOrder> subOrderList;

    private long orderTotalFee;

    private int orderActionStates;

    private int orderShowState;

    private UserDO user;

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public List<SubOrder> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<SubOrder> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public int getOrderActionStates() {
        return orderActionStates;
    }

    public void setOrderActionStates(int orderActionStates) {
        this.orderActionStates = orderActionStates;
    }

    public int getOrderShowState() {
        return orderShowState;
    }

    public void setOrderShowState(int orderShowState) {
        this.orderShowState = orderShowState;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public long getOrderTotalFee() {
        return orderTotalFee;
    }

    public void setOrderTotalFee(long orderTotalFee) {
        this.orderTotalFee = orderTotalFee;
    }

   public LogisticsOrderDO getLogisticsOrderDO() {
        return logisticsOrderDO;
    }

    public void setLogisticsOrderDO(LogisticsOrderDO logisticsOrderDO) {
        this.logisticsOrderDO = logisticsOrderDO;
    }

     public MainOrder(BizOrderDO bizOrderDO, List<SubOrder> subOrderList) {
        this.bizOrderDO = bizOrderDO;
        this.subOrderList = subOrderList;


}
}

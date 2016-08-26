package com.yimayhd.sellerAdmin.model.trade;

import java.util.List;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.user.client.domain.UserDO;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class MainOrder {
    private TcMainOrder tcMainOrder;

    private LogisticsOrderDO logisticsOrderDO;

    private List<SubOrder> subOrderList;

    private long orderTotalFee;

    private int orderActionStates;

    private int orderShowState;
    
    private int orderStatus;//订单状态
    
    private String orderStatusStr;
    
    private int orderType;//订单类型
    
    private String orderTypeStr;

    private UserDO user;
    
    private String customerServiceNote;//卖家备注
    
    private long requirement;

    private long value;
    
    private long userPointNum;//使用的积分
    
    private long itemPrice_;//主订单实付金额
    


	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustomerServiceNote() {
		return customerServiceNote;
	}

	public void setCustomerServiceNote(String customerServiceNote) {
		this.customerServiceNote = customerServiceNote;
	}

	public TcMainOrder getTcMainOrder() {
        return tcMainOrder;
    }

    public void setTcMainOrder(TcMainOrder tcMainOrder) {
        this.tcMainOrder = tcMainOrder;
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

    public MainOrder(TcMainOrder tcMainOrder, List<SubOrder> subOrderList) {
        this.tcMainOrder = tcMainOrder;
        this.subOrderList = subOrderList;
    }

	public long getRequirement() {
		return requirement;
	}

	public void setRequirement(long requirement) {
		this.requirement = requirement;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getUserPointNum() {
		return userPointNum;
	}

	public void setUserPointNum(long userPointNum) {
		this.userPointNum = userPointNum;
	}

	public long getItemPrice_() {
		return itemPrice_;
	}

	public void setItemPrice_(long itemPrice_) {
		this.itemPrice_ = itemPrice_;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
	}

	public void setOrderTypeStr(String orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}
    
}

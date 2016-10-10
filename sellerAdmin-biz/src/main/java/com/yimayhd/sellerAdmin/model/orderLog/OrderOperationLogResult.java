package com.yimayhd.sellerAdmin.model.orderLog;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogResult implements Serializable{

    private List<OrderOperationLogDTO> orderOperationLogDTOList;
    private int totalCount;

    public List<OrderOperationLogDTO> getOrderOperationLogDTOList() {
        return orderOperationLogDTOList;
    }

    public void setOrderOperationLogDTOList(List<OrderOperationLogDTO> orderOperationLogDTOList) {
        this.orderOperationLogDTOList = orderOperationLogDTOList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

        private static final long serialVersionUID = 6849283422903948418L;
}

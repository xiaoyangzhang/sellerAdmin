package com.yimayhd.sellerAdmin.client.model.orderLog;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogResult implements Serializable{

    private static final long serialVersionUID = 2010315782335925922L;
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

}

package com.yimayhd.sellerAdmin.client.model.orderLog;


import com.yimayhd.sellerAdmin.client.base.BasePageQuery;

import java.util.Date;


/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogQuery extends BasePageQuery {

    private static final long serialVersionUID = -8196260883114296399L;
    private Long operationId;

    private Long bizNo;

    private Date gmtCreatedStart;

    private Date gmtCreatedEnd;

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Long getBizNo() {
        return bizNo;
    }

    public void setBizNo(Long bizNo) {
        this.bizNo = bizNo;
    }

    public Date getGmtCreatedStart() {
        return gmtCreatedStart;
    }

    public void setGmtCreatedStart(Date gmtCreatedStart) {
        this.gmtCreatedStart = gmtCreatedStart;
    }

    public Date getGmtCreatedEnd() {
        return gmtCreatedEnd;
    }

    public void setGmtCreatedEnd(Date gmtCreatedEnd) {
        this.gmtCreatedEnd = gmtCreatedEnd;
    }
}

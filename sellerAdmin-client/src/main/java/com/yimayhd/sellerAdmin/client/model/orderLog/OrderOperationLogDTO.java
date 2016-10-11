package com.yimayhd.sellerAdmin.client.model.orderLog;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogDTO implements Serializable {
    private static final long serialVersionUID = 2333686619394070105L;

    private long id;
    private long bizNo;//'订单号'
    private long operationId;//操作人id
    private int status;//'状态'
    private String content;//'修改内容'
    private String desc;//'备注'
    private Date gmtCreated;
    private Date gmtModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBizNo() {
        return bizNo;
    }

    public void setBizNo(long bizNo) {
        this.bizNo = bizNo;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}

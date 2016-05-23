package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

public class CategoryQualificationDO implements Serializable {
    private Long id;

    private Long merchantCategory;

    private Long qulificationId;

    private Boolean required;

    private Integer serialNo;

    private Integer status;

    private Date gmtCreated;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(Long merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public Long getQulificationId() {
        return qulificationId;
    }

    public void setQulificationId(Long qulificationId) {
        this.qulificationId = qulificationId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
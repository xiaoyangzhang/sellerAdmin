package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantCatetoryScopeDO implements Serializable {
    private Long id;

    private Long merchantCategoryId;

    private Long businessScopeId;

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

    public Long getMerchantCategoryId() {
        return merchantCategoryId;
    }

    public void setMerchantCategoryId(Long merchantCategoryId) {
        this.merchantCategoryId = merchantCategoryId;
    }

    public Long getBusinessScopeId() {
        return businessScopeId;
    }

    public void setBusinessScopeId(Long businessScopeId) {
        this.businessScopeId = businessScopeId;
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
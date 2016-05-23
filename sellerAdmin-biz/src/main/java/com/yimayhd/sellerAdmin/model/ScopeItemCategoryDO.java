package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

public class ScopeItemCategoryDO implements Serializable {
    private Long id;

    private Long businessScopeId;

    private Long itemCategoryId;

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

    public Long getBusinessScopeId() {
        return businessScopeId;
    }

    public void setBusinessScopeId(Long businessScopeId) {
        this.businessScopeId = businessScopeId;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
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
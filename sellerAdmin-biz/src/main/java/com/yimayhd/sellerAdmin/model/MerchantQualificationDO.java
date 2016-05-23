package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantQualificationDO implements Serializable {
    private Long id;

    private Long merchantCategoryId;

    private Long qulificationId;

    private Long sellerId;

    private String value;

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

    public Long getQulificationId() {
        return qulificationId;
    }

    public void setQulificationId(Long qulificationId) {
        this.qulificationId = qulificationId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
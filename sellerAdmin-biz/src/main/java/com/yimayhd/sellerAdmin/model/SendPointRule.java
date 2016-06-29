package com.yimayhd.sellerAdmin.model;

import java.util.Date;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * Created by Administrator on 2015/11/9.
 */
public class SendPointRule extends BaseModel {
    private Long ratio;//比率（人民币/积分）；

    private Integer avaiablepPeriod;

    private Long operaId;

    private String operaName;

    private Date effectiveDate;

    public Long getRatio() {
        return ratio;
    }

    public void setRatio(Long ratio) {
        this.ratio = ratio;
    }

    public Integer getAvaiablepPeriod() {
        return avaiablepPeriod;
    }

    public void setAvaiablepPeriod(Integer avaiablepPeriod) {
        this.avaiablepPeriod = avaiablepPeriod;
    }

    public Long getOperaId() {
        return operaId;
    }

    public void setOperaId(Long operaId) {
        this.operaId = operaId;
    }

    public String getOperaName() {
        return operaName;
    }

    public void setOperaName(String operaName) {
        this.operaName = operaName;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}

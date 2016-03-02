package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Refund extends BaseModel {
    private String refundNO;//交易编号
    private String userName;//用户姓名
    private String phone;//手机号
    private BigDecimal refundMoney;//退款金额
    private BigDecimal shouldRefundPoint;//需返还积分
    private BigDecimal availablePoint;//可返还积分
    private BigDecimal deductMoneyOffsetPoint;//超出积分扣现
    private BigDecimal factRefundMoney;//实际退款金额
    private Date refundTime;//退款时间
    private Integer refundStatus;//退款状态
    private String operatorName;//操作员
    private String remark;//备注

    public String getRefundNO() {
        return refundNO;
    }

    public void setRefundNO(String refundNO) {
        this.refundNO = refundNO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public BigDecimal getShouldRefundPoint() {
        return shouldRefundPoint;
    }

    public void setShouldRefundPoint(BigDecimal shouldRefundPoint) {
        this.shouldRefundPoint = shouldRefundPoint;
    }

    public BigDecimal getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(BigDecimal availablePoint) {
        this.availablePoint = availablePoint;
    }

    public BigDecimal getDeductMoneyOffsetPoint() {
        return deductMoneyOffsetPoint;
    }

    public void setDeductMoneyOffsetPoint(BigDecimal deductMoneyOffsetPoint) {
        this.deductMoneyOffsetPoint = deductMoneyOffsetPoint;
    }

    public BigDecimal getFactRefundMoney() {
        return factRefundMoney;
    }

    public void setFactRefundMoney(BigDecimal factRefundMoney) {
        this.factRefundMoney = factRefundMoney;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

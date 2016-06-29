package com.yimayhd.sellerAdmin.model;

import java.math.BigDecimal;
import java.util.Date;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Trade extends BaseModel {
    private String tradNO;//交易编号
    private String userName;//会员姓名
    private String phone;//手机号
    private BigDecimal shouldMoney;//应付金额
    private BigDecimal tradeMoney;//实付金额
    private BigDecimal reduceMoney;//优惠金额
    private BigDecimal usePoint;//使用积分
    private BigDecimal sendPoint;//赠送积分
    private BigDecimal point;//剩余积分
    private Integer tradeStatus;//交易状态
    private Date tradeTime;//交易时间
    private String terminalName;//终端
    private String cashierName;//收银员
    private String remark;//备注

    public String getTradNO() {
        return tradNO;
    }

    public void setTradNO(String tradNO) {
        this.tradNO = tradNO;
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

    public BigDecimal getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(BigDecimal shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public BigDecimal getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(BigDecimal reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public BigDecimal getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(BigDecimal usePoint) {
        this.usePoint = usePoint;
    }

    public BigDecimal getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(BigDecimal sendPoint) {
        this.sendPoint = sendPoint;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


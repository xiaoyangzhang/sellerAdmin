package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.yimayhd.sellerAdmin.util.NumUtil;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;

/**
 * Created by Administrator on 2015/12/8.
 */
public class IMallRefundRecordExportVO implements Serializable {
    private static final long serialVersionUID = 327486250498079109L;

    private Long id;
    private long sellerId;
    private String tradeId;
    private String serialNumber;
    private long departId;
    private String department;
    private int jobNumber;
    private String terminalNumber;
    private long payment;
    private long refundPayment;
    private Date refundTime;
    private Date receiptTime;
    private Date gmtCreated;
    private Date gmtModified;
    private String number;
    private double paymentY;//付款金额元
    private double refundPaymentY;//退款金额元

    public static IMallRefundRecordExportVO getIMallRefundRecordExportVO(IMallRefundRecordDO iMallRefundRecordDO) throws Exception{
        IMallRefundRecordExportVO iMallRefundRecordExportVO = new IMallRefundRecordExportVO();
        BeanUtils.copyProperties(iMallRefundRecordDO,iMallRefundRecordExportVO);
        //分转元
        iMallRefundRecordExportVO.setPaymentY(NumUtil.moneyTransformDouble(iMallRefundRecordExportVO.getPayment()));
        iMallRefundRecordExportVO.setRefundPaymentY(NumUtil.moneyTransformDouble(iMallRefundRecordExportVO.getRefundPayment()));
        return iMallRefundRecordExportVO;
    }

    public double getPaymentY() {
        return paymentY;
    }

    public void setPaymentY(double paymentY) {
        this.paymentY = paymentY;
    }

    public double getRefundPaymentY() {
        return refundPaymentY;
    }

    public void setRefundPaymentY(double refundPaymentY) {
        this.refundPaymentY = refundPaymentY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public long getDepartId() {
        return departId;
    }

    public void setDepartId(long departId) {
        this.departId = departId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public long getRefundPayment() {
        return refundPayment;
    }

    public void setRefundPayment(long refundPayment) {
        this.refundPayment = refundPayment;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

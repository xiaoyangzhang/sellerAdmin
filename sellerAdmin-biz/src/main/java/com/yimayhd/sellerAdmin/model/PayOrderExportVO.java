package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.domain.order.PayOperationDO;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.domain.order.PayOrderDetailDO;
import com.yimayhd.pay.client.model.enums.PayStatus;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by Administrator on 2015/12/8.
 */
public class PayOrderExportVO implements Serializable {
    private static final long serialVersionUID = -5914093680361571384L;
    private long id;
    private long userId;
    private long sellerId;
    private String authCode;
    private String outSellerId;
    private long totalAmount;
    private long discountableAmount;
    private long undiscountableAmount;
    private String subject;
    private String body;
    private String operatorId;
    private String storeId;
    private String terminalId;
    private String timeExpire;
    private String timeoutExpress;
    private String tradeNo;
    private String buyerUserId;
    private String buyerAccount;
    private long receiptAmount;
    private long invoiceAmount;
    private long buyerPayAmount;
    private long pointAmount;
    private Date gmtPayment;
    private Date gmtCancel;
    private Date gmtRefund;
    private String storeName;
    private int payStatus;
    private int payChannel;
    private long bizOrderId;
    private int outType;
    private Date gmtCreated;
    private Date gmtModified;
    private List<PayOrderDetailDO> payOrderDetailDOs;
    private List<PayOperationDO> payHistories;

    private double totalAmountY;//支付金额元
    private String payStatusName;//支付状态名称

    public static PayOrderExportVO getPayOrderExportVO(PayOrderDO payOrderDO) throws Exception{
        PayOrderExportVO payOrderExportVO = new PayOrderExportVO();
        BeanUtils.copyProperties(payOrderDO, payOrderExportVO);
        //分转元
        payOrderExportVO.setTotalAmountY(NumUtil.moneyTransformDouble(payOrderExportVO.getTotalAmount()));
        PayStatus payStatus = PayStatus.getByStatus(payOrderExportVO.getPayStatus());
        payOrderExportVO.setPayStatusName(null == payStatus ? "其他" : payStatus.getDesc());
        return payOrderExportVO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getOutSellerId() {
        return outSellerId;
    }

    public void setOutSellerId(String outSellerId) {
        this.outSellerId = outSellerId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getDiscountableAmount() {
        return discountableAmount;
    }

    public void setDiscountableAmount(long discountableAmount) {
        this.discountableAmount = discountableAmount;
    }

    public long getUndiscountableAmount() {
        return undiscountableAmount;
    }

    public void setUndiscountableAmount(long undiscountableAmount) {
        this.undiscountableAmount = undiscountableAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public long getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(long buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public long getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(long pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public Date getGmtCancel() {
        return gmtCancel;
    }

    public void setGmtCancel(Date gmtCancel) {
        this.gmtCancel = gmtCancel;
    }

    public Date getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(Date gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public int getOutType() {
        return outType;
    }

    public void setOutType(int outType) {
        this.outType = outType;
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

    public List<PayOrderDetailDO> getPayOrderDetailDOs() {
        return payOrderDetailDOs;
    }

    public void setPayOrderDetailDOs(List<PayOrderDetailDO> payOrderDetailDOs) {
        this.payOrderDetailDOs = payOrderDetailDOs;
    }

    public List<PayOperationDO> getPayHistories() {
        return payHistories;
    }

    public void setPayHistories(List<PayOperationDO> payHistories) {
        this.payHistories = payHistories;
    }

    public double getTotalAmountY() {
        return totalAmountY;
    }

    public void setTotalAmountY(double totalAmountY) {
        this.totalAmountY = totalAmountY;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }
}

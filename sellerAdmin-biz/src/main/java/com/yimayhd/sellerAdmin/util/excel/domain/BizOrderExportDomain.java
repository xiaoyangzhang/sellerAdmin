package com.yimayhd.sellerAdmin.util.excel.domain;

import com.yimayhd.sellerAdmin.util.NumUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuxiaopeng on 16/11/16.
 */
public class BizOrderExportDomain implements Serializable{

    private static final long serialVersionUID = 2109650230766674333L;

    /**
     * 订单编号
     */
    private long bizOrderId;

    /**
     * 父订单编号
     */
    private long parentBizOrderId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 商品id
     */
    private long commodityId;

    /**
     * 单价
     */
    private long unitPrice;

    /**
     * 数量
     */
    private long itemNum;

    /**
     * 类型
     */
    private String itemType;

    /**
     * 买家昵称
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerTel;

    /**
     * 订单状态
     */
    private String bizOrderType;

    /**
     * 商品价格
     */
    private long itemPrice;

    /**
     * 已优惠
     */
    private long discount;

    /**
     * 订单总价
     */
    private long bizOrderTotalPrice;

    /**
     * 实收款
     */
    private long realCollection;

    /**
     * 支付积分
     */
    private long payScore;

    /**
     * 创建订单时间
     */
    private Date bizOrderCreateTime;

    /**
     * 支付订单时间
     */
    private Date bizOrderPayTime;

    /**
     * 买家备注
     */
    private String buyerNotes;

    /**
     * 买家备注
     */
    private String sellerNotes;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号码
     */
    private String consigneeTel;

    /**
     * 收货省
     */
    private String receivingProvince;

    /**
     * 收货市
     */
    private String receivingCity;

    /**
     * 收货地区
     */
    private String receivingArea;

    /**
     * 收货详细地址
     */
    private String receivingAdress;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人手机
     */
    private String contactsTel;

    /**
     * 联系人邮箱
     */
    private String contactsEmail;

    /**
     * 游客姓名
     */
    private String touristName;

    /**
     * 手机号
     */
    private String telNo;

    /**
     * 证件类型
     */
    private int credentialsType;

    /**
     * 证件号
     */
    private long credentialsNo;

    /**
     * 关单原因
     */
    private String closeBizReason;

    /**
     * 游客信息
     */
    private String travelListStr;

    public String getTravelListStr() {
        return travelListStr;
    }

    public void setTravelListStr(String travelListStr) {
        this.travelListStr = travelListStr;
    }

    public long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public long getParentBizOrderId() {
        return parentBizOrderId;
    }

    public void setParentBizOrderId(long parentBizOrderId) {
        this.parentBizOrderId = parentBizOrderId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public String getUnitPrice() {
        return NumUtil.moneyTransform(unitPrice);
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getItemNum() {
        return itemNum;
    }

    public void setItemNum(long itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBizOrderType() {
        return bizOrderType;
    }

    public void setBizOrderType(String bizOrderType) {
        this.bizOrderType = bizOrderType;
    }

    public String getItemPrice() {
        return NumUtil.moneyTransform(itemPrice);
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDiscount() {
        return NumUtil.moneyTransform(discount);
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getBizOrderTotalPrice() {
        return NumUtil.moneyTransform(bizOrderTotalPrice);
    }

    public void setBizOrderTotalPrice(long bizOrderTotalPrice) {
        this.bizOrderTotalPrice = bizOrderTotalPrice;
    }

    public String getRealCollection() {
        return NumUtil.moneyTransform(realCollection);
    }

    public void setRealCollection(long realCollection) {
        this.realCollection = realCollection;
    }

    public long getPayScore() {
        return payScore;
    }

    public void setPayScore(long payScore) {
        this.payScore = payScore;
    }

    public Date getBizOrderCreateTime() {
        return bizOrderCreateTime;
    }

    public void setBizOrderCreateTime(Date bizOrderCreateTime) {
        this.bizOrderCreateTime = bizOrderCreateTime;
    }

    public Date getBizOrderPayTime() {
        return bizOrderPayTime;
    }

    public void setBizOrderPayTime(Date bizOrderPayTime) {
        this.bizOrderPayTime = bizOrderPayTime;
    }

    public String getBuyerNotes() {
        return buyerNotes;
    }

    public void setBuyerNotes(String buyerNotes) {
        this.buyerNotes = buyerNotes;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getReceivingProvince() {
        return receivingProvince;
    }

    public void setReceivingProvince(String receivingProvince) {
        this.receivingProvince = receivingProvince;
    }

    public String getReceivingCity() {
        return receivingCity;
    }

    public void setReceivingCity(String receivingCity) {
        this.receivingCity = receivingCity;
    }

    public String getReceivingArea() {
        return receivingArea;
    }

    public void setReceivingArea(String receivingArea) {
        this.receivingArea = receivingArea;
    }

    public String getReceivingAdress() {
        return receivingAdress;
    }

    public void setReceivingAdress(String receivingAdress) {
        this.receivingAdress = receivingAdress;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public int getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(int credentialsType) {
        this.credentialsType = credentialsType;
    }

    public long getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(long credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getCloseBizReason() {
        return closeBizReason;
    }

    public void setCloseBizReason(String closeBizReason) {
        this.closeBizReason = closeBizReason;
    }

    public String getSellerNotes() {
        return sellerNotes;
    }

    public void setSellerNotes(String sellerNotes) {
        this.sellerNotes = sellerNotes;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}

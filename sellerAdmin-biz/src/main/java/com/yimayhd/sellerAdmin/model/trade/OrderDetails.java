package com.yimayhd.sellerAdmin.model.trade;

import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class OrderDetails {
    private MainOrder mainOrder;
    // 联系人
    private ContactUser contacts;
    // 游客
    private List<ContactUser> tourists;

    private String buyerName;

    private String buyerNiceName;

    private String buyerPhoneNum;

    private String buyerMemo;

    private long totalFee;

    private long actualTotalFee;

    private int payChannel;

    private String closeReason;

    private Date consignTime;

    public MainOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(MainOrder mainOrder) {
        this.mainOrder = mainOrder;
    }

    public ContactUser getContacts() {
        return contacts;
    }

    public void setContacts(ContactUser contacts) {
        this.contacts = contacts;
    }

    public List<ContactUser> getTourists() {
        return tourists;
    }

    public void setTourists(List<ContactUser> tourists) {
        this.tourists = tourists;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNiceName() {
        return buyerNiceName;
    }

    public void setBuyerNiceName(String buyerNiceName) {
        this.buyerNiceName = buyerNiceName;
    }

    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getActualTotalFee() {
        return actualTotalFee;
    }

    public void setActualTotalFee(long actualTotalFee) {
        this.actualTotalFee = actualTotalFee;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }
}

package com.yimayhd.sellerAdmin.client.model.reply;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi on 16/11/15.
 */
public class ReplyVO implements Serializable {
    private static final long serialVersionUID = -2448181062692430426L;
    private long id;

    private int domain;

    private long skuId;

    private long itemId;

    private long sellerId;

    private long orderId;

    private long mainOrderId;

    private int orderType;

    private long outId;

    private int outType;

    private long userId;

    private String content;

    private String picUrls;

    private int anony;

    private long score;

    private int hasPic;

    private int rateStatus;

    private String feature;

    private Date backTime;

    private String backContent;

    private String backPics;

    private Date addTime;

    private String addContent;

    private String addPics;

    private int status;

    private int version;

    private Date gmtCreated;

    private Date gmtModified;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(long mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public long getOutId() {
        return outId;
    }

    public void setOutId(long outId) {
        this.outId = outId;
    }

    public int getOutType() {
        return outType;
    }

    public void setOutType(int outType) {
        this.outType = outType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public int getAnony() {
        return anony;
    }

    public void setAnony(int anony) {
        this.anony = anony;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getHasPic() {
        return hasPic;
    }

    public void setHasPic(int hasPic) {
        this.hasPic = hasPic;
    }

    public int getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(int rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getBackPics() {
        return backPics;
    }

    public void setBackPics(String backPics) {
        this.backPics = backPics;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddContent() {
        return addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public String getAddPics() {
        return addPics;
    }

    public void setAddPics(String addPics) {
        this.addPics = addPics;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

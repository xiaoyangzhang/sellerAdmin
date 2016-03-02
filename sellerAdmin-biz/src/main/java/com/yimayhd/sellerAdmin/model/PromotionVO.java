package com.yimayhd.sellerAdmin.model;


import com.yimayhd.promotion.client.domain.PromotionDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/1/11.
 */
public class PromotionVO extends PromotionDO {

    private double valueY;//金额或者折扣
    private boolean isDel = false;
    private boolean isModify = false;
    private long itemId;
    private long itemSkuId;
    private double priceY;
    private String itemTitle;
    private String skuTitle;
    private long stockNum;
    private int itemStatus;

    public static PromotionVO getPromotionVO(PromotionDO promotionDO){
        PromotionVO promotionVO = new PromotionVO();
        BeanUtils.copyProperties(promotionDO,promotionVO);
        promotionVO.setValueY(promotionDO.getValue() / 100);
        return promotionVO;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setIsModify(boolean isModify) {
        this.isModify = isModify;
    }

    public double getValueY() {
        return valueY;
    }

    public void setValueY(double valueY) {
        this.valueY = valueY;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public long getStockNum() {
        return stockNum;
    }

    public void setStockNum(long stockNum) {
        this.stockNum = stockNum;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }
}

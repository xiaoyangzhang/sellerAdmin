package com.yimayhd.sellerAdmin.model;

import com.yimayhd.activitycenter.domain.ActActivityDO;

/**
 * Created by czf on 2016/2/6.
 */
public class ActActivityVO extends ActActivityDO {

    private int entityType;

    private int entityId;//店铺优惠用

    private double requirementY;

    private double valueY;

    private int promotionType;

    private String startDateStr;

    private String endDateStr;

    private int shopPromotionId;

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public double getRequirementY() {
        return requirementY;
    }

    public void setRequirementY(double requirementY) {
        this.requirementY = requirementY;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public double getValueY() {
        return valueY;
    }

    public void setValueY(double valueY) {
        this.valueY = valueY;
    }

    public int getShopPromotionId() {
        return shopPromotionId;
    }

    public void setShopPromotionId(int shopPromotionId) {
        this.shopPromotionId = shopPromotionId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}

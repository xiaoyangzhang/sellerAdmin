package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Club extends BaseModel {
    private String name;//名称
    private String logoUrl;//logo
    private String picturePoster;//俱乐部宣传图
    private Integer sequenceNum;//排序
    private Integer joinStatus;//是否允许加入状态
    private Integer showStatus;//是否显示状态
    private Long joinNum;//成员数
    private Long limitNum;//成员限额
    private String manageUserName;//部长名称
    private String manageUserLogoUrl;//部长logo
    private Long hasActivityNum;//累计活动

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPicturePoster() {
        return picturePoster;
    }

    public void setPicturePoster(String picturePoster) {
        this.picturePoster = picturePoster;
    }

    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public Long getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Long joinNum) {
        this.joinNum = joinNum;
    }

    public Long getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Long limitNum) {
        this.limitNum = limitNum;
    }

    public String getManageUserName() {
        return manageUserName;
    }

    public void setManageUserName(String manageUserName) {
        this.manageUserName = manageUserName;
    }

    public String getManageUserLogoUrl() {
        return manageUserLogoUrl;
    }

    public void setManageUserLogoUrl(String manageUserLogoUrl) {
        this.manageUserLogoUrl = manageUserLogoUrl;
    }

    public Long getHasActivityNum() {
        return hasActivityNum;
    }

    public void setHasActivityNum(Long hasActivityNum) {
        this.hasActivityNum = hasActivityNum;
    }
}

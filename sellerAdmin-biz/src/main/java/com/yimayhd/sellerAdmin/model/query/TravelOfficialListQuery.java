package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialListQuery extends BaseQuery {
    private Integer travelStatus;
    private String title;
    private Long regionId;
    private String regionName;
    private String userName;


    private String travelName;  //  游记名称
    private String startDate;   //发布开始时间
    private String endDate;     //
    private String nickName;
    private String poiContent;

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPoiContent() {
        return poiContent;
    }

    public void setPoiContent(String poiContent) {
        this.poiContent = poiContent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

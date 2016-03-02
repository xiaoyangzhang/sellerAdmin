package com.yimayhd.sellerAdmin.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Activity{
    private Long id; // 俱乐部活动

    private Long outId; // 外键

    private String title; // 活动标题

    private String clubName; // 俱乐部名称

    private Integer memberCount; // 人数

    private Integer memberCountLimit; // 报名人数限制

    private Long originalPrice; // 原价

    private Long preferentialPrice; // 优惠价

    private Integer status; // 活动状态 未开始、已满员、报名中、已结束

    private String image; // 图片

    private Date modifyTime; //

    private Date createrTime; //

    private Integer outType; // 活动类型

    private String club; // 预留字段

    private String favorName; // 活动优惠名称

    private String favorComent; // 活动优惠内容

    private String recruitingGroups; // 招募的对象

    private Date startDate; // 开始时间

    private Date endDate; // 结束时间

    private String poiContent; // 地理位置信息

    private String costDesc; // 费用说明

    private String activeRoute; // 行程详情

    private String serviceTel; // 客服电话

    private String content; // 活动描述

    private Long productId; // 商品id

    private Integer isStatus; // 是否显示 是、否

    private String clubImg; // 俱乐部logo

    private Date enrollBeginDate;//报名开始时间

    private Date enrollEndDate;//报名结束时间

    private Integer sequenceNum;//排序

    private Long provinceId;//省份
    private String provinceName;//省份

    private Long cityId;//城市
    private String cityName;//城市名称

    private String addrDetail;//详细地址

    private String outline;//概要

    private List<String> tagList;//标签

    private String costExplain;//费用说明
    private String reminder;//温馨提示
    private String buyProcess;//购买流程

    private String activityDetailWeb;//活动详情web
    private String activityDetailWebUrl;//活动详情webUrl
    private String activityDetailApp;//活动详情app
    private String activityDetailAppUrl;//活动详情appUrl



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(Long preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFavorName() {
        return favorName;
    }

    public void setFavorName(String favorName) {
        this.favorName = favorName;
    }

    public String getFavorComent() {
        return favorComent;
    }

    public void setFavorComent(String favorComent) {
        this.favorComent = favorComent;
    }

    public String getRecruitingGroups() {
        return recruitingGroups;
    }

    public void setRecruitingGroups(String recruitingGroups) {
        this.recruitingGroups = recruitingGroups;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPoiContent() {
        return poiContent;
    }

    public void setPoiContent(String poiContent) {
        this.poiContent = poiContent;
    }

    public String getCostDesc() {
        return costDesc;
    }

    public void setCostDesc(String costDesc) {
        this.costDesc = costDesc;
    }

    public String getActiveRoute() {
        return activeRoute;
    }

    public void setActiveRoute(String activeRoute) {
        this.activeRoute = activeRoute;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    public String getClubImg() {
        return clubImg;
    }

    public void setClubImg(String clubImg) {
        this.clubImg = clubImg;
    }

    public Date getEnrollBeginDate() {
        return enrollBeginDate;
    }

    public void setEnrollBeginDate(Date enrollBeginDate) {
        this.enrollBeginDate = enrollBeginDate;
    }

    public Date getEnrollEndDate() {
        return enrollEndDate;
    }

    public void setEnrollEndDate(Date enrollEndDate) {
        this.enrollEndDate = enrollEndDate;
    }

    public Integer getMemberCountLimit() {
        return memberCountLimit;
    }

    public void setMemberCountLimit(Integer memberCountLimit) {
        this.memberCountLimit = memberCountLimit;
    }

    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getCostExplain() {
        return costExplain;
    }

    public void setCostExplain(String costExplain) {
        this.costExplain = costExplain;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getBuyProcess() {
        return buyProcess;
    }

    public void setBuyProcess(String buyProcess) {
        this.buyProcess = buyProcess;
    }

    public String getActivityDetailWeb() {
        return activityDetailWeb;
    }

    public void setActivityDetailWeb(String activityDetailWeb) {
        this.activityDetailWeb = activityDetailWeb;
    }

    public String getActivityDetailApp() {
        return activityDetailApp;
    }

    public void setActivityDetailApp(String activityDetailApp) {
        this.activityDetailApp = activityDetailApp;
    }

    public String getActivityDetailWebUrl() {
        return activityDetailWebUrl;
    }

    public void setActivityDetailWebUrl(String activityDetailWebUrl) {
        this.activityDetailWebUrl = activityDetailWebUrl;
    }

    public String getActivityDetailAppUrl() {
        return activityDetailAppUrl;
    }

    public void setActivityDetailAppUrl(String activityDetailAppUrl) {
        this.activityDetailAppUrl = activityDetailAppUrl;
    }
}

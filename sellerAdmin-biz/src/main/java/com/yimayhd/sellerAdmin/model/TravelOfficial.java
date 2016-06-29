package com.yimayhd.sellerAdmin.model;

import java.util.Date;
import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseModel;
import com.yimayhd.snscenter.client.domain.TravelJsonDO;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficial extends BaseModel {

  
	private static final long serialVersionUID = 1L;
    private long id;
	private String title;//
    private Long regionId;//
    private String regionName;//

    private Integer travelStatus;//
    private Integer browseNum;
    private Date publishDate;//发布时间
    private Integer praiseNum;//点赞数
    private Integer collectionNum;//收藏数
    private Integer pv;//浏览量
    private String backImg; // 封面
    private String preface;//前言
    private String imgContentJson;//图文详情
    private List<TravelJsonDO> listTravelJsonDO;//把图文详情转换成这个list对象
    //用户的
    private long createId;
    private String userPhoto;
    private String nickName;
    private String createUserName;
    
    public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}

	public String getImgContentJson() {
        return imgContentJson;
    }

    public void setImgContentJson(String imgContentJson) {
        this.imgContentJson = imgContentJson;
    }

    public String getUserPhoto() { return userPhoto; }

    public void setUserPhoto(String userPhoto) { this.userPhoto = userPhoto; }

    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName;}

    public long getCreateId() { return createId; }

    public void setCreateId(long createId) { this.createId = createId; }


	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
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

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public List<TravelJsonDO> getListTravelJsonDO() {
        return listTravelJsonDO;
    }

    public void setListTravelJsonDO(List<TravelJsonDO> listTravelJsonDO) {
        this.listTravelJsonDO = listTravelJsonDO;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}

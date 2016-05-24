package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.ic.client.model.domain.item.RoomFeature;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 16/5/24.
 */
public class RoomMessageVO implements Serializable {

    private static final long serialVersionUID = 6247666067320834527L;

    private long				roomId;
    private String				name;
    private long				hotelId;
    private int					domain;
    private int 				status;
    private Double    area;//面积
    private Integer peopleNum ;//人数
    // 床型,面积,网络,窗,加床,楼层,人数
    private RoomFeature feature;
    private long				version;
    private List<String> pics;
    private Date gmtModified;
    private Date				gmtCreated;



    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RoomFeature getFeature() {
        return feature;
    }

    public void setFeature(RoomFeature feature) {
        this.feature = feature;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }
}

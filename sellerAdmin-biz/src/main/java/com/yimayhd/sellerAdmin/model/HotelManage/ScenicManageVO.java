package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageVO extends BaseQuery {

    private Long id ;// '自增id',
    private String name ;// '景点名称'

    private Integer provinceId;//省id
    private Integer cityId;//市d
    private Integer townId;//区id
    private Integer level;//景区等级

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

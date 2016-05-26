package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.sellerAdmin.base.BaseQuery;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageVO extends BaseQuery {
    private Long sellerId;//商家ID
    private Long scenicId;//景区资源
    private Long itemId ;// '自增id',
    private String name ;// '景点名称'
    private String title;//标题
    private Long price;//价格
    private Long originalPrice;//门市价格
    private Integer ticketId;//票型ID
    private String ticketTitle;//票型名称


//    private Integer provinceId;//省id
//    private Integer cityId;//市d
//    private Integer townId;//区id
    private Integer level;//景区等级

    private String locationText;

    private long locationProvinceId;

    private String locationProvinceName;

    private long locationCityId;

    private String locationCityName;

    private long locationTownId;

    private String locationTownName;

    private Long categoryId;//类目ID
    private Integer startBookTimeLimit;// 提前预定天数

    private String supplierCalendar;//价格日历json 信息,

    private List<TicketDO> ticketDOList;//景区门票类型

    private String  dynamicEntry;//列表参数  json

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public long getLocationProvinceId() {
        return locationProvinceId;
    }

    public void setLocationProvinceId(long locationProvinceId) {
        this.locationProvinceId = locationProvinceId;
    }

    public String getLocationProvinceName() {
        return locationProvinceName;
    }

    public void setLocationProvinceName(String locationProvinceName) {
        this.locationProvinceName = locationProvinceName;
    }

    public long getLocationCityId() {
        return locationCityId;
    }

    public void setLocationCityId(long locationCityId) {
        this.locationCityId = locationCityId;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public long getLocationTownId() {
        return locationTownId;
    }

    public void setLocationTownId(long locationTownId) {
        this.locationTownId = locationTownId;
    }

    public String getLocationTownName() {
        return locationTownName;
    }

    public void setLocationTownName(String locationTownName) {
        this.locationTownName = locationTownName;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStartBookTimeLimit() {
        return startBookTimeLimit;
    }

    public void setStartBookTimeLimit(Integer startBookTimeLimit) {
        this.startBookTimeLimit = startBookTimeLimit;
    }

    public String getSupplierCalendar() {
        return supplierCalendar;
    }

    public void setSupplierCalendar(String supplierCalendar) {
        this.supplierCalendar = supplierCalendar;
    }

    public List<TicketDO> getTicketDOList() {
        return ticketDOList;
    }

    public void setTicketDOList(List<TicketDO> ticketDOList) {
        this.ticketDOList = ticketDOList;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getDynamicEntry() {
        return dynamicEntry;
    }

    public void setDynamicEntry(String dynamicEntry) {
        this.dynamicEntry = dynamicEntry;
    }
}

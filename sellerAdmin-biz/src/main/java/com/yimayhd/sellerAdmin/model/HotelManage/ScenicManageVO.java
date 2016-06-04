package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.sellerAdmin.base.BaseQuery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageVO implements Serializable
{
    private static final long serialVersionUID = -1431722364275021759L;
    private long sellerId;//商家ID
    private long scenicId;//景区资源
    private long itemId ;// '自增id',
    private String name ;// '景点名称'
    private String title;//标题
    private BigDecimal price;//价格
    private BigDecimal originalPrice;//门市价格
    private long ticketId;//票型ID
    private String ticketTitle;//票型名称
    private String area;//区域
    private long subjectId;// 主题ID
    private String subjectName;//主题名称



//    private Integer provinceId;//省id
//    private Integer cityId;//市d
//    private Integer townId;//区id
    private int level;//景区等级

    private String locationText;

    private long locationProvinceId;

    private String locationProvinceName;

    private long locationCityId;

    private String locationCityName;

    private long locationTownId;

    private String locationTownName;

    private long categoryId;//类目ID
    private Integer startBookTimeLimit;// 提前预定天数

    private String supplierCalendar;//价格日历json 信息,

    private List<TicketDO> ticketDOList;//景区门票类型

    private String  dynamicEntry;//列表参数  json
    private List<BizCategoryInfo> bizCategoryInfoList;// 动态列表参数\
    private String UUID ;//uuid
    private Integer page =1;
    private Integer pageSize =8 ;


    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getScenicId() {
        return scenicId;
    }

    public void setScenicId(long scenicId) {
        this.scenicId = scenicId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
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

    public String getDynamicEntry() {
        return dynamicEntry;
    }

    public void setDynamicEntry(String dynamicEntry) {
        this.dynamicEntry = dynamicEntry;
    }

    public List<BizCategoryInfo> getBizCategoryInfoList() {
        return bizCategoryInfoList;
    }

    public void setBizCategoryInfoList(List<BizCategoryInfo> bizCategoryInfoList) {
        this.bizCategoryInfoList = bizCategoryInfoList;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}

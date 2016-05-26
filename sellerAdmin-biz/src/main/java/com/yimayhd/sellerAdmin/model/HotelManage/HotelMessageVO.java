package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.sellerAdmin.base.BaseQuery;

import java.util.List;

/**
 * 酒店信息
 * @author wangdi
 */
public class HotelMessageVO extends BaseQuery {

	private Long sellerId;// 商户Id
	private Long hotelId;//酒店ID
	private String name;//酒店名称
	private String area;//酒店区域
	private String address;//酒店地址
	private String phone;//酒店电话

//	private Long provinceId;//省id
//	private Long cityId;//市id
//	private Long townId;//区id
private String locationText;

	private Long locationProvinceId;

	private String locationProvinceName;

	private Long locationCityId;

	private String locationCityName;

	private Long locationTownId;

	private String locationTownName;



	private String title; // '标题',
	private String code; // '商品代码',
	private Integer payType  ; // COMMENT '可支持支付方式',

	private Long itemId;// 商品Id

	private Integer outType;//商品类型:1.酒店.2客栈
	private String outTypeStr;//商品类型:1.酒店.2客栈
	private Integer cancelLimit;// 退订限制:1,可退,2不可退,3免费退
	private String cancelLimitStr;// 退订限制:1,可退,2不可退,3免费退
	private String description;// 退订规则
	private List<Integer> latestCheckin;//最晚到店时间 中台
	private String storeLastTime;// 最晚到店时间
	private Integer startBookTimeLimit;// 提前预定天数
	private Integer breakfast;//早餐
	//{"supplier_calendar":{ "seller_id":"2088102122524333","biz_list":[{"stock_num":10,"price":"8.8","vTxt":"2088101117955611"}]}}
	private String supplierCalendar;//价格日历json 信息,
	private Long roomId;//房型ID

	private RoomMessageVO roomMessageVO;//房型
	private List<RoomMessageVO> listRoomMessageVO;//房型信息

	private Long categoryId;

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}



	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getOutType() {
		return outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public Integer getCancelLimit() {
		return cancelLimit;
	}

	public void setCancelLimit(Integer cancelLimit) {
		this.cancelLimit = cancelLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Integer> getLatestCheckin() {
		return latestCheckin;
	}

	public void setLatestCheckin(List<Integer> latestCheckin) {
		this.latestCheckin = latestCheckin;
	}

	public String getStoreLastTime() {
		return storeLastTime;
	}

	public void setStoreLastTime(String storeLastTime) {
		this.storeLastTime = storeLastTime;
	}

	public Integer getStartBookTimeLimit() {
		return startBookTimeLimit;
	}

	public void setStartBookTimeLimit(Integer startBookTimeLimit) {
		this.startBookTimeLimit = startBookTimeLimit;
	}

	public Integer getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public List<RoomMessageVO> getListRoomMessageVO() {
		return listRoomMessageVO;
	}

	public void setListRoomMessageVO(List<RoomMessageVO> listRoomMessageVO) {
		this.listRoomMessageVO = listRoomMessageVO;
	}

	public String getSupplierCalendar() {
		return supplierCalendar;
	}

	public void setSupplierCalendar(String supplierCalendar) {
		this.supplierCalendar = supplierCalendar;
	}

	public RoomMessageVO getRoomMessageVO() {
		return roomMessageVO;
	}

	public void setRoomMessageVO(RoomMessageVO roomMessageVO) {
		this.roomMessageVO = roomMessageVO;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getOutTypeStr() {
		return outTypeStr;
	}

	public void setOutTypeStr(String outTypeStr) {
		this.outTypeStr = outTypeStr;
	}


	public String getCancelLimitStr() {
		return cancelLimitStr;
	}

	public void setCancelLimitStr(String cancelLimitStr) {
		this.cancelLimitStr = cancelLimitStr;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getLocationText() {
		return locationText;
	}

	public void setLocationText(String locationText) {
		this.locationText = locationText;
	}

	public Long getLocationProvinceId() {
		return locationProvinceId;
	}

	public void setLocationProvinceId(Long locationProvinceId) {
		this.locationProvinceId = locationProvinceId;
	}

	public String getLocationProvinceName() {
		return locationProvinceName;
	}

	public void setLocationProvinceName(String locationProvinceName) {
		this.locationProvinceName = locationProvinceName;
	}

	public Long getLocationCityId() {
		return locationCityId;
	}

	public void setLocationCityId(Long locationCityId) {
		this.locationCityId = locationCityId;
	}

	public String getLocationCityName() {
		return locationCityName;
	}

	public void setLocationCityName(String locationCityName) {
		this.locationCityName = locationCityName;
	}

	public Long getLocationTownId() {
		return locationTownId;
	}

	public void setLocationTownId(Long locationTownId) {
		this.locationTownId = locationTownId;
	}

	public String getLocationTownName() {
		return locationTownName;
	}

	public void setLocationTownName(String locationTownName) {
		this.locationTownName = locationTownName;
	}
}

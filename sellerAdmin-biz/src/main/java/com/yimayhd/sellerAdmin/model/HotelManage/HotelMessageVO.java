package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.sellerAdmin.base.BaseQuery;

import java.util.List;

/**
 * 酒店信息
 * @author wangdi
 */
public class HotelMessageVO extends BaseQuery {

	private Long hotelId;//酒店ID
	private String name;//酒店名称
	private String area;//酒店区域
	private String address;//酒店地址
	private String phone;//酒店电话

	private Long provinceId;//省id
	private Long cityId;//市id
	private Long townId;//区id


	private String title; // '标题',
	private String code; // '商品代码',
	private Integer payType  ; // COMMENT '可支持支付方式',

	private Long itemId;// 商品Idl

	private Integer type;//商品类型:1.酒店.2客栈
	private Integer cancelLimit;// 退订限制:1,可退,2不可退,3免费退
	private String description;// 退订规则
	private List<Integer> latestCheckin;//最晚到店时间 中台
	private String storeLastTime;// 最晚到店时间
	private Long startBookTimeLimit;// 提前预定天数
	private Integer breakfast;//早餐

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

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Long getStartBookTimeLimit() {
		return startBookTimeLimit;
	}

	public void setStartBookTimeLimit(Long startBookTimeLimit) {
		this.startBookTimeLimit = startBookTimeLimit;
	}

	public Integer getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}
}

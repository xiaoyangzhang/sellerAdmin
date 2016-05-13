package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * 酒店信息
 * @author wangdi
 */
public class HotelMessageVO extends BaseQuery {

	private Long hotelId;//酒店ID
	private String name;//酒店名称
	private String area;//酒店区域
	private String address;//酒店地址
	private Integer phone;//酒店电话

	private Integer provinceId;//省id
	private Integer cityId;//市d
	private Integer townId;//区id


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

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
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
}

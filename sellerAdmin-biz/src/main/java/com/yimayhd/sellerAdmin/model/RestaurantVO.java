package com.yimayhd.sellerAdmin.model;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;

/**
 * 餐厅表单对象
 * 
 * @author yebin
 *
 */
public class RestaurantVO {
	private long id;
	private String name;
	private String description;
	private MasterRecommend recommend;
	private String logoUrl;
	private double locationX;
	private double locationY;
	private String locationText;
	private long locationProvinceId;
	private String locationProvinceName;
	private long locationCityId;
	private String locationCityName;
	private long locationTownId;
	private String locationTownName;
	private String contactPerson;
	private String contactPhone;
	private String oneword;
	private String coverPics;
	private String picListStr;// 图片集的str

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MasterRecommend getRecommend() {
		return recommend;
	}

	public void setRecommend(MasterRecommend recommend) {
		this.recommend = recommend;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getOneword() {
		return oneword;
	}

	public void setOneword(String oneword) {
		this.oneword = oneword;
	}

	public String getCoverPics() {
		return coverPics;
	}

	public void setCoverPics(String coverPics) {
		this.coverPics = coverPics;
	}

	public RestaurantDO toRestaurantDO() {
		return toRestaurantDO(null);
	}

	public RestaurantDO toRestaurantDO(RestaurantDO restaurantDTO) {
		if (restaurantDTO == null) {
			restaurantDTO = new RestaurantDO();
		}
		BeanUtils.copyProperties(this, restaurantDTO);
		String coverPics = this.getCoverPics();
		if (StringUtils.isNotBlank(coverPics)) {
			restaurantDTO.setPictures(Arrays.asList(coverPics.split("\\|")));
		}
		return restaurantDTO;
	}

	public String getPicListStr() {
		return picListStr;
	}

	public void setPicListStr(String picListStr) {
		this.picListStr = picListStr;
	}
}

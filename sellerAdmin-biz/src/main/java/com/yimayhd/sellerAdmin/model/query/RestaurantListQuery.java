package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.model.Region;

/**
 * 列表查询类
 * 
 * @author yebin
 *
 */
public class RestaurantListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -872735064959961097L;
	private String name;// 名称
	private Integer status;// 状态
	private String provinceCode;// 省
	private Region cityCode;// 市
	private String personOrPhone;// 联系人
	private String beginTime;// 创建日期_开始时间
	private String endTime;// 创建日期_结束时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Region getCityCode() {
		return cityCode;
	}
	public void setCityCode(Region cityCode) {
		this.cityCode = cityCode;
	}
	public String getPersonOrPhone() {
		return personOrPhone;
	}
	public void setPersonOrPhone(String personOrPhone) {
		this.personOrPhone = personOrPhone;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}

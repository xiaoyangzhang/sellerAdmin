package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/9.
 */
public class UserListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -603714674325753343L;
	private String name;
	private String tel;
	private Long cityName;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getCityName() {
		return cityName;
	}

	public void setCityName(Long cityName) {
		this.cityName = cityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

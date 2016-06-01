package com.yimayhd.sellerAdmin.model.line;

import com.yimayhd.user.client.dto.CityDTO;

/**
 * 
 * 城市VO
 * 
 * @author yebin
 *
 */
public class CityVO extends TagDTO {
	private String code;
	private CityDTO city;

	public CityVO() {
		super();
	}

	public CityVO(long id, String name, CityDTO city) {
		super(id, name);
		this.city = city;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

package com.yimayhd.sellerAdmin.model.line;


/**
 * 
 * 城市VO
 * 
 * @author yebin
 *
 */
public class CityVO extends TagDTO {
	private String code;
	private City city;

	public CityVO() {
		super();
	}

	public CityVO(long id, String name, City city) {
		super(id, name);
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

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

	public CityVO() {
		super();
	}

	public CityVO(long id, String code, String name) {
		super(id, name);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

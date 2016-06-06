package com.yimayhd.sellerAdmin.model.line;

import java.io.Serializable;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5941714299727260712L;
	private String code;
	private String name;
	private String firstLetter;
	private int type; // 

	public City(String code, String name, String firstLetter) {

		this.code = code;
		this.name = name;
		this.firstLetter = firstLetter;
//		this.type = type;
	}
	
//	public City(String code, String name,int type) {
//		this(code,name,null,type);
//	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

package com.yimayhd.sellerAdmin.model.line.pictxt;

import java.io.Serializable;

/**
 * 图文项
 * 
 * @author yebin
 *
 */
public class PictureTextItemVo implements Serializable{
	private static final long serialVersionUID = -6988747828375320041L;
	private String type;
	private String value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

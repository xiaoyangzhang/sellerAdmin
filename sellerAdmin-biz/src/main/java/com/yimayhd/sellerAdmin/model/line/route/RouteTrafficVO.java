package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;

/**
 * 行程交通方式
 * 
 * @author yebin
 *
 */
public class RouteTrafficVO implements Serializable {
	private static final long serialVersionUID = -492091641597506573L;
	private Integer type;
	private String description;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package com.yimayhd.gf.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

public class GFTagVoQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;
	
	public String name;
	
	public int status;
	
	public int type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

	
	 
	
	
	
}

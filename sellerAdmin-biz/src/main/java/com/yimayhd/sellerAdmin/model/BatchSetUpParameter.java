package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.List;

public class BatchSetUpParameter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
}

package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;

public class MerchantVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8880248623904097407L;
	private int domainId;
	private long userId;
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	

}

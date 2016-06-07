package com.yimayhd.commission.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

public class CommissionListQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405732862120567997L;
	
	/**
	 * 用户账号
	 */
	private String userName;
	
	/**
	 * 联系电话
	 */
	private String telNum;
	/**
	 * 系统标识ID
	 */
	private long domainId;
	
	/**
	 * 会员名称
	 */
	private String payeeAccountName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}
	
	
}

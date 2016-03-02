package com.yimayhd.commission.model.param;

import java.io.Serializable;

public class ExtractDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1531930227548183513L;
	/**用户账号*/
	private long userId;
	/**会员名称*/
	private String userName;
	/**系统标识ID*/
	private int domainId;
	/***/
	private long fromId;
	/**联系电话*/
	private String telNum;
	/**用户账户*/
	private String payeeAccount;
	/**返现金额*/
	private String commissionAmt;
	
    public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public long getFromId() {
		return fromId;
	}
	public void setFromId(long fromId) {
		this.fromId = fromId;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	public String getCommissionAmt() {
		return commissionAmt;
	}
	public void setCommissionAmt(String commissionAmt) {
		this.commissionAmt = commissionAmt;
	}
    
}

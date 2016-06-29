package com.yimayhd.sellerAdmin.model.draft;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;

public class DraftVO implements Serializable{

	/**
	 * serialVersionUID = -1798221570651670075L;
	 */
	private static final long serialVersionUID = -1798221570651670075L;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 草稿名称
	 */
	private String draftName;
	
	/**
	 * 草稿内容序列化后的对象
	 */
	private Object jsonObject;
	
	/**
	 * 子类型
	 */
	private int subType;
	
	/**
	 * 主类型
	 */
	private int mainType;

	/**
	 * 账号id
	 */
	private Long accountId;

	/**
	 * domainId
	 */
	private int domainId;

	/**
	 * 子类型显示用
	 */
	private String subTypeName;

	/**
	 * 创建时间
	 */
	private Date gmtCreated;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDraftName() {
		return draftName;
	}

	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	public Object getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(Object jsonObject) {
		this.jsonObject = jsonObject;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public int getMainType() {
		return mainType;
	}

	public void setMainType(int mainType) {
		this.mainType = mainType;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}

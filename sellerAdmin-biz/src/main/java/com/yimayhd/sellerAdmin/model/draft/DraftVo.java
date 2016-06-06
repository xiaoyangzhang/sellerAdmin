package com.yimayhd.sellerAdmin.model.draft;

import java.io.Serializable;

public class DraftVo implements Serializable{

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

}

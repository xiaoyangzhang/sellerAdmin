package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;

public class HomeAdvertiseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882178949754280883L;

	private Long showcaseId;

	private String title;

	private String summary;

	private String imgUrl;

	private String itemId;

	private String itemType;

	private String itemName;
	
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getShowcaseId() {
		return showcaseId;
	}

	public void setShowcaseId(Long showcaseId) {
		this.showcaseId = showcaseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}

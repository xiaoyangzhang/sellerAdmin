package com.yimayhd.sellerAdmin.model.vo;

import java.io.Serializable;

/**
  * @autuor : xusq
  * @date : 2015年11月30日
  * @description : 回显showcase信息
  */
public class CfgResultInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3894408522360799520L;
	
	private long showCaseId;

	private long itemId;
	
	private String itemTitle;
	
	private String itemDesc;
	
	private long itemPrice;
	
	private String itemImg;
	
	private String extName;
	
	private String extDesc;
	
	private String extImg;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public String getExtDesc() {
		return extDesc;
	}

	public void setExtDesc(String extDesc) {
		this.extDesc = extDesc;
	}

	public String getExtImg() {
		return extImg;
	}

	public void setExtImg(String extImg) {
		this.extImg = extImg;
	}

	public long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(long itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public long getShowCaseId() {
		return showCaseId;
	}

	public void setShowCaseId(long showCaseId) {
		this.showCaseId = showCaseId;
	}
	
	
}

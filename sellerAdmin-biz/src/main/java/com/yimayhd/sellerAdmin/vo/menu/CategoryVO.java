package com.yimayhd.sellerAdmin.vo.menu;

import java.io.Serializable;

public class CategoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -174622783734382598L;
	
	private long categoryId;
	private String categoryName;
	private boolean isLeaf;
	private int level;
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}

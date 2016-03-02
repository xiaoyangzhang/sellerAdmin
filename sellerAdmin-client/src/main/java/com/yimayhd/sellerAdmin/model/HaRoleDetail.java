package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

public class HaRoleDetail extends BaseModel {

	private static final long serialVersionUID = -1432374716510275302L;

	private String menuName;
	
	private String menuUrl;
	
	private Long roleId;
	
	// Key 
	private Long roleMenuId;
	
	private Boolean isOwn;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Boolean getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(Boolean isOwn) {
		this.isOwn = isOwn;
	}
	
}

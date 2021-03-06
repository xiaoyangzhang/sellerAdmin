package com.yimayhd.sellerAdmin.model.vo.menu;

import java.io.Serializable;
import java.util.List;

public class MenuVO implements Serializable, Comparable<MenuVO> {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * url
	 */
	private String url;
	/**
	 * 是否是叶子节点
	 */
	private boolean leaf;
	/**
	 * 父级菜单ID
	 */
	private long parentId;

	private int type;

	private int requestType;

	private String parentName;
	/**
	 * 子菜单
	 */
	private List<MenuVO> children;

	public boolean hasParent() {
		if (parentId > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(MenuVO o) {
		if (o == null) {
			return 1;
		} else {
			if (parentId > o.parentId) {
				return 1;
			} else if (parentId == o.parentId) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

}

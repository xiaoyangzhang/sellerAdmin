package com.yimayhd.sellerAdmin.base.menu;

import java.io.Serializable;

public class LocationVO implements Serializable {
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
	 * 下一级路径
	 */
	private LocationVO next;

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

	public LocationVO getNext() {
		return next;
	}

	public void setNext(LocationVO next) {
		this.next = next;
	}


}

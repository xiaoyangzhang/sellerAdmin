package com.yimayhd.sellerAdmin.model.line;

import com.yimayhd.ic.client.model.domain.share_json.RouteTextItem;

/**
 * 只包含ID和名称的数据对象
 * 
 * @author yebin
 *
 */
public class IdNamePair {
	private long id;
	private String name;

	public IdNamePair() {
	}

	public IdNamePair(RouteTextItem routeTextItem) {
		this.id = routeTextItem.getId();
		this.name = routeTextItem.getName();
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
}

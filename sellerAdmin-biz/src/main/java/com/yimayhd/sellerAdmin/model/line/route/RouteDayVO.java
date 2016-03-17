package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;

/**
 * 行程-天
 * 
 * @author yebin
 *
 */
public class RouteDayVO implements Serializable {
	private static final long serialVersionUID = 7070001338543340926L;
	private long routeItemId;
	private String title;
	private String description;
	private List<String> picUrls;

	public RouteDayVO() {
	}

	public RouteDayVO(RouteItemDetail detail) {
		if (detail != null) {
			this.routeItemId = detail.getId();
			this.title = detail.getName();
			this.description = detail.getShortDesc();
			this.picUrls = detail.getPics();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}

	public long getRouteItemId() {
		return routeItemId;
	}

	public void setRouteItemId(long routeItemId) {
		this.routeItemId = routeItemId;
	}
}

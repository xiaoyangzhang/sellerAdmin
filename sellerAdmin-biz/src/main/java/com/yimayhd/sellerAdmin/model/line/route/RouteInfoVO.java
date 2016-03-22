package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 行程信息
 * 
 * @author yebin
 *
 */
public class RouteInfoVO implements Serializable {
	private static final long serialVersionUID = -5895286238386434516L;

	private long routeId;
	private List<RouteDayVO> routeDays;
	private Set<Long> updatedRouteItems;
	private Set<Long> deletedRouteItems;

	public Set<Long> getUpdatedRouteItems() {
		return updatedRouteItems;
	}

	public void setUpdatedRouteItems(Set<Long> updatedRouteItems) {
		this.updatedRouteItems = updatedRouteItems;
	}

	public Set<Long> getDeletedRouteItems() {
		return deletedRouteItems;
	}

	public void setDeletedRouteItems(Set<Long> deletedRouteItems) {
		this.deletedRouteItems = deletedRouteItems;
	}

	public long getRouteId() {
		return routeId;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	public void setRouteDays(List<RouteDayVO> routeDays) {
		this.routeDays = routeDays;
	}

	public List<RouteDayVO> getRouteDays() {
		return routeDays;
	}
}

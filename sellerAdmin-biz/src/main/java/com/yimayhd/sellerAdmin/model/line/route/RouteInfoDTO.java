package com.yimayhd.sellerAdmin.model.line.route;

import java.util.List;

import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.param.item.line.RouteItemUpdateDTO;

/**
 * 行程信息
 * 
 * @author yebin
 *
 */
public class RouteInfoDTO {
	private List<RouteItemDO> addRouteItemList;
	private List<Long> delRouteItemList;
	private List<RouteItemUpdateDTO> updrouteItemList;

	public List<RouteItemDO> getAddRouteItemList() {
		return addRouteItemList;
	}

	public void setAddRouteItemList(List<RouteItemDO> addRouteItemList) {
		this.addRouteItemList = addRouteItemList;
	}

	public List<Long> getDelRouteItemList() {
		return delRouteItemList;
	}

	public void setDelRouteItemList(List<Long> delRouteItemList) {
		this.delRouteItemList = delRouteItemList;
	}

	public List<RouteItemUpdateDTO> getUpdrouteItemList() {
		return updrouteItemList;
	}

	public void setUpdrouteItemList(List<RouteItemUpdateDTO> updrouteItemList) {
		this.updrouteItemList = updrouteItemList;
	}
}

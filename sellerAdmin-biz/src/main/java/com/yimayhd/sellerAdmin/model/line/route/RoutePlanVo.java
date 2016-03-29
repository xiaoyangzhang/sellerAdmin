package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;

/**
 * 自由行行程
 * 
 * @author yebin
 *
 */
public class RoutePlanVo implements Serializable {
	private static final long serialVersionUID = -8507331081900225417L;
	private RouteTrafficVO go; // 去程
	private RouteTrafficVO back;// 返程
	private String hotelInfo;
	private String scenicInfo;

	public RouteTrafficVO getGo() {
		return go;
	}

	public void setGo(RouteTrafficVO go) {
		this.go = go;
	}

	public RouteTrafficVO getBack() {
		return back;
	}

	public void setBack(RouteTrafficVO back) {
		this.back = back;
	}

	public String getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(String hotelInfo) {
		this.hotelInfo = hotelInfo;
	}

	public String getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(String scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

}

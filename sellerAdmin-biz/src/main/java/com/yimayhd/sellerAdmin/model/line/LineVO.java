package com.yimayhd.sellerAdmin.model.line;

import java.io.Serializable;

import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.detail.PictureTextVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RoutePlanVo;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public class LineVO implements Serializable {
	private static final long serialVersionUID = -3473801970066242380L;
	private BaseInfoVO baseInfo;// 基础信息
	private PictureTextVO pictureText;// 图文详情
	private RoutePlanVo freeLineRoute;// 行程计划
	private RouteInfoVO routeInfo;// 行程信息
	private PriceInfoVO priceInfo;// 价格信息
	private NeedKnowVO needKnow;// 报名须知

	protected boolean readonly = false;

	public BaseInfoVO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfoVO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public PriceInfoVO getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfoVO priceInfo) {
		this.priceInfo = priceInfo;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public PictureTextVO getPictureText() {
		return pictureText;
	}

	public void setPictureText(PictureTextVO pictureText) {
		this.pictureText = pictureText;
	}

	public NeedKnowVO getNeedKnow() {
		return needKnow;
	}

	public void setNeedKnow(NeedKnowVO needKnow) {
		this.needKnow = needKnow;
	}

	public RouteInfoVO getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(RouteInfoVO routeInfo) {
		this.routeInfo = routeInfo;
	}

	public RoutePlanVo getFreeLineRoute() {
		return freeLineRoute;
	}

	public void setFreeLineRoute(RoutePlanVo freeLineRoute) {
		this.freeLineRoute = freeLineRoute;
	}
}

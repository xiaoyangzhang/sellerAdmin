package com.yimayhd.sellerAdmin.model.line;

import java.io.Serializable;

import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.detail.DetailInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RoutePlanVO;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public class LineVO implements Serializable {
	private static final long serialVersionUID = -3473801970066242380L;
	private long categoryId;
	private long options;

	private int itemType;
	private BaseInfoVO baseInfo;// 基础信息
	private DetailInfoVO detailInfo;// 图文详情
	private RoutePlanVO routePlan;// 行程计划
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getOptions() {
		return options;
	}

	public void setOptions(long options) {
		this.options = options;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public DetailInfoVO getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(DetailInfoVO detailInfo) {
		this.detailInfo = detailInfo;
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

	public RoutePlanVO getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(RoutePlanVO routePlan) {
		this.routePlan = routePlan;
	}
}

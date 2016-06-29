package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522270147472331376L;
	private String name;// 酒店名称
	private int hotelStatus;// 状态
	private long regionId;// 区域ID
	private String regionName;// 区域名称
	private String hotelNameOrTel;// 酒店联系电话
	private String BeginDate;// 开始创建时间
	private String endDate;// 结束创建时间
	private Integer pageBegin;
	
	public int getHotelStatus() {
		return hotelStatus;
	}

	public void setHotelStatus(int hotelStatus) {
		this.hotelStatus = hotelStatus;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBeginDate() {
		return BeginDate;
	}

	public void setBeginDate(String beginDate) {
		BeginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHotelNameOrTel() {
		return hotelNameOrTel;
	}

	public void setHotelNameOrTel(String hotelNameOrTel) {
		this.hotelNameOrTel = hotelNameOrTel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageBegin() {
		return pageBegin;
	}

	public void setPageBegin(Integer pageBegin) {
		this.pageBegin = pageBegin;
	}
}

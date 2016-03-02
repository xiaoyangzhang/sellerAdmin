package com.yimayhd.sellerAdmin.model;

import java.util.List;

import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;

public class CommScenicVO extends ScenicPublishDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String smallListPic;//方形小列表图，主要用于订单
	private String bigListPic;//扁长大列表图，主要用于伴手礼等商品列表
	private String coverPics;//封面大图String
	private List<String> picList;//封面大图List
	private Long endTime;
	private Long startDayTime;
	private Long startHourTime;
	private Long[] check;
	private double priceF;
	
	
	
	
	public String getSmallListPic() {
		return smallListPic;
	}
	public void setSmallListPic(String smallListPic) {
		this.smallListPic = smallListPic;
	}
	public String getBigListPic() {
		return bigListPic;
	}
	public void setBigListPic(String bigListPic) {
		this.bigListPic = bigListPic;
	}
	public String getCoverPics() {
		return coverPics;
	}
	public void setCoverPics(String coverPics) {
		this.coverPics = coverPics;
	}
	public List<String> getPicList() {
		return picList;
	}
	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getStartDayTime() {
		return startDayTime;
	}
	public void setStartDayTime(Long startDayTime) {
		this.startDayTime = startDayTime;
	}
	public Long getStartHourTime() {
		return startHourTime;
	}
	public void setStartHourTime(Long startHourTime) {
		this.startHourTime = startHourTime;
	}
	public Long[] getCheck() {
		return check;
	}
	public void setCheck(Long[] check) {
		this.check = check;
	}
	public double getPriceF() {
		return priceF;
	}
	public void setPriceF(double priceF) {
		this.priceF = priceF;
	}
	
	
	
	
}

package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.domain.CityActivityDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.enums.ActivityPicUrlsKey;
import com.yimayhd.user.entity.City;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;


public class CityActivityVO extends CityActivityDO {

	private double priceY;//价格元
	private Long[] tagList;
	private String endDateStr;
	private Long[] cityCodeList;

	public double getPriceY() {
		return priceY;
	}

	public void setPriceY(double priceY) {
		this.priceY = priceY;
	}

	public Long[] getTagList() {
		return tagList;
	}

	public void setTagList(Long[] tagList) {
		this.tagList = tagList;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Long[] getCityCodeList() {
		return cityCodeList;
	}

	public void setCityCodeList(Long[] cityCodeList) {
		this.cityCodeList = cityCodeList;
	}

	public CityActivityDO getCityActivityDO(){
		CityActivityDO cityActivityDO = new CityActivityDO();
		BeanUtils.copyProperties(this, cityActivityDO);
		return cityActivityDO;
	}
}

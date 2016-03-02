package com.yimayhd.sellerAdmin.model;

import com.yimayhd.snscenter.client.enums.ActivityPicUrlsKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;

import java.util.HashMap;
import java.util.Map;


public class ActivityVO extends ActivityInfoDTO {

	private double originalPriceY;//价格元
	private double preferentialPriceY;
	private Long[] tagList;
	private String activityDateStr;
	private String startDateStr;
	private String endDateStr;
	private String imagePC;
	private String contentPC;
    


	public static ActivityInfoDTO getActivityInfoDTO(ActivityVO activityVO) throws Exception{
		ActivityInfoDTO activityInfoDTO = new ActivityVO();
		BeanUtils.copyProperties(activityVO, activityInfoDTO);
		activityInfoDTO.setOriginalPrice(NumUtil.doubleToLong(activityVO.getOriginalPriceY()));
		activityInfoDTO.setPreferentialPrice(NumUtil.doubleToLong(activityVO.getPreferentialPriceY()));
		activityInfoDTO.setActivityDate( DateUtil.convertStringToDateUseringFormats(activityVO.getActivityDateStr(), DateUtil.DAY_HORU_FORMAT));
		activityInfoDTO.setStartDate(DateUtil.convertStringToDateUseringFormats(activityVO.getStartDateStr(), DateUtil.DAY_HORU_FORMAT));
		activityInfoDTO.setEndDate(DateUtil.convertStringToDateUseringFormats(activityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT));
		//新增的时候PC展示图
		if(StringUtils.isNotBlank(activityVO.getImagePC())){
			Map<String, String> picUrls = new HashMap<String, String>();
			picUrls.put(ActivityPicUrlsKey.BIG_LIST_PIC.getCode(), activityVO.getImagePC());
			activityInfoDTO.setPicUrlsMap(picUrls);
		}
		return activityInfoDTO;
	}
	public static ActivityVO getActivityVO(ActivityInfoDTO activityInfoDTO){
		ActivityVO activityVO = new ActivityVO();
		BeanUtils.copyProperties(activityInfoDTO,activityVO);
		//分转元
		activityVO.setOriginalPriceY(NumUtil.moneyTransformDouble(activityVO.getOriginalPrice()));
		activityVO.setPreferentialPriceY(NumUtil.moneyTransformDouble(activityVO.getPreferentialPrice()));
		return activityVO;
	}
	public double getOriginalPriceY() {
		return originalPriceY;
	}
	public void setOriginalPriceY(double originalPriceY) {
		this.originalPriceY = originalPriceY;
	}
	public Long[] getTagList() {
		return tagList;
	}
	public void setTagList(Long[] tagList) {
		this.tagList = tagList;
	}
	public double getPreferentialPriceY() {
		return preferentialPriceY;
	}
	public void setPreferentialPriceY(double preferentialPriceY) {
		this.preferentialPriceY = preferentialPriceY;
	}
	public String getActivityDateStr() {
		return activityDateStr;
	}
	public void setActivityDateStr(String activityDateStr) {
		this.activityDateStr = activityDateStr;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}


	public String getImagePC() {
		return imagePC;
	}

	public void setImagePC(String imagePC) {
		this.imagePC = imagePC;
	}
	public String getContentPC() {
		return contentPC;
	}
	public void setContentPC(String contentPC) {
		this.contentPC = contentPC;
	}
	
	
}

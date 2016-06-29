package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.ItemVO;
import com.yimayhd.resourcecenter.model.resource.SubjectInfo;
import com.yimayhd.resourcecenter.model.resource.TravelSpecialInfo;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultInfo;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.service.DiscoveryCfgService;

public class DiscoveryCfgServiceImpl implements DiscoveryCfgService{
	
	/**
	 * 伴手礼boothid
	 */
	private static final long DISCOVERY_CONFIG_ITEM_BOOTH_ID = 74;
	
	/**
	 * 品质游记boothid
	 */
	private static final long DISCOVERY_CONFIG_TRAVEL_SPECIAL_BOOTH_ID = 75;
	
	/**
	 * 直播boothid
	 */
	private static final long DISCOVERY_CONFIG_SUBJECT_BOOTH_ID = 76;

	@Autowired
	private ShowcaseClientServer showCaseClientServer;
	@Autowired
	private BoothClientServer boothCilentServer;
	
	@Override
	public RcResult<Boolean> addItemList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addSubjectList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public CfgResultVO getItemList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(DISCOVERY_CONFIG_ITEM_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO cfgResultVO = new CfgResultVO();
			
			setBoothInfo(cfgResultVO,  boothResult.getT());
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			showCaseQuery.setBoothId(DISCOVERY_CONFIG_ITEM_BOOTH_ID);
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> cfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo cfgResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					cfgResultInfo = new CfgResultInfo();
					
					ItemVO item = JSON.parseObject(showcaseDO.getOperationContent(), ItemVO.class);
					
					cfgResultInfo.setItemId(item.getId());
					cfgResultInfo.setItemTitle(item.getTitle());
					cfgResultInfo.setItemImg(item.getPicUrls().get(0));
					
					cfgInfoList.add(cfgResultInfo);
				}
				
				
				cfgResultVO.setCfgInfoList(cfgInfoList);
			}
			
			return cfgResultVO;
		}
		
		return null;
	}

	@Override
	public CfgResultVO getTravelSpecialList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(DISCOVERY_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO cfgResultVO = new CfgResultVO();
			
			setBoothInfo(cfgResultVO,  boothResult.getT());
			
			
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			showCaseQuery.setBoothId(DISCOVERY_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> cfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo cfgResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					cfgResultInfo = new CfgResultInfo();
					
					TravelSpecialInfo travelSpecialInfo = JSON.parseObject(showcaseDO.getOperationContent(), TravelSpecialInfo.class);
					
					cfgResultInfo.setItemId(travelSpecialInfo.getId());
					cfgResultInfo.setItemTitle(travelSpecialInfo.getTitle());
					cfgResultInfo.setItemImg(travelSpecialInfo.getBackImg());
					cfgResultInfo.setExtName(travelSpecialInfo.getUserInfo().getNickname());
					cfgResultInfo.setExtImg(travelSpecialInfo.getUserInfo().getAvatar());
					
					cfgInfoList.add(cfgResultInfo);
				}
				
				
				cfgResultVO.setCfgInfoList(cfgInfoList);
			}
			
			return cfgResultVO;
		}
		
		
		
		return null;
	}

	@Override
	public CfgResultVO getSubjectList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(DISCOVERY_CONFIG_SUBJECT_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO cfgResultVO = new CfgResultVO();
			
			setBoothInfo(cfgResultVO,  boothResult.getT());
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(DISCOVERY_CONFIG_SUBJECT_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> cfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo cfgResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					cfgResultInfo = new CfgResultInfo();
					
					SubjectInfo subjectInfo = JSON.parseObject(showcaseDO.getOperationContent(), SubjectInfo.class);
					
					cfgResultInfo.setItemId(subjectInfo.getId());
					cfgResultInfo.setItemTitle(subjectInfo.getTextContent());
					cfgResultInfo.setExtName(subjectInfo.getUserInfo().getNickname());
					cfgResultInfo.setExtImg(subjectInfo.getUserInfo().getAvatar());
					
					cfgInfoList.add(cfgResultInfo);
				}
				
				
				cfgResultVO.setCfgInfoList(cfgInfoList);
			}
			
			return cfgResultVO;
		}
		
		return null;
	}

	private void setBoothInfo(CfgResultVO cfgResultVO, BoothDO boothDO) {
		
		cfgResultVO.setBoothCode(boothDO.getCode());
		cfgResultVO.setBoothDesc(boothDO.getDesc());
		cfgResultVO.setBoothId(boothDO.getId());
	}

}

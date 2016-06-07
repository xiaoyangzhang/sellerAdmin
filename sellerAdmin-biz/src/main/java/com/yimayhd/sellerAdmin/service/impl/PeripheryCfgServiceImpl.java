package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.CityInfo;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.SnsActivePageInfo;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultInfo;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.service.PeripheryCfgService;

public class PeripheryCfgServiceImpl implements PeripheryCfgService{
	
	/**
	 * 俱乐部主题boothid
	 */
	private static final long PERIPHERY_CONFIG_CLUB_CATEGORY_BOOTH_ID = 71;
	
	/**
	 * 热门周边boothid
	 */
	private static final long PERIPHERY_CONFIG_CITY_BOOTH_ID = 72;
	
	/**
	 * 精彩活动boothid
	 */
	private static final long PERIPHERY_CONFIG_ACTIVITY_BOOTH_ID = 73;

	@Autowired
	private ShowcaseClientServer showCaseClientServer;
	@Autowired
	private BoothClientServer boothCilentServer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PeripheryCfgServiceImpl.class);
	
	@Override
	public RcResult<Boolean> addClubCategoryList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addActivityList(CfgBaseVO cfgBaseVO) {
		return null;
	}

	@Override
	public CfgResultVO getClubCattegoryList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(PERIPHERY_CONFIG_CLUB_CATEGORY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO cfgResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(PERIPHERY_CONFIG_CLUB_CATEGORY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			cfgResultVO.setBoothCode(boothDO.getCode());
			cfgResultVO.setBoothDesc(boothDO.getDesc());
			cfgResultVO.setBoothId(PERIPHERY_CONFIG_CLUB_CATEGORY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> peripheryCfgInfoList  = setBaseShowCase(showcaseResult.getList());
				
				cfgResultVO.setCfgInfoList(peripheryCfgInfoList);
			}
			
			return cfgResultVO;
		}
		
		
		
		return null;
	}

	@Override
	public CfgResultVO getActivityList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(PERIPHERY_CONFIG_ACTIVITY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			
			
			CfgResultVO peripheryCfgResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(PERIPHERY_CONFIG_ACTIVITY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			peripheryCfgResultVO.setBoothCode(boothDO.getCode());
			peripheryCfgResultVO.setBoothDesc(boothDO.getDesc());
			peripheryCfgResultVO.setBoothId(PERIPHERY_CONFIG_ACTIVITY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> peripheryCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo cfgResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					cfgResultInfo = new CfgResultInfo();
					
					SnsActivePageInfo activityInfo = JSON.parseObject(showcaseDO.getOperationContent(), SnsActivePageInfo.class);
					
					cfgResultInfo.setItemId(activityInfo.getId());
					cfgResultInfo.setItemTitle(activityInfo.getClubName());
					
					peripheryCfgInfoList.add(cfgResultInfo);
				}
				
				
				peripheryCfgResultVO.setCfgInfoList(peripheryCfgInfoList);
			}
			
			return peripheryCfgResultVO;
		}
		
		return null;
	}
		

	@Override
	public CfgResultVO getCityList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(PERIPHERY_CONFIG_CITY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO cfgResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(PERIPHERY_CONFIG_CITY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			cfgResultVO.setBoothCode(boothDO.getCode());
			cfgResultVO.setBoothDesc(boothDO.getDesc());
			cfgResultVO.setBoothId(PERIPHERY_CONFIG_CITY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> cfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo cfgResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					cfgResultInfo = new CfgResultInfo();
					
					CityInfo cityInfo = JSON.parseObject(showcaseDO.getOperationContent(), CityInfo.class);
					
					cfgResultInfo.setItemId(cityInfo.id);
					cfgResultInfo.setItemTitle(cityInfo.name);
					cfgResultInfo.setItemImg(cityInfo.url);
					
					cfgInfoList.add(cfgResultInfo);
				}
				
				
				cfgResultVO.setCfgInfoList(cfgInfoList);
			}
			
			return cfgResultVO;
		}
		return null;
	}

	
	private List<CfgResultInfo> setBaseShowCase(List<ShowCaseResult> showCaseList) {
		
		if(null == showCaseList){
			return null;
		}
		
		List<CfgResultInfo> cfgResultInfos = new ArrayList<CfgResultInfo>();
		
		CfgResultInfo cfgResultInfo = null;
		
		for (ShowCaseResult showCaseResult : showCaseList) {
			
			ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
			cfgResultInfo = new CfgResultInfo();
			
			cfgResultInfo.setItemId(showcaseDO.getOperationId());
			cfgResultInfo.setItemImg(showcaseDO.getImgUrl());
			cfgResultInfo.setItemTitle(showcaseDO.getTitle());
			cfgResultInfo.setItemDesc(showcaseDO.getSummary());
			
			cfgResultInfos.add(cfgResultInfo);
		}
		
		
		return cfgResultInfos;
	}
}

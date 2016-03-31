package com.yimayhd.sellerAdmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.service.PeripheryCfgService;
import com.yimayhd.resourcecenter.model.result.RcResult;
 

/**
  * @autuor : xusq
  * @date : 2015年12月2日
  * @description : 周边管理
  */
@Controller
@RequestMapping("/B2C/peripheryManage")
public class PeripheryManageController extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(PeripheryManageController.class);
	
	@Autowired
	private PeripheryCfgService peripheryCfgService;
	
	@RequestMapping("/index")
	public String toPeripheryIndex(Model model){
		
		CfgResultVO clubCategoryList = peripheryCfgService.getClubCattegoryList();
		CfgResultVO cityList = peripheryCfgService.getCityList();
		CfgResultVO activityList = peripheryCfgService.getActivityList();
		
		Map<String, CfgResultVO> peripheryCfg = new HashMap<String, CfgResultVO>();
		
		peripheryCfg.put("clubCategoryList", clubCategoryList);
		peripheryCfg.put("cityList", cityList);
		peripheryCfg.put("activityList", activityList);
		
		model.addAttribute("peripheryCfg", peripheryCfg);
		
		
		
		return "/system/homeCfg/peripheryIndex";
	}
	
	@RequestMapping(value="/addPeripheryClubCategory",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addPeripheryClubCategory(CfgBaseVO cfgBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addClubCategoryListStatus = peripheryCfgService.addClubCategoryList(cfgBaseVO);
		
		if(addClubCategoryListStatus.isSuccess()){
			
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			
		} else {
			
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			responseVo.setMessage(addClubCategoryListStatus.getResultMsg());
		}
		
		
		
		return responseVo;
	}
	
	@RequestMapping(value="/addPeripheryCity",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addPeripheryCity(CfgBaseVO cfgBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addCityListStatus = peripheryCfgService.addCityList(cfgBaseVO);
		
		if(addCityListStatus.isSuccess()){
			
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			
		} else {
			
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			responseVo.setMessage(addCityListStatus.getResultMsg());
		}
		
		return responseVo;
	}
	
	@RequestMapping(value="/addPeripheryActivity",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addPeripheryActivity(CfgBaseVO cfgBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addActivityListStatus = peripheryCfgService.addActivityList(cfgBaseVO);
		
		if(addActivityListStatus.isSuccess()){
			
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			
		} else {
			
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			responseVo.setMessage(addActivityListStatus.getResultMsg());
		}
		
		return responseVo;
	}
}

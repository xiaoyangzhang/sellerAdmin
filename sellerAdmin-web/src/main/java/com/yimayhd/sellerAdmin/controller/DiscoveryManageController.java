package com.yimayhd.sellerAdmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.service.DiscoveryCfgService;

/**
  * @autuor : xusq
  * @date : 2015年12月3日
  * @description : 发现管理
  */
@Controller
@RequestMapping("/B2C/discoveryManage")
public class DiscoveryManageController extends BaseController{
	
	@Autowired
	private DiscoveryCfgService discoveryCfgService;

	@RequestMapping("/index")
	public String toDiscoveryIndex(Model model){
		
		Map<String , CfgResultVO> cfgResult = new HashMap<String , CfgResultVO>();
		
		CfgResultVO itemList = discoveryCfgService.getItemList();
		CfgResultVO travelSpecialList = discoveryCfgService.getTravelSpecialList();
		CfgResultVO subjectList = discoveryCfgService.getSubjectList();
		
		cfgResult.put("itemList", itemList);
		cfgResult.put("travelSpecialList", travelSpecialList);
		cfgResult.put("subjectList", subjectList);
		
		model.addAttribute("discoveryCfg", cfgResult);
		
		return "/system/homeCfg/discoveryIndex";
	}
	

	/**
	  * @date : 2015年12月3日
	  * @description : 伴手礼
	  */
	@RequestMapping("/addDiscoveryItem")
	@ResponseBody
	public ResponseVo addDiscoveryItem(CfgBaseVO baseVO){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
	}
	
	/**
	  * @date : 2015年12月3日
	  * @description : 品质游记
	  */
	@RequestMapping("/addDiscoveryTravelSpecial")
	@ResponseBody
	public ResponseVo addDiscoveryTravelSpecial(CfgBaseVO baseVO){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
	}
	
	@RequestMapping("/addDisoverySubject")
	@ResponseBody
	public ResponseVo addDisoverySubject(CfgBaseVO baseVO){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
		
	}
}

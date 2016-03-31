package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.AppVersionDO;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.enums.BoothStatusType;
import com.yimayhd.resourcecenter.model.enums.HomePageShowCase;
import com.yimayhd.resourcecenter.model.enums.OperationStatusType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseFeatureKey;
import com.yimayhd.resourcecenter.model.enums.ShowcaseShowType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.query.AppVersionQuery;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.AppVersionClientService;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.OperationClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;

@Controller
@RequestMapping("/rcBooth")
public class RcBoothController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RcBoothController.class);
	@Autowired
	BoothClientServer boothClientServer;
	@Autowired
	OperationClientServer operationClientServer;
	@Autowired
	ShowcaseClientServer showcaseClientServer;
	@Autowired
	AppVersionClientService appVersionClientService;
	
	
	@RequestMapping("/list") 
	public String list(Model model,Integer pageNumber,String code,String status)throws Exception{
		if(null == pageNumber){
			pageNumber = 1;
		}
		BoothQuery boothQuery=new BoothQuery();
		boothQuery.setCode(code);
		boothQuery.setPageNo(pageNumber);
		boothQuery.setPageSize(10); 
		if(StringUtils.isNotEmpty(status)){
			boothQuery.setStatus(Integer.parseInt(status));
		}
		RCPageResult<BoothDO> boothResult = boothClientServer.getBoothResult(boothQuery);
		PageVO<BoothDO> pageVO = null;
		
		if(boothResult.isSuccess()){
			pageVO = new PageVO<BoothDO>(pageNumber, 10, boothResult.getTotalCount());
			model.addAttribute("bothList", boothResult.getList());
			
		}
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("code", code);
		model.addAttribute("status", status);
		return "/system/resources/booth/list";
	}
	
	@RequestMapping("/initAdd")
	public String initAdd(String id,Model model){
		if(StringUtils.isNotEmpty(id)){
			RcResult<BoothDO> boothResult = boothClientServer.getBoothById(Long.parseLong(id));
			if(boothResult.isSuccess()){
				BoothDO booth = boothResult.getT();
				model.addAttribute("booth", booth);
			}
		}
		return "/system/resources/booth/detail";
	}
	/**
	 * 添加或者修改
	 */
	@RequestMapping("/insertOrupdate") 
	@ResponseBody
	public ResponseVo insertOrupdate(HttpServletRequest request,HttpServletResponse response,BoothDO boothDO){
		
		ResponseVo responseVo = new ResponseVo();
		//TODO 报错注释
		/*if(boothDO.getId()!=null){   //id不为空时    修改
			RcResult<Boolean> updResult = boothClientServer.update(boothDO);
			if(updResult.isSuccess()){
				responseVo.setMessage("更改成功！");
				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			}else{
				responseVo.setMessage(updResult.getResultMsg());
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}else{
			RcResult<Boolean> addResult = boothClientServer.insert(boothDO);
			if(addResult.isSuccess()){
				responseVo.setMessage("添加成功！");
				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			}else{
				responseVo.setMessage(addResult.getResultMsg());
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}*/
		return responseVo;
	}
	/**
	 * 上下架
	 */
	@RequestMapping("/updown") 
	@ResponseBody
	public ResponseVo updown(String id,String type){
		ResponseVo ajaxResponse=new ResponseVo();
		RcResult<BoothDO> boothResult = boothClientServer.getBoothById(Long.parseLong(id));
		if(boothResult.isSuccess()){
			BoothDO boothDo = boothResult.getT();
			if(type.equals("1")){//上架
				boothDo.setStatus(BoothStatusType.ON_SHELF.getValue());
			}else{
				boothDo.setStatus(BoothStatusType.OFF_SHELF.getValue());
			}
			RcResult<Boolean> updResult = boothClientServer.update(boothDo);
			if(updResult.isSuccess()){
				ajaxResponse.setMessage("操作成功！");
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}else{
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
	}

	@RequestMapping("/operaList") 
	public String operaList(Model model,Integer pageNumber,String code,String status)throws Exception{
		OperationQuery operationQuery=new OperationQuery();
		operationQuery.setCode(code);
		operationQuery.setPageNo(pageNumber);
		operationQuery.setPageSize(10); 
		if(StringUtils.isNotEmpty(status)){
			operationQuery.setStatus(Integer.parseInt(status));
		}
		RCPageResult<OperationDO> operationResult = operationClientServer.getOperationResult(operationQuery);
		
		PageVO<OperationDO> pageVO = null;
		
		if(operationResult.isSuccess()){
			model.addAttribute("operaList", operationResult.getList());
			pageVO = new PageVO<OperationDO>(pageNumber, 10, operationResult.getTotalCount());
		}
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("code", code);
		model.addAttribute("status", status);
		return "/system/resources/opera/list";
	}
	/**
	 * 进入添加界面
	 */
	@RequestMapping("initAddOpera")
	public String initAddOpera(String id,Model model){
		if(StringUtils.isNotEmpty(id)){
			RcResult<OperationDO> operationDo=operationClientServer.getOperationById(Long.parseLong(id));
			if(operationDo.isSuccess()){
				OperationDO operationDO = operationDo.getT();
				model.addAttribute("operationDO", operationDO);
			}
		}
		return "/system/resources/opera/desc";
	}
	
	/**
	 * 添加或者修改
	 */
	@RequestMapping("/operaAdd") 
	@ResponseBody
	public ResponseVo operaAdd(OperationDO operationDO){
		ResponseVo ajaxResponse=new ResponseVo();
		if(operationDO.getId()!=null){   //id不为空时    修改
			RcResult<Boolean> updResult = operationClientServer.update(operationDO);
		    if(updResult.isSuccess()){
				ajaxResponse.setMessage("更新成功！");
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}else{
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}else{
			RcResult<Boolean> addResult = operationClientServer.insert(operationDO);
			if(addResult.isSuccess()){
				ajaxResponse.setMessage("添加成功！");
				ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
			}else{
				ajaxResponse.setMessage(addResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
	}
	/**
	 * 上下架
	 */
	@RequestMapping("/updownOpera") 
	@ResponseBody
	public ResponseVo updownOpera(String id,String type){
		ResponseVo ajaxResponse=new ResponseVo();
		RcResult<OperationDO> operaResult = operationClientServer.getOperationById(Long.parseLong(id));
		if(operaResult.isSuccess()){
			OperationDO operationDO = operaResult.getT();
			if(type.equals("1")){//上架
				operationDO.setStatus(OperationStatusType.ON_SHELF.getValue());
			}else{
				operationDO.setStatus(OperationStatusType.OFF_SHELF.getValue());
			}
			RcResult<Boolean> updResult = operationClientServer.update(operationDO);
			if(updResult.isSuccess()){
				ajaxResponse.setMessage("操作成功！");
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}else{
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
	}
	/**********************************rc_showcase*********************************************/
	/**
	 * 列表
	 */
	@RequestMapping("/showList")
	public String showList(Model model,String status,String boothId,Integer pageNumber,String appVersionCode){
		
		if(null == pageNumber){
			pageNumber = 1;
		}
		ShowcaseQuery showcaseQuery=new ShowcaseQuery();
		if(StringUtils.isNotEmpty(status)){
			showcaseQuery.setStatus(Integer.parseInt(status));
		}
		if(StringUtils.isNotEmpty(boothId)){
			showcaseQuery.setBoothId(Long.parseLong(boothId));
		}
		if(StringUtils.isNotEmpty(appVersionCode)){
			showcaseQuery.setAppVersionCode(Long.parseLong(appVersionCode));
		}
		showcaseQuery.setPageNo(pageNumber);
		showcaseQuery.setPageSize(10);
		RCPageResult<ShowCaseResult> showCaseResult=showcaseClientServer.getShowcaseResult(showcaseQuery);
		//查询展位列表
		BoothQuery boothQuery=new BoothQuery();
		boothQuery.setNeedPage(false);
		RCPageResult<BoothDO> boothResult = boothClientServer.getBoothResult(boothQuery);
		
		
		AppVersionQuery appVersionQuery = new AppVersionQuery();
		appVersionQuery.setPageSize(100);
		RcResult<List<AppVersionDO>> result = appVersionClientService.queryAppVersionList(appVersionQuery);
		
		PageVO<ShowcaseDO> pageVO = null;
		
		if (result != null && result.isSuccess()){
			model.addAttribute("appVersionList", result.getT());
			
			pageVO = new PageVO<ShowcaseDO>(pageNumber, 10, showCaseResult.getTotalCount());
		}
		model.addAttribute("appVersionCode", appVersionCode);
		model.addAttribute("boothList", boothResult.getList());
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("showCaseResult", showCaseResult.getList());
		model.addAttribute("boothId", boothId);
		model.addAttribute("status", status);
		return "/system/resources/show/list";
	}
	/**
	 * 首页资源列表
	 */
	@RequestMapping("/homePageShowList")
	public String homePageShowList(Model model,String status,String boothId,String showType, String appVersion,int page){
		//查询展位列表
		List<BoothDO> boothList = getHomePageShowCase();
		if (boothList != null){
			model.addAttribute("boothList",boothList);
		}

		ShowcaseQuery showcaseQuery=new ShowcaseQuery();
		if(StringUtils.isNotEmpty(status)){
			showcaseQuery.setStatus(Integer.parseInt(status));
		}
		if (StringUtils.isNotEmpty(appVersion)){
			showcaseQuery.setAppVersionCode(Long.parseLong(appVersion));
		}
		if (StringUtils.isNotEmpty(showType)){
			showcaseQuery.setShowType(Integer.parseInt(showType));
		}
		if (StringUtils.isBlank(boothId)){
			if (boothList != null){
				List<Long> boothIds = new ArrayList<Long>();
				for (BoothDO boothDO : boothList){
					boothIds.add(boothDO.getId());
				}
				showcaseQuery.setBoothIds(boothIds);
			}
		} else {
			showcaseQuery.setBoothId(Long.parseLong(boothId));
		}
		showcaseQuery.setPageNo(page);
		showcaseQuery.setPageSize(10);
		RCPageResult<ShowCaseResult> showCaseResult=showcaseClientServer.getShowcaseResult(showcaseQuery);

		PageVO<ShowcaseDO> pageVO = new PageVO<ShowcaseDO>(page, 10, showCaseResult.getTotalCount());
		
		//查询app版本号
		AppVersionQuery appVersionQuery = new AppVersionQuery();
		appVersionQuery.setPageSize(100);
		RcResult<List<AppVersionDO>> result = appVersionClientService.queryAppVersionList(appVersionQuery);
		if (result != null && result.isSuccess()){
			Map<Long,String> appVersionDOMap = new HashMap<Long, String>();
			List<AppVersionDO> appVersionDOList = result.getT();
			for (AppVersionDO appVersionDO : appVersionDOList){
				appVersionDOMap.put(appVersionDO.getId(), appVersionDO.getName());
			}
			model.addAttribute("appVersionList", appVersionDOList);
			model.addAttribute("appVersionMap", appVersionDOMap);
		}
		Map<Integer,String> showTypeMap = new HashMap<Integer, String>();
		for (ShowcaseShowType showcaseShowType : ShowcaseShowType.values()){
			showTypeMap.put(showcaseShowType.getShowType(),showcaseShowType.getDesc());
		}
		Map<Integer, String> showcaseStatusMap = new HashMap<Integer, String>();
		for (ShowcaseStauts s : ShowcaseStauts.values()){
			showcaseStatusMap.put(s.getStatus(), s.getDesc());
		}
		model.addAttribute("showcaseStatusList", ShowcaseStauts.values());
		model.addAttribute("showcaseStatusMap", showcaseStatusMap);
		model.addAttribute("showTypeMap", showTypeMap);
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("showType",showType);
		model.addAttribute("showTypeList",ShowcaseShowType.values());
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("showCaseResult", showCaseResult.getList());
		model.addAttribute("boothId", boothId);
		model.addAttribute("status", status);
		return "/system/resources/show/homepageList";
	}

	//获取首页显示的booth
	private List<BoothDO> getHomePageShowCase(){
		BoothQuery boothQuery=new BoothQuery();
		boothQuery.setNeedPage(false);
		RCPageResult<BoothDO> boothResult = boothClientServer.getBoothResult(boothQuery);
		if (boothResult != null && boothResult.isSuccess()){
			List<BoothDO> boothList = new ArrayList<BoothDO>();
			List<BoothDO> boothDOList =	boothResult.getList();
			for (BoothDO boothDO : boothDOList){
				for (HomePageShowCase homePageShowCase : HomePageShowCase.values()){
					if (homePageShowCase.getValue().equals(boothDO.getCode())){
						boothList.add(boothDO);
					}
				}
			}

			if (boothList.size() > 0){
				return boothList;
			}
		}
		return null;
	}
	/**
	 * 进入添加界面
	 */
	@RequestMapping("/initShow")
	public String initShow(String id,Model model){
		//查询展位列表
		BoothQuery boothQuery=new BoothQuery();
		boothQuery.setNeedPage(false);
		boothQuery.setStatus(BoothStatusType.ON_SHELF.getValue());
		RCPageResult<BoothDO> boothResult = boothClientServer.getBoothResult(boothQuery);
		model.addAttribute("boothList", boothResult.getList());
		//查询操作类型列表
		OperationQuery operationQuery=new OperationQuery();
		operationQuery.setNeedPage(false);
		operationQuery.setStatus(OperationStatusType.ON_SHELF.getValue());
		RCPageResult<OperationDO> operationResult = operationClientServer.getOperationResult(operationQuery);
		model.addAttribute("operationList", operationResult.getList());
		AppVersionQuery appVersionQuery = new AppVersionQuery();
		appVersionQuery.setPageSize(100);
		RcResult<List<AppVersionDO>> result = appVersionClientService.queryAppVersionList(appVersionQuery);
		if (result != null && result.isSuccess()){
			model.addAttribute("appVersionList", result.getT());
		}
		if(StringUtils.isNotEmpty(id)){  //修改
			ShowCaseDTO showCaseDTO=new ShowCaseDTO();
			showCaseDTO.setShowcaseId(Long.parseLong(id));
			showCaseDTO.setNeedOperationDO(true);
			showCaseDTO.setNeedBoothDO(true);
			RcResult<ShowCaseResult> showCaseResult=showcaseClientServer.getShowcaseResult(showCaseDTO);
			if(showCaseResult.isSuccess()){
				model.addAttribute("showCase", showCaseResult.getT());
			}
		}
	    model.addAttribute("showcaseFeatureKey",ShowcaseFeatureKey.values());
		model.addAttribute("showTypeList", ShowcaseShowType.values());
		return "/system/resources/show/detail";
	}
	/**
	 * 进入首页添加界面
	 */
	@RequestMapping("/initHomePageShow")
	public String initHomePageShow(String id,Model model){
		//查询展位列表
		BoothQuery boothQuery=new BoothQuery();
		boothQuery.setNeedPage(false);
		boothQuery.setStatus(BoothStatusType.ON_SHELF.getValue());
		RCPageResult<BoothDO> boothResult = boothClientServer.getBoothResult(boothQuery);
		model.addAttribute("boothList", boothResult.getList());
		//查询操作类型列表
		OperationQuery operationQuery=new OperationQuery();
		operationQuery.setNeedPage(false);
		operationQuery.setStatus(OperationStatusType.ON_SHELF.getValue());
		RCPageResult<OperationDO> operationResult = operationClientServer.getOperationResult(operationQuery);
		model.addAttribute("operationList", operationResult.getList());
		AppVersionQuery appVersionQuery = new AppVersionQuery();
		appVersionQuery.setPageSize(100);
		RcResult<List<AppVersionDO>> result = appVersionClientService.queryAppVersionList(appVersionQuery);
		if (result != null && result.isSuccess()){
			model.addAttribute("appVersionList", result.getT());
		}
		if(StringUtils.isNotEmpty(id)){  //修改
			ShowCaseDTO showCaseDTO=new ShowCaseDTO();
			showCaseDTO.setShowcaseId(Long.parseLong(id));
			showCaseDTO.setNeedOperationDO(true);
			showCaseDTO.setNeedBoothDO(true);
			RcResult<ShowCaseResult> showCaseResult=showcaseClientServer.getShowcaseResult(showCaseDTO);
			if(showCaseResult.isSuccess()){
				model.addAttribute("showCase", showCaseResult.getT());
			}
		}
		model.addAttribute("showcaseFeatureKey",ShowcaseFeatureKey.values());
		model.addAttribute("showTypeList", ShowcaseShowType.values());
		return "/system/resources/show/homepageDesc";
	}
	/**
	 * 添加或者修改数据
	 */
	@RequestMapping("/addOrUpdShow")
	@ResponseBody
	public ResponseVo addOrUpdShow(ShowcaseDO showcaseDO,String featureradio){

		ResponseVo ajaxResponse=new ResponseVo();
		if (!"2".equals(featureradio)){
			showcaseDO.setShowcaseFeature(null);
			showcaseDO.setFeature("");
		}
		try{
			if(showcaseDO.getId()!=null){
				RcResult<Boolean> reslutUpd=showcaseClientServer.update(showcaseDO);
				if(reslutUpd.isSuccess()){
					ajaxResponse.setMessage("更新成功！");
					ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
				}else{
					ajaxResponse.setMessage(reslutUpd.getResultMsg());
					ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
				}
			}else{
				RcResult<Boolean> reslutAdd= showcaseClientServer.insert(showcaseDO);
				if(reslutAdd.isSuccess()){
					ajaxResponse.setMessage("添加成功！");
					ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
				}else{
					ajaxResponse.setMessage(reslutAdd.getResultMsg());
					ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
				}
			}
		} catch (Exception e){
			ajaxResponse.setMessage("保存失败");
			ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
//			LogUtils.operError(RcBoothController.class,e.getMessage());
			logger.error("保存失败，  showCaseDO={}",JSON.toJSONString(showcaseDO), e);
		}

		return ajaxResponse;
	}

	/**
	 * 添加或者修改数据
	 */
	@RequestMapping("/addOrUpdShowHomePage")
	@ResponseBody
	public ResponseVo addOrUpdShowHomePage(ShowcaseDO showcaseDO,String feature){
		if (StringUtils.isNotEmpty(feature)){
			Map<String, String> featureMap = showcaseDO.getShowcaseFeature();
			if (featureMap == null){
				featureMap = new HashMap<String, String>();
			}
			String[] fea = feature.split(",");
			if (fea != null && fea.length > 0) {
				for (String f : fea) {
					ShowcaseFeatureKey showcaseFeatureKey = ShowcaseFeatureKey.getByCode(f);
					if (showcaseFeatureKey == null) {
						continue;
					}
					switch (showcaseFeatureKey) {
						case HOT_TAG:
							featureMap.put(ShowcaseFeatureKey.HOT_TAG.getCode(), "1");
							break;
						default:
							break;
					}
				}
			}
			showcaseDO.setShowcaseFeature(featureMap);
		}
		ResponseVo ajaxResponse=new ResponseVo();
		try{
			if(showcaseDO.getId()!=null){
				RcResult<Boolean> reslutUpd=showcaseClientServer.update(showcaseDO);
				if(reslutUpd.isSuccess()){
					ajaxResponse.setMessage("更新成功！");
					ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
				}else{
					ajaxResponse.setMessage(reslutUpd.getResultMsg());
					ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
				}
			}else{
				RcResult<Boolean> reslutAdd= showcaseClientServer.insert(showcaseDO);
				if(reslutAdd.isSuccess()){
					ajaxResponse.setMessage("添加成功！");
					ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
				}else{
					ajaxResponse.setMessage(reslutAdd.getResultMsg());
					ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
				}
			}
		} catch (Exception e){
			ajaxResponse.setMessage("保存失败");
			ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			logger.error("保存失败，  showCaseDO={}",JSON.toJSONString(showcaseDO), e);
//			LogUtils.operError(RcBoothController.class,e.getMessage());
		}

		return ajaxResponse;
	}
	/**
	 * 上下架
	 */
	@RequestMapping("/updownShow") 
	@ResponseBody
	public ResponseVo updownShow(String id,String type){
		ResponseVo ajaxResponse=new ResponseVo();
		ShowCaseDTO showCaseDTO=new ShowCaseDTO();
		showCaseDTO.setShowcaseId(Long.parseLong(id));
		showCaseDTO.setNeedOperationDO(true);
		showCaseDTO.setNeedBoothDO(true);
		RcResult<ShowCaseResult> showCaseResult=showcaseClientServer.getShowcaseResult(showCaseDTO);
		if(showCaseResult.isSuccess()){
			ShowCaseResult result = showCaseResult.getT();
			if(type.equals("1")){//上架
				result.getShowcaseDO().setStatus(ShowcaseStauts.ONLINE.getStatus());
			}else{
				result.getShowcaseDO().setStatus(ShowcaseStauts.OFFLINE.getStatus());
			}
			RcResult<Boolean> updResult = showcaseClientServer.update(result.getShowcaseDO());
			if(updResult.isSuccess()){
				ajaxResponse.setMessage("操作成功！");
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}else{
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
	}
	@RequestMapping("/delShow") 
	@ResponseBody
	public ResponseVo delShow(String id){
		ResponseVo ajaxResponse=new ResponseVo(true);
		RcResult<Boolean> result = showcaseClientServer.delete(Long.parseLong(id));
		if(result.isSuccess()){
			ajaxResponse.setMessage("删除成功！");
			ajaxResponse.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			ajaxResponse.setMessage(result.getResultMsg());
			ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
		}
		return ajaxResponse;
	}
}

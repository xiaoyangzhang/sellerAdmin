package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yimayhd.sellerAdmin.base.BaseQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.TripBo;
import com.yimayhd.sellerAdmin.model.TripBoQuery;
import com.yimayhd.sellerAdmin.service.TripService;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
/** 
* @ClassName: DepartureManageController 
* @Description: (出发地、目的地管理，目的地关联相应的 推荐信息，如 必买 必去 酒店 直播) 
* @author create by yushengwei @ 2015年12月4日 上午11:03:29 
*/

@Controller 
@RequestMapping("/B2C/trip")
public class TripManageController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(TripManageController.class);
	
	@Autowired TripService tripService;
	
	
	/**
	* @Title: add 
	* @Description:(新增出发地/目的地) 
	* @author create by yushengwei @ 2015年12月8日 下午3:10:08 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/add")
	public String toAdd(Model model,@ModelAttribute("TripBo") TripBo tripBo){
		try {
			if (null != tripBo && 0 != tripBo.getCityCode()) {
				RegionDO regionDO = tripService.saveOrUpdateDetail(tripBo,false);
				if (null != regionDO) {
					return "/success";
				}
			}
		} catch (Exception e) {
			logger.error("trip+add,parameter[tripBo]="+JSON.toJSONString(tripBo)+" |error="+e.toString());
		}
		return "/error";
	}
	
	/**
	* @Title: relevance 
	* @Description:(目的地关联 线路，酒店，景区) 
	* @author create by yushengwei @ 2015年12月8日 上午10:57:14 
	* @param @param model
	* @param @param destinationId
	* @param @param lineCityCode
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/recommended/relevance")
	public String relevance(Model model,int type,int cityCode,int[] resourceId){
		try {
			if ( null == resourceId || resourceId.length <= 0) {
				model.addAttribute("errMsg", "参数不正确");
				return "/error";
			}
			tripService.relevanceRecommended(type, cityCode, resourceId);
		} catch (Exception e) {
			logger.error("trip+relevance,parameter[type]="+type+",cityCode="+cityCode+",resourceId="+JSON.toJSONString(resourceId)+" |error="+e.toString());
		}
		return "/success";
	}
	

	/**
	* @Title: list 
	* @Description:(出发地或最美当地) 
	* @author create by yushengwei @ 2015年12月7日 下午5:14:40 
	* @param @param model
	* @param @param type
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/list")
	public String list(Model model, TripBoQuery query, Integer pageNumber,Integer pageSize){
		if(null == query || 0 == query.getType()){
			query = new TripBoQuery();
			query.setType(RegionType.DESC_REGION.getType());
		}
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		} else {
			query.setPageNo(BaseQuery.DEFAULT_PAGE);
		}
		if(pageSize!= null) {
			query.setPageSize(pageSize);
		} else{
			query.setPageSize(BaseQuery.DEFAULT_SIZE);
		}
		PageVO<RegionDO> list = tripService.selectRegion(query);
		model.addAttribute("pageVo",list);
		model.addAttribute("regionList",list);
		
		if(RegionType.DEPART_REGION.getType() == query.getType() ){//出发地 3
			return "/system/trip/origin_list";
		}else if (RegionType.DESC_REGION.getType() == query.getType()){//目的地 4
			return "/system/trip/beautiful_local_list";
		}	
		return "/error";
	}
	
	/**
	* @Title: selectDepartureList 
	* @Description:(获取省市区列表) 
	* @author create by yushengwei @ 2015年12月7日 下午3:13:16 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/selectRegion")
	@ResponseBody
	public ResponseVo selectDepartureList(Model model,int type,boolean isAll){
		//已经根据status,过滤掉相应type下已经创建过的城市
		// 1-酒店区域 2-景区区域 3-线路出发地 4-线路目的地
		List<RegionDO> list = tripService.selectRegion(RegionType.DESC_REGION.getType(),isAll);
		if(CollectionUtils.isNotEmpty(list)){
			return new ResponseVo(list);			
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
	
	/**
	* @Title: block 
	* @Description:(停用或启用某个目的地)
	* @author create by yushengwei @ 2015年12月22日 上午10:12:09 
	* @param @param model
	* @param @param id
	* @param @param request
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/setJoinStatus")
	@ResponseBody
	//public ResponseVo block(@RequestParam("ids[]") List<Long> ids, int status, HttpServletRequest request){
	public ResponseVo block(@RequestParam("ids") Long ids, int status, HttpServletRequest request){
		boolean flag = false;
		try {
			List<Long> listIds = new ArrayList<Long>();
			listIds.add(ids);
			flag = tripService.blockOrUnBlock(listIds,status);
		} catch (Exception e) {
			logger.error("trip+block,parameter[ids]="+JSON.toJSONString(ids)+",status="+status+" |error="+e.toString());
		}
		return new ResponseVo(flag);
	}
	
	@RequestMapping(value="/toEdit/{id}",method=RequestMethod.GET)
	public String toEdit(Model model,@PathVariable(value = "id")long id, HttpServletRequest request){
		if(0==id){
			return "/error";
		}
		TripBo tripBo = tripService.getTripBo(id);
		model.addAttribute("tripId",id);
		model.addAttribute("tripBo",tripBo);
		model.addAttribute("isEdit",true);
		model.addAttribute("cityId",tripBo.getId());
		model.addAttribute("cityName",tripBo.getCityName());
		return "/system/trip/add_destination/destination_base_info";
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, HttpServletRequest request,@PathVariable(value = "id")long id, @ModelAttribute("TripBo") TripBo tripBo){
		if(0==id || null == tripBo ){
			model.addAttribute("errMsg","参数错误，id不能为0或tripBo不能为空");
			return "/error";
		}
		try {
			tripBo.setId(id);
			RegionDO regionDO = tripService.saveOrUpdateDetail(tripBo,true);
			if (null != regionDO) {
				return "/success";
			}
		} catch (Exception e) {
			logger.error("trip+update_destination Failure,parameter[tripBo]="+JSON.toJSONString(tripBo)+" |error="+e.toString());
		}
		model.addAttribute("errMsg","修改目的地失败");
		return "/error";
	}
	
	/**
	* @Title: originToAdd 
	* @Description:(出发地新增) 
	* @author create by yushengwei @ 2015年12月09日 上午10:32:45 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/origin/toAdd")
	public String originToAdd(Model model){
		return "/system/trip/origin_edit";
	}
	
	
	/**
	* @Title: toAdd 
	* @Description:(新增目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午3:46:59 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/departure/toAdd")
	public String toAdd(Model model){
		return "/system/trip/add_destination/destination_base_info";
	}
	
		
}

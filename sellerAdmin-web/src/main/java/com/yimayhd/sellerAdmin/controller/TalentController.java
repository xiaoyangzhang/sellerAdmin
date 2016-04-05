package com.yimayhd.sellerAdmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.service.RegionService;
/**
 * 达人申请入驻
 * 
 * @author zhangxy
 * 
 */
@Controller
@RequestMapping("/talent")
public class TalentController extends BaseController {

	protected  Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TalentBiz talentBiz;
	
	@RequestMapping(value="agreement",method=RequestMethod.GET)
	public String toAgreementPage(HttpServletRequest request,HttpServletResponse response,Model model){
		return "system/talent/agreement";
		
	}
	/**
	 * 跳转到填写达人申请资料页面1
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="toAddUserdatafill_pageOne",method=RequestMethod.GET)
	public String toAddUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model){
		
		return "system/talent/userdatafill_a";
		
	}
	@RequestMapping("toEditUserdatafill_pageOne")
	public String toEditUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model) {
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		return "system/talent/userdatafill_a";
		
	}
	/**
	 * 跳转到达人申请入驻资料页面2
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toAddUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){

		return "system/talent/userdatafill_b";
		
	}
	@RequestMapping(value="toEditUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toEditUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		return "system/talent/userdatafill_b";
		
	}
	
	/**
	 * 跳转到达人入驻待审核页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="verification",method=RequestMethod.GET)
	public String verificationPage(HttpServletRequest request,HttpServletResponse response,Model model){
		return "system/talent/verification";
		
	}

	
	/**
	 * 保存资料页面1并跳转到资料页面2
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="saveExamineInfo_pageOne",method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
		
			WebResult<String> result=new WebResult<String>();
			WebResultSupport resultSupport = talentBiz.addExamineInfo(vo);
			if (resultSupport.isSuccess()) {
				result.setValue("toAddUserdatafill_pageTwo");
				//resultSupport.setUrl("toAddUserdatafill_b.htm");
			}
			if (resultSupport.isSuccess()) {
				result.setValue("verification");
				//resultSupport.setUrl("verification.htm");
			}
			else {
				result.setWebReturnCode(resultSupport.getWebReturnCode());
			}
			return result;
		
	}
	/**
	 * 保存达人入驻申请页面2并跳转到待审核页面
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="saveExamineInfo_pageTwo",method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
			WebResult<String> result=new WebResult<String>();
			WebResultSupport resultSupport = talentBiz.addExamineInfo(vo);
			if (resultSupport.isSuccess()) {
				result.setValue("verification");
				//resultSupport.setUrl("verification.htm");
			}
			else {
				result.setWebReturnCode(resultSupport.getWebReturnCode());
			}
			return result;
		
	}
	/**
	 * 新增达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddTalentInfo",method=RequestMethod.GET)
	public String addTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		model.addAttribute("serviceTypes", talentBiz.getServiceTypes());
		
		return "system/talent/eredar";
		
	}
	/**
	 * 编辑达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toEditTalentInfo",method=RequestMethod.GET)
	public String editTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model) {
		model.addAttribute("serviceTypes", talentBiz.getServiceTypes());
		model.addAttribute("talentInfo", talentBiz.getTalentInfo());
		return "system/talent/eredar";
		
	}
	/**
	 * 保存达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="saveTalentInfo",method=RequestMethod.POST)
	@ResponseBody
	public WebResultSupport addTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model,TalentInfoVO vo ){
		
			WebResultSupport resultSupport = talentBiz.addTalentInfo(vo);
			
			return resultSupport;
		
		
	}
}

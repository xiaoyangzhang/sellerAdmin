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
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
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
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private RegionService regionService;
	@RequestMapping(value="agreement.htm",method=RequestMethod.GET)
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
	
	@RequestMapping(value="userdatafill_a.htm",method=RequestMethod.GET)
	public String toUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model){
//		try {
//			List<Region> provinces = regionService.getProvince();
//			model.addAttribute("provinces", provinces);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return "system/talent/userdatafill_a";
		
	}
	
	
	@RequestMapping(value="verification.htm",method=RequestMethod.GET)
	public String verificationPage(HttpServletRequest request,HttpServletResponse response,Model model){
		return "system/talent/verification";
		
	}

	@RequestMapping("saveExamineFile.do")
	@ResponseBody
	public String test(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoDTO dto){
		ExamineInfoVO vo=new ExamineInfoVO();
		vo.setA("aaa");
		vo.setB("bbb");
		return "callback("+JSON.toJSONString(vo)+")";
	}
	
	@RequestMapping("saveExamineFile.dos")
	@ResponseBody
	public String tests(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoDTO dto){
		ExamineInfoVO vo=new ExamineInfoVO();
		vo.setA("aaa");
		vo.setB("bbb");
		return JSON.toJSONString(vo);
	}
	/**
	 * 保存资料页面1并跳转到资料页面2
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping("saveExamineInfo_a.do")
	//@ResponseBody
	public String saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoDTO dto){
		return "system/talent/userdatafill_b";
	}
	@RequestMapping("saveExamineInfo_b.do")
	//@ResponseBody
	public String saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoDTO dto){
		return "system/talent/verification";
	}
	@RequestMapping("toAddTalentInfo.htm")
	public String talentInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		model.addAttribute("serviceTypes", talentInfoDealService.queryTalentServiceType());
		//MemResult<TalentInfoDTO> talentInfo= talentInfoDealService.queryTalentInfoByUserId(new SessionManager().getUserId(),1200 );
		/*if (null == talentInfo ) {
			log.error("talentInfoDealService.queryTalentInfoByUserId result is null and param"+JSON.toJSONString(talentInfo)+"and userId="+new SessionManager().getUserId()+"and domainId="+1200);
			throw new BaseException("");
		}
		else if (!talentInfo.isSuccess()) {
			log.error("talentInfoDealService.queryTalentInfoByUserId result error:"+JSON.toJSONString(talentInfo)+"and userId="+new SessionManager().getUserId()+"and domainId="+1200);
			
		}
		else {*/
			
		//	model.addAttribute("talentInfo", talentInfo.getValue());
		//}
		return "system/talent/eredar";
		
	}
	@RequestMapping("saveTalentInfo.do")
	@ResponseBody
	public ResponseVo saveTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model,TalentInfoVO vo ){
		//TalentInfoVO vo = JSON.parseObject(TalentInfoVOStr, TalentInfoVO.class);
		try {
			ResponseVo responseVo=new ResponseVo();
			MemResult<Boolean> result = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo));
			
			if (result.isSuccess()) {
				//return "/success";
				responseVo.setMessage("添加成功！");
				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			} else {
				//responseVo.setMessage(result.getResultMsg());
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			}
			return responseVo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseVo.error(e);
		}
		
	}
}

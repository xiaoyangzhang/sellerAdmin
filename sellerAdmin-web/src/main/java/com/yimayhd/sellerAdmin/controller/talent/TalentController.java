package com.yimayhd.sellerAdmin.controller.talent;

import java.util.Arrays;

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

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExaminePageNo;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
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
	@Autowired
	private MerchantBiz merchantBiz;
	@Autowired
	private ExamineDealService examineDealService;
	/**
	 * 处理审核结果信息
	 * @param dto
	 * @return
	 */
	public void getCheckResultMsg(ExamineResultDTO dto,Model model) {
		if (dto == null || ( dto.getDealMes() == null)) {
			return ;
		}
		if (ExamineStatus.EXAMIN_ERROR.getStatus() == dto.getStatus().getStatus()) {
			model.addAttribute("checkResultInfo", Arrays.asList(dto.getDealMes().split(Constant.SYMBOL_SEMIONLON)));
		}
		
	}
	/**
	 * 跳转到达人审核协议
	 * @return
	 */
	@RequestMapping(value="agreement",method=RequestMethod.GET)
	public String toAgreementPage(Model model) {
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		return "system/talent/agreement";
		
	}
	/**
	 * 跳转到填写达人申请资料页面1
	  
	
	 * @return
	 */
	
	@RequestMapping(value="toAddUserdatafill_pageOne",method=RequestMethod.GET)
	public String toAddUserdatafill_a(Model model){
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_a";
		
	}
	@RequestMapping(value="toEditUserdatafill_pageOne",method=RequestMethod.GET)
	public String toEditUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model) {
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_a";
		
	}
	/**
	 * 跳转到达人申请入驻资料页面2
	
	 * @return
	 */
	@RequestMapping(value="toAddUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toAddUserdatafill_b(Model model) {
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("bankList", talentBiz.getBankList());
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_b";
		
	}
	@RequestMapping(value="toEditUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toEditUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		model.addAttribute("bankList", talentBiz.getBankList());
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_b";
		
	}
	
	/**
	 * 跳转到达人入驻待审核页面
	 
	 * @return
	 */
	@RequestMapping(value="verification",method=RequestMethod.GET)
	public String verificationPage(Model model) {
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		return "system/talent/verification";
		
	}

	
	/**
	 * 保存资料页面1并跳转到资料页面2
	 * @param request
	 * @param response
	 * @param model
	 * @param vo 封装的达人审核资料对象
	 * @return
	 */
	@RequestMapping(value="saveExamineInfo_pageOne",method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
//		checkVisitPage();
			WebResult<String> result=new WebResult<String>();
			ExamineInfoDTO examineInfoDTO = talentBiz.getExamineInfo();
			WebResultSupport resultSupport = talentBiz.addExamineInfo(vo,ExaminePageNo.PAGE_ONE.getPageNO());
			if (resultSupport.isSuccess()) {
				if (null == examineInfoDTO || examineInfoDTO.getSellerId() <= 0 ) {
					result.setValue("toAddUserdatafill_pageTwo");
				}
				else {
					result.setValue("toEditUserdatafill_pageTwo");
					
				}
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
	 * @param vo 封装的达人审核资料对象
	 * @return
	 */
	@RequestMapping(value="saveExamineInfo_pageTwo",method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
		//	checkVisitPage();
			WebResult<String> result=new WebResult<String>();
			WebResultSupport resultSupport = talentBiz.addExamineInfo(vo,ExaminePageNo.PAGE_TWO.getPageNO());
			if (resultSupport.isSuccess()) {
				result.setValue("verification");
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
		
		model.addAttribute("talentBiz", talentBiz);
		model.addAttribute("serviceTypes", talentBiz.getServiceTypes());
		model.addAttribute("talentInfo", talentBiz.getTalentInfo());
		return "system/talent/eredar";
		
	}
	/**
	 * 保存达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @param vo 封装的达人基本信息对象
	 * @return
	 */
	@RequestMapping(value="saveTalentInfo",method=RequestMethod.POST)
	@ResponseBody
	public WebResultSupport addTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model,TalentInfoVO vo ){
		
			WebResultSupport resultSupport = talentBiz.addTalentInfo(vo);
			
			return resultSupport;
		
		
	}
	
	
	public  String judgeAuthority(Model model,long userId,String pageType){
		String chooseUrl = "/system/merchant/chosetype";
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(userId);
		try {
			MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
			if(!result.isSuccess()){
				return chooseUrl;
			}
			if(null == result.getValue()){
				return null;
			}
			ExamineInfoDTO dto = result.getValue() ;
			int type = dto.getType();
			int status = dto.getExaminStatus();
			if(ExamineStatus.EXAMIN_ING.getStatus() == status ){//等待审核状态
				return "/system/merchant/verification";
			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
				if(ExamineType.MERCHANT.getType()==type){
					return "redirect:/merchant/toAddBasicPage";
				}else if(ExamineType.TALENT.getType()==type){
					return "redirect:/talent/toAddTalentInfo";
				}
			}else if(ExamineStatus.EXAMIN_ERROR.getStatus() == status){//审核不通过
				if("edit".equals(pageType)){
					return null;
				}
				
				info.setType(type);
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
				}
				if(ExamineType.MERCHANT.getType()==type){
					model.addAttribute("type", Constant.MERCHANT_NAME_CN);
				}else if(ExamineType.TALENT.getType()==type){
					model.addAttribute("type", Constant.TALENT_NAME_CN);
				}
				return "/system/merchant/nothrough";
			}else{
				return null;
			}
		} catch (Exception e) {
			model.addAttribute("服务器出现错误，请稍后重新登录");
			return chooseUrl;
		}
		return chooseUrl;
		
	}

}

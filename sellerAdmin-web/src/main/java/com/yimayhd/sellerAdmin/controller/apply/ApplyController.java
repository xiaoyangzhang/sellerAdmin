package com.yimayhd.sellerAdmin.controller.apply;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.MerchantApplyBiz;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.QualificationVO;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;
/**
 * 申请入驻 
 * 
 * @author zhangxy
 *
 */
@Controller
@RequestMapping("/apply")
public class ApplyController extends BaseController {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MerchantBiz merchantBiz;
	
	@Autowired
	private UserService userService;
	
	@Resource
	private MerchantService merchantService;
	
	@Resource
	private ExamineDealService examineDealService;
	
	@Resource
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private TalentBiz talentBiz;
	@Autowired
	private MerchantApplyBiz merchantApplyBiz;
	
	/**
	 * 跳转到选择页面
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "toChoosePage")
	public String toChoosePage(Model model,boolean reject){
		String chooseUrl = "/system/merchant/chosetype";
		if(reject){
			return chooseUrl;
		}
		//权限
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "");
		if(null != judgeRest){
			return judgeRest;
		}else{
			return chooseUrl;
		}
		
	}
	*//**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/merchant/toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		return "/system/merchant/agreement";
	}
	
	*//**
	 * 跳转到商户入驻填写页面A
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/merchant/toDetailPage")
	public String toBusinessDetailPage(Model model){
		//权限
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		if(result.isSuccess()){
			model.addAttribute("examineInfo", result.getValue());
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}
		}
		return "/system/merchant/userdatafill_a";
	}
	
	*//**
	 * 跳转到商户入驻填写页面B
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/merchant/toDetailPageB")
	public String toDetailPageB(Model model){
		//权限
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		if(result.isSuccess()){
			model.addAttribute("examineInfo", result.getValue());
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}
		}
		MemResult<List<BankInfoDTO>> bankResult = talentInfoDealService.queryBankList();
		if(bankResult.isSuccess()){
			model.addAttribute("bankList", bankResult.getValue());
		}
		
		return "/system/merchant/userdatafill_b";
	}
	*//**
	 * 新增或修改商户入驻填写信息PAGE-1
	 * @param userDetailInfo
	 * @return
	 *//*
	@RequestMapping(value="/merchant/saveUserdata" ,method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveUserdata(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/merchant/toDetailPageB");
		}else{
			rest.setWebReturnCode(result.getWebReturnCode());
		}
		return rest;
		
	}
	
	*//**
	 * 新增或修改商户入驻填写信息PAGE-2
	 * @param userDetailInfo
	 * @return
	 *//*
	@RequestMapping(value="/merchant/saveUserdataB" ,method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveUserdataB(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			InfoQueryDTO info = new InfoQueryDTO();
			info.setDomainId(Constant.DOMAIN_JIUXIU);
			info.setSellerId(sessionManager.getUserId());
			info.setType(ExamineType.MERCHANT.getType());
			merchantBiz.changeExamineStatusIntoIng(info);
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/merchant/toVerifyPage");
		}else{
			rest.setWebReturnCode(result.getWebReturnCode());
		}
		return rest;
	}
	
	*//**
	 * 跳转到商户入驻等待审核页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/merchant/toVerifyPage")
	public String toBusinessVerifyPage(Model model){
		return "/system/merchant/verification";
	}
	*/
	
	/**
	 * 处理审核结果信息
	 * @param dto
	 * @return
	 */
	public String getCheckResultMsg(ExamineResultDTO dto) {
		if (dto == null || ( dto.getDealMes() == null)) {
			return null;
		}
		String msg = null;
		if (ExamineStatus.EXAMIN_ERROR.getStatus() == dto.getStatus().getStatus() || ExamineStatus.EXAMIN_NOT_ABLE.getStatus() == dto.getStatus().getStatus()) {
			msg = dto.getDealMes();
		}
		return msg;
	}
	/**
	 * 跳转到达人审核协议
	 * @return
	 */
	@RequestMapping(value="/talent/agreement",method=RequestMethod.GET)
	public String toAgreementPage(Model model) {
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		return "system/talent/agreement";
		
	}
	/**
	 * 跳转到填写达人申请资料页面1
	  
	
	 * @return
	 */
	
	@RequestMapping(value="/talent/toAddUserdatafill_pageOne",method=RequestMethod.GET)
	public String toAddUserdatafill_a(Model model){
//		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
//		if(null != judgeRest){
//			return judgeRest;
//		}
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_a";
		
	}
	@RequestMapping(value="/talent/toEditUserdatafill_pageOne",method=RequestMethod.GET)
	public String toEditUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model) {
//		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
//		if(null != judgeRest){
//			return judgeRest;
//		}
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_a";
		
	}
	/**
	 * 跳转到达人申请入驻资料页面2
	
	 * @return
	 */
	@RequestMapping(value="/talent/toAddUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toAddUserdatafill_b(Model model) {
//		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
//		if(null != judgeRest){
//			return judgeRest;
//		}
		model.addAttribute("bankList", talentBiz.getBankList());
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_b";
		 
	}
	@RequestMapping(value="/talent/toEditUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toEditUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){
//		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
//		if(null != judgeRest){
//			return judgeRest;
//		}
		model.addAttribute("examineInfo", talentBiz.getExamineInfo());
		model.addAttribute("bankList", talentBiz.getBankList());
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_b";
		
	}
	
	/**
	 * 跳转到达人入驻待审核页面
	 
	 * @return
	 */
	@RequestMapping(value="/talent/verification",method=RequestMethod.GET)
	public String verificationPage(Model model) {
//		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
//		if(null != judgeRest){
//			return judgeRest;
//		}
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
	@RequestMapping(value="/talent/saveExamineInfo_pageOne",method=RequestMethod.POST)
	@ResponseBody
	public MemResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
			
			MemResult<String> result=new MemResult<String>();
			ExamineInfoDTO examineInfoDTO = talentBiz.getExamineInfo();
			
			MemResult<Boolean> addResult = talentBiz.addExamineInfo(vo);
//			if (resultSupport == null) {
//				result.setWebReturnCode(WebReturnCode.TALENT_BASIC_SAVE_FAILURE);
//				return result;
//			}
			if (result.isSuccess()) {
				if (null == examineInfoDTO || examineInfoDTO.getSellerId() <= 0) {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toAddUserdatafill_pageTwo");
				} else {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toEditUserdatafill_pageTwo");
					
				}
			}else{
				result.setReturnCode(addResult.getReturnCode());
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
	@RequestMapping(value="/talent/saveExamineInfo_pageTwo",method=RequestMethod.POST)
	@ResponseBody
	public MemResult<String> saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
		MemResult<String> result = new MemResult<>();
		MemResult<Boolean> addResult = talentBiz.addExamineInfo(vo);
			//更新审核状态
			vo.setType(ExamineType.TALENT.getType());
			MemResult<Boolean> updateCheckStatusResult = talentBiz.updateCheckStatus(vo);
			if (updateCheckStatusResult == null) {
				result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
				return result;
			}
			if (addResult.isSuccess()
					&& updateCheckStatusResult.isSuccess()) {
				result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/verification");
			} else if (!addResult.isSuccess()) {
				result.setSuccess(false);
				result.setErrorMsg(addResult.getErrorMsg());
				result.setErrorCode(addResult.getErrorCode());
				
			} else if (!updateCheckStatusResult.isSuccess()) {

				result.setSuccess(false);
				result.setErrorMsg(updateCheckStatusResult.getErrorMsg());
				result.setErrorCode(updateCheckStatusResult.getErrorCode());
				
			}
			return result;
		
	}
	
	
	/**
	 * 判断权限的通用方法
	 * @param model
	 * @param userId
	 * @param pageType
	 * @return
	 */
	/*public  String judgeAuthority(Model model,long userId,String pageType){
		String chooseUrl = "/system/chooseType";
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
				if(ExamineType.MERCHANT.getType()==type){
					return "/system/merchant/verification";
				}else if(ExamineType.TALENT.getType()==type){
					return "/system/talent/verification";
				}
			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
				if(ExamineType.MERCHANT.getType()==type){
					return "redirect:/basicInfo/merchant/toAddBasicPage";
				}else if(ExamineType.TALENT.getType()==type){
					return "redirect:/basicInfo/talent/toAddTalentInfo";
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
				model.addAttribute("url", "/apply/toChoosePage?reject=true");
				return "/system/merchant/nothrough";
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return chooseUrl;
		}
		return chooseUrl;
		
	}*/
	//----------------------2期商家入驻-------------------------//
	public  String judgeAuthority(Model model,long userId,String pageType){
		String url = "/system/seller/chooseType";
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(userId);
		try {
			MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
			if(null == result.getValue() || !result.isSuccess()){
				return url;
			}
			ExamineInfoDTO dto = result.getValue() ;
			int type = dto.getType();
			int status = dto.getExaminStatus();
			if (ExamineStatus.EXAMIN_OK.getStatus() == status) {
				if(ExamineType.MERCHANT.getType()==type){
					return "redirect:/basicInfo/merchant/toAddBasicPage";
				}else if(ExamineType.TALENT.getType()==type){
					return "redirect:/basicInfo/talent/toAddTalentInfo";
				}
			}
//			if(ExamineStatus.EXAMIN_ING.getStatus() == status ){//等待审核状态
//				if(ExamineType.MERCHANT.getType()==type){
//					return "/system/seller/verification";
//				}else if(ExamineType.TALENT.getType()==type){
//					return "/system/talent/verification";
//				}
//			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
//				if(ExamineType.MERCHANT.getType()==type){
//					return "redirect:/basicInfo/seller/toAddBasicPage";
//				}else if(ExamineType.TALENT.getType()==type){
//					return "redirect:/basicInfo/talent/toAddTalentInfo";
//				}
//			}else if(ExamineStatus.EXAMIN_ERROR.getStatus() == status){//审核不通过
//				if("edit".equals(pageType)){
//					return null;
//				}
//				
			if (ExamineStatus.EXAMIN_ERROR.getStatus() == status && ExamineType.MERCHANT.getType() == type) {
				url = "system/seller/nothrough";
			}
			if (ExamineStatus.EXAMIN_ERROR.getStatus() == status && ExamineType.TALENT.getType() == type) {
				url = "system/talent/nothrough";
			}
			info.setType(type);
			MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
			if(rest.isSuccess() && (null!=rest.getValue())){
				model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
			}
//				if(ExamineType.MERCHANT.getType()==type){
//					model.addAttribute("type", Constant.MERCHANT_NAME_CN);
//				}else if(ExamineType.TALENT.getType()==type){
//					model.addAttribute("type", Constant.TALENT_NAME_CN);
//				}
//				model.addAttribute("url", "/apply/toChoosePage?reject=true");
//				return "/system/merchant/nothrough";
//			}else{
//				return null;
//			}
			model.addAttribute("url", "/apply/toChoosePage?reject=true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			url = "system/error/500";
		}
		return url;
		
	}
	@RequestMapping(value = "/toChoosePage")
	public String toChoosePage(Model model,boolean reject){
		String chooseUrl = "/system/seller/chooseType";
		if(reject){
			return chooseUrl;
		}
		long userId = sessionManager.getUserId();
		//权限
		String judgeRest = judgeAuthority(model,userId, "");
		//if(null != judgeRest){
			return judgeRest;
		//}else{
		//	return chooseUrl;
		//}
		//return chooseUrl;
	}
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		InfoQueryDTO examInfoQueryDTO = new InfoQueryDTO();
		examInfoQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		examInfoQueryDTO.setType(ExamineType.MERCHANT.getType());
		examInfoQueryDTO.setSellerId(sessionManager.getUserId());
		WebResult<Boolean> changeExamineStatusResult = merchantApplyBiz.changeExamineStatus(examInfoQueryDTO);
		if (changeExamineStatusResult == null || !changeExamineStatusResult.isSuccess()) {
			return "/system/error/500";
		}
		return "/system/seller/agreement";
	}
	
	/**
	 * 跳转到商户入驻填写页面A
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toDetailPage")
	public String toBusinessDetailPage(Model model){
		//权限
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		WebResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if(result != null && result.isSuccess()){
			model.addAttribute("examineInfo", result.getValue());
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}
		}
		model.addAttribute("bankList", talentBiz.getBankList());
		return "/system/seller/userdatafill_a";
	}
	
	/**
	 * 跳转到商户入驻填写页面B
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toDetailPageB")
	public String toDetailPageB(Model model){
		//权限
		WebResult<List<QualificationVO>> qualificationResult = merchantApplyBiz.getQualification();
		if(qualificationResult.isSuccess() && qualificationResult.getValue() != null) {
			List<QualificationVO> qualificationList = qualificationResult.getValue();
			model.addAttribute("qualifications", qualificationList);
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		WebResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if(result.isSuccess()) {
			ExamineInfoVO examineInfoVO = result.getValue();
			model.addAttribute("examineInfo", examineInfoVO);
			
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}

		}
		
		return "/system/seller/userdatafill_b";
	}
	

	/**
	 * 新增或修改商户入驻填写信息PAGE-1
	 * @param userDetailInfo
	 * @return
	 */
	@RequestMapping(value="/seller/saveUserdata" ,method=RequestMethod.POST)
	@ResponseBody
	public MemResult<String> saveUserdata(ExamineInfoVO examineInfoVO){
//		MemResult<String> checkResult = checkMerchantApplyParams(examineInfoVO);
//		if (!checkResult.isSuccess()) {
//			return checkResult;
//		}
		MemResult<String> rest = new MemResult<String>();
		MemResult<Boolean> result = merchantApplyBiz.submitExamineInfo(examineInfoVO);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/seller/toDetailPageB");
		}else{
			rest.setReturnCode(result.getReturnCode());
		}
		return rest;
		
	}
	
	/**
	 * 新增或修改商户入驻填写信息PAGE-2
	 * @param userDetailInfo
	 * @return
	 */
	@RequestMapping(value="/seller/saveUserdataB" ,method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveUserdataB(ExamineInfoVO examineInfoVO){
		WebResult<String> rest = new WebResult<String>();
		List<MerchantQualificationDO> merchantQualifications = JSON.parseArray(examineInfoVO.getMerchantQualificationStr(), MerchantQualificationDO.class);
		for (MerchantQualificationDO mqDO : merchantQualifications) {
			mqDO.setDomainId(Constant.DOMAIN_JIUXIU);
			mqDO.setSellerId(getCurrentUserId());
			mqDO.setStatus(1);
		}
		examineInfoVO.setMerchantQualifications(merchantQualifications);
		WebResult<String> checkResult = checkMerchantApplyQualification(examineInfoVO);
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		examineInfoVO.setType(ExamineType.MERCHANT.getType());
		MemResult<Boolean> result = merchantApplyBiz.updateMerchantQualification(examineInfoVO);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/seller/toAggrementPage");
		}else{
			rest.setWebReturnCode(WebReturnCode.UPDATE_ERROR);
		}
		return rest;
	}
	
	/**
	 * 跳转到商户入驻等待审核页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toVerifyPage")
	public String toBusinessVerifyPage(Model model){
		return "/system/seller/verification";
	}
	/**
	 * 
	* created by zhangxy
	* @date 2016年5月30日
	* @Title: checkParams 
	* @Description: 店铺入驻第一页参数校验
	* @param @param examineInfoVO
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private MemResult<String> checkMerchantApplyParams(ExamineInfoVO examineInfoVO) {
		MemResult<String> checkResult = new MemResult<String>();
		if (examineInfoVO == null) {
			log.error("param error:examineInfoVO={}",JSON.toJSONString(examineInfoVO));
			checkResult.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入负责人姓名");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCard()) || !(Constant.ID_CARD.equals(examineInfoVO.getPrincipleCard()))
				|| !( Constant.PASSPORT.equals(examineInfoVO.getPrincipleCard())) || !( Constant.GUIDE_CERTIFICATE.equals(examineInfoVO.getPrincipleCard())) 
				|| !( Constant.CAR_LICENSE.equals(examineInfoVO.getPrincipleCard()))) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请选择负责人证件类型");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardId())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入负责人证件号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleTel()) /*|| (Pattern.matches(Constant.REGEX_MOBILE, examineInfoVO.getPrincipleTel()))*/) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入负责人手机号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleMail()) /*|| (Pattern.matches(Constant.REGEX_EMAIL, examineInfoVO.getPrincipleMail()))*/) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入负责人邮箱");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardDown())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请上传负责人身份证反面");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardUp())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请上传负责人身份证正面");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getCardInHand())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请上传负责人手持身份证照片");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getSellerName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入商户名称");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getLegralName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入法定代表人姓名");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getLawPersonCard())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入法定代表人身份证号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getSaleLicenseNumber())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入营业执照号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getTaxRegisterNumber())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入税务登记证号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getAddress())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入营业地址");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getSaleScope())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入经营范围");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenBankId())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请选择开户银行");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入开户名称");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getAccountNum())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入开户帐号");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getProvince()) || StringUtils.isBlank(examineInfoVO.getCity()) || StringUtils.isBlank(examineInfoVO.getAccountBankName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入结算开户行");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getMerchantName())) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请输入店铺名称");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getScopeIds())) {
		checkResult.setErrorCode(-1);
		checkResult.setErrorMsg("请选择经营范围");
		checkResult.setSuccess(false);
		return checkResult;
		}else if (examineInfoVO.getMerchantCategoryId() <= 0) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请选择身份");
			checkResult.setSuccess(false);
			return checkResult;
		}else if (examineInfoVO.getIsDirectSale() <= 0) {
			checkResult.setErrorCode(-1);
			checkResult.setErrorMsg("请选择店铺性质");
			checkResult.setSuccess(false);
			return checkResult;
		}
		//
		/*WebResult<List<MerchantCategoryScopeDO>> merchantCategoryScopeResult = merchantApplyBiz.getMerchantCategoryScope(examineInfoVO.getMerchantCategoryId());
		if (merchantCategoryScopeResult != null && merchantCategoryScopeResult.isSuccess() && merchantCategoryScopeResult.getValue() != null) {
			List<Long> idList = new ArrayList<Long>();
			String[] scopeIds = examineInfoVO.getScopeIds().split(",");
			for (MerchantCategoryScopeDO scopeDO : merchantCategoryScopeResult.getValue()) {
				for (String scopeId : scopeIds) {
					if (scopeDO.getBusinessScopeId() == Long.parseLong(scopeId)) {
						idList.add(Long.parseLong(scopeId));
					}
				}
			}
			if (idList.size() == 0) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请选择正确的经营范围");
				checkResult.setSuccess(false);
				return checkResult;
			}
		}*/
		//
		/*if (examineInfoVO.getIsDirectSale() == ExamineCharacter.DIRECT_SALE.getType()) {
			if (examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.HOME_BRANCH_AGENCY.getSubType() || 
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.BROAD_BRANCH_AGENCY.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.HOTEL_AGENT.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.INDIVIDUAL_BUSINESS.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.PARTNERSHIP_BUSINESS.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.OTHER_ORG.getSubType()) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请选择正确的店铺性质");
				checkResult.setSuccess(false);
				return checkResult;
				
			}
		}*/
		/*if (examineInfoVO.getIsDirectSale() == ExamineCharacter.BOUTIQUE.getType()) {
			if (examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.SCENIC_SPOT.getSubType() || 
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.AMUSEMENT_PARK.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.TICKET_AGENT.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.SCENIC_SPOT_GROUP.getSubType() ||
					examineInfoVO.getMerchantCategoryId() == MerchantCategoryType.TOURISM_PROMOTION.getSubType() ) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请选择正确的店铺性质");
				checkResult.setSuccess(false);
				return checkResult;
				
			}
		}*/
		return checkResult;
	}
	/**
	 * 
	 * created by zhangxy
	 * @date 2016年5月30日
	 * @Title: checkParams 
	 * @Description: 店铺入驻第2页参数校验
	 * @param @param examineInfoVO
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	private WebResult<String> checkMerchantApplyQualification(ExamineInfoVO examineInfoVO) {
		WebResult<String> checkResult = new WebResult<String>();
		if (examineInfoVO == null) {
			log.error("param error:examineInfoVO={}",JSON.toJSONString(examineInfoVO));
			checkResult.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return checkResult;
		}
		WebResult<List<QualificationVO>> qualificationResult = merchantApplyBiz.getQualification();
		if (qualificationResult == null || !qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
			log.error("not found the qualification with category of this merchant, result:QualificationVO={}",JSON.toJSONString(qualificationResult));
			checkResult.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_CATEGORY_QUALIFICATION_FAILED);
			return checkResult;
		}
		List<QualificationVO> queryQualificationList = qualificationResult.getValue();
		List<MerchantQualificationDO> merchantQualificationList = examineInfoVO.getMerchantQualifications();
		for (MerchantQualificationDO merchantQualificationDO : merchantQualificationList) {
			for (QualificationVO vo : queryQualificationList) {
				if (merchantQualificationDO.getQulificationId() == vo.getQualificationId() && vo.isRequired() && StringUtils.isBlank(merchantQualificationDO.getContent())) {
					checkResult.setWebReturnCode(WebReturnCode.UPLOAD_QUALIFICATION_FAILED);
					return checkResult;
				}
			}
		}
		//
		/*if (examineInfoVO.getMerchantCategoryId() == 12 ||  examineInfoVO.getMerchantCategoryId() == 14 ) {
			if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传营业执照副本正面");
				checkResult.setSuccess(false);
				return checkResult;
			}else if (StringUtils.isBlank(examineInfoVO.getTravingCard())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传旅行社业务经营许可证复印件正面");
				checkResult.setSuccess(false);
				return checkResult;
			}else if ( StringUtils.isBlank(examineInfoVO.getTravelAgencyInsurance())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传旅行社责任险保险单复印件正面");
				checkResult.setSuccess(false);
				return checkResult;
			}*/
		//
		/*if (examineInfoVO.getMerchantCategoryId() == 13 || examineInfoVO.getMerchantCategoryId() == 15) {
			if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传营业执照副本正面");
				checkResult.setSuccess(false);
				return checkResult;
			}else if (StringUtils.isBlank(examineInfoVO.getTravingCard())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传旅行社业务经营许可证复印件正面");
				checkResult.setSuccess(false);
				return checkResult;
			}else if ( StringUtils.isBlank(examineInfoVO.getTravelAgencyInsurance())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传旅行社责任险保险单复印件正面");
				checkResult.setSuccess(false);
				return checkResult;
			}else if ( StringUtils.isBlank(examineInfoVO.getTravelAgencyBranchAgreement())) {
				checkResult.setErrorCode(-1);
				checkResult.setErrorMsg("请上传旅行社分社补充协议");
				checkResult.setSuccess(false);
				return checkResult;
			}
			}*/
		//
			/*if (examineInfoVO.getMerchantCategoryId() == 2) {
				for (MerchantScopeDO scope : scopeIds) {
					
					if (scope.getBusinessScopeId() == 2 || scope.getBusinessScopeId() == 3 || scope.getBusinessScopeId() == 4 || scope.getBusinessScopeId() == 7 ) {
						if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传营业执照副本正面");
							checkResult.setSuccess(false);
							return checkResult;
						}else if (StringUtils.isBlank(examineInfoVO.getTravingCard())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传旅行社业务经营许可证复印件正面");
							checkResult.setSuccess(false);
							return checkResult;
						}else if ( StringUtils.isBlank(examineInfoVO.getTravelAgencyInsurance())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传旅行社责任险保险单复印件正面");
							checkResult.setSuccess(false);
							return checkResult;
						}
					if (scope.getBusinessScopeId() == 5) {
						if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传营业执照副本正面");
							checkResult.setSuccess(false);
							return checkResult;
						}
						if (examineInfoVO.getIsDirectSale() == 1 && StringUtils.isBlank(examineInfoVO.getHotelGoodsAuthorization())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传酒店商品授权书");
							checkResult.setSuccess(false);
							return checkResult;
						}
					}
					if (scope.getBusinessScopeId() == 6 ) {
						if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传营业执照副本复印件");
							checkResult.setSuccess(false);
							return checkResult;
						}else if (StringUtils.isBlank(examineInfoVO.getScenicTicketAuthorization())) {
							checkResult.setErrorCode(-1);
							checkResult.setErrorMsg("请上传景点门票授权书或合作协议");
							checkResult.setSuccess(false);
							return checkResult;
						}
					}
					}
				}
			}*/
			//
			/*if (examineInfoVO.getMerchantCategoryId() == 3 ) {
				
			}
		}*/
		return checkResult;
	}
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月7日
	* @Title: getCategoryScope 
	* @Description: 根据选择的身份查询可以选择的经营范围
	* @param @param merchantCategoryId
	* @param @return    设定文件 
	* @return MemResult<String>    返回类型 
	* @throws
	 */
	@RequestMapping(value="getBusinessScope",method=RequestMethod.POST)
	@ResponseBody
	public MemResult<String>  getCategoryScope(long merchantCategoryId) {
		MemResult<String> result = new MemResult<String>();
		if (merchantCategoryId <= 0) {
			result.setReturnCode(MemberReturnCode.CATEGORY_BUSINESS_SCOPE_FAILED);
			return result;
		}
		WebResult<List<MerchantCategoryScopeDO>> queryResult = merchantApplyBiz.getMerchantCategoryScope(merchantCategoryId);
		result.setValue(JSON.toJSONString(queryResult.getValue()));
		return result;
	}
	
}

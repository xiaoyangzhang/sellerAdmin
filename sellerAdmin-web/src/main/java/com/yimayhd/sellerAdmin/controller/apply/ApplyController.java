package com.yimayhd.sellerAdmin.controller.apply;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
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
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExaminePageNo;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.MerchantApplyBiz;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.result.BizResult;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
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
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
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
	
	@RequestMapping(value="/talent/toAddUserdatafill_pageOne",method=RequestMethod.GET)
	public String toAddUserdatafill_a(Model model){
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_a";
		
	}
	@RequestMapping(value="/talent/toEditUserdatafill_pageOne",method=RequestMethod.GET)
	public String toEditUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model) {
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
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
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("bankList", talentBiz.getBankList());
		model.addAttribute("checkResultInfo",getCheckResultMsg(talentBiz.getCheckResult()));
		return "system/talent/userdatafill_b";
		 
	}
	@RequestMapping(value="/talent/toEditUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toEditUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
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
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
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
	@RequestMapping(value="/talent/saveExamineInfo_pageOne",method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
			
			WebResult<String> result=new WebResult<String>();
			ExamineInfoDTO examineInfoDTO = talentBiz.getExamineInfo();
			
			WebResult<Boolean> resultSupport = talentBiz.addExamineInfo(vo);
			if (resultSupport == null) {
				result.setWebReturnCode(WebReturnCode.TALENT_BASIC_SAVE_FAILURE);
				return result;
			}
			if (resultSupport.isSuccess()) {
				if (null == examineInfoDTO || examineInfoDTO.getSellerId() <= 0) {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toAddUserdatafill_pageTwo");
				} else {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toEditUserdatafill_pageTwo");
					
				}
			}else{
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
	@RequestMapping(value="/talent/saveExamineInfo_pageTwo",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
			BizResult<String> bizResult = new BizResult<>();
			WebResult<Boolean> resultSupport = talentBiz.addExamineInfo(vo);
			if (resultSupport == null) {
				bizResult.init(false, -1, "保存失败");
				//BizResult.buildFailResult(-1, "保存失败", false);
				return bizResult;
			}
			//更新审核状态
			MemResult<Boolean> updateCheckStatusResult = talentBiz.updateCheckStatus(vo);
			if (updateCheckStatusResult == null) {
				bizResult.init(false, -1, "保存失败");
				//BizResult.buildFailResult(-1, "保存失败", false);
				return bizResult;
			}
			if (resultSupport.isSuccess()
					&& updateCheckStatusResult.isSuccess()) {
				bizResult.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/verification");
			} else if (!resultSupport.isSuccess()) {
				bizResult.setSuccess(false);
				bizResult.setMsg(resultSupport.getResultMsg());
				bizResult.setCode(resultSupport.getErrorCode());
				
			} else if (!updateCheckStatusResult.isSuccess()) {

				bizResult.setSuccess(false);
				bizResult.setMsg(updateCheckStatusResult.getErrorMsg());
				bizResult.setCode(updateCheckStatusResult.getErrorCode());
				
			}
			return bizResult;
		
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
					return "/system/seller/verification";
				}else if(ExamineType.TALENT.getType()==type){
					return "/system/talent/verification";
				}
			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
				if(ExamineType.MERCHANT.getType()==type){
					return "redirect:/basicInfo/seller/toAddBasicPage";
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
				return "/system/seller/nothrough";
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return chooseUrl;
		}
		return chooseUrl;
		
	}
	@RequestMapping(value = "/toChoosePage")
	public String toChoosePage(Model model,boolean reject){
		String chooseUrl = "/system/chooseType";
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
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toAggrementPage")
	public String toBusinessAggrementPage(Model model){
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
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		//MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		MemResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if(result != null && result.isSuccess()){
			model.addAttribute("examineInfo", result.getValue());
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}
		}
		MemResult<List<MerchantCategoryDO>> categories = merchantApplyBiz.getAllMerchantCategory();
		if (categories != null && categories.isSuccess()) {
			model.addAttribute("merchantCategories", categories.getValue());
			
		}
		MemResult<List<BusinessScopeDO>> scopes = merchantApplyBiz.getAllBusinessScopes();
		if (scopes != null && scopes.isSuccess()) {
			model.addAttribute("businessScopes", scopes.getValue());
			
		}
		//model.addAttribute("businessScopes", merchantApplyBiz.getAllBusinessScopes());
		
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
		String judgeRest = judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		
		//MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		MemResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if(result.isSuccess()){
			model.addAttribute("examineInfo", result.getValue());
			
			if(null!=result.getValue() && (result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW || result.getValue().getExaminStatus() == Constant.MERCHANT_TYPE_HALF)){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes()));
				}
			}
		}
//		MemResult<List<BankInfoDTO>> bankResult = talentInfoDealService.queryBankList();
//		if(bankResult.isSuccess()){
//			model.addAttribute("bankList", bankResult.getValue());
//		}
//		MemResult<List<QualificationDO>> qualifications = merchantApplyBiz.getAllQualificaitons();
//		if (qualifications != null && qualifications.isSuccess()) {
//			model.addAttribute("qualifications", qualifications.getValue());
//			
//		}
		//model.addAttribute("qualifications", merchantApplyBiz.getAllQualificaitons());
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
		MemResult<String> rest = new MemResult<String>();
	//	WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		List<MerchantScopeDO> merchantScopes = JSON.parseArray(examineInfoVO.getMerchantScopeStr(), MerchantScopeDO.class);
		examineInfoVO.setMerchantScopes(merchantScopes);
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
	public MemResult<String> saveUserdataB(ExamineInfoVO examineInfoVO){
		MemResult<String> rest = new MemResult<String>();
		//WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		List<MerchantQualificationDO> merchantQualifications = JSON.parseArray(examineInfoVO.getMerchantQualificationStr(), MerchantQualificationDO.class);
		examineInfoVO.setMerchantQualifications(merchantQualifications);
		MemResult<Boolean> result = merchantApplyBiz.updateMerchantQualification(examineInfoVO);
		if(result.isSuccess()){
			InfoQueryDTO info = new InfoQueryDTO();
			info.setDomainId(Constant.DOMAIN_JIUXIU);
			info.setSellerId(sessionManager.getUserId());
			info.setType(ExamineType.MERCHANT.getType());
			merchantBiz.changeExamineStatusIntoIng(info);
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/seller/toVerifyPage");
		}else{
			rest.setReturnCode(result.getReturnCode());
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
	* @Description: 商户入驻参数校验
	* @param @param examineInfoVO
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean checkParams(ExamineInfoVO examineInfoVO) {
		
		return false;
	}
}

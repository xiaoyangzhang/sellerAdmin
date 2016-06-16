package com.yimayhd.sellerAdmin.controller.apply;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
import com.taobao.common.tfs.TfsManager;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
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
import com.yimayhd.membercenter.enums.MerchantType;
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
@RequestMapping("/apply/")
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
	@Autowired
	private TfsManager tfsManager;
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
	@RequestMapping(value="/talent/nothrough",method=RequestMethod.GET)
	public String toTalentNothrough(Model model) {
		//model.addAttribute("url", "/apply/toChoosePage?reject=true");
		CheckResult(model, MerchantType.TALENT.getType());
		return "/system/talent/nothrough";
		
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
	public WebResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
			WebResult<String> checkResult = checkTalentParams_PageOne(vo);
			if (checkResult != null && !checkResult.isSuccess()) {
				return checkResult;
			}
			WebResult<String> result=new WebResult<String>();
			ExamineInfoDTO examineInfoDTO = talentBiz.getExamineInfo();
			
			WebResult<Boolean> addResult = talentBiz.addExamineInfo(vo);
			if (addResult == null) {
				result.setWebReturnCode(WebReturnCode.TALENT_BASIC_SAVE_FAILURE);
				return result;
			}
			if (result.isSuccess()) {
				if (null == examineInfoDTO || examineInfoDTO.getSellerId() <= 0) {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toAddUserdatafill_pageTwo");
				} else {
					result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/toEditUserdatafill_pageTwo");
					
				}
			}else{
				result.setWebReturnCode(addResult.getWebReturnCode());
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
	public WebResult<String> saveExamineFile_b(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
		WebResult<String> checkResult = checkTalentParams_PageTwo(vo);
		if (checkResult != null && !checkResult.isSuccess()) {
			return checkResult;
		}
		WebResult<String> result = new WebResult<>();
		WebResult<Boolean> addResult = talentBiz.addExamineInfo(vo);
		//更新审核状态
		vo.setType(ExamineType.TALENT.getType());
		WebResult<Boolean> updateCheckStatusResult = talentBiz.updateCheckStatus(vo);
		if (updateCheckStatusResult == null) {
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			return result;
		}
		if (addResult.isSuccess()
				&& updateCheckStatusResult.isSuccess()) {
			result.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/talent/verification");
		} else if (!addResult.isSuccess()) {
			return WebResult.failure(WebReturnCode.ADD_FAILURE, "达人入驻信息保存失败");
		} else if (!updateCheckStatusResult.isSuccess()) {
			return WebResult.failure(WebReturnCode.UPDATE_ERROR, "修改审核状态失败");

			
		}
		return result;
		
	}
	
	
	
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
			if(ExamineStatus.EXAMIN_ING.getStatus() == status  ){//等待审核状态
				if(ExamineType.MERCHANT.getType()==type){
					return "redirect:seller/toVerifyPage";
				}else if(ExamineType.TALENT.getType()==type){
					return "redirect:talent/verification";
				}
			}
			if (ExamineStatus.EXAMIN_ERROR.getStatus() == status && ExamineType.MERCHANT.getType() == type) {
				return "redirect:seller/nothrough";
			}
			if (ExamineStatus.EXAMIN_ERROR.getStatus() == status && ExamineType.TALENT.getType() == type) {
				return "redirect:talent/nothrough";
			}
			//model.addAttribute("url", "/apply/toChoosePage?reject=true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			url = "system/error/500";
		}
		return url;
		
	}
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月16日
	* @Title: CheckResult 
	* @Description: 获取审批失败的原因
	* @param @param model
	* @param @param type    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void CheckResult(Model model,int type) {
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(getCurrentUserId());
		info.setType(type);
		MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
		if(rest.isSuccess() && (null!=rest.getValue())){
			model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
		}
	}
	@RequestMapping(value="/seller/nothrough",method=RequestMethod.GET) 
	public String toMerchantNothrough(Model model) {
		//model.addAttribute("url", "/apply/toChoosePage?reject=true");
		CheckResult(model, MerchantType.MERCHANT.getType());
		return "system/seller/nothrough";
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
		return judgeRest;
	}
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/seller/toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		
		WebResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if (result == null || !result.isSuccess() || result.getValue() == null) {
			return "/system/error/500";
		}
		ExamineInfoVO examineInfoVO = result.getValue();
		ExamineInfoDTO dto = new ExamineInfoDTO();
		dto.setDomainId(Constant.DOMAIN_JIUXIU);
		dto.setMerchantCategoryId(examineInfoVO.getMerchantCategoryId());
		MemResult<List<MerchantCategoryDO>> merchantCategoryResult = merchantApplyBiz.getMerchantCategory(dto);
		if (merchantCategoryResult != null && merchantCategoryResult.isSuccess() && merchantCategoryResult.getValue() != null ) {
			List<MerchantCategoryDO> MerchantCategoryList = merchantCategoryResult.getValue();
			if (MerchantCategoryList.get(0).getType() == MerchantType.TRAVEL_AGENCY.getType() || MerchantCategoryList.get(0).getType() == MerchantType.TOUR_COR.getType()) {
				return "/system/seller/travel_agreement";
			}else if (MerchantCategoryList.get(0).getType() == MerchantType.CITY_COR.getType()) {
				return "/system/seller/city_agreement";
				
			}else if (MerchantCategoryList.get(0).getType() == MerchantType.HOTEL.getType()) {
				return "/system/seller/hotel_agreement";
				
			}else if (MerchantCategoryList.get(0).getType() == MerchantType.SCENIC.getType()) {
				return "/system/seller/scenic_agreement";
				
			}
		}
		return "/system/error/500";
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
	public WebResult<String> saveUserdata(ExamineInfoVO examineInfoVO){
		WebResult<String> checkResult = checkMerchantApplyParams(examineInfoVO);
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		WebResult<String> rest = new WebResult<String>();
		WebResult<Boolean> result = merchantApplyBiz.submitExamineInfo(examineInfoVO);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/apply/seller/toDetailPageB");
		}else{
			rest.setWebReturnCode(result.getWebReturnCode());
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
		WebResult<ExamineInfoVO> result = merchantApplyBiz.getExamineInfo();
		if (result == null || !result.isSuccess() || result.getValue() == null) {
			return "/system/error/500";
		}
		if (result.getValue().getExaminStatus() == ExamineStatus.EXAMIN_ERROR.getStatus()) {
			return "redirect:/apply/seller/nothrough";
		}
		InfoQueryDTO examInfoQueryDTO = new InfoQueryDTO();
		examInfoQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		examInfoQueryDTO.setType(ExamineType.MERCHANT.getType());
		examInfoQueryDTO.setSellerId(sessionManager.getUserId());
		WebResult<Boolean> changeExamineStatusResult = merchantApplyBiz.changeExamineStatus(examInfoQueryDTO);
		if (changeExamineStatusResult == null || !changeExamineStatusResult.isSuccess()) {
			return "/system/error/500";
		}
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
	private WebResult<String> checkMerchantApplyParams(ExamineInfoVO examineInfoVO) {
		WebResult<String> checkResult = new WebResult<String>();
		if (examineInfoVO == null) {
			log.error("param error:examineInfoVO={}",JSON.toJSONString(examineInfoVO));
			checkResult.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return checkResult;
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCard()) || !(Constant.ID_CARD.equals(examineInfoVO.getPrincipleCard())
				||  Constant.PASSPORT.equals(examineInfoVO.getPrincipleCard()) ||  Constant.GUIDE_CERTIFICATE.equals(examineInfoVO.getPrincipleCard()) 
				||  Constant.CAR_LICENSE.equals(examineInfoVO.getPrincipleCard()))){
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardId())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleTel()) || !(Pattern.matches(Constant.REGEX_MOBILE, examineInfoVO.getPrincipleTel()))) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleMail()) || !(Pattern.matches(Constant.REGEX_EMAIL, examineInfoVO.getPrincipleMail()))) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardDown())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请上传必填的资质");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardUp())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请上传必填的资质");

		}else if (StringUtils.isBlank(examineInfoVO.getCardInHand())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请上传必填的资质");

		}else if (StringUtils.isBlank(examineInfoVO.getSellerName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getLegralName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getLawPersonCard())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getSaleLicenseNumber())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getTaxRegisterNumber())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getAddress())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getSaleScope())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenBankId())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getAccountNum())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getProvince()) || StringUtils.isBlank(examineInfoVO.getCity()) || StringUtils.isBlank(examineInfoVO.getAccountBankName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getMerchantName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getScopeIds())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (examineInfoVO.getMerchantCategoryId() <= 0) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (examineInfoVO.getIsDirectSale() <= 0) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}
	
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
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月16日
	* @Title: checkTalentParams_PageOne 
	* @Description: 达人入驻信息第一页参数校验
	* @param @param examineInfoVO
	* @param @return    设定文件 
	* @return WebResult<String>    返回类型 
	* @throws
	 */
	private WebResult<String> checkTalentParams_PageOne(ExamineInfoVO examineInfoVO) {
		if (examineInfoVO == null) {
			log.error("params error:ExamineInfoVO={}",JSON.toJSONString(examineInfoVO));
			return WebResult.failure(WebReturnCode.PARAM_ERROR);
		}else if (StringUtils.isBlank(examineInfoVO.getSellerName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
		}else if (StringUtils.isBlank(examineInfoVO.getLegralName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getLawPersonCard())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getSaleLicenseNumber())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getTaxRegisterNumber())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getSaleScope())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getAddress())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getBusinessLicense())) {
			return WebResult.failure(WebReturnCode.UPLOAD_QUALIFICATION_FAILED, "请上传必填的资质");
			
		}
		return null;
	}
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月16日
	* @Title: checkTalentParams_PageTwo 
	* @Description: 达人入驻信息第二页参数校验
	* @param @param examineInfoVO
	* @param @return    设定文件 
	* @return WebResult<String>    返回类型 
	* @throws
	 */
	
	private WebResult<String> checkTalentParams_PageTwo(ExamineInfoVO examineInfoVO) {
		if (examineInfoVO == null) {
			log.error("params error:ExamineInfoVO={}",JSON.toJSONString(examineInfoVO));
			return WebResult.failure(WebReturnCode.PARAM_ERROR);
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCard()) || !(Constant.ID_CARD.equals(examineInfoVO.getPrincipleCard())
				|| Constant.PASSPORT.equals(examineInfoVO.getPrincipleCard()) ||  Constant.GUIDE_CERTIFICATE.equals(examineInfoVO.getPrincipleCard()) 
				||  Constant.CAR_LICENSE.equals(examineInfoVO.getPrincipleCard()))){
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");

		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardId())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleMail())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleTel())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardDown())) {
			return WebResult.failure(WebReturnCode.UPLOAD_QUALIFICATION_FAILED, "请上传必填的资质");
			
		}else if (StringUtils.isBlank(examineInfoVO.getPrincipleCardUp())) {
			return WebResult.failure(WebReturnCode.UPLOAD_QUALIFICATION_FAILED, "请上传必填的资质");
			
		}else if (StringUtils.isBlank(examineInfoVO.getCardInHand())) {
			return WebResult.failure(WebReturnCode.UPLOAD_QUALIFICATION_FAILED, "请上传必填的资质");
			
		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenBankId())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		
		}else if (StringUtils.isBlank(examineInfoVO.getFinanceOpenName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
				
		
		}else if (StringUtils.isBlank(examineInfoVO.getAccountNum())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		
		}else if (StringUtils.isBlank(examineInfoVO.getAccountBankName())) {
			return WebResult.failure(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE, "请检查填写的数据");
			
		}
		return null;
	}
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月16日
	* @Title: download 
	* @Description: 下载入驻协议
	* @param @param request
	* @param @param response
	* @param @param fileType    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value="download",method=RequestMethod.GET)
	public void download(HttpServletRequest request,HttpServletResponse response,int fileType) {
		try {
			String newFileName = "";
			String tfsFileName = "";
			if (1 == fileType) {
				newFileName = new String("九休开放平台总则.pdf".getBytes("UTF-8"),"ISO-8859-1");
				tfsFileName = "L13NxTBXET1R4oIErK";
			}else if (2 == fileType) {
				newFileName = new String("九休开放平台酒店管理规定.pdf".getBytes("UTF-8"),"ISO-8859-1");
				tfsFileName = "T1.XZTByCT1R4oIErK";
			}else if (4 == fileType) {
				newFileName = new String("九休开放平台同城管理规定.pdf".getBytes("UTF-8"),"ISO-8859-1");
				tfsFileName = "T1pzZTByJT1R4oIErK";
			}else if (5 == fileType) {
				newFileName = new String("九休开放平台景区管理规定.pdf".getBytes("UTF-8"),"ISO-8859-1");
				tfsFileName = "T1ZFZTByhT1R4oIErK";
			}else if (6 == fileType) {
				newFileName = new String("九休开放平台旅行社管理规定.pdf".getBytes("UTF-8"),"ISO-8859-1");
				tfsFileName = "T1IzxTBXdT1R4oIErK";
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("expires", -1);
			response.setHeader("Content-Disposition", "attachment; filename="+ newFileName);
			byte[] b = new byte[10240];
			OutputStream os = response.getOutputStream();
			boolean fetchFileResult = tfsManager.fetchFile(tfsFileName, "pdf",os );
			
			os.write(b);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
	}
}

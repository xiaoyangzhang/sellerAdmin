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
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.result.BizResult;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
import com.yimayhd.tradecenter.client.model.enums.ExamineeStatus;
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
	
	
	
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		return "/system/merchant/agreement";
	}
	
	/**
	 * 跳转到商户入驻填写页面A
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toDetailPage")
	public String toBusinessDetailPage(Model model){
		//权限
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		if(result.isSuccess()){
			model.addAttribute("imgSrc",Constant.TFS_URL);
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
	
	/**
	 * 跳转到商户入驻填写页面B
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toDetailPageB")
	public String toDetailPageB(Model model){
		//权限
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getType());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		if(result.isSuccess()){
			model.addAttribute("imgSrc",Constant.TFS_URL);
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
	/**
	 * 新增或修改商户入驻填写信息PAGE-1
	 * @param userDetailInfo
	 * @return
	 */
	@RequestMapping(value="/merchant/saveUserdata" ,method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveUserdata(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"apply/merchant/toDetailPageB");
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
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"apply/merchant/toVerifyPage");
		}else{
			rest.setWebReturnCode(result.getWebReturnCode());
		}
		return rest;
	}
	
	/**
	 * 跳转到商户入驻等待审核页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toVerifyPage")
	public String toBusinessVerifyPage(Model model){
		return "/system/merchant/verification";
	}
	
	
	/**
	 * 处理审核结果信息
	 * @param dto
	 * @return
	 */
	public void getCheckResultMsg(ExamineResultDTO dto,Model model) {
		if (dto == null || ( dto.getDealMes() == null)) {
			return ;
		}
		if (ExamineStatus.EXAMIN_ERROR.getStatus() == dto.getStatus().getStatus() || ExamineStatus.EXAMIN_NOT_ABLE.getStatus() == dto.getStatus().getStatus()) {
			model.addAttribute("checkResultInfo", Arrays.asList(dto.getDealMes().split(Constant.SYMBOL_SEMIONLON)));
		}
		
	}
	/**
	 * 跳转到达人审核协议
	 * @return
	 */
	@RequestMapping(value="/talent/agreement",method=RequestMethod.GET)
	public String toAgreementPage(Model model) {
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_a";
		
	}
	@RequestMapping(value="toEditUserdatafill_pageOne",method=RequestMethod.GET)
	public String toEditUserdatafill_a(HttpServletRequest request,HttpServletResponse response,Model model) {
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
	@RequestMapping(value="/talent/toAddUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toAddUserdatafill_b(Model model) {
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
		if(null != judgeRest){
			return judgeRest;
		}
		model.addAttribute("bankList", talentBiz.getBankList());
		getCheckResultMsg(talentBiz.getCheckResult(),model);
		return "system/talent/userdatafill_b";
		
	}
	@RequestMapping(value="/talent/toEditUserdatafill_pageTwo",method=RequestMethod.GET)
	public String toEditUserdatafill_b(HttpServletRequest request,HttpServletResponse response,Model model){
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
	@RequestMapping(value="/talent/verification",method=RequestMethod.GET)
	public String verificationPage(Model model) {
		String judgeRest = merchantBiz.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
	public BizResult<String> saveExamineFile_a(HttpServletRequest request,HttpServletResponse response,Model model,ExamineInfoVO vo){
//		checkVisitPage();
			//WebResult<String> result=new WebResult<String>();
			BizResult<String> bizResult = new BizResult<>();
			ExamineInfoDTO examineInfoDTO = talentBiz.getExamineInfo();
			MemResult<Boolean> resultSupport = talentBiz.addExamineInfo(vo,ExaminePageNo.PAGE_ONE.getPageNO());
			if (resultSupport == null) {
				//bizResult.buildFailResult(-1, "保存失败", false);
				bizResult.init(false, -1, "保存失败");
				return bizResult;
			}
			if (resultSupport.isSuccess()) {
				if (null == examineInfoDTO
						|| examineInfoDTO.getSellerId() <= 0) {
					bizResult.setValue("talent/toAddUserdatafill_pageTwo");
				} else {
					bizResult.setValue("talent/toEditUserdatafill_pageTwo");
					
				}
			}
			
			else {
				//result.setWebReturnCode(resultSupport.getWebReturnCode());
				bizResult.buildFailResult(resultSupport.getErrorCode(),
						resultSupport.getErrorMsg(),
						resultSupport.getValue());
				
			}
			return bizResult;
		
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
		//	checkVisitPage();
			//WebResult<String> result=new WebResult<String>();
			BizResult<String> bizResult = new BizResult<>();
			MemResult<Boolean> resultSupport = talentBiz.addExamineInfo(vo,ExaminePageNo.PAGE_TWO.getPageNO());
			MemResult<Boolean> updateCheckStatusResult = talentBiz.updateCheckStatus(vo);
			if (resultSupport == null && updateCheckStatusResult == null) {
				//bizResult.buildFailResult(-1, "保存失败", false);
				bizResult.init(false, -1, "保存失败");
				return bizResult;
			}
			if (resultSupport.isSuccess()
					&& updateCheckStatusResult.isSuccess()) {
				bizResult.setValue("talent/verification");
			} else if (!resultSupport.isSuccess()) {
				//bizResult.setWebReturnCode(resultSupport.getWebReturnCode());
				bizResult.buildFailResult(resultSupport.getErrorCode(),
						resultSupport.getErrorMsg(),
						resultSupport.getValue());
			} else if (!updateCheckStatusResult.isSuccess()) {
				bizResult.buildFailResult(
						updateCheckStatusResult.getErrorCode(),
						updateCheckStatusResult.getErrorMsg(),
						updateCheckStatusResult.getValue());
				//result.setWebReturnCode(updateCheckStatusResult.getWebReturnCode());
			}
			return bizResult;
		
	}
}

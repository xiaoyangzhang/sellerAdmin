package com.yimayhd.sellerAdmin.controller.merchant;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

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
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.controller.merchant.helper.MerchantHelper;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
import com.yimayhd.tradecenter.client.model.enums.ExamineeStatus;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;
/**
 * 
* @ClassName: BussinessShopController 
* @Description: 商户店铺相关
* @author wangjun
* @date 2016年3月25日 下午3:29:05 
*
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController{
	
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
	
	/**
	 * 跳转到选择页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toChoosePage")
	public String toChoosePage(Model model,boolean reject){
		String chooseUrl = "/system/merchant/chosetype";
		if(reject){
			return chooseUrl;
		}
		//权限
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "");
		if(null != judgeRest){
			return judgeRest;
		}else{
			return chooseUrl;
		}
		
//		InfoQueryDTO info = new InfoQueryDTO();
//		info.setDomainId(Constant.DOMAIN_JIUXIU);
//		info.setSellerId(sessionManager.getUserId());
//		try {
//			MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
//			if(!result.isSuccess() || null == result.getValue()){
//				return chooseUrl;
//			}
//			ExamineInfoDTO dto = result.getValue() ;
//			int type = dto.getType();
//			int status = dto.getExaminStatus();
//			if(ExamineStatus.EXAMIN_ING.getStatus() == status ){//等待审核状态
//				return "/system/merchant/verification";
//			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
//				if(ExamineType.MERCHANT.getType()==type){
//					return "redirect:/merchant/toAddBasicPage";
//				}else if(ExamineType.TALENT.getType()==type){
//					return "redirect://talent/toAddTalentInfo";
//				}
//			}else if(ExamineStatus.EXAMIN_ERROR.getStatus() == status){//审核不通过
//				info.setType(type);
//				
//				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
//				if(rest.isSuccess() && (null!=rest.getValue())){
//					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
//				}
//				if(ExamineType.MERCHANT.getType()==type){
//					model.addAttribute("type", Constant.MERCHANT_NAME_CN);
//				}else if(ExamineType.TALENT.getType()==type){
//					model.addAttribute("type", Constant.TALENT_NAME_CN);
//				}
//				model.addAttribute("url", "/merchant/toChoosePage?reject=true");
//				return "/system/merchant/nothrough";
//			}else{
//				return chooseUrl;
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			model.addAttribute("服务器出现错误，请稍后重新登录");
//			return chooseUrl;
//		}
//		return chooseUrl;
	}
	
	/**
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddBasicPage")
	public String toBusinessPage(Model model){
		try {
			//判断权限
			UserDO user = sessionManager.getUser();
			model.addAttribute("nickName", user.getNickname());
			
			BaseResult<MerchantDO> meResult = merchantService.getMerchantBySellerId(user.getId(), Constant.DOMAIN_JIUXIU);
			if(meResult.isSuccess() && null != meResult.getValue()){
				model.addAttribute("id", meResult.getValue().getId());
				model.addAttribute("name",meResult.getValue().getName());
				model.addAttribute("address",meResult.getValue().getAddress());
				model.addAttribute("imgSrc",Constant.TFS_URL);
				if(null != meResult.getValue().getLoopImages()){
					model.addAttribute("djImage", meResult.getValue().getLoopImages().get(0).toString());
				}
				if(null != meResult.getValue().getLogo()){
					model.addAttribute("txImage", meResult.getValue().getLogo());
				}
				model.addAttribute("merchantPrincipalTel", meResult.getValue().getMerchantPrincipalTel());
				model.addAttribute("serviceTel", meResult.getValue().getServiceTel());
				
			}
			return "/system/merchant/merchant";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("服务器出现错误，请稍后重新登录");
			return "/system/merchant/merchant";
		}
		
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	@RequestMapping(value = "/saveBasic",method=RequestMethod.POST)
	@ResponseBody
	public WebResultSupport saveBusinessBasic(MerchantInfoVo basicInfo){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(sessionManager.getUserId());
		userDTO.setNickname(basicInfo.getNickName());

		WebResultSupport result = merchantBiz.updateUser(userDTO);
		if (result.isSuccess()) {
			if (basicInfo.getId() == 0) {// 新增
				WebResultSupport merChantResult = merchantBiz
						.saveMerchant(basicInfo);
				return merChantResult;
			} else {// 修改
				WebResultSupport updateResult = merchantBiz
						.updateMerchantInfo(basicInfo);
				return updateResult;
			}
		} else {
			return result;
		}
	}
	
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		return "/system/merchant/agreement";
	}
	
	/**
	 * 跳转到商户入驻填写页面A
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toDetailPage")
	public String toBusinessDetailPage(Model model){
		//权限
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
			if(null!=result.getValue() && result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
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
	@RequestMapping(value = "toDetailPageB")
	public String toDetailPageB(Model model){
		//权限
		String judgeRest = this.judgeAuthority(model,sessionManager.getUserId(), "edit");
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
			if(null!=result.getValue() && result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW){//审核不通过时
				MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
				if(rest.isSuccess() && (null!=rest.getValue())){
					model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
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
	@RequestMapping(value="saveUserdata" ,method=RequestMethod.POST)
	@ResponseBody
	public WebResult<String> saveUserdata(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/merchant/toDetailPageB");
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
	@RequestMapping(value="saveUserdataB" ,method=RequestMethod.POST)
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
			rest.setValue(WebResourceConfigUtil.getActionDefaultFontPath()+"/merchant/toVerifyPage");
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
	@RequestMapping(value = "toVerifyPage")
	public String toBusinessVerifyPage(Model model){
		return "/system/merchant/verification";
	}
	
	/**
	 * 跳转到商户入驻审核不通过过页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "toNotThrowPage")
	public String toBusinessNotThrowPage(Model model){
		try {
			InfoQueryDTO info = new InfoQueryDTO();
			info.setType(ExamineType.MERCHANT.getType());
			info.setDomainId(Constant.DOMAIN_JIUXIU);
			info.setSellerId(sessionManager.getUserId());
			MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
			if(rest.isSuccess() && (null!=rest.getValue())){
				model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
			}
			return "/system/merchant/nothrough";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "/error";
		}
		
	}*/
	
	public  String judgeAuthority(Model model,long userId,String pageType){
		String chooseUrl = "/system/merchant/chosetype";
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(userId);
		try {
			MemResult<ExamineInfoDTO> result = merchantBiz.queryMerchantExamineInfoBySellerId(info);
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
				model.addAttribute("url", "/merchant/toChoosePage?reject=true");
				return "/system/merchant/nothrough";
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("服务器出现错误，请稍后重新登录");
			return chooseUrl;
		}
		return chooseUrl;
		
	}
	
}

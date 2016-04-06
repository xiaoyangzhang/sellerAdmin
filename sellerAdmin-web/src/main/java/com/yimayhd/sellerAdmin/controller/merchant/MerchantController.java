package com.yimayhd.sellerAdmin.controller.merchant;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
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
	public String toChoosePage(Model model){
		InfoQueryDTO info = new InfoQueryDTO();
		
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoById(info);
		if(result.isSuccess()){
			if(null != result.getValue()){
				if(result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_WAIT ){//等待审核状态
					if(null!=result.getValue().getFinanceOpenBankId()){//信息填写完整
						return "/system/merchant/verification";
					}else{//信息填写不完整，跳转到选择页面
						return "/system/merchant/chosetype";
					}
				}else if(result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_ACCESS){//审核通过
					return "";
				}else if(result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW){//审核不通过
					
					info.setType(result.getValue().getType());
					
					MemResult<ExamineResultDTO> rest = examineDealService.queryExamineDealResult(info);
					if(rest.isSuccess() && (null!=rest.getValue())){
						model.addAttribute("reason", rest.getValue().getDealMes() == null ? null :Arrays.asList(rest.getValue().getDealMes().split(Constant.SYMBOL_SEMIONLON)));
					}
					
					if(ExamineType.MERCHANT.getId()==result.getValue().getType()){
						model.addAttribute("type", "商家");
						model.addAttribute("url", "/sellerAdmin/merchant/toDetailPage");
					}else if(ExamineType.TALENT.getId()==result.getValue().getType()){
						model.addAttribute("type", "达人");
						model.addAttribute("url", "/sellerAdmin/talent/toEditUserdatafill_pageOne");
					}
					return "/system/merchant/nothrough";
				}else{//未知状态
					return "/error";
				}
			}else{//第一次进入
				return "/system/merchant/chosetype";
			}
		}else{
			if(MemberReturnCode.MERCHANT_NOT_FOUND_ERROR.getCode() == result.getErrorCode()){//第一次进入(返回的错误信息为商家不存在)
				return "/system/merchant/chosetype";
			}else{
				return "/error";
			}
		}
	}
	
	/**
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddBasicPage")
	public String toBusinessPage(Model model){
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
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	@RequestMapping(value = "/saveBasic" )
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
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getId());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoById(info);
		if(result.isSuccess()){
			model.addAttribute("imgSrc",Constant.TFS_URL);
			model.addAttribute("examineInfo", result.getValue());
			if(result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW){//审核不通过时
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
		InfoQueryDTO info = new InfoQueryDTO();
		info.setType(ExamineType.MERCHANT.getId());
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoById(info);
		if(result.isSuccess()){
			model.addAttribute("imgSrc",Constant.TFS_URL);
			model.addAttribute("examineInfo", result.getValue());
			if(result.getValue().getExaminStatus()==Constant.MERCHANT_TYPE_NOTTHROW){//审核不通过时
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
	@RequestMapping(value="saveUserdata")
	@ResponseBody
	public WebResult<String> saveUserdata(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			rest.setValue("/sellerAdmin/merchant/toDetailPageB");
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
	@RequestMapping(value="saveUserdataB")
	@ResponseBody
	public WebResultSupport saveUserdataB(UserDetailInfo userDetailInfo){
		WebResult<String> rest = new WebResult<String>();
		
		WebResultSupport result = merchantBiz.saveUserdata(userDetailInfo);
		if(result.isSuccess()){
			rest.setValue("/sellerAdmin/merchant/toVerifyPage");
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
	 */
	@RequestMapping(value = "toNotThrowPage")
	public String toBusinessNotThrowPage(Model model){
		try {
			InfoQueryDTO info = new InfoQueryDTO();
			info.setType(ExamineType.MERCHANT.getId());
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
		
	}
	
	
	
	
	
}

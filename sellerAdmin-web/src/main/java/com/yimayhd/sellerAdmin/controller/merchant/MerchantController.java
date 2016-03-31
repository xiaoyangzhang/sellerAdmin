package com.yimayhd.sellerAdmin.controller.merchant;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
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
	
	/**
	 * 跳转到选择页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toChoosePage")
	public String toChoosePage(Model model){
		return "/system/merchant/chosetype";
	}
	
	/**
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddBusinessPage")
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
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAggrementPage")
	public String toBusinessAggrementPage(Model model){
		return "/system/merchant/agreement";
	}
	
	/**
	 * 跳转到商户入驻填写页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toDetailPage")
	public String toBusinessDetailPage(Model model){
		return "/system/merchant/userdatafill_a";
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
		return "/system/merchant/nothrough";
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	@RequestMapping(value = "/saveBusinessBasic" )
	@ResponseBody
	public WebResultSupport saveBusinessBasic(MerchantInfoVo basicInfo){
		UserDTO userDTO =new UserDTO();
		MerchantDO merchantDO = new MerchantDO();
		MerchantDTO merchantDTO = new MerchantDTO();
		
		setMerchant(userDTO, merchantDO,merchantDTO, basicInfo);
		
		WebResultSupport result = merchantBiz.updateUser(userDTO);
		if(result.isSuccess()){
			if(basicInfo.getId() == 0){//新增
				WebResultSupport merChantResult = merchantBiz.saveMerchant(merchantDO);
				return merChantResult;
			}else{//修改
				WebResultSupport updateResult = merchantBiz.updateMerchantInfo(merchantDTO);
				return updateResult;
			}
		}else{
			return result;
		}
	}
	
	@RequestMapping(value = "/saveUserDataFillA" )
	@ResponseBody
	public WebResultSupport saveUserDataFillA(MerchantInfoVo basicInfo){
		UserDTO userDTO =new UserDTO();
		MerchantDO merchantDO = new MerchantDO();
		MerchantDTO merchantDTO = new MerchantDTO();
		
		setMerchant(userDTO, merchantDO,merchantDTO, basicInfo);
		
		WebResultSupport result = merchantBiz.updateUser(userDTO);
		if(result.isSuccess()){
			if(basicInfo.getId() == 0){//新增
				WebResultSupport merChantResult = merchantBiz.saveMerchant(merchantDO);
				return merChantResult;
			}else{//修改
				WebResultSupport updateResult = merchantBiz.updateMerchantInfo(merchantDTO);
				return updateResult;
			}
		}else{
			return result;
		}
	}
	
	
	
	private void setMerchant(UserDTO userDTO,MerchantDO merchantDO,MerchantDTO merchantDTO,MerchantInfoVo basicInfo){
		userDTO.setId(sessionManager.getUserId());
		userDTO.setNickname(basicInfo.getNickName());
		
		merchantDO.setSellerId(sessionManager.getUserId());
		
		merchantDO.setOption(MerchantOption.MERCHANT.getOption());
		merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantDO.setName(basicInfo.getName());
		merchantDO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDO.setServiceTel(basicInfo.getServiceTel());
		merchantDO.setAddress(basicInfo.getAddress());
		//店铺店招
		merchantDO.setLoopImages(basicInfo.getDjImage());
		//商户头像
		merchantDO.setLogo(basicInfo.getTxImage());
		
		merchantDTO.setId(basicInfo.getId());
		merchantDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantDTO.setName(basicInfo.getName());
		merchantDTO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDTO.setServiceTel(basicInfo.getServiceTel());
		merchantDTO.setAddress(basicInfo.getAddress());
		//店铺店招
		merchantDTO.setLoopImages(basicInfo.getDjImage());
		//商户头像
		merchantDTO.setLogoImage(basicInfo.getTxImage());
	}
	
}

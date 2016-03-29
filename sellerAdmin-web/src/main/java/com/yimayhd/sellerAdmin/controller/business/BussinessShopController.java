package com.yimayhd.sellerAdmin.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.BusinessShopBiz;
import com.yimayhd.sellerAdmin.controller.business.vo.BusinessBasicInfoVo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.UserDTO;
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
@RequestMapping("/business")
public class BussinessShopController extends BaseController{
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private BusinessShopBiz businessShopBiz;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBusinessPage")
	public String toBusinessPage(Model model){
		/*UserDO user = sessionManager.getUser();
		model.addAttribute("nickName", user.getNickname());*/
		return "/system/businessShop/merchant";
	}
	
	/**
	 * 跳转到商户入驻用户协议页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBusinessAggrementPage")
	public String toBusinessAggrementPage(Model model){
		return "/system/businessShop/agreement";
	}
	
	/**
	 * 跳转到商户入驻填写页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBusinessDetailPage")
	public String toBusinessDetailPage(Model model){
		return "/system/businessShop/userdatafill";
	}
	
	/**
	 * 跳转到商户入驻等待审核页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBusinessVerifyPage")
	public String toBusinessVerifyPage(Model model){
		return "/system/businessShop/verification";
	}
	
	/**
	 * 跳转到商户入驻审核不通过过页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBusinessNotThrowPage")
	public String toBusinessNotThrowPage(Model model){
		return "/system/businessShop/nothrough";
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	@RequestMapping(value = "/saveBusinessBasic" ,method = RequestMethod.POST)
	public WebResultSupport saveBusinessBasic(BusinessBasicInfoVo basicInfo){
		UserDTO userDTO =new UserDTO();
		userDTO.setId(sessionManager.getUserId());
		userDTO.setNickname(basicInfo.getNickName());
		
		MerchantDO merchantDO = new MerchantDO();
		merchantDO.setName(basicInfo.getName());
		merchantDO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDO.setServiceTel(basicInfo.getServiceTel());
		merchantDO.setAddress(basicInfo.getAddress());
		//店铺店招
		merchantDO.setLoopImages(basicInfo.getDjImage());
		//商户头像
		merchantDO.setLogo(basicInfo.getTxImage());
		
		WebResultSupport result = businessShopBiz.updateUser(userDTO);
		if(result.isSuccess()){
			WebResultSupport merChantResult = businessShopBiz.saveMerchant(merchantDO);
			if(merChantResult.isSuccess()){
				return merChantResult;
			}else{
				return merChantResult;
			}
		}else{
			return result;
		}
	}
}

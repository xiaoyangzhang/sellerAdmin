package com.yimayhd.sellerAdmin.controller.basicInfo;
/**
 * 商家基本信息
 * 
 * @author zhangxy
 */
import java.util.ArrayList;
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

import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.result.BizResult;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;
@Controller
@RequestMapping("/basicInfo")
public class BasicInfoController extends BaseController {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private TalentBiz talentBiz;
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
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toAddBasicPage")
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
				if(null != meResult.getValue().getBackgroudImage()){
					model.addAttribute("ttImage", meResult.getValue().getBackgroudImage());
				}
				if(null != meResult.getValue().getLogo()){
					model.addAttribute("dbImage", meResult.getValue().getLogo());
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
	@RequestMapping(value = "/merchant/saveBasic",method=RequestMethod.POST)
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
	 * 编辑达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/talent/toAddTalentInfo",method=RequestMethod.GET)
	public String editTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		model.addAttribute("talentBiz", talentBiz);
		model.addAttribute("serviceTypes", talentBiz.getServiceTypes());
		try {
			MemResult<TalentInfoDTO> queryTalentInfoResult = talentInfoDealService.queryTalentInfoByUserId(sessionManager.getUserId(), Constant.DOMAIN_JIUXIU);
			TalentInfoDTO dto = null;
			if (queryTalentInfoResult.isSuccess() && queryTalentInfoResult.getValue() != null) {
				TalentInfoDTO talentInfoDTO = queryTalentInfoResult.getValue();
				
				List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
				if (pictures == null ) {
					pictures = new ArrayList<String>();
				}
				//填充店铺头图集合
				while (pictures.size() < Constant.TALENT_SHOP_PICNUM) {
					pictures.add("");
				}
				dto = talentInfoDTO;
				model.addAttribute("talentInfo", dto);
			}
			return "system/talent/eredar";
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return "system/talent/eredar";
		}
		
	}
	/**
	 * 保存达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @param vo 封装的达人基本信息对象
	 * @return
	 */
	@RequestMapping(value="/talent/saveTalentInfo",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> addTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model,TalentInfoVO vo ){
			BizResult<String> bizResult = new BizResult<>();
			MemResult<Boolean> addTalentInfoResult = talentBiz.addTalentInfo(vo);
			if (addTalentInfoResult == null) {
				bizResult.init(false, -1, "保存失败");
				return bizResult;
			}
			if (addTalentInfoResult.isSuccess()) {
				bizResult.setValue("/toAddTalentInfo");
			}
			else {
				bizResult.init(false, addTalentInfoResult.getErrorCode(),
						addTalentInfoResult.getErrorMsg());
			}
			return bizResult;
		
		
	}
}

package com.yimayhd.sellerAdmin.controller.basicInfo;
/**
 * 商家基本信息
 * 
 * @author zhangxy
 */
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.query.MerchantCategoryQueryDTO;
import com.yimayhd.membercenter.client.service.examine.MerchantApplyService;
import org.apache.commons.lang3.StringUtils;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.enums.MerchantNameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.talent.TalentInfoDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.result.BizResult;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
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
	@Resource
	private MerchantApplyService merchantApplyService;
	
	
	
	/**
	 * 跳转到商户基本信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/merchant/toAddBasicPage")
	public String toBusinessPage(Model model,HttpServletResponse response){
		try {
			UserDO user = sessionManager.getUser();
			InfoQueryDTO info = new InfoQueryDTO();
			info.setDomainId(Constant.DOMAIN_JIUXIU);
			info.setSellerId(user.getId());
			MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
			if (merchantInfoResult == null || !merchantInfoResult.isSuccess() || merchantInfoResult.getValue().getType() != MerchantType.MERCHANT.getType()) {
				String url = UrlHelper.getUrl( WebResourceConfigUtil.getRootPath(), "/error/lackPermission") ;
				response.sendRedirect(url);
			}
			//判断权限
			model.addAttribute("nickName", user.getNickname());
			BaseResult<MerchantDO> meResult = merchantService.getMerchantBySellerId(user.getId(), Constant.DOMAIN_JIUXIU);
			if(meResult.isSuccess() && null != meResult.getValue()){
				
				model.addAttribute("id", meResult.getValue().getId());
				model.addAttribute("name",meResult.getValue().getName());
				model.addAttribute("address",meResult.getValue().getAddress());
				model.addAttribute("merchantName",meResult.getValue().getName());
				if(null != meResult.getValue().getBackgroudImage()){
					model.addAttribute("ttImage", meResult.getValue().getBackgroudImage());
				}
				if(null != meResult.getValue().getLogo()){
					model.addAttribute("dbImage", meResult.getValue().getLogo());
				}
				model.addAttribute("merchantPrincipalTel", meResult.getValue().getMerchantPrincipalTel());
				model.addAttribute("serviceTel", meResult.getValue().getServiceTel());
				model.addAttribute("merchantDesc", meResult.getValue().getTitle());
				if (StringUtils.isBlank(meResult.getValue().getBackgroudImage()) || StringUtils.isBlank(meResult.getValue().getServiceTel()) || StringUtils.isBlank(meResult.getValue().getLogo()) ) {
					return "/system/seller/merchant_head";
				}
				/**添加协议下载***/
				/*ExamineInfoDTO examineInfoDTO = merchantInfoResult.getValue();
				MerchantCategoryQueryDTO merchantCategoryQueryDTO = new MerchantCategoryQueryDTO();
				merchantCategoryQueryDTO.setDomainId(examineInfoDTO.getDomainId());
				merchantCategoryQueryDTO.setId(examineInfoDTO.getMerchantCategoryId());
				MemResult<List<MerchantCategoryDO>> merchantCategoryDOListResult  =  merchantApplyService.getMerchantCategory(merchantCategoryQueryDTO);
				if(!merchantCategoryDOListResult.isSuccess()||merchantCategoryDOListResult.getValue()==null){
					log.error(merchantCategoryDOListResult.getErrorMsg()+"domainId:{},MerchantCategoryId:{},sellerId:{}",examineInfoDTO.getDomainId(),examineInfoDTO.getMerchantCategoryId(),examineInfoDTO.getSellerId());
					return "/system/seller/merchant";
				}
				MerchantCategoryDO merchantCategoryDO = merchantCategoryDOListResult.getValue().get(0);*/
				Map<Integer,String> merchantNameTypeMap = new TreeMap<Integer,String>(new Comparator<Integer>() {
					public int compare(Integer obj1, Integer obj2) {
						// 降序排序
						return obj2.compareTo(obj1);
					}
				});
				merchantNameTypeMap.put(MerchantNameType.ALL_USER.getType(),MerchantNameType.ALL_USER.getScheme());
				merchantNameTypeMap.put(MerchantNameType.TOUR_COR.getType(),MerchantNameType.TOUR_COR.getScheme());
				merchantNameTypeMap.put(MerchantNameType.HOTEL.getType(),MerchantNameType.HOTEL.getScheme());
				merchantNameTypeMap.put(MerchantNameType.SCENIC.getType(),MerchantNameType.SCENIC.getScheme());
				merchantNameTypeMap.put(MerchantNameType.CITY_COR.getType(),MerchantNameType.CITY_COR.getScheme());
				model.addAttribute("merchantNameTypeMap", merchantNameTypeMap);


			}

			//type
			return "/system/seller/merchant";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "/system/error/500";
		}
		
	}
	
	/**
	 * 保存商户基本信息
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/merchant/saveBasic",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> saveBusinessBasic(MerchantInfoVo basicInfo,HttpServletResponse response){
		BizResult<String> result = new BizResult<String>();
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
		if (merchantInfoResult == null || !merchantInfoResult.isSuccess() || merchantInfoResult.getValue().getType() != MerchantType.MERCHANT.getType()) {
			String url = UrlHelper.getUrl( WebResourceConfigUtil.getRootPath(), "/error/lackPermission") ;
				result.setSuccess(false);
				result.setValue(url);
				return result;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setId(sessionManager.getUserId());
		userDTO.setNickname(basicInfo.getNickName());

		 WebResultSupport updateUserResult = merchantBiz.updateUser(userDTO);
		if (updateUserResult.isSuccess()) {
			if (basicInfo.getId() == 0) {// 新增
				WebResultSupport merChantResult = merchantBiz
						.saveMerchant(basicInfo);
				if (merChantResult == null || !merChantResult.isSuccess()) {
					result.setSuccess(false);
					result.setMsg(merChantResult.getResultMsg());
					return result;
				}
				//return merChantResult;
			} else {// 修改
				WebResultSupport updateResult = merchantBiz
						.updateMerchantInfo(basicInfo);
				if (updateResult == null || !updateResult.isSuccess()) {
					result.setSuccess(false);
					result.setMsg(updateResult.getResultMsg());
					return result;
				}
				//return updateResult;
			}
			return result;
		} 
		result.setSuccess(false);
		result.setMsg(updateUserResult.getResultMsg());
		return result;
		
	}
	/**
	 * 编辑达人基本信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/talent/toAddTalentInfo",method=RequestMethod.GET)
	public String toAddTalentInfo(HttpServletRequest request,HttpServletResponse response,Model model) {
		
	//	model.addAttribute("talentBiz", talentBiz);
		model.addAttribute("serviceTypes", talentBiz.getServiceTypes());
		//try {
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
		if (merchantInfoResult == null || !merchantInfoResult.isSuccess() || merchantInfoResult.getValue().getType() != MerchantType.TALENT.getType()) {
			String url = UrlHelper.getUrl( WebResourceConfigUtil.getRootPath(), "/error/lackPermission") ;
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				log.error("merchantBiz.queryMerchantExamineInfoBySellerId param:InfoQueryDTO={} ,error:{}",JSON.toJSONString(info),e);
			}
		}
		WebResult<TalentInfoDTO> dtoResult = talentBiz.queryTalentInfoByUserId();
		if (dtoResult == null) {
			return "/system/error/500";
		}
		if (dtoResult.isSuccess()) {
			TalentInfoDTO talentInfoDTO = dtoResult.getValue();
			if (talentInfoDTO == null || talentInfoDTO.getTalentInfoDO() == null) {
				return "/system/error/500";
			}
			List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
			if (pictures == null ) {
				pictures = new ArrayList<String>();
			}
			//填充店铺头图集合
			while (pictures.size() < Constant.TALENT_SHOP_PICNUM) {
				pictures.add("");
			}
			model.addAttribute("talentInfo", talentInfoDTO);
			TalentInfoDO talentInfoDO = talentInfoDTO.getTalentInfoDO();
			if (StringUtils.isBlank(talentInfoDO.getAvatar()) || StringUtils.isBlank(talentInfoDO.getReallyName()) || StringUtils.isBlank(talentInfoDO.getServeDesc()) || 
					talentInfoDO.getCityCode() <= 0 || talentInfoDO.getProvinceCode() <= 0 || talentInfoDO.getBirthday() == null || 
					talentInfoDO.getGender() <= 0 || CollectionUtils.isEmpty(talentInfoDO.getPictures()) || 
					CollectionUtils.isEmpty(talentInfoDO.getServiceTypes()) || talentInfoDTO.getPictureTextDTO() == null ) {
				return "/system/talent/talent";
			}
		}
		return "system/talent/eredar";
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			return "system/talent/eredar";
//		}
		
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
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(sessionManager.getUserId());
		MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
		if (merchantInfoResult == null || !merchantInfoResult.isSuccess() || merchantInfoResult.getValue().getType() != MerchantType.TALENT.getType()) {
			String url = UrlHelper.getUrl( WebResourceConfigUtil.getRootPath(), "/error/lackPermission") ;
			try {
				//request.getRequestDispatcher("/error/lackPermission").forward(request, response);
				//response.sendRedirect(url);
				bizResult.setSuccess(false);
				bizResult.setValue(url);
				return bizResult;
			} catch (Exception e) {
				log.error("merchantBiz.queryMerchantExamineInfoBySellerId param:InfoQueryDTO={} ,error:{}",JSON.toJSONString(info),e);
			}
		}
		WebResult<Boolean> addTalentInfoResult = talentBiz.addTalentInfo(vo);
		if (addTalentInfoResult == null) {
			bizResult.init(false, -1, "保存失败");
			return bizResult;
		}
		if (addTalentInfoResult.isSuccess()) {
			bizResult.setValue("/toAddTalentInfo");
		}
		else {
			bizResult.init(false, addTalentInfoResult.getErrorCode(),
					addTalentInfoResult.getResultMsg());
		}
		return bizResult;
		
		
	}
	
}

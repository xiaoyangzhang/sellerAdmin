package com.yimayhd.sellerAdmin.biz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.entity.merchant.HomePage;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantInfo;
import org.yimayhd.sellerAdmin.entity.merchant.Qualification;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.MerchantVO;
import com.yimayhd.sellerAdmin.repo.MerchantRepo;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.query.MerchantQuery;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.session.manager.SessionManager;

public class MerchantBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(MerchantBiz.class);
	
	@Autowired
	private MerchantRepo merchantRepo;
	@Autowired
	private SessionManager sessionManager;
	@Resource
	private ExamineDealService examineDealService;
	
	@MethodLogger
	public MemResult<ExamineInfoDTO> queryMerchantExamineInfoBySellerId(InfoQueryDTO info){
		return merchantRepo.queryMerchantExamineInfoBySellerId(info);
	}
	
	/**
	 * 
	 * @param name
	 * @return 存在数据，返回true；  查询失败，返回true； 其他返回false
	 */
	public boolean isMerchantNameExist(String name, long sellerId){
		if( StringUtils.isBlank(name) ){
			return false;
		}
		MerchantQuery merchantQuery = new MerchantQuery();
		merchantQuery.setName(name);
		merchantQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		WebResult<List<MerchantDO>> result = merchantRepo.queryMerchant(merchantQuery);
		if( result == null || !result.isSuccess() ){
			//如果查询操作有出错，为防止出现重复名称，此处返回true，禁止用户此次操作
			return true;
		}
		List<MerchantDO> merchantDOs = result.getValue()	;
		if( CollectionUtils.isEmpty(merchantDOs) ){
			return false;
		}
		for( MerchantDO merchantDO : merchantDOs ){
			if( merchantDO.getSellerId() != sellerId ){
				return true ;
			}
		}
		return false;
	}
	
	
	/**
	 * 修改用户信息
	 * @param userDTO
	 * @return
	 */
	@MethodLogger
	public WebResultSupport updateUser(UserDTO userDTO){
		WebResultSupport webResult = new WebResultSupport();
		BaseResult<UserDO> result = merchantRepo.updateUser(userDTO);
		LOGGER.debug("userDTO={}", JSONObject.toJSONString(userDTO));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.USER_NICKNAME_EXIT);
		}
		return webResult;
		
	}
	/**
	 * 保存商户基本信息
	 * @param basicInfo
	 * @return
	 */
	@MethodLogger
	public WebResultSupport saveMerchant(MerchantInfoVo basicInfo){
		WebResultSupport webResult = new WebResultSupport();
		MerchantDO merchantDO = new MerchantDO();

		setMerchantAddMag(merchantDO, basicInfo);

		BaseResult<MerchantDO> result = merchantRepo.saveMerchant(merchantDO);
		LOGGER.debug("merchantDO={}", JSONObject.toJSONString(merchantDO));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_BASIC_SAVE_FAILURE);
		}
		return webResult;
	}
	
	/**
	 * 修改商户基本信息
	 * @param basicInfo
	 * @return
	 */
	@MethodLogger
	public WebResultSupport updateMerchantInfo(MerchantInfoVo basicInfo){
		WebResultSupport webResult = new WebResultSupport();
		MerchantDTO merchantDTO = new MerchantDTO();

		setMerchantEditMag( merchantDTO, basicInfo);

		BaseResult<Boolean> result = merchantRepo.updateMerchantInfo(merchantDTO);
		LOGGER.debug("merchantDTO={}", JSONObject.toJSONString(merchantDTO));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_BASIC_EDIT_FAILURE);
		}
		return webResult;
	}
	
	/**
	 * 保存或修改商户入驻填写信息
	 * @param userDetailInfo
	 * @return
	 */
	@MethodLogger
	public WebResultSupport saveUserdata(UserDetailInfo userDetailInfo){
		WebResultSupport webResult = new WebResultSupport();
		long userId = sessionManager.getUserId() ;
		boolean exit = isMerchantNameExist(userDetailInfo.getMerchantName(), userId);
		if( exit ){
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_NAME_EXIST);
			return webResult ;
		}
		
		
		ExamineInfoDTO ex = new ExamineInfoDTO();
		
		setExamineInfo(ex,userDetailInfo);
		//提交的当前页数，如果是第一次提交，不update当前的status
		MemResult<Boolean> result = merchantRepo.saveUserdata(ex);
		LOGGER.debug("examineInfoDTO={}", JSONObject.toJSONString(ex));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE);
		}
		return webResult;
	}
	
	
	/**
	 * 修改商户入驻状态
	 * @param infoQueryDTO
	 * @return
	 */
	@MethodLogger
	public WebResultSupport changeExamineStatusIntoIng(InfoQueryDTO infoQueryDTO){
		WebResultSupport webResult = new WebResultSupport();
		MemResult<Boolean> result = merchantRepo.changeExamineStatusIntoIng(infoQueryDTO);
		LOGGER.debug("infoQueryDTO={}", JSONObject.toJSONString(infoQueryDTO));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE);
		}
		return webResult;
	}

	/**
	 * 判断权限的通用方法
	 * @return
	 *//*
	public  String judgeAuthority(Model model,long userId,String pageType){
		UserDO userDO = sessionManager.getUser() ;
		long option = userDO.getOptions() ;
		boolean isTalent = UserOptions.CERTIFICATED.has(option) ;
		boolean isMerchant = UserOptions.COMMERCIAL_TENANT.has(option) ;
		if(isTalent){
			return "redirect:/basicInfo/talent/toAddTalentInfo";
		}else if(isMerchant){
			return "redirect:/basicInfo/merchant/toAddBasicPage";
		}
		
		String chooseUrl = "/system/merchant/chosetype";
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
				if(MerchantType.MERCHANT.getType()==type){
					return "/system/merchant/verification";
				}else if(MerchantType.TALENT.getType()==type){
					return "/system/talent/verification";
				}
			}else if(ExamineStatus.EXAMIN_OK.getStatus() == status){//审核通过
				if(MerchantType.MERCHANT.getType()==type){
					return "redirect:/basicInfo/merchant/toAddBasicPage";
				}else if(MerchantType.TALENT.getType()==type){
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
				if(MerchantType.MERCHANT.getType()==type){
					model.addAttribute("type", Constant.MERCHANT_NAME_CN);
				}else if(MerchantType.TALENT.getType()==type){
					model.addAttribute("type", Constant.TALENT_NAME_CN);
				}
				model.addAttribute("url", "/apply/toChoosePage?reject=true");
				return "/system/merchant/nothrough";
			}else{
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.addAttribute("服务器出现错误，请稍后重新登录");
			return chooseUrl;
		}
		return chooseUrl;
		
	}*/
	
	
	private void setMerchantAddMag(MerchantDO merchantDO,MerchantInfoVo basicInfo){
		merchantDO.setSellerId(sessionManager.getUserId());
		
		merchantDO.setOption(MerchantOption.MERCHANT.getOption());
		merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
		//merchantDO.setName(basicInfo.getName());
		merchantDO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDO.setServiceTel(basicInfo.getServiceTel());
		merchantDO.setAddress(basicInfo.getAddress());
		//店铺头图
		merchantDO.setBackgroudImage(basicInfo.getTtImage());
		//店铺店标
		merchantDO.setLogo(basicInfo.getDbImage());
		merchantDO.setTitle(basicInfo.getMerchantDesc());
		
	}
	
	private void setMerchantEditMag(MerchantDTO merchantDTO,MerchantInfoVo basicInfo){
		merchantDTO.setId(basicInfo.getId());
		merchantDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		//merchantDTO.setName(basicInfo.getName());
		merchantDTO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDTO.setServiceTel(basicInfo.getServiceTel());
		merchantDTO.setAddress(basicInfo.getAddress());
		//店铺头图
		merchantDTO.setBackgroundImage(basicInfo.getTtImage());;
		//店铺店标
		merchantDTO.setLogoImage(basicInfo.getDbImage());
		merchantDTO.setTitle(basicInfo.getMerchantDesc());
	}
	
	
	
	private  void setExamineInfo(ExamineInfoDTO ex,UserDetailInfo userDetailInfo){
		ex.setId(userDetailInfo.getId());
		
		
		ex.setSellerId(sessionManager.getUserId());
		ex.setSellerName(userDetailInfo.getSellerName());
		ex.setDomainId(Constant.DOMAIN_JIUXIU);
		ex.setType(ExamineType.MERCHANT.getType());
		ex.setMerchantName(userDetailInfo.getMerchantName());
		ex.setLegralName(userDetailInfo.getLegralName());
		ex.setAddress(userDetailInfo.getAddress());
		ex.setSaleScope(userDetailInfo.getSaleScope());
		ex.setLegralCardUp(userDetailInfo.getLegralCardUp());
		ex.setLegralCardDown(userDetailInfo.getLegralCardDown());
		ex.setBusinessLicense(userDetailInfo.getBusinessLicense());
		ex.setOrgCard(userDetailInfo.getOrgCard());
		ex.setAffairsCard(userDetailInfo.getAffairsCard());
		ex.setOpenCard(userDetailInfo.getOpenCard());
		ex.setTravingCard(userDetailInfo.getTravingCard());
		ex.setTouchProve(userDetailInfo.getTouchProve());
		ex.setTravelAgencyAuthorization(userDetailInfo.getTravelAgencyAuthorization());
		ex.setTravelAgencyInsurance(userDetailInfo.getTravelAgencyInsurance());
		
		ex.setPrincipleName(userDetailInfo.getPrincipleName());
		ex.setPrincipleCard(userDetailInfo.getCard());
		ex.setPrincipleCardId(userDetailInfo.getPrincipleCardId());
		ex.setPrincipleTel(userDetailInfo.getPrincipleTel());
		ex.setPrincipleMail(userDetailInfo.getPrincipleMail());
		ex.setPrincipleCardUp(userDetailInfo.getPrincipleCardUp());
		ex.setPrincipleCardDown(userDetailInfo.getPrincipleCardDown());
		
		ex.setFinanceOpenBankId(userDetailInfo.getBank());
		ex.setFinanceOpenBankName(userDetailInfo.getFinanceOpenBankName());
		ex.setFinanceOpenName(userDetailInfo.getFinanceOpenName());
		ex.setAccountNum(userDetailInfo.getAccountNum());
		ex.setAccountBankProvince(userDetailInfo.getAccountBankProvince());
		ex.setAccountBankProvinceCode(userDetailInfo.getProvince());
		ex.setAccountBankCity(userDetailInfo.getAccountBankCity());
		ex.setAccountBankCityCode(userDetailInfo.getCity());
		ex.setAccountBankName(userDetailInfo.getAccountBankName());
		
		ex.setCooperation1(userDetailInfo.getCooperation1());
		ex.setCooperation2(userDetailInfo.getCooperation2());
		ex.setCooperation3(userDetailInfo.getCooperation3());
		ex.setCooperation4(userDetailInfo.getCooperation4());
		ex.setCooperation5(userDetailInfo.getCooperation5());
		ex.setCardInHand(userDetailInfo.getCardInHand());
	}
	
	/**
	 * 通过用户id获取用户的信息
	* created by zhangxiaoyang
	* @date 2016年8月22日
	* @Title: queryMerchantBySellerId 
	* @Description: TODO
	* @param @param merchantVO
	* @param @return    设定文件 
	* @return WebResult<MerchantDO>    返回类型 
	* @throws
	 */
	public WebResult<MerchantDO> queryMerchantBySellerId(MerchantVO merchantVO) {
		WebResult<MerchantDO> result = new WebResult<MerchantDO>();
		if (merchantVO == null || merchantVO.getDomainId() <= 0 || merchantVO.getUserId() <= 0) {
			LOGGER.error("param error:MerchantVO={}",JSON.toJSONString(merchantVO));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		//FIXME 上面的参数错误的校验，应该放在merchantRepo.queryMerchantBySellerId中
		LOGGER.debug("merchantRepo.queryMerchantBySellerId param:MerchantVO={}",JSON.toJSONString(merchantVO));
		BaseResult<MerchantDO> queryMerchantResult = merchantRepo.queryMerchantBySellerId(merchantVO);
		LOGGER.debug("merchantRepo.queryMerchantBySellerId result:{}",JSON.toJSONString(queryMerchantResult));
		if (queryMerchantResult == null || !queryMerchantResult.isSuccess() || queryMerchantResult.getValue() == null) {
			LOGGER.error("param:MerchantVO={},result:{}",JSON.toJSONString(merchantVO),JSON.toJSONString(queryMerchantResult));
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_FAILED);
			return result;
		}
		
		result.setValue(queryMerchantResult.getValue());
		return result;
	}
	/**
	 * 查询店铺资质
	* created by zhangxiaoyang
	* @date 2016年8月22日
	* @Title: queryMerchantQualification 
	* @Description: TODO
	* @param @param queryDTO
	* @param @return    设定文件 
	* @return WebResult<List<MerchantQualificationDO>>    返回类型 
	* @throws
	 */
	public WebResult<Qualification> queryMerchantQualification(QualificationQueryDTO queryDTO) {
		WebResult<Qualification> result = new WebResult<Qualification>();
		if (queryDTO == null || queryDTO.getDomainId() <= 0 || queryDTO.getSellerId() <= 0 || queryDTO.getQualificationId() <= 0) {
			LOGGER.error("param error:QualificationQueryDTO={}",JSON.toJSONString(queryDTO));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		//FIXME 上面的参数错误的校验，应该放在merchantRepo.queryMerchantQualification中
		MemResult<List<MerchantQualificationDO>> merchantQualificationResult = merchantRepo.queryMerchantQualification(queryDTO);
		if (merchantQualificationResult == null || !merchantQualificationResult.isSuccess() || CollectionUtils.isEmpty(merchantQualificationResult.getValue())) {
			LOGGER.error("param:QualificationQueryDTO={},result:{}",JSON.toJSONString(queryDTO),JSON.toJSONString(merchantQualificationResult));
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_QUALIFICATION_FAILED);
			return result;
		}
		List<MerchantQualificationDO> merchantQualification = merchantQualificationResult.getValue();
		Qualification qualification = new Qualification();
		qualification.saleLicensePic = merchantQualification.get(0).getContent();
		result.setValue(qualification);
		return result;
	}
}

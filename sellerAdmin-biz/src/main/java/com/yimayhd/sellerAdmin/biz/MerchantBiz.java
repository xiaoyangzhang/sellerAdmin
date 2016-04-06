package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.repo.MerchantRepo;
import com.yimayhd.sellerAdmin.vo.merchant.MerchantInfoVo;
import com.yimayhd.sellerAdmin.vo.merchant.UserDetailInfo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.session.manager.SessionManager;

public class MerchantBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(MerchantBiz.class);
	
	@Autowired
	private MerchantRepo merchantRepo;
	@Autowired
	private SessionManager sessionManager;
	
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
			webResult.setWebReturnCode(WebReturnCode.UPDATE_USER_ERROR);
		}
		return webResult;
		
	}
	/**
	 * 保存商户基本信息
	 * @param merchantDO
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
	 * @param merchantDTO
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
		ExamineInfoDTO ex = new ExamineInfoDTO();
		
		setExamineInfo(ex,userDetailInfo);
		
		WebResultSupport webResult = new WebResultSupport();
		MemResult<Boolean> result = merchantRepo.saveUserdata(ex);
		LOGGER.debug("examineInfoDTO={}", JSONObject.toJSONString(ex));
		if(result.isSuccess()){
			webResult.isSuccess();
		}else{
			webResult.setWebReturnCode(WebReturnCode.MERCHANT_INFO_EDIT_FAILURE);
		}
		return webResult;
	}
	
	private void setMerchantAddMag(MerchantDO merchantDO,MerchantInfoVo basicInfo){
		merchantDO.setSellerId(sessionManager.getUserId());
		
		merchantDO.setOption(MerchantOption.MERCHANT.getOption());
		merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
		//merchantDO.setName(basicInfo.getName());
		merchantDO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDO.setServiceTel(basicInfo.getServiceTel());
		merchantDO.setAddress(basicInfo.getAddress());
		//店铺店招
		merchantDO.setLoopImages(basicInfo.getDjImage());
		//商户头像
		merchantDO.setLogo(basicInfo.getTxImage());
		
	}
	
	private void setMerchantEditMag(MerchantDTO merchantDTO,MerchantInfoVo basicInfo){
		merchantDTO.setId(basicInfo.getId());
		merchantDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		//merchantDTO.setName(basicInfo.getName());
		merchantDTO.setMerchantPrincipalTel(basicInfo.getMerchantPrincipalTel());
		merchantDTO.setServiceTel(basicInfo.getServiceTel());
		merchantDTO.setAddress(basicInfo.getAddress());
		//店铺店招
		merchantDTO.setLoopImages(basicInfo.getDjImage());
		//商户头像
		merchantDTO.setLogoImage(basicInfo.getTxImage());
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
		
	}
}

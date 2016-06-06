package com.yimayhd.sellerAdmin.biz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.CategoryQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.BusinessScopeQueryDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.query.MerchantCategoryQueryDTO;
import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.enums.MerchantCategoryType;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.MerchantConverter;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.QualificationVO;
import com.yimayhd.sellerAdmin.repo.MerchantApplyRepo;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
* @ClassName: MerchantApplyBiz
* @Description: 管理商家入驻
* @author zhangxy
* @date 2016年5月30日 上午9:45:31
*
 */
public class MerchantApplyBiz {

	protected final Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private MerchantApplyRepo merchantApplyRepo;
	public MemResult<Boolean> submitExamineInfo(ExamineInfoVO examineInfoVO) {
		long userId = sessionManager.getUserId();
		MemResult<Boolean> result = new MemResult<Boolean>();
		if (examineInfoVO == null) {
			log.error("params error:examineInfoVO={}",JSON.toJSONString(examineInfoVO));
			result.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
			return result;
		}
		try {
//			InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
//			examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
//			examineQueryDTO.setType(MerchantType.MERCHANT.getType());
//			examineQueryDTO.setSellerId(userId);
//			MemResult<ExamineInfoDTO> examineInfoResult = merchantApplyRepo.getExamineInfo(examineQueryDTO);
			BusinessScopeQueryDTO queryDTO = new BusinessScopeQueryDTO();
			queryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
			queryDTO.setSellerId(userId);
			MemResult<List<MerchantScopeDO>> merchantScopeResult = merchantApplyRepo.getMerchantScope(queryDTO);
			if (merchantScopeResult != null && merchantScopeResult.isSuccess() && merchantScopeResult.getValue() != null) {
				List<BusinessScopeQueryDTO> queryDTOs = new ArrayList<>();
				for (MerchantScopeDO msDO : merchantScopeResult.getValue()) {
					BusinessScopeQueryDTO bsQueryDTO = new BusinessScopeQueryDTO();
					bsQueryDTO.setDomainId(msDO.getDomainId());
					bsQueryDTO.setSellerId(msDO.getSellerId());
					bsQueryDTO.setStatus(-1);
					queryDTOs.add(bsQueryDTO);
				}
				merchantApplyRepo.updateMerchantScopeStatus(queryDTOs);
			}
			ExamineInfoDTO dto = MerchantConverter.convertVO2DTO(examineInfoVO, userId);
			dto.setType(MerchantType.MERCHANT.getType());
			result = merchantApplyRepo.submitExamineInfo(dto);
			
			
		} catch (Exception e) {
			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
		}
		return result;
	}
	
	public MemResult<Boolean> updateMerchantQualification(ExamineInfoVO examineInfoVO) {
		long userId = sessionManager.getUserId();
		MemResult<Boolean> result = new MemResult<Boolean>();
		if (examineInfoVO == null) {
			log.error("params error:examineInfoVO={}",examineInfoVO);
			result.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
			return result;
		}
		try {
			QualificationQueryDTO queryDTO = new QualificationQueryDTO();
			queryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
			queryDTO.setSellerId(userId);
			MemResult<List<MerchantQualificationDO>> merchantQualificationResult = merchantApplyRepo.getMerchantQualification(queryDTO);
			if (merchantQualificationResult != null && merchantQualificationResult.isSuccess() && merchantQualificationResult.getValue() != null) {
				List<QualificationQueryDTO> queryDTOs = new ArrayList<>();
				for (MerchantQualificationDO mqDO : merchantQualificationResult.getValue()) {
					QualificationQueryDTO qualificationQueryDTO = new QualificationQueryDTO();
					qualificationQueryDTO.setDomainId(mqDO.getDomainId());
					qualificationQueryDTO.setSellerId(mqDO.getSellerId());
					qualificationQueryDTO.setStatus(-1);
					queryDTOs.add(qualificationQueryDTO);
				}
				merchantApplyRepo.updateMerchantQualificationStatus(queryDTOs);
			}
			ExamineInfoDTO dto = new ExamineInfoDTO();
			dto.setSellerId(userId);
			dto.setDomainId(Constant.DOMAIN_JIUXIU);
			dto.setMerchantQualifications(examineInfoVO.getMerchantQualifications());
			result = merchantApplyRepo.updateMerchantQualification(dto);
			
		} catch (Exception e) {
			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
		}
		return result;
	}
	public WebResult<ExamineInfoVO> getExamineInfo() {
		WebResult<ExamineInfoVO> result = new WebResult<ExamineInfoVO>();
		 long userId = sessionManager.getUserId();
		 InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		 examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		 examineQueryDTO.setType(MerchantType.MERCHANT.getType());
		 examineQueryDTO.setSellerId(userId);
		 MemResult<ExamineInfoDTO> queryResult = merchantApplyRepo.getExamineInfo(examineQueryDTO);
		 if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null ) {
			result.setWebReturnCode(WebReturnCode.QUERY_EXAMINFO_FAILED);
			return result;
		}
		 ExamineInfoDTO dto = queryResult.getValue();
		 result.setValue(MerchantConverter.convertDTO2VO(dto));
//		 MerchantQualificationDO merchantQualification = new MerchantQualificationDO();
//		 merchantQualification.setDomainId(Constant.DOMAIN_JIUXIU);
//		 merchantQualification.setSellerId(sessionManager.getUserId());
//		MemResult<List<QualificationDO>> qualificationResult = applyService.getQualificationBySellerId(merchantQualification);
//		if (!qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
//			//result.setReturnCode(qualificationResult.getReturnCode());
//			result.setErrorCode(qualificationResult.getErrorCode());
//			result.setErrorMsg(qualificationResult.getErrorMsg());
//			result.setSuccess(false);
//			return result;
//			
//		}
//		result.getValue().setQualifications(qualificationResult.getValue());
	//	MemResult<List<MerchantQualificationDO>> merchantQualificationResult = getMerchantQualificationBySellerId();
		 WebResult<List<MerchantQualificationDO>> merchantQualificationResult = getMerchantQualification();
		if (!merchantQualificationResult.isSuccess() || merchantQualificationResult.getValue() == null) {
			return result;
			
		}
		result.getValue().setMerchantQualifications(merchantQualificationResult.getValue());
//		MerchantScopeDO merchantScope = new MerchantScopeDO();
//		merchantScope.setDomainId(Constant.DOMAIN_JIUXIU);
//		merchantScope.setSellerId(sessionManager.getUserId());
//		MemResult<List<BusinessScopeDO>> scopeResult = businessScopeService.getBusinessScope(merchantScope);
		WebResult<List<MerchantScopeDO>> merchantScopeResult= getMerchantScope();
		if (!merchantScopeResult.isSuccess() || merchantScopeResult.getValue() == null) {
			return result;
			
		}
		//result.getValue().setBusinessScopes(scopeResult.getValue());
		result.getValue().setMerchantScopes(merchantScopeResult.getValue());
		return result;
	}
	
//	public MemResult<List<MerchantCategoryDO>> getAllMerchantCategory() {
//		MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
//		MerchantCategoryDO merchantCategory = new MerchantCategoryDO();
//		merchantCategory.setDomainId(Constant.DOMAIN_JIUXIU);
//		//MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
//		MemResult<List<MerchantCategoryDO>> queryResult = applyService.getMerchantCategory(merchantCategory);
//		if (queryResult == null || !queryResult.isSuccess()) {
//			//result.setReturnCode(queryResult.getReturnCode());
//			result.setErrorCode(queryResult.getErrorCode());
//			result.setErrorMsg(queryResult.getErrorMsg());
//			result.setSuccess(false);
//			return result;
//		}
//		result.setValue(queryResult.getValue());
//		return result;
//	}
//	
//	public MemResult<List<BusinessScopeDO>> getAllBusinessScopes() {
//		MemResult<List<BusinessScopeDO>> result = new MemResult<List<BusinessScopeDO>>();
//		BusinessScopeDO businessScope = new BusinessScopeDO();
//		businessScope.setDomainId(Constant.DOMAIN_JIUXIU);
//		MemResult<List<BusinessScopeDO>> queryResult = applyService.getBusinessScope(businessScope,null);
//		if (queryResult == null || !queryResult.isSuccess()) {
//			result.setErrorCode(queryResult.getErrorCode());
//			result.setErrorMsg(queryResult.getErrorMsg());
//			result.setSuccess(false);
//			//result.setReturnCode(queryResult.getReturnCode());
//			return result;
//		}
//		result.setValue(queryResult.getValue());
//		return result;
//	}
	
	public WebResult<List<MerchantCategoryScopeDO>> getMerchantCategoryScope(long merchantCategoryId) {
		WebResult<List<MerchantCategoryScopeDO>> result = new WebResult<List<MerchantCategoryScopeDO>>();
		if (merchantCategoryId <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		BusinessScopeQueryDTO queryDTO = new BusinessScopeQueryDTO();
		queryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		queryDTO.setMerchantCategoryId(merchantCategoryId);
		MemResult<List<MerchantCategoryScopeDO>> queryResult = merchantApplyRepo.getMerchantCategoryScope(queryDTO);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_CATEGORY_SCOPE_FAILED);
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}

	public WebResult<List<QualificationVO>> getQualification() {
		WebResult<List<QualificationVO>> result = new WebResult<List<QualificationVO>>();
		 long userId = sessionManager.getUserId();
		 InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		 examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		 examineQueryDTO.setSellerId(userId);
		 MemResult<ExamineInfoDTO> examineInfoResult = merchantApplyRepo.getExamineInfo(examineQueryDTO);
		 if (examineInfoResult == null || !examineInfoResult.isSuccess() || examineInfoResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.NOT_FOUNT_MERCHANT);
			return result;
		}
		ExamineInfoDTO dto = examineInfoResult.getValue();
		
		
		
		
		
		List<Long> cqIdList = new ArrayList<>();
		List<MerchantCategoryScopeDO> merchantCategoryScopeList = getMerchantCategoryScope(dto);
		MemResult<List<MerchantCategoryDO>> merchantCategoryResult = getMerchantCategory(dto);
		if(merchantCategoryResult == null || !merchantCategoryResult.isSuccess() || merchantCategoryResult.getValue() == null ) {
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			return result;
		}
		
		if (merchantCategoryResult.getValue().get(0).getType() == MerchantType.TRAVEL_AGENCY.getType()) {
			cqIdList = null;
			
		}else{
			
			for (MerchantCategoryScopeDO scope : merchantCategoryScopeList) {
				cqIdList.add(scope.getBusinessScopeId());
			}
		}
//		if (dto.getMerchantCategoryId() == MerchantCategoryType.HOME_HEAD_AGENCY.getSubType() ||  
//				dto.getMerchantCategoryId() == MerchantCategoryType.HOME_BRANCH_AGENCY.getSubType() || 
//				dto.getMerchantCategoryId() == MerchantCategoryType.BROAD_HEAD_AGENCY.getSubType() || 
//				dto.getMerchantCategoryId() == MerchantCategoryType.BROAD_BRANCH_AGENCY.getSubType()) {
//		}
		
		
		
		List<CategoryQualificationDO> categoryQualificationResult = getCategoryQualification(dto);
		if (categoryQualificationResult == null || categoryQualificationResult.size() == 0) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_CATEGORY_QUALIFICATION_FAILED);
			 return result;
		}
		Set<Long> qualificationIds = new HashSet<>();
		for (CategoryQualificationDO cQualificationDO : categoryQualificationResult) {
			qualificationIds.add(cQualificationDO.getQulificationId());
		}
		
		dto.setIdSet(qualificationIds);
		WebResult<List<QualificationVO>> qualificationVOResult = getQualificationVO(dto);
		if (qualificationVOResult == null || !qualificationVOResult.isSuccess() || qualificationVOResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_CATEGORY_QUALIFICATION_FAILED);
			return result;
		}
//		QualificationDO qualification = new QualificationDO();
//		qualification.setDomainId(Constant.DOMAIN_JIUXIU);
//		MemResult<List<QualificationDO>> qualificationResult = applyService.getQualification(qualification, qualificationIds);
//		if (qualificationResult == null || !qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
//			return result;
//		}
		List<QualificationVO> qualificationVOList = qualificationVOResult.getValue();
		//List<QualificationVO> qualificationList = new ArrayList<QualificationVO>();
		for (CategoryQualificationDO cq:categoryQualificationResult) {
			//Map<String, QualificationDO> map = new HashMap<String, QualificationDO>();
			QualificationVO qualificationVO = new QualificationVO();
			for (QualificationVO qf	 : qualificationVOList) {
				if (cq.getQulificationId() == qf.getId()) {
					//QualificationDO qualificationDO = new QualificationDO();
					qualificationVO.setRequired(cq.isRequired());
//					qualificationVO.setTip(qf.getTip());
//					qualificationVO.setId(qf.getId());
				//	map.put(qf.getTitle(), qualificationDO);
//					qualificationVO.setTitle(qf.getTitle());
//					qualificationVO.setNum(qf.getNum());
//					qualificationVO.setType(qf.getType());
//					qualificationVO.setOverallNote(qf.getOverallNote());
					break;
				}
			}
		//	qualificationList.add(qualificationVO);
			//mapList.add(map);
		}
//		MerchantQualificationDO merchantQualification = new MerchantQualificationDO();
//		merchantQualification.setDomainId(Constant.DOMAIN_JIUXIU);
//		merchantQualification.setSellerId(userId);
//		MemResult<List<MerchantQualificationDO>> merchantQualificationResult = applyService.getMerchantQualification(merchantQualification);
		WebResult<List<MerchantQualificationDO>> merchantQualificationResult = getMerchantQualification();
//		if (merchantQualificationResult == null || !merchantQualificationResult.isSuccess() || merchantQualificationResult.getValue() == null) {
//			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_QUALIFICATION_FAILED);
//			return result;
//		}
		if (merchantQualificationResult != null && merchantQualificationResult.isSuccess() && merchantQualificationResult.getValue() != null) {
			List<MerchantQualificationDO> merchantQualificationList = merchantQualificationResult.getValue();
			for (QualificationVO vo : qualificationVOList) {
				for (MerchantQualificationDO mqDO : merchantQualificationList) {
					if (vo.getId() == mqDO.getQulificationId()) {
						vo.setContent(mqDO.getContent());
					}
				}
			}
		}
		result.setValue(qualificationVOList);
		return result;
	}
	public WebResult<List<MerchantScopeDO>> getMerchantScope() {
		WebResult<List<MerchantScopeDO>> result = new WebResult<List<MerchantScopeDO>>();
		BusinessScopeQueryDTO queryDTO = new BusinessScopeQueryDTO();
		queryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		queryDTO.setSellerId(sessionManager.getUserId());
		MemResult<List<MerchantScopeDO>> queryResult = merchantApplyRepo.getMerchantScope(queryDTO);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_SCOPE_FAILED);
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}
	
	public WebResult<List<MerchantQualificationDO>> getMerchantQualification() {
		WebResult<List<MerchantQualificationDO>> result = new WebResult<List<MerchantQualificationDO>>();
		QualificationQueryDTO queryDTO = new QualificationQueryDTO();
		queryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		queryDTO.setSellerId(sessionManager.getUserId());
		MemResult<List<MerchantQualificationDO>> queryResult = merchantApplyRepo.getMerchantQualification(queryDTO);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_QUALIFICATION_FAILED);
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}
	
	public List<CategoryQualificationDO> getCategoryQualification(ExamineInfoDTO examineInfoDTO) {
		QualificationQueryDTO qualificationQueryDTO = new QualificationQueryDTO();
		 qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		 qualificationQueryDTO.setMerchantCategoryId(examineInfoDTO.getMerchantCategoryId());
		 qualificationQueryDTO.setDirectSale(examineInfoDTO.getIsDirectSale()); 
			
		MemResult<List<CategoryQualificationDO>> categoryQualificationResult = merchantApplyRepo.getCategoryQualification(qualificationQueryDTO);
		if (categoryQualificationResult == null || !categoryQualificationResult.isSuccess() || categoryQualificationResult.getValue() == null) {
			return null;
		}
		return categoryQualificationResult.getValue();
	}
	
	public List<MerchantCategoryScopeDO> getMerchantCategoryScope(ExamineInfoDTO examineInfoDTO) {
		BusinessScopeQueryDTO businessScopeQueryDTO = new BusinessScopeQueryDTO();
		businessScopeQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		businessScopeQueryDTO.setMerchantCategoryId(examineInfoDTO.getMerchantCategoryId());
		MemResult<List<MerchantCategoryScopeDO>> merchantCategoryScopeResult = merchantApplyRepo.getMerchantCategoryScope(businessScopeQueryDTO);
		if (merchantCategoryScopeResult == null || !merchantCategoryScopeResult.isSuccess() || merchantCategoryScopeResult.getValue() == null) {
			return null;
		}
		return merchantCategoryScopeResult.getValue();
	}
	
	public MemResult<List<MerchantCategoryDO>> getMerchantCategory(ExamineInfoDTO examineInfoDTO) {
	//	MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
		MerchantCategoryQueryDTO queryDTO = new MerchantCategoryQueryDTO();
		queryDTO.setDomainId(examineInfoDTO.getDomainId());
		queryDTO.setId(examineInfoDTO.getMerchantCategoryId());
		return merchantApplyRepo.getMerchantCategory(queryDTO);
	}
	
	public WebResult<List<QualificationVO>> getQualificationVO(ExamineInfoDTO examineInfoDTO) {
		WebResult<List<QualificationVO>> result = new WebResult<List<QualificationVO>>();
		QualificationQueryDTO queryDTO = new QualificationQueryDTO();
		queryDTO.setDomainId(examineInfoDTO.getDomainId());
		queryDTO.setDirectSale(examineInfoDTO.getIsDirectSale());
		queryDTO.setMerchantCategoryId(examineInfoDTO.getMerchantCategoryId());
		queryDTO.setIdSet(examineInfoDTO.getIdSet());
		MemResult<List<QualificationDO>> qualificationResult = merchantApplyRepo.getQualification(queryDTO);
		if (qualificationResult == null || !qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
			result.setWebReturnCode(WebReturnCode.QUERY_MERCHANT_CATEGORY_QUALIFICATION_FAILED);
			return result;
		}
		List<QualificationVO> qualificationVOList = new ArrayList<QualificationVO>();
		for (QualificationDO qualificationDO : qualificationResult.getValue()) {
			QualificationVO qualificationVO = MerchantConverter.converterQualificationDO2VO(qualificationDO);
			qualificationVOList.add(qualificationVO);
		}
		result.setValue(qualificationVOList);
		return result;
	}
	public WebResult<Boolean> updateMerchantScopeStatus(BusinessScopeQueryDTO queryDTO) {
		WebResult<Boolean> result = new WebResult<>();
		if (queryDTO == null || queryDTO.getDomainId() <= 0) {
			log.error("params error:BusinessScopeQueryDTO={}",JSON.toJSONString(queryDTO));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		MemResult<Boolean> updateResult = merchantApplyRepo.updateMerchantScopeStatus(queryDTO);
		if (updateResult == null || !updateResult.isSuccess() || !updateResult.getValue()) {
			result.setWebReturnCode(WebReturnCode.UPDATE_ERROR);
			
		}
		return result;
	}
	
	public WebResult<Boolean> updateMerchantQualificationStatus(QualificationQueryDTO queryDTO) {
		WebResult<Boolean> result = new WebResult<>();
		if (queryDTO == null || queryDTO.getDomainId() <= 0) {
			log.error("params error:QualificationQueryDTO={}",JSON.toJSONString(queryDTO));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		MemResult<Boolean> updateResult = merchantApplyRepo.updateMerchantQualificationStatus(queryDTO);
		if (updateResult == null || !updateResult.isSuccess() || !updateResult.getValue()) {
			result.setWebReturnCode(WebReturnCode.UPDATE_ERROR);
			
		}
		return result;
	}
}

package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.CategoryQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ApplyService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.MerchantConverter;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
* @ClassName: MerchantApplyRepo
* @Description: 管理商家入驻
* @author zhangxy
* @date 2016年5月30日 上午9:44:54
*
 */
public class MerchantApplyRepo {
	protected Logger log = LoggerFactory.getLogger("TalentRepo");
	@Autowired
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private ApplyService applyService;
	
	public MemResult<Boolean> submitExamineInfo(ExamineInfoVO examineInfoVO) {
		long userId = sessionManager.getUserId();
		MemResult<Boolean> result = new MemResult<Boolean>();
		if (examineInfoVO == null) {
			log.error("params error:examineInfoVO={}",examineInfoVO);
			result.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
			return result;
		}
		try {
			ExamineInfoDTO dto = MerchantConverter.convertVO2DTO(examineInfoVO, userId);
			dto.setType(ExamineType.MERCHANT.getType());
			result = applyService.submitExamineInfo(dto);
			
			return result;
		} catch (Exception e) {
			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
			return result;
		}
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
			ExamineInfoDTO dto = new ExamineInfoDTO();
			dto.setSellerId(userId);
			dto.setDomainId(Constant.DOMAIN_JIUXIU);
			//dto.setType(ExamineType.TALENT.getType());
			dto.setMerchantQualifications(examineInfoVO.getMerchantQualifications());
			result = applyService.updateMerchantQualification(dto);
			
			return result;
		} catch (Exception e) {
			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
			return result;
		}
	}
	public MemResult<ExamineInfoVO> getExamineInfo() {
		MemResult<ExamineInfoVO> result = new MemResult<ExamineInfoVO>();
		 InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		 examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		 examineQueryDTO.setType(ExamineType.MERCHANT.getType());
		 long userId = sessionManager.getUserId();
		 examineQueryDTO.setSellerId(userId);
		 MemResult<ExamineInfoDTO> queryResult = examineDealService.queryMerchantExamineInfoBySellerId(examineQueryDTO);
		 if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			//result.setReturnCode(queryResult.getReturnCode());
			result.setErrorCode(queryResult.getErrorCode());
			result.setErrorMsg(queryResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		 ExamineInfoDTO dto = queryResult.getValue();
		 result.setValue(MerchantConverter.convertDTO2VO(dto));
		 MerchantQualificationDO merchantQualification = new MerchantQualificationDO();
		 merchantQualification.setDomainId(Constant.DOMAIN_JIUXIU);
		 merchantQualification.setSellerId(sessionManager.getUserId());
		MemResult<List<QualificationDO>> qualificationResult = applyService.getQualificationBySellerId(merchantQualification);
		if (!qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
			//result.setReturnCode(qualificationResult.getReturnCode());
			result.setErrorCode(qualificationResult.getErrorCode());
			result.setErrorMsg(qualificationResult.getErrorMsg());
			result.setSuccess(false);
			return result;
			
		}
		result.getValue().setQualifications(qualificationResult.getValue());
		MerchantScopeDO merchantScope = new MerchantScopeDO();
		merchantScope.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantScope.setSellerId(sessionManager.getUserId());
		MemResult<List<BusinessScopeDO>> scopeResult = applyService.getBusinessScopesBySellerId(merchantScope);
		if (!scopeResult.isSuccess() || scopeResult.getValue() == null) {
			//result.setReturnCode(scopeResult.getReturnCode());
			result.setErrorCode(scopeResult.getErrorCode());
			result.setErrorMsg(scopeResult.getErrorMsg());
			result.setSuccess(false);
			return result;
			
		}
		result.getValue().setBusinessScopes(scopeResult.getValue());
		return result;
	}
	
	public MemResult<List<MerchantCategoryDO>> getAllMerchantCategory() {
		MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
		MerchantCategoryDO merchantCategory = new MerchantCategoryDO();
		merchantCategory.setDomainId(Constant.DOMAIN_JIUXIU);
		//MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
		MemResult<List<MerchantCategoryDO>> queryResult = applyService.getMerchantCategory(merchantCategory);
		if (queryResult == null || !queryResult.isSuccess()) {
			//result.setReturnCode(queryResult.getReturnCode());
			result.setErrorCode(queryResult.getErrorCode());
			result.setErrorMsg(queryResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}
	
	public MemResult<List<BusinessScopeDO>> getAllBusinessScopes() {
		MemResult<List<BusinessScopeDO>> result = new MemResult<List<BusinessScopeDO>>();
		BusinessScopeDO businessScope = new BusinessScopeDO();
		businessScope.setDomainId(Constant.DOMAIN_JIUXIU);
		MemResult<List<BusinessScopeDO>> queryResult = applyService.getBusinessScope(businessScope,null);
		if (queryResult == null || !queryResult.isSuccess()) {
			result.setErrorCode(queryResult.getErrorCode());
			result.setErrorMsg(queryResult.getErrorMsg());
			result.setSuccess(false);
			//result.setReturnCode(queryResult.getReturnCode());
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}
	
	public MemResult<List<MerchantCategoryScopeDO>> getMerchantCategoryScope(long merchantCategoryId) {
		MemResult<List<MerchantCategoryScopeDO>> result = new MemResult<List<MerchantCategoryScopeDO>>();
		MerchantCategoryScopeDO merchantCategoryScope = new MerchantCategoryScopeDO();
		merchantCategoryScope.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantCategoryScope.setMerchantCategoryId(merchantCategoryId);
		MemResult<List<MerchantCategoryScopeDO>> queryResult = applyService.getMerchantCategoryScope(merchantCategoryScope,null);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			//result.setReturnCode(queryResult.getReturnCode());
			result.setErrorCode(queryResult.getErrorCode());
			result.setErrorMsg(queryResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		result.setValue(queryResult.getValue());
		return result;
	}

	public MemResult<List<Map<String, QualificationDO>>> getQualificationByCategoryId() {
		 MemResult<List<Map<String, QualificationDO>>> result = new MemResult<List<Map<String, QualificationDO>>>();
		 InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		 examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		 examineQueryDTO.setType(ExamineType.MERCHANT.getType());
		 long userId = sessionManager.getUserId();
		 examineQueryDTO.setSellerId(userId);
		 MemResult<ExamineInfoDTO> queryResult = examineDealService.queryMerchantExamineInfoBySellerId(examineQueryDTO);
		 if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
			result.setErrorCode(queryResult.getErrorCode());
			result.setErrorMsg(queryResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		 ExamineInfoDTO dto = queryResult.getValue();
		CategoryQualificationDO categoryQualification = new CategoryQualificationDO();
		categoryQualification.setDomainId(Constant.DOMAIN_JIUXIU);
		categoryQualification.setMerchantCategoryId(dto.getMerchantCategoryId());
		categoryQualification.setIsDirectSale(dto.getIsDirectSale());
		MerchantCategoryScopeDO merchantCategoryScope = new MerchantCategoryScopeDO();
		merchantCategoryScope.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantCategoryScope.setMerchantCategoryId(dto.getMerchantCategoryId());
		MemResult<List<MerchantCategoryScopeDO>> merchantCategoryScopeResult = applyService.getMerchantCategoryScope(merchantCategoryScope, null);
		if (merchantCategoryScopeResult == null || !merchantCategoryScopeResult.isSuccess() || merchantCategoryScopeResult.getValue() == null) {
			result.setErrorCode(merchantCategoryScopeResult.getErrorCode());
			result.setErrorMsg(merchantCategoryScopeResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		
		List<Long> cqIdList = new ArrayList<>();
		for (MerchantCategoryScopeDO scope : merchantCategoryScopeResult.getValue()) {
			cqIdList.add(scope.getBusinessScopeId());
		}
		
		MemResult<List<CategoryQualificationDO>> categoryQualificationResult = applyService.getCategoryQualification(categoryQualification, cqIdList);
		if (categoryQualificationResult == null || !categoryQualificationResult.isSuccess() || categoryQualificationResult.getValue() == null) {
			result.setErrorCode(categoryQualificationResult.getErrorCode());
			result.setErrorMsg(categoryQualificationResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		
		QualificationDO qualification = new QualificationDO();
		qualification.setDomainId(Constant.DOMAIN_JIUXIU);
		List<Long> qualificationIds = new ArrayList<>();
		MemResult<List<QualificationDO>> qualificationResult = applyService.getQualification(qualification, qualificationIds);
		if (qualificationResult == null || !qualificationResult.isSuccess() || qualificationResult.getValue() == null) {
			result.setErrorCode(qualificationResult.getErrorCode());
			result.setErrorMsg(qualificationResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		
		List<Map<String, QualificationDO>> mapList = new ArrayList<>();
		for (CategoryQualificationDO cq:categoryQualificationResult.getValue()) {
			for (QualificationDO qf	 : qualificationResult.getValue()) {
				Map<String, QualificationDO> map = new HashMap<String, QualificationDO>();
				if (cq.getQulificationId() == qf.getId()) {
					QualificationDO qualificationDO = new QualificationDO();
					qualificationDO.setRequired(cq.getRequired());
					qualificationDO.setTip(qf.getTip());
					map.put(qf.getTitle(), qualificationDO);
				}
				mapList.add(map);
			}
		}
		result.setValue(mapList);
		return result;
	}
	
}

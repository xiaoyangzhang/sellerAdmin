package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
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
		 examineQueryDTO.setType(ExamineType.TALENT.getType());
		 long userId = sessionManager.getUserId();
		 examineQueryDTO.setSellerId(userId);
		 MemResult<ExamineInfoDTO> queryResult = examineDealService.queryMerchantExamineInfoBySellerId(examineQueryDTO);
		 if (queryResult == null || !queryResult.isSuccess()) {
			result.setReturnCode(queryResult.getReturnCode());
			return result;
		}
		 ExamineInfoDTO dto = queryResult.getValue();
		MemResult<List<QualificationDO>> qualificationResult = applyService.getMerchantQualificationBySellerId(dto.getSellerId(), dto.getDomainId());
		if (!qualificationResult.isSuccess()) {
			result.setReturnCode(queryResult.getReturnCode());
			return result;
			
		}
		result.getValue().setQualifications(qualificationResult.getValue());
		MemResult<List<BusinessScopeDO>> scopeResult = applyService.getMerchantScopeBySellerId(dto.getSellerId(), dto.getDomainId());
		if (!scopeResult.isSuccess()) {
			result.setReturnCode(queryResult.getReturnCode());
			return result;
			
		}
		result.getValue().setBusinessScopes(scopeResult.getValue());
		return result;
	}
	
	public MemResult<List<MerchantCategoryDO>> getAllMerchantCategory() {
		//MemResult<List<MerchantCategoryDO>> result = new MemResult<List<MerchantCategoryDO>>();
		MemResult<List<MerchantCategoryDO>> result = applyService.getAllMerchantCategory(Constant.DOMAIN_JIUXIU);
		if (result == null || !result.isSuccess()) {
			result.setReturnCode(MemberReturnCode.DB_READ_FAILED);
			return result;
		}
		return result;
	}
	
	public MemResult<List<BusinessScopeDO>> getAllBusinessScopes() {
		MemResult<List<BusinessScopeDO>> result = applyService.getAllBusinessScope(Constant.DOMAIN_JIUXIU);
		if (result == null || !result.isSuccess()) {
			result.setReturnCode(MemberReturnCode.DB_READ_FAILED);
			return result;
		}
		return result;
	}
	public MemResult<List<QualificationDO>> getAllQualificaitons() {
		MemResult<List<QualificationDO>> result = applyService.getAllQualification(Constant.DOMAIN_JIUXIU);
		if (result == null || !result.isSuccess()) {
			result.setReturnCode(MemberReturnCode.DB_READ_FAILED);
			return result;
		}
		return result;
	}
	
	
}

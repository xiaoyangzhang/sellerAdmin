package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
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
import com.yimayhd.membercenter.client.query.BusinessScopeQueryDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.query.MerchantCategoryQueryDTO;
import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.BusinessScopeService;
import com.yimayhd.membercenter.client.service.QualificationService;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.client.service.examine.MerchantApplyService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.membercenter.enums.MerchantCategoryType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.MerchantConverter;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.QualificationVO;
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
	private MerchantApplyService merchantApplyService;
	@Autowired
	private QualificationService qualificationService;
	@Autowired
	private BusinessScopeService businessScopeService;
	public MemResult<Boolean> submitExamineInfo(ExamineInfoDTO	 dto) {
//		long userId = sessionManager.getUserId();
//		MemResult<Boolean> result = new MemResult<Boolean>();
//		if (examineInfoVO == null) {
//			log.error("params error:examineInfoVO={}",JSON.toJSONString(examineInfoVO));
//			result.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
//			return result;
//		}
//		try {
//			ExamineInfoDTO dto = MerchantConverter.convertVO2DTO(examineInfoVO, userId);
//			dto.setType(MerchantType.MERCHANT.getType());
			//result = 
			
			return merchantApplyService.submitExamineInfo(dto);
//		} catch (Exception e) {
//			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
//			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
//			return result;
//		}
	}
	
	public MemResult<Boolean> updateMerchantQualification(ExamineInfoDTO	 dto) {
//		long userId = sessionManager.getUserId();
//		MemResult<Boolean> result = new MemResult<Boolean>();
//		if (examineInfoVO == null) {
//			log.error("params error:examineInfoVO={}",examineInfoVO);
//			result.setReturnCode(MemberReturnCode.PARAMTER_ERROR);
//			return result;
//		}
//		try {
//			ExamineInfoDTO dto = new ExamineInfoDTO();
//			dto.setSellerId(userId);
//			dto.setDomainId(Constant.DOMAIN_JIUXIU);
//			dto.setMerchantQualifications(examineInfoVO.getMerchantQualifications());
			//result =
			
			return  qualificationService.updateMerchantQualification(dto);
//		} catch (Exception e) {
//			log.error("params :examineInfo={} error:{}",examineInfoVO,e);
//			result.setReturnCode(MemberReturnCode.SAVE_MERCHANT_FAILED);
//			return result;
//		}
	}
	public MemResult<ExamineInfoDTO> getExamineInfo(InfoQueryDTO examineQueryDTO) {
		return examineDealService.queryMerchantExamineInfoBySellerId(examineQueryDTO);
	}
	
	
	
	
	
	
	public MemResult<List<MerchantCategoryScopeDO>> getMerchantCategoryScope(BusinessScopeQueryDTO businessScopeQueryDTO) {
		
		return businessScopeService.getMerchantCategoryScope(businessScopeQueryDTO);
	}

	public MemResult<List<QualificationDO>> getQualification(QualificationQueryDTO queryDTO) {
		
		return qualificationService.getQualification(queryDTO);
	}
	public MemResult<List<MerchantScopeDO>> getMerchantScope(BusinessScopeQueryDTO queryDTO) {
		
		return businessScopeService.getMerchantScope(queryDTO);
	}
	
	public MemResult<List<MerchantQualificationDO>> getMerchantQualification(QualificationQueryDTO qualificationQueryDTO) {
		
		
		return qualificationService.getMerchantQualification(qualificationQueryDTO);
	}
	
	public MemResult<List<CategoryQualificationDO>> getCategoryQualification(QualificationQueryDTO qualificationQueryDTO) {
		return qualificationService.getCategoryQualification(qualificationQueryDTO);
	}
	
	public MemResult<List<MerchantCategoryDO>> getMerchantCategory(MerchantCategoryQueryDTO merchantCategoryQueryDTO) {
		
		return merchantApplyService.getMerchantCategory(merchantCategoryQueryDTO);
	}
	
	public MemResult<Boolean> updateMerchantScopeStatus(BusinessScopeQueryDTO queryDTO) {
		return businessScopeService.updateMerchantScopeStatus(queryDTO);
	}
	
	public MemResult<Boolean> updateMerchantQualificationStatus(QualificationQueryDTO queryDTO) {
		return qualificationService.updateMerchantQualificationStatus(queryDTO);
	}
	
	public MemResult<Integer> updateMerchantScopeStatus(List<BusinessScopeQueryDTO> queryDTOs) {
		return businessScopeService.updateStatusBatch(queryDTOs);
	}
	public MemResult<Integer> updateMerchantQualificationStatus(List<QualificationQueryDTO> queryDTOs) {
		return qualificationService.updateStatusBatch(queryDTOs);
	}
}

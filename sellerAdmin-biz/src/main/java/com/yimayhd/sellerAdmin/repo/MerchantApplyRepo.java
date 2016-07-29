package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import net.pocrd.entity.AbstractReturnCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
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
import com.yimayhd.membercenter.client.service.BusinessScopeService;
import com.yimayhd.membercenter.client.service.QualificationService;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.client.service.examine.MerchantApplyService;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.query.MerchantQuery;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
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
	@Autowired
	private MerchantService merchantService;
	@MethodLogger
	public MemResult<Boolean> submitExamineInfo(ExamineInfoDTO	 dto) {
		
		return merchantApplyService.submitExamineInfo(dto);
	}
	@MethodLogger
	public MemResult<Boolean> updateMerchantQualification(ExamineInfoDTO	 dto) {
			
			return  qualificationService.updateMerchantQualification(dto);
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
	
//	public MemResult<Boolean> updateMerchantScopeStatus(BusinessScopeQueryDTO queryDTO) {
//		return businessScopeService.updateMerchantScopeStatus(queryDTO);
//	}
//	
//	public MemResult<Boolean> updateMerchantQualificationStatus(QualificationQueryDTO queryDTO) {
//		return qualificationService.updateMerchantQualificationStatus(queryDTO);
//	}
//	
//	public MemResult<Integer> updateMerchantScopeStatus(List<BusinessScopeQueryDTO> queryDTOs) {
//		return businessScopeService.updateStatusBatch(queryDTOs);
//	}
//	public MemResult<Integer> updateMerchantQualificationStatus(List<QualificationQueryDTO> queryDTOs) {
//		return qualificationService.updateStatusBatch(queryDTOs);
//	}
	@MethodLogger
	public MemResult<Boolean> checkMerchantNameIsExist(ExamineInfoDTO examineInfoDTO) {
		return examineDealService.checkMerchantNameIsExist(examineInfoDTO);
	}
	public WebResult<List<MerchantDO>> queryMerchant(MerchantQuery merchantQuery){
		WebResult<List<MerchantDO>> result = new WebResult<List<MerchantDO>>() ;
		if( merchantQuery == null || merchantQuery.getDomainId() <=0 ){
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		BaseResult<List<MerchantDO>> queryResult = merchantService.getMerchantList(merchantQuery);
		if( queryResult == null || !queryResult.isSuccess() ){
			log.error("getMerchantList failed!  merchantQuery={}  Result={}", JSON.toJSON(merchantQuery), JSON.toJSON(queryResult));
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		
		List<MerchantDO> merchantDOs = queryResult.getValue() ;
		result.setValue(merchantDOs);
		return result ;
	}
	@MethodLogger
	public MemResult<Boolean> changeExamineStatus(InfoQueryDTO examInfoQueryDTO) {
		return examineDealService.changeExamineStatusIntoIng(examInfoQueryDTO);
	}
	/**
	 * 根据用户id获取商户信息
	 * @param userId
	 * @return
	 */
	public BaseResult<MerchantDO> getMerchantBySellerId(long userId){
		if(userId < 0){
			return null;
		}
		BaseResult<MerchantDO> result = merchantService.getMerchantBySellerId(userId, Constant.DOMAIN_JIUXIU);
		if(null==result || !result.isSuccess() || null==result.getValue()){
			log.error("getMerchantBySellerId failed!  userId={}  Result={}", JSON.toJSON(userId), JSON.toJSON(result));
			return null ;
		}
		return result;
	}
}

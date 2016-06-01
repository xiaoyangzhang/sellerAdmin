package com.yimayhd.sellerAdmin.biz;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
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

protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private MerchantApplyRepo merchantApplyRepo;
	public MemResult<Boolean> submitExamineInfo(ExamineInfoVO examineInfoVO) {
		return merchantApplyRepo.submitExamineInfo(examineInfoVO);
	}
	
	public MemResult<Boolean> updateMerchantQualification(ExamineInfoVO examineInfoVO) {
		return merchantApplyRepo.updateMerchantQualification(examineInfoVO);
	}
	public MemResult<ExamineInfoVO> getExamineInfo() {
		return merchantApplyRepo.getExamineInfo();
	}
	
	public MemResult<List<MerchantCategoryDO>> getAllMerchantCategory() {
		return merchantApplyRepo.getAllMerchantCategory();
	}
	
	public MemResult<List<BusinessScopeDO>> getAllBusinessScopes() {
		return merchantApplyRepo.getAllBusinessScopes();
	}
//	public MemResult<List<QualificationDO>> getAllQualificaitons() {
//		return merchantApplyRepo.
//	}
	
	public MemResult<List<MerchantCategoryScopeDO>> getMerchantCategoryScopeByMerchantCategoryId(long merchantCategoryId) {
		return merchantApplyRepo.getMerchantCategoryScope(merchantCategoryId);
	}
	
	public MemResult<List<Map<String, QualificationDO>>> getQualificationByCategoryId() {
		return merchantApplyRepo.getQualificationByCategoryId();
	}
}

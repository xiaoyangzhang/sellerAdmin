package com.yimayhd.sellerAdmin.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.MerchantConverter;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.repo.MerchantApplyRepo;
import com.yimayhd.sellerAdmin.repo.TalentRepo;
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
	public MemResult<List<QualificationDO>> getAllQualificaitons() {
		return merchantApplyRepo.getAllQualificaitons();
	}
}

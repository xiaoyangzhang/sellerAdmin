package com.yimayhd.sellerAdmin.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.repo.TalentRepo;
import com.yimayhd.user.session.manager.SessionManager;
/***
 * 
 * @author zhangxiaoyang
 *
 */
public class TalentBiz {

	protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private TalentRepo talentRepo;
	/**
	 * 获取达人基本信息的服务类型
	 * @return
	 */
	public MemResult<List<CertificatesDO>> getServiceTypes() {
		return  talentRepo.getServiceTypes();
	}
	/**
	 * 根据userID查询达人审核信息
	 * @return
	 */
	public ExamineInfoDTO getExamineInfo () {
		
 		return talentRepo.getExamineInfo(Constant.DOMAIN_JIUXIU, sessionManager.getUserId());
	}
	/**
	 * 根据userID和domainID查询达人基本信息
	 * @return
	 */
//	public TalentInfoDTO getTalentInfo() {
//		
//		return talentRepo.getTalentInfo(Constant.DOMAIN_JIUXIU, sessionManager.getUserId());
//	}
	/**
	 * 新增达人基本信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public MemResult<Boolean> addTalentInfo(TalentInfoVO vo)  {
		if (vo == null ) {
			log.error("get examineSubmitDTO params error :vo="+vo);
			//throw new BaseException("参数错误");
			return MemResult.buildFailResult(-1, "参数错误", false);
		}
		return talentRepo.addTalentInfo(vo);
		
	}
	/**
	 * 新增达人申请入驻资料信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@MethodLogger
	public WebResult<Boolean> addExamineInfo(ExamineInfoVO vo)  {
		if (vo == null ) {
			log.error(" params error :vo={}",vo);
			//throw new BaseException("参数错误");
			return WebResult.failure(WebReturnCode.PARAM_ERROR);
			//return WebResult.success(false, "参数错误");
		}
		return talentRepo.addExamineInfo(vo);
	}
	/**
	 * 获取达人入驻审核结果
	 * @return
	 */
	public ExamineResultDTO getCheckResult() {
		
		return talentRepo.getCheckResult() == null?null:talentRepo.getCheckResult();
		
	}
	
	public List<BankInfoDTO> getBankList() {
		return talentRepo.getBankList();
	}
	public  String formatDate (Date date) {   
		if (date == null) {
			return null;
		}
	    return  new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	public MemResult<Boolean> updateCheckStatus(ExamineInfoVO vo) {
		if (vo == null) {
			log.error("param : ExamineInfoVO={}",vo);
			return MemResult.buildFailResult(-1, "参数错误", false);
		}
		return  talentRepo.updateCheckStatus(vo);
		
	}
	public MemResult<TalentInfoDTO> queryTalentInfoByUserId () {
		return talentRepo.queryTalentInfoByUserId(sessionManager.getUserId(), Constant.DOMAIN_JIUXIU);
	}
}

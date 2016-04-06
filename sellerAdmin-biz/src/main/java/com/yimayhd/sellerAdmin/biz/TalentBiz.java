package com.yimayhd.sellerAdmin.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.repo.TalentRepo;
import com.yimayhd.user.session.manager.SessionManager;
/***
 * 
 * @author zhangxy
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
		
 		return talentRepo.getExamineInfo(1200, sessionManager.getUserId());
	}
	/**
	 * 根据userID和domainID查询达人基本信息
	 * @return
	 */
	public TalentInfoDTO getTalentInfo() {
		
		return talentRepo.getTalentInfo(1200, sessionManager.getUserId());
	}
	/**
	 * 新增达人基本信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public WebResultSupport addTalentInfo(TalentInfoVO vo)  {
		return talentRepo.addTalentInfo(vo);
		
	}
	/**
	 * 新增达人申请入驻资料信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public WebResultSupport addExamineInfo(ExamineInfoVO vo)  {
		return talentRepo.addExamineInfo(vo);
	}
	/**
	 * 获取达人入驻审核结果
	 * @return
	 */
	public String getCheckResult() {
		
		return talentRepo.getCheckResult();
		
	}
	
	public List<BankInfoDTO> getBankList() {
		//MemResult<List<BankInfoDTO>> bankList = talentInfoDealService.queryBankList();
		return talentRepo.getBankList();
	}
	public  String formatDate (Date date) {   
	    return  new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}

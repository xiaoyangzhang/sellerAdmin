package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.user.session.manager.SessionManager;

/***
 * 
 * @author zhangxy
 *
 */
public class TalentRepo {

	protected Logger log = LoggerFactory.getLogger("TalentRepo");
	@Autowired
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private SessionManager sessionManager;
	public MemResult<List<CertificatesDO>> getServiceTypes() {
		MemResult<List<CertificatesDO>> serviceTypes = talentInfoDealService.queryTalentServiceType();
		return serviceTypes;
	}
	
	/**
	 * 根据userID查询达人审核信息
	 * @return
	 */
	public ExamineInfoDTO getExamineInfo (int domainId,long userId) {
		ExamineInfoDTO dto = null;
		if (domainId <=0 || userId <= 0) {
			log.error(" params error: domainId={},userId={}",domainId,userId);
			return dto;
		//	throw new BaseException("参数错误");
		}
		InfoQueryDTO queryDTO=new InfoQueryDTO();
		queryDTO.setDomainId(domainId);
		queryDTO.setType(ExamineType.TALENT.getType());
		queryDTO.setSellerId(userId);
		MemResult<ExamineInfoDTO> examineInfoResult = examineDealService.queryMerchantExamineInfoBySellerId(queryDTO);
		if (examineInfoResult == null || !examineInfoResult.isSuccess() || (examineInfoResult.getValue() == null)) {
			return dto;
		}
		dto = examineInfoResult.getValue();
 		return dto;
	}
	
	/**
	 * 根据userID和domainID查询达人基本信息
	 * @return
	 */
//	public TalentInfoDTO getTalentInfo(int domainId,long userId) {
//		if (domainId <= 0 || userId <= 0 ) {
//			log.error("get talentInfo error params: domainId="+domainId+"userId="+userId);
//			throw new BaseException("参数错误");
//		}
//		RepoUtils.requestLog(log, "talentInfoDealService.queryTalentInfoByUserId", userId,domainId);
//		MemResult<TalentInfoDTO> talentInfoResult  = talentInfoDealService.queryTalentInfoByUserId(userId, domainId);
//		RepoUtils.requestLog(log, "talentInfoDealService.queryTalentInfoByUserId", talentInfoResult.getValue());
//		
//		TalentInfoDTO dto = null;
//		if (talentInfoResult != null) {
//			TalentInfoDTO talentInfoDTO = talentInfoResult.getValue();
//
//			List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
//			//填充店铺头图集合
//				while(pictures.size() < Constant.TALENT_SHOP_PICNUM) {
//					pictures.add("");
//				}
//			
//			dto = talentInfoDTO;
//		}
//		return dto;
//	}
	
	/**
	 * 新增达人基本信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public MemResult<Boolean> addTalentInfo(TalentInfoVO vo)  {
		if (vo == null) {
			return null;
		}
		MemResult<Boolean> talentInfoResult=null;
		try {
			RepoUtils.requestLog(log, "talentInfoDealService.updateTalentInfo", vo);
			 talentInfoResult = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo,sessionManager.getUserId()));
			 RepoUtils.requestLog(log, "talentInfoDealService.updateTalentInfo", talentInfoResult);
			
			 return talentInfoResult;
		} catch (Exception e) {
			log.error("param : TalentInfoVO={}",vo,e);
			return null;
		}
		
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
		if (vo == null) {
			return null;
		}
		WebResult<Boolean> result = new WebResult<Boolean>();
		MemResult<Boolean> ExamineInfoResult = null;
		try {
			ExamineInfoDTO dto = vo.getExamineInfoDTO(vo, sessionManager.getUserId());
			ExamineInfoResult = examineDealService.submitMerchantExamineInfo(dto);

			if(ExamineInfoResult == null || !ExamineInfoResult.isSuccess()) {
				
				int code = ExamineInfoResult.getErrorCode() ;
				if(MemberReturnCode.DB_MERCHANTNAME_FAILED.getCode() == code ) {
					result.setWebReturnCode( WebReturnCode.TALENT_MERCHANT_NAME_EXIST );
				}else if( MemberReturnCode.DB_EXAMINE_FAILED.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.APPROVE_PASSED_DISABLE_MODIFY);
				}else{
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				}
			}
			return result;
			
		} catch (Exception e) {
			log.error("param :vo={}",vo,e);
			return null;
		}
	}
	public List<BankInfoDTO> getBankList() {
		MemResult<List<BankInfoDTO>> bankList = talentInfoDealService.queryBankList();
		RepoUtils.requestLog(log, " talentInfoDealService.queryBankList", bankList);
		
		return bankList.getValue();
	}
	
	/**
	 * 获取达人入驻审核结果
	 * @return
	 */
	@MethodLogger
	public ExamineResultDTO getCheckResult() {
		InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		examineQueryDTO.setType(ExamineType.TALENT.getType());
		examineQueryDTO.setSellerId(sessionManager.getUserId());
		
		try {
			MemResult<ExamineResultDTO> examineDealResult = examineDealService.queryExamineDealResult(examineQueryDTO);
			if (examineDealResult == null || examineDealResult.getValue() == null ) {
				return null;
			}
			return examineDealResult.getValue();
		} catch (Exception e) {
			log.error("examineQueryDTO={}",examineQueryDTO,e);
			return null;
		}

		
	}
	public MemResult<Boolean> updateCheckStatus(ExamineInfoVO vo) {
		try {
			MemResult<Boolean> changeExamineStatusResult = examineDealService.changeExamineStatusIntoIng(vo.getInfoQueryDTO(sessionManager.getUserId()));

			return changeExamineStatusResult;
		} catch (Exception e) {
			log.error("param:ExamineInfoVO={}",vo,e);
			return null;
		}
		
	}
}

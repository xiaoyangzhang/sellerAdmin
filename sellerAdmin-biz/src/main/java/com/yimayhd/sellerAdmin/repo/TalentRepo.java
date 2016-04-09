package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.tradecenter.client.model.enums.ExamineeStatus;
import com.yimayhd.user.session.manager.SessionManager;

/***
 * 
 * @author zhangxy
 *
 */
public class TalentRepo {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private SessionManager sessionManager;
	public MemResult<List<CertificatesDO>> getServiceTypes() {
		MemResult<List<CertificatesDO>> serviceTypes = talentInfoDealService.queryTalentServiceType();
		RepoUtils.requestLog(log,"talentInfoDealService.queryTalentServiceType", serviceTypes.getValue());
		return serviceTypes;
	}
	
	/**
	 * 根据userID查询达人审核信息
	 * @return
	 */
	public ExamineInfoDTO getExamineInfo (int domainId,long userId) {
		if (domainId <=0 || userId <= 0) {
			log.error("get examineInfo error and params: domainId="+domainId+"userId="+userId);
			throw new BaseException("参数错误");
		}
		InfoQueryDTO queryDTO=new InfoQueryDTO();
		queryDTO.setDomainId(domainId);
		queryDTO.setType(ExamineType.TALENT.getType());
		queryDTO.setSellerId(userId);
		RepoUtils.requestLog(log,"examineDealService.queryMerchantExamineInfoById", queryDTO);
		MemResult<ExamineInfoDTO> examineInfoResult = examineDealService.queryMerchantExamineInfoBySellerId(queryDTO);
		RepoUtils.requestLog(log, "examineDealService.queryMerchantExamineInfoById", examineInfoResult.getValue());
		ExamineInfoDTO dto = null;
 		if (examineInfoResult != null) {
			dto = examineInfoResult.getValue();
		}
 		return dto;
	}
	
	/**
	 * 根据userID和domainID查询达人基本信息
	 * @return
	 */
	public TalentInfoDTO getTalentInfo(int domainId,long userId) {
		if (domainId <= 0 || userId <= 0 ) {
			log.error("get talentInfo error params: domainId="+domainId+"userId="+userId);
			throw new BaseException("参数错误");
		}
		RepoUtils.requestLog(log, "talentInfoDealService.queryTalentInfoByUserId", userId,domainId);
		MemResult<TalentInfoDTO> talentInfoResult  = talentInfoDealService.queryTalentInfoByUserId(userId, domainId);
		RepoUtils.requestLog(log, "talentInfoDealService.queryTalentInfoByUserId", talentInfoResult.getValue());
		
		TalentInfoDTO dto = null;
		if (talentInfoResult != null) {
			TalentInfoDTO talentInfoDTO = talentInfoResult.getValue();

			List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
			//填充店铺头图集合
				while(pictures.size() < Constant.TALENT_SHOP_PICNUM) {
					pictures.add("");
				}
			
			dto = talentInfoDTO;
		}
		return dto;
	}
	
	/**
	 * 新增达人基本信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public WebResultSupport addTalentInfo(TalentInfoVO vo)  {
		if (vo == null) {
			return null;
		}
		MemResult<Boolean> talentInfoResult=null;
		WebResultSupport webResultSupport=new WebResultSupport();
		try {
			RepoUtils.requestLog(log, "talentInfoDealService.updateTalentInfo", vo);
			 talentInfoResult = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo,sessionManager.getUserId()));
			 RepoUtils.requestLog(log, "talentInfoDealService.updateTalentInfo", talentInfoResult);
			
			 if (talentInfoResult.isSuccess()) {
				
			}
			else {
				webResultSupport.setWebReturnCode(WebReturnCode.TALENT_BASIC_SAVE_FAILURE);
			}
			 return webResultSupport;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			webResultSupport.setWebReturnCode(WebReturnCode.TALENT_BASIC_SAVE_FAILURE);
			return webResultSupport;
		}
		
	}
	
	/**
	 * 新增达人申请入驻资料信息
	 * @param vo
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public WebResultSupport addExamineInfo(ExamineInfoVO vo,int pageNo)  {
		if (vo == null) {
			return null;
		}
		MemResult<Boolean> ExamineInfoResult = null;
		WebResultSupport webResultSupport=new WebResultSupport();
		try {
			RepoUtils.requestLog(log, " examineDealService.submitMerchantExamineInfo", vo);
			 ExamineInfoResult = examineDealService.submitMerchantExamineInfo(vo);
			 RepoUtils.requestLog(log, " examineDealService.submitMerchantExamineInfo", ExamineInfoResult);
			 if (ExamineInfoResult.isSuccess()) {
				
			}
			 else {
				webResultSupport.setWebReturnCode(WebReturnCode.TALENT_INFO_SAVE_FAILURE);
			}
			return webResultSupport;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			webResultSupport.setWebReturnCode(WebReturnCode.TALENT_INFO_SAVE_FAILURE);
			return webResultSupport;
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
	public ExamineResultDTO getCheckResult() {
		InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		examineQueryDTO.setType(ExamineType.TALENT.getType());
		examineQueryDTO.setSellerId(sessionManager.getUserId());
		
		RepoUtils.requestLog(log, " examineDealService.queryExamineDealResult", examineQueryDTO);
		MemResult<ExamineResultDTO> examineDealResult = examineDealService.queryExamineDealResult(examineQueryDTO);
		RepoUtils.requestLog(log, " examineDealService.queryExamineDealResult", examineDealResult.getValue());
		if (examineDealResult.getValue() == null /*|| ( examineDealResult.getValue().getStatus().getStatus() == ExamineStatus.EXAMIN_OK.getStatus())*/) {
			return null;
		}
		return examineDealResult.getValue();

		
	}
}

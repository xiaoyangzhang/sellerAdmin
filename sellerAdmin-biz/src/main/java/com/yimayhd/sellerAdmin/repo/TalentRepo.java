package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.sun.tools.classfile.Annotation.element_value;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.MerchantConverter;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
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
//	@Autowired
//	private ApplyService applyServiceRef;
	public WebResult<List<CertificatesDO>> getServiceTypes() {
		MemResult<List<CertificatesDO>> serviceTypes = talentInfoDealService.queryTalentServiceType();
		if (serviceTypes == null) {
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}else if (!serviceTypes.isSuccess() || serviceTypes.getValue() == null) {
			return WebResult.failure(WebReturnCode.QUERY_FAILURE, serviceTypes.getErrorMsg());
		}
		return WebResult.success(serviceTypes.getValue());
		//return serviceTypes;
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
		queryDTO.setType(MerchantType.TALENT.getType());
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
	 * @return
	 * @throws Exception
	 */
	public WebResult<Boolean> addTalentInfo(TalentInfoVO vo)  {
		if (vo == null) {
			log.error("params error:TalentInfoVO={}",JSON.toJSONString(vo));
			
			return WebResult.failure(WebReturnCode.PARAM_ERROR);
		}
		WebResult<Boolean> result=new WebResult<Boolean>();
		try {
			 MemResult<Boolean> updateResult = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo,sessionManager.getUserId()));
			 if (updateResult == null) {
				return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
			}else if (!updateResult.isSuccess()) {
				return WebResult.failure(WebReturnCode.UPDATE_ERROR, updateResult.getErrorMsg());
			}
		} catch (Exception e) {
			log.error("param : TalentInfoVO={}",vo,e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
		
	}
	
	/**
	 * 新增达人申请入驻资料信息
	 * @param vo
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@MethodLogger
	public WebResult<Boolean> addExamineInfo(ExamineInfoVO vo)  {
		if (vo == null) {
			return null;
		}
		//WebResult<Boolean> result = new WebResult<Boolean>();
		WebResult<Boolean> result = new WebResult<Boolean>();
		try {
			ExamineInfoDTO dto = MerchantConverter.convertVO2DTO(vo, sessionManager.getUserId());
			dto.setType(ExamineType.TALENT.getType());
			MemResult<Boolean> ExamineInfoResult = examineDealService.submitMerchantExamineInfo(dto);
			if (ExamineInfoResult == null) {
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			}else if(!ExamineInfoResult.isSuccess() || ExamineInfoResult.getValue() == null) {
				int code = ExamineInfoResult.getErrorCode() ;
				if(MemberReturnCode.DB_MERCHANTNAME_FAILED.getCode() == code ) {
					result.setWebReturnCode( WebReturnCode.TALENT_MERCHANT_NAME_EXIST );
				}else if( MemberReturnCode.DB_EXAMINE_FAILED.getCode() == code ){
					result.setWebReturnCode(WebReturnCode.APPROVE_PASSED_DISABLE_MODIFY);
				}else {
					result.setWebReturnCode(WebReturnCode.TALENT_INFO_SAVE_FAILURE);
				}
				//return WebResult.failure(WebReturnCode.TALENT_INFO_SAVE_FAILURE, ExamineInfoResult.getErrorMsg());
			}
//			if(ExamineInfoResult == null || !ExamineInfoResult.isSuccess()) {
//				result2.setErrorCode(errorCode);
//				int code = ExamineInfoResult.getErrorCode() ;
//				if(MemberReturnCode.DB_MERCHANTNAME_FAILED.getCode() == code ) {
//					result.setWebReturnCode( WebReturnCode.TALENT_MERCHANT_NAME_EXIST );
//				}else if( MemberReturnCode.DB_EXAMINE_FAILED.getCode() == code ){
//					result.setWebReturnCode(WebReturnCode.APPROVE_PASSED_DISABLE_MODIFY);
//				}else{
//					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
//				}
//			}
			//return result;
			
		} catch (Exception e) {
			log.error("param :ExamineInfoVO={} error:{}",vo,e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	
	

	public List<BankInfoDTO> getBankList() {
		MemResult<List<BankInfoDTO>> bankList = talentInfoDealService.queryBankList();
		
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
		examineQueryDTO.setType(MerchantType.TALENT.getType());
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
	public WebResult<Boolean> updateCheckStatus(ExamineInfoVO vo) {
		WebResult<Boolean> result = new WebResult<Boolean>();
		try {
			InfoQueryDTO infoQueryDTO = vo.getInfoQueryDTO(sessionManager.getUserId());
			infoQueryDTO.setType(vo.getType());
			
			MemResult<Boolean> changeExamineStatusResult = examineDealService.changeExamineStatusIntoIng(infoQueryDTO);
			if (changeExamineStatusResult == null) {
				return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
			}else if (!changeExamineStatusResult.isSuccess()) {
				return WebResult.failure(WebReturnCode.UPDATE_ERROR, changeExamineStatusResult.getErrorMsg());
			}
			
		} catch (Exception e) {
			log.error("param:ExamineInfoVO={}",vo,e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
		
	}
	
	public WebResult<TalentInfoDTO> queryTalentInfoByUserId(long userId,int domainId) {
		WebResult<TalentInfoDTO> result = new WebResult<TalentInfoDTO>();
		if (userId <= 0 || domainId <= 0) {
			log.error("params error : userId={},domainId={}",userId,domainId);
			return WebResult.failure(WebReturnCode.PARAM_ERROR);
		}
		try {
			 MemResult<TalentInfoDTO> queryResult = talentInfoDealService.queryTalentInfoByUserId(userId, domainId);
			if (queryResult == null ) {
				return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
			}else if (!queryResult.isSuccess() || queryResult.getValue() == null) {
				return WebResult.failure(WebReturnCode.QUERY_FAILURE, queryResult.getErrorMsg());
			}
		} catch (Exception e) {
			log.error("params : userId={},domainId={} error:{}",userId,domainId,e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
}

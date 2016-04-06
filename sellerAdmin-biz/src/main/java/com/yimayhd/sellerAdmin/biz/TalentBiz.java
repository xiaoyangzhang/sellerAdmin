package com.yimayhd.sellerAdmin.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.BankInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;
import com.yimayhd.sellerAdmin.model.TalentInfoVO;
import com.yimayhd.sellerAdmin.service.RegionService;
import com.yimayhd.user.session.manager.SessionManager;
/***
 * 
 * @author zhangxy
 *
 */
public class TalentBiz {

	protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TalentInfoDealService talentInfoDealService;
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private RegionService regionService;
	/**
	 * 获取达人基本信息的服务类型
	 * @return
	 */
	public MemResult<List<CertificatesDO>> getServiceTypes() {
		return  talentInfoDealService.queryTalentServiceType();
	}
	/**
	 * 根据userID查询达人审核信息
	 * @return
	 */
	public ExamineInfoDTO getExamineInfo () {
		InfoQueryDTO queryDTO=new InfoQueryDTO();
		queryDTO.setDomainId(1200);
		queryDTO.setType(1);
		queryDTO.setSellerId(25);
		MemResult<ExamineInfoDTO> examineInfoResult = examineDealService.queryMerchantExamineInfoById(queryDTO);
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
	public TalentInfoDTO getTalentInfo() {
		MemResult<TalentInfoDTO> talentInfoResult  = talentInfoDealService.queryTalentInfoByUserId(19000, 1200);
		TalentInfoDTO dto = null;
		if (talentInfoResult != null) {
			TalentInfoDTO talentInfoDTO = talentInfoResult.getValue();
//			for (String pic : talentInfoDTO.getTalentInfoDO().getPictures()) {
//				if (StringUtils.isBlank(pic)) {
//					pic = "";
//				}
//			}
			List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
			//if (pictures.size() < 5) {
				while(pictures.size() < 5) {
					pictures.add("");
				}
			//}
			
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
		MemResult<Boolean> talentInfoResult=null;
		WebResultSupport webResultSupport=new WebResultSupport();
		try {
			 talentInfoResult = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo));
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
	public WebResultSupport addExamineInfo(ExamineInfoVO vo)  {
		MemResult<Boolean> ExamineInfoResult = null;
		WebResultSupport webResultSupport=new WebResultSupport();
		try {
			 ExamineInfoResult = examineDealService.submitMerchantExamineInfo(vo.getExamineInfoDTO(vo));
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
	/**
	 * 获取达人入驻审核结果
	 * @return
	 */
	public WebResultSupport getCheckResult() {
		WebResultSupport  webResultSupport = new WebResultSupport();
		InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
		examineQueryDTO.setDomainId(1200);
		examineQueryDTO.setType(1);
		examineQueryDTO.setSellerId(25);
		MemResult<ExamineResultDTO> examineDealResult = examineDealService.queryExamineDealResult(examineQueryDTO);
		if (examineDealResult.getValue().isOK()) {
			webResultSupport.initSuccess(examineDealResult.getValue().getDealMes());
		}
		else {
		//	webResultSupport.setWebReturnCode(WebReturnCode.TALENT_CHECKRESULT_FAILURE);
			webResultSupport.initFailure(WebReturnCode.TALENT_CHECKRESULT_FAILURE, examineDealResult.getValue().getDealMes());
		}
		return webResultSupport;
		
	}
	
	public List<BankInfoDTO> getBankList() {
		MemResult<List<BankInfoDTO>> bankList = talentInfoDealService.queryBankList();
		return bankList.getValue();
	}
	public  String formatDate (Date date) {   
	    return  new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}

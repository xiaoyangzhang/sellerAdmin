package com.yimayhd.sellerAdmin.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
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
			dto = talentInfoResult.getValue();
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
		try {
			 talentInfoResult = talentInfoDealService.updateTalentInfo(vo.getTalentInfoDTO(vo));
			//WebResultSupport resultSupport = talentBiz.addTalentInfo(vo);
			
			 return getWebResultSupport(talentInfoResult);
			//return resultSupport;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			WebResultSupport webResultSupport=new WebResultSupport();
			WebReturnCode returnCode=new WebReturnCode(-1, "保存失败");
			webResultSupport.setWebReturnCode(returnCode);
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
		try {
			 ExamineInfoResult = examineDealService.submitMerchantExamineInfo(vo.getExamineInfoDTO(vo));
		
			return getWebResultSupport(ExamineInfoResult);
		 //return getWebResultSupport(talentInfoResult);
			//return resultSupport;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			WebResultSupport webResultSupport=new WebResultSupport();
			WebReturnCode returnCode=new WebReturnCode(-1, "保存失败");
			webResultSupport.setWebReturnCode(returnCode);
			return webResultSupport;
		}
	}
	
	public WebResultSupport getWebResultSupport(MemResult<Boolean> result) {
		WebResultSupport webResultSupport=new WebResultSupport();
		if (result.isSuccess()) {
			
		}
		else {
			WebReturnCode returnCode=new WebReturnCode(result.getErrorCode(), result.getErrorMsg());

			webResultSupport.setWebReturnCode(returnCode);
		}
		return webResultSupport;
		
	}
}

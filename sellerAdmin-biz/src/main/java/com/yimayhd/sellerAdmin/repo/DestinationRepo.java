package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.dto.DestinationNode;
import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 目的地Repo
 * 
 * @author xiemingna
 *
 */
public class DestinationRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	public static final int STATUS_DISABLE = 2;
	public static final int STATUS_ENABLE = 1;
	@Autowired
	private DestinationService destinationServiceRef;
	
	public List<DestinationNode> queryListBySimplecodes(List<Integer> codeList) {
		int code = DestinationOutType.LINE.getCode();
		int domainJiuxiu = Constant.DOMAIN_JIUXIU;
		RepoUtils.requestLog(log, "destinationServiceRef.queryOverseaDestinationTree", code,domainJiuxiu);
		DestinationQueryDTO destinationQuery=new DestinationQueryDTO();
		destinationQuery.setCodeList(codeList);
		RcResult<List<DestinationNode>> baseResult = destinationServiceRef.queryDestinationTree(destinationQuery);
		RepoUtils.resultLog(log, "destinationServiceRef.queryOverseaDestinationTree", baseResult);
		return baseResult.getT();
	}
	public List<DestinationNode> queryInlandDestinationTree(int code) {
		int domainJiuxiu = Constant.DOMAIN_JIUXIU;
		RepoUtils.requestLog(log, "destinationServiceRef.queryInlandDestinationTree", code,domainJiuxiu);
		RcResult<List<DestinationNode>> baseResult = destinationServiceRef.queryInlandDestinationTree(code,domainJiuxiu);
		RepoUtils.resultLog(log, "destinationServiceRef.queryInlandDestinationTree", baseResult);
		return baseResult.getT();
	}
	public List<DestinationNode> queryOverseaDestinationTree() {
		int code = DestinationOutType.LINE.getCode();
		int domainJiuxiu = Constant.DOMAIN_JIUXIU;
		RepoUtils.requestLog(log, "destinationServiceRef.queryOverseaDestinationTree", code,domainJiuxiu);
		RcResult<List<DestinationNode>> baseResult = destinationServiceRef.queryOverseaDestinationTree(code,domainJiuxiu);
		RepoUtils.resultLog(log, "destinationServiceRef.queryOverseaDestinationTree", baseResult);
		return baseResult.getT();
	}
	public List<DestinationDO> queryDestinationList(List<Integer> codeList) {
		int code = DestinationOutType.LINE.getCode();
		int domainJiuxiu = Constant.DOMAIN_JIUXIU;
		DestinationQueryDTO destinationQueryDTO=new DestinationQueryDTO();
		destinationQueryDTO.setOutType(code);
		destinationQueryDTO.setCodeList(codeList);
		destinationQueryDTO.setDomain(domainJiuxiu);
		RepoUtils.requestLog(log, "destinationServiceRef.queryDestinationList", code,domainJiuxiu,codeList);
		RcResult<List<DestinationDO>> baseResult = destinationServiceRef.queryDestinationList(destinationQueryDTO);
		RepoUtils.resultLog(log, "destinationServiceRef.queryDestinationList", baseResult);
		return baseResult.getT();
	}
}

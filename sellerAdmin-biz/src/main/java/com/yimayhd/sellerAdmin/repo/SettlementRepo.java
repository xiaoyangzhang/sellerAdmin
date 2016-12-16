package com.yimayhd.sellerAdmin.repo;

import com.yimayhd.pay.client.service.settlement.SettlementQueryService;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;
import com.yimayhd.pay.client.model.query.settlement.SettlementQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author hongfei.guo
 *
 */
public class SettlementRepo {
	private Logger log = LoggerFactory.getLogger(getClass());


	@Resource
	private SettlementQueryService settlementQueryServiceRef;


	
	/**
     * 查询某个商户所有结算信息
     * @param query
     * @return
     */
    public PayPageResultDTO<SettlementDTO> queryMerchantSettlements(SettlementQuery query){
		RepoUtils.requestLog(log, "settlementServiceRef.queryMerchantSettlements", query);
		PayPageResultDTO<SettlementDTO> result = settlementQueryServiceRef.queryMerchantSettlements(query);
		RepoUtils.resultLog(log, "settlementServiceRef.queryMerchantSettlements", result);
		return result;
	}
    
    /**
     * 查询商户某个结算单的详细信息
     * @param query
     * @return
     */
    public PayPageResultDTO<SettlementDetailDTO> queryMerchantSettlementDetails(SettlementQuery query){
		RepoUtils.requestLog(log, "settlementServiceRef.queryMerchantSettlementDetails", query);
		PayPageResultDTO<SettlementDetailDTO> result = settlementQueryServiceRef.queryMerchantSettlementDetails(query);
		RepoUtils.resultLog(log, "settlementServiceRef.queryMerchantSettlementDetails", result);
		return result;
	}
    
    /**
     * 查询某个商户待结算单的信息
     * @param query
     * @return
     */
    public PayPageResultDTO<SettlementDetailDTO> queryMerchantUnsettlements(SettlementQuery query){
		RepoUtils.requestLog(log, "settlementServiceRef.queryMerchantUnsettlements", query);
		PayPageResultDTO<SettlementDetailDTO> result = settlementQueryServiceRef.queryMerchantUnsettlements(query);
		RepoUtils.resultLog(log, "settlementServiceRef.queryMerchantUnsettlements", result);
		return result;
	}
}

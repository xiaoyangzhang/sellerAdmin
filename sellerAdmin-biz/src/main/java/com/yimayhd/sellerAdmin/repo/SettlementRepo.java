package com.yimayhd.sellerAdmin.repo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;
import com.yimayhd.pay.client.model.query.settlement.SettlementQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.service.settlement.SettlementService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 
 * @author hongfei.guo
 *
 */
public class SettlementRepo {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private SettlementService settlementServiceRef;
	
	/**
     * 查询某个商户所有结算信息
     * @param query
     * @return
     */
    public PayPageResultDTO<SettlementDTO> queryMerchantSettlements(SettlementQuery query){
		RepoUtils.requestLog(log, "settlementServiceRef.queryMerchantSettlements", query);
		PayPageResultDTO<SettlementDTO> result = settlementServiceRef.queryMerchantSettlements(query);
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
		PayPageResultDTO<SettlementDetailDTO> result = settlementServiceRef.queryMerchantSettlementDetails(query);
		RepoUtils.resultLog(log, "settlementServiceRef.queryMerchantSettlementDetails", result);
		return result;
	}
    
    /**
     * 查询某个商户待结算单的信息
     * @param query
     * @return
     */
    public PayPageResultDTO<SettlementDTO> queryMerchantUnsettlements(SettlementQuery query){
		RepoUtils.requestLog(log, "settlementServiceRef.queryMerchantUnsettlements", query);
		PayPageResultDTO<SettlementDTO> result = settlementServiceRef.queryMerchantUnsettlements(query);
		RepoUtils.resultLog(log, "settlementServiceRef.queryMerchantUnsettlements", result);
		return result;
	}
}

package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.SettlementVO;
import com.yimayhd.sellerAdmin.model.SettlementDetailVO;
import com.yimayhd.sellerAdmin.model.query.SettlementQuery;

public interface SettlementService {

	/**
     * 查询某个商户所有结算信息
     * @param query
     * @return
     */
    public PageVO<SettlementVO> queryMerchantSettlements(SettlementQuery query, long userId) throws Exception;
    
    /**
     * 查询商户某个结算单的详细信息
     * @param query
     * @return
     */
    public PageVO<SettlementDetailVO> queryMerchantSettlementDetails(SettlementQuery query, long userId) throws Exception;
    
    /**
     * 查询某个商户待结算单的信息
     * @param query
     * @return
     */
    public PageVO<SettlementVO> queryMerchantUnsettlements(SettlementQuery query, long userId) throws Exception;
}

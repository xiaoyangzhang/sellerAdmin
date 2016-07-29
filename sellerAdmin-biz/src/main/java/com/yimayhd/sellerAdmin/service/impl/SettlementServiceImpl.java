package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.SettlementDetailVO;
import com.yimayhd.sellerAdmin.model.SettlementVO;
import com.yimayhd.sellerAdmin.model.query.SettlementQuery;
import com.yimayhd.sellerAdmin.repo.SettlementRepo;
import com.yimayhd.sellerAdmin.service.SettlementService;

/**
 * Created by hongfei.guo on 2016/07/25.
 */
public class SettlementServiceImpl implements SettlementService {

	private static final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);
	
	@Autowired
	private SettlementRepo settlementRepo;

	@Override
	public PageVO<SettlementVO> queryMerchantSettlements(SettlementQuery query, long userId) throws Exception {
		
		com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = SettlementQuery.getSettlementQuery(query, userId);
		PayPageResultDTO<SettlementDTO> result = settlementRepo.queryMerchantSettlements(queryDO);
		if(null == result){
            log.error("queryMerchantSettlements.queryMerchantSettlements--settlementRepo.queryMerchantSettlements return value is null and parame: {}", JSON.toJSONString(queryDO));
            throw new BaseException("查询用户账户，返回结果错误");
        }else if(!result.isSuccess()){
            log.error("queryMerchantSettlements.queryMerchantSettlements--settlementRepo.queryMerchantSettlements return value error ! returnValue : {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(queryDO));
            throw new NoticeException(result.getResultMsg());
        }
		
		List<SettlementVO> retList = new ArrayList<SettlementVO>();
		List<SettlementDTO> list = result.getList();
		if(CollectionUtils.isNotEmpty(list)){
			SettlementDTO dto = null;
			for(int i = 0; i < list.size(); i++){
				dto = list.get(i);
				retList.add(SettlementVO.getSettlementVO(dto));
			}
		}
		
		return new PageVO<SettlementVO>(query.getPageNo(), query.getPageSize(), result.getTotalCount(), retList);
	}

	@Override
	public PageVO<SettlementDetailVO> queryMerchantSettlementDetails(SettlementQuery query, long userId) throws Exception {
		
		com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = SettlementQuery.getSettlementQuery(query, userId);
		PayPageResultDTO<SettlementDetailDTO> result = settlementRepo.queryMerchantSettlementDetails(queryDO);
		if(null == result){
            log.error("queryMerchantSettlements.queryMerchantSettlementDetails--settlementRepo.queryMerchantSettlementDetails return value is null and parame: {}", JSON.toJSONString(queryDO));
            throw new BaseException("查询用户账户，返回结果错误");
        }else if(!result.isSuccess()){
            log.error("queryMerchantSettlements.queryMerchantSettlementDetails--settlementRepo.queryMerchantSettlementDetails return value error ! returnValue : {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(queryDO));
            throw new NoticeException(result.getResultMsg());
        }
		
		List<SettlementDetailVO> retList = transformSettlementList(result);
		return new PageVO<SettlementDetailVO>(query.getPageNo(), query.getPageSize(), result.getTotalCount(), retList);
	}

	@Override
	public PageVO<SettlementDetailVO> queryMerchantUnsettlements(SettlementQuery query, long userId) throws Exception {
		
		com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = SettlementQuery.getSettlementQuery(query, userId);
		PayPageResultDTO<SettlementDetailDTO> result = settlementRepo.queryMerchantUnsettlements(queryDO);
		if(null == result){
            log.error("queryMerchantSettlements.queryMerchantUnsettlements--settlementRepo.queryMerchantUnsettlements return value is null and parame: {}", JSON.toJSONString(queryDO));
            throw new BaseException("查询用户账户，返回结果错误");
        }else if(!result.isSuccess()){
            log.error("queryMerchantSettlements.queryMerchantUnsettlements--settlementRepo.queryMerchantUnsettlements return value error ! returnValue : {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(queryDO));
            throw new NoticeException(result.getResultMsg());
        }
		
		List<SettlementDetailVO> retList = transformSettlementList(result);
		return new PageVO<SettlementDetailVO>(query.getPageNo(), query.getPageSize(), result.getTotalCount(), retList);
	}
	
	private List<SettlementDetailVO> transformSettlementList(PayPageResultDTO<SettlementDetailDTO> result){
		List<SettlementDetailVO> retList = new ArrayList<SettlementDetailVO>();
		if(result == null){
			return retList;
		}
		
		List<SettlementDetailDTO> list = result.getList();
		if(CollectionUtils.isNotEmpty(list)){
			SettlementDetailDTO dto = null;
			for(int i = 0; i < list.size(); i++){
				dto = list.get(i);
				retList.add(SettlementDetailVO.getSettlementDetailVO(dto));
			}
		}
		return retList;
	}
	
}

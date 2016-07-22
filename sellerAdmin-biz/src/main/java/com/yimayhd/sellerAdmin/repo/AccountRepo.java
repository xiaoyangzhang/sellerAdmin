package com.yimayhd.sellerAdmin.repo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.pay.client.model.param.eleaccount.WithdrawalDTO;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccountSingleQuery;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccountInfoResult;
import com.yimayhd.pay.client.service.eleaccount.EleAccBillService;
import com.yimayhd.pay.client.service.eleaccount.EleAccHandlerService;
import com.yimayhd.pay.client.service.eleaccount.EleAccInfoService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 
 * @author hongfei.guo
 *
 */
public class AccountRepo {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private EleAccInfoService eleAccInfoServiceRef;
	
	@Resource
	private EleAccHandlerService eleAccHandlerServiceRef;
	
	@Resource
	private EleAccBillService eleAccBillServiceRef;
	
	/**
     * 查询电子账户的信息
     * @param eleAccountSingleQuery
     * @return
     */
	public EleAccountInfoResult querySingleEleAccount(EleAccountSingleQuery query){
		RepoUtils.requestLog(log, "eleAccInfoServiceRef.querySingleEleAccount", query);
		EleAccountInfoResult result = eleAccInfoServiceRef.querySingleEleAccount(query);
		RepoUtils.resultLog(log, "eleAccInfoServiceRef.querySingleEleAccount", result);
		return result;
	}
    
    /**
     * 账户提现
     * @param withdrawalDTO
     * @return
     */
	public ResultSupport withdrawal(WithdrawalDTO dto){
		RepoUtils.requestLog(log, "eleAccHandlerServiceRef.withdrawal", dto);
		ResultSupport result = eleAccHandlerServiceRef.withdrawal(dto);
		RepoUtils.resultLog(log, "eleAccHandlerServiceRef.withdrawal", result);
		return result;
	}
    
    /**
     * 用户收支明细的查询
     * @param eleAccBillDetailQuery
     * @return
     */
	public ResultSupport queryEleAccBillDetail(EleAccBillDetailQuery query){
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", query);
		ResultSupport result = eleAccBillServiceRef.queryEleAccBillDetail(query);
		RepoUtils.resultLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", result);
		return result;
	}
}

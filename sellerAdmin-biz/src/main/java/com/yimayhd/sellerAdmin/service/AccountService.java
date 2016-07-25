package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.EleAccountBillVO;
import com.yimayhd.sellerAdmin.model.EleAccountInfoVO;
import com.yimayhd.sellerAdmin.model.WithdrawalVO;
import com.yimayhd.sellerAdmin.model.query.AccountQuery;

public interface AccountService {

	/**
     * 查询电子账户的信息
     * @param query
     * @return
     */
	public EleAccountInfoVO querySingleEleAccount(AccountQuery query) throws Exception;
    
    /**
     * 账户提现
     * @param withdrawalVO
     * @return
     */
	public boolean withdrawal(WithdrawalVO withdrawalVO) throws Exception;
    
    /**
     * 用户收支明细的查询
     * @param query
     * @return
     */
	public PageVO<EleAccountBillVO> queryEleAccBillDetail(AccountQuery query) throws Exception;
}

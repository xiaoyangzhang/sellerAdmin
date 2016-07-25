package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccountSingleQuery;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.util.DateUtil;


public class AccountQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	/**是否需要账户余额信息*/
    private boolean isNeedBalance;

    /**是否需要待结算金额*/
    private boolean isNeedUnSettlementAmount;

    /**交易类型 见枚举 TransType*/
    private String TransType;

    /**交易开始时间*/
    private String transStartDate;
    /**交易结束时间*/
    private String transEndDate;
    
    public static EleAccountSingleQuery getEleAccountSingleQuery(AccountQuery query){
    	
    	EleAccountSingleQuery queryDO = new EleAccountSingleQuery();
    	queryDO.setIsNeedBalance(true);
    	queryDO.setNeedUnSettlementAmount(true);
    	return queryDO;
    }
    
    public static EleAccBillDetailQuery getEleAccBillDetailQuery(AccountQuery query){
    	
    	EleAccBillDetailQuery queryDO = new EleAccBillDetailQuery();
    	queryDO.setTransType(Integer.parseInt(query.getTransType()));
    	queryDO.setTransStartDate(DateUtil.parseDate(query.getTransStartDate()));
    	queryDO.setTransEndDate(DateUtil.parseDate(query.getTransEndDate()));
    	return queryDO;
    }

	public boolean isNeedBalance() {
		return isNeedBalance;
	}

	public void setNeedBalance(boolean isNeedBalance) {
		this.isNeedBalance = isNeedBalance;
	}

	public boolean isNeedUnSettlementAmount() {
		return isNeedUnSettlementAmount;
	}

	public void setNeedUnSettlementAmount(boolean isNeedUnSettlementAmount) {
		this.isNeedUnSettlementAmount = isNeedUnSettlementAmount;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getTransStartDate() {
		return transStartDate;
	}

	public void setTransStartDate(String transStartDate) {
		this.transStartDate = transStartDate;
	}

	public String getTransEndDate() {
		return transEndDate;
	}

	public void setTransEndDate(String transEndDate) {
		this.transEndDate = transEndDate;
	}
   
}


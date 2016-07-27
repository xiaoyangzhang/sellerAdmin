package com.yimayhd.sellerAdmin.model.query;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccountSingleQuery;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.util.DateUtil;


public class AccountQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	/**交易类型 见枚举 TransType*/
    private String transType;

    /**交易开始时间*/
    private String transStartDate;
    /**交易结束时间*/
    private String transEndDate;
    
    public static EleAccountSingleQuery getEleAccountSingleQuery(long userId){
    	
    	EleAccountSingleQuery queryDO = new EleAccountSingleQuery();
    	queryDO.setIsNeedBalance(true);
    	queryDO.setNeedUnSettlementAmount(true);
    	queryDO.setUserId(userId);
    	return queryDO;
    }
    
    public static EleAccBillDetailQuery getEleAccBillDetailQuery(AccountQuery query, long userId){
    	
    	EleAccBillDetailQuery queryDO = new EleAccBillDetailQuery();
    	if(StringUtils.isNotEmpty(query.getTransType())){
    		queryDO.setTransType(Integer.parseInt(query.getTransType()));
    	}
    	if(StringUtils.isNotEmpty(query.getTransStartDate())){
    		queryDO.setTransStartDate(DateUtil.parseDate(query.getTransStartDate()));
    	}
    	if(StringUtils.isNotEmpty(query.getTransEndDate())){
    		queryDO.setTransEndDate(DateUtil.parseDate(query.getTransEndDate()));
    	}
    	queryDO.setNeedCount(true);
    	queryDO.setUserId(userId);
    	return queryDO;
    }
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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


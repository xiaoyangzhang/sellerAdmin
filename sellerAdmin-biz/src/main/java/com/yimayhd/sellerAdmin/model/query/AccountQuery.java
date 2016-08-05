package com.yimayhd.sellerAdmin.model.query;

import java.text.ParseException;

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
    	queryDO.setGetFromCache(false);
    	return queryDO;
    }
    
    public static EleAccBillDetailQuery getEleAccBillDetailQuery(AccountQuery query, long userId) throws ParseException{
    	
    	EleAccBillDetailQuery queryDO = new EleAccBillDetailQuery();
    	if(StringUtils.isNotEmpty(query.getTransType())){
    		queryDO.setTransType(Integer.parseInt(query.getTransType()));
    	}
    	if(StringUtils.isNotEmpty(query.getTransStartDate())){
    		queryDO.setTransStartDate(DateUtil.formatMinTimeForDate(query.getTransStartDate()));
    	}
    	if(StringUtils.isNotEmpty(query.getTransEndDate())){
    		queryDO.setTransEndDate(DateUtil.formatMaxTimeForDate(query.getTransEndDate()));
    	}
    	if(query.getPageNo() != null){
    		queryDO.setPageNo(query.getPageNo());
    	}
    	if(query.getPageSize() != null){
    		queryDO.setPageSize(query.getPageSize());
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


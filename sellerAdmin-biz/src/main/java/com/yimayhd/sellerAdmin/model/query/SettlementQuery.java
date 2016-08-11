package com.yimayhd.sellerAdmin.model.query;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.base.BaseQuery;


public class SettlementQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;
	
	/** 商户ID */
    private String sellerId;

	/** 结算单号 */
    private String settlementId;
    
    /** 结算日期,格式：yyyy-MM-dd */
    private String reqDate;
    
    /** 结算日期,格式：MM月dd日 */
    private String reqDateWithOutYear;
	
	/** 结算日期-开始日期,格式：yyyy-MM-dd */
    private String reqDateStart;

    /** 结算日期-结束日期,格式：yyyy-MM-dd */
    private String reqDateEnd;

    
    public static com.yimayhd.pay.client.model.query.settlement.SettlementQuery getSettlementQuery(SettlementQuery query, long userId){
    	
    	com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = new com.yimayhd.pay.client.model.query.settlement.SettlementQuery(); 
    	
    	queryDO.setSellerId(userId);
    	if(StringUtils.isNotEmpty(query.getSettlementId())){
    		queryDO.setSettlementId(Long.parseLong(query.getSettlementId()));
    	}
    	if(StringUtils.isNotEmpty(query.getReqDate())){
    		queryDO.setSettlementDate(query.getReqDate());
    	}
    	if(StringUtils.isNotEmpty(query.getReqDateStart())){
    		queryDO.setSettlementDateStart(query.getReqDateStart());
    	}
    	if(StringUtils.isNotEmpty(query.getReqDateEnd())){
    		queryDO.setSettlementDateEnd(query.getReqDateEnd());
    	}
    	if(query.getPageNo() != null){
    		queryDO.setPageNo(query.getPageNo());
    	}
    	if(query.getPageSize() != null){
    		queryDO.setPageSize(query.getPageSize());
    	}
    	queryDO.setNeedCount(true);
    	return queryDO;
    }
    
    public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	public String getReqDateStart() {
		return reqDateStart;
	}
	public void setReqDateStart(String reqDateStart) {
		this.reqDateStart = reqDateStart;
	}
	public String getReqDateEnd() {
		return reqDateEnd;
	}
	public void setReqDateEnd(String reqDateEnd) {
		this.reqDateEnd = reqDateEnd;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqDateWithOutYear() {
		return reqDateWithOutYear;
	}
	public void setReqDateWithOutYear(String reqDateWithOutYear) {
		this.reqDateWithOutYear = reqDateWithOutYear;
	}
}


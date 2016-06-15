package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * 
* @ClassName: AssessmentListQuery 
* @Description: 
* @author wangjun
* @date 2016年5月17日 下午2:53:45 
*
 */
public class AssessmentListQuery extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNO;//订单编号
    private String nickName;//买家昵称
    private String itemNo;//商品编号
	private String beginDate;
	private String endDate;
	private Integer domain; //1000--b2c、1100--gf;
	private long sellerId;
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getDomain() {
		return domain;
	}
	public void setDomain(Integer domain) {
		this.domain = domain;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	
	
}

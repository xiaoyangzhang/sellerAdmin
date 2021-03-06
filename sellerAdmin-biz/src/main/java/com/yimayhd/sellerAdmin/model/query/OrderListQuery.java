package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderListQuery extends BaseQuery {
	private static final long serialVersionUID = 2911545482500153296L;
	private String orderNO;//订单编号
    private String orderStat;//订单状态
    private String buyerPhone;//买家手机号
    private String buyerName;//买家昵称
    private String itemName;//名称
    private String affiliatedAsk;//附属要求
	private String beginDate;
	private String endDate;
	private int [] orderTypes;//订单类型
	private Integer domain; //1000--b2c、1100--gf;
	private int itemType;//商品类型
	private long sellerId;
	private long outId; // 景区酒店 资源id
	private int outType;// 景区酒店资源类型
	private String hotelName;//酒店名称
	private String scenicName;//景区名称
	private String assesmentType;//评价状态


	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAffiliatedAsk() {
		return affiliatedAsk;
	}
	public void setAffiliatedAsk(String affiliatedAsk) {
		this.affiliatedAsk = affiliatedAsk;
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

	public int[] getOrderTypes() {
		return orderTypes;
	}

	public void setOrderTypes(int[] orderTypes) {
		this.orderTypes = orderTypes;
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

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getAssesmentType() {
		return assesmentType;
	}
	public void setAssesmentType(String assesmentType) {
		this.assesmentType = assesmentType;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public long getOutId() {

		return outId;
	}

	public void setOutId(long outId) {
		this.outId = outId;
	}

	public int getOutType() {
		return outType;
	}

	public void setOutType(int outType) {
		this.outType = outType;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
}

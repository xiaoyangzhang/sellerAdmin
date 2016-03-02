package com.yimayhd.sellerAdmin.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7256530163857309908L;
	// 订单状态
	private Integer orderState;
	// 买家信息
	private String buyerMobile;// 买家手机
	private String buyerNickname;// 买家昵称
	private String buyerName;// 买家姓名
	// 订单基础信息
	private String orderNO;// 订单编号
	private Date orderTime;// 下订单时间
	private Date paymentTime;// 付款时间
	private String paymentMethod;// 付款方式
	private String buyerNote;// 买家备注
	private String invoice;// 发票
	private String customerServiceNote;// 客服备注
	// 联系人
	private List<Contact> contacts;
	// 游客
	private List<Tourist> tourists;
	// 订单商品信息
	private BigDecimal orderTotalPrice;// 订单总额
	private List<Coupon> coupons;// 优惠券
	private List<Commodity> commodityList;// 商品列表

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBuyerNote() {
		return buyerNote;
	}

	public void setBuyerNote(String buyerNote) {
		this.buyerNote = buyerNote;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getCustomerServiceNote() {
		return customerServiceNote;
	}

	public void setCustomerServiceNote(String customerServiceNote) {
		this.customerServiceNote = customerServiceNote;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Tourist> getTourists() {
		return tourists;
	}

	public void setTourists(List<Tourist> tourists) {
		this.tourists = tourists;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public List<Commodity> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}

	public String getBuyerNickname() {
		return buyerNickname;
	}

	public void setBuyerNickname(String buyerNickname) {
		this.buyerNickname = buyerNickname;
	}

}

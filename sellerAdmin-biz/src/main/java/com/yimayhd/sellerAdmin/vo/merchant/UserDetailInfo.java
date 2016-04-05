package com.yimayhd.sellerAdmin.vo.merchant;

import java.io.Serializable;

public class UserDetailInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private long sellerId;
	// 商户名称
	private String sellerName;

	private int domainId;
	// 审核状态
	private int examinStatus;
	// 入住类型
	private int type;
	// 店铺名称 此字段只有店铺有 达人无
	private String merchantName;
	// 法人姓名
	private String legralName;
	// 营业地址
	private String address;
	// 经营范围
	private String saleScope;
	// 法人身份证正面
	private String legralCardUp;
	// 法人身份证反面
	private String legralCardDown;
	// 营业执照副本正面
	private String businessLicense;
	// 组织机构代码证正面
	private String orgCard;
	// 税务登记证正面
	private String affairsCard;
	// 开户许可证正面
	private String openCard;
	// 旅行社业务经营许可证正面
	private String travingCard;
	// 联系人变更证明
	private String touchProve;
	// 旅行社授权书
	private String travelAgencyAuthorization;
	// 旅行社责任险证明正面
	private String travelAgencyInsurance;

	// *******************负责人信息****************************//
	// 商户负责人姓名
	private String principleName;
	// 负责人证件类型名称
	private String card;
	// 负责人证件号码
	private String principleCardId;
	// 负责人手机号码
	private long principleTel;
	// 负责人邮箱
	private String principleMail;
	// 负责人身份证正面
	private String principleCardUp;
	// 负责人身份证反面
	private String principleCardDown;
    // ******************财务信息*********************//

    // 财务开户银行ID
    private String bank;
    // 财务开户银行NAME
    private String financeOpenBankName;
    // 财务开户名称
    private String financeOpenName;
    // 财务结算账号
    private String accountNum;
    // 财务结算开户行省份
    private String accountBankProvince;
    // 财务结算开户行省份code
    private String province;
    // 财务结算开户行城市
    private String accountBankCity;
    // 财务结算开户行城市CODE
    private String city;
    // 开户行名称
    private String accountBankName;

    // *****************合作合同******************//
    // 合同1
    private String cooperation1;
    // 合同2
    private String cooperation2;
    // 合同3
    private String cooperation3;
    // 合同4
    private String cooperation4;
    // 合同5
    private String cooperation5;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public int getExaminStatus() {
		return examinStatus;
	}
	public void setExaminStatus(int examinStatus) {
		this.examinStatus = examinStatus;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getLegralName() {
		return legralName;
	}
	public void setLegralName(String legralName) {
		this.legralName = legralName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSaleScope() {
		return saleScope;
	}
	public void setSaleScope(String saleScope) {
		this.saleScope = saleScope;
	}
	public String getLegralCardUp() {
		return legralCardUp;
	}
	public void setLegralCardUp(String legralCardUp) {
		this.legralCardUp = legralCardUp;
	}
	public String getLegralCardDown() {
		return legralCardDown;
	}
	public void setLegralCardDown(String legralCardDown) {
		this.legralCardDown = legralCardDown;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getOrgCard() {
		return orgCard;
	}
	public void setOrgCard(String orgCard) {
		this.orgCard = orgCard;
	}
	public String getAffairsCard() {
		return affairsCard;
	}
	public void setAffairsCard(String affairsCard) {
		this.affairsCard = affairsCard;
	}
	public String getOpenCard() {
		return openCard;
	}
	public void setOpenCard(String openCard) {
		this.openCard = openCard;
	}
	public String getTravingCard() {
		return travingCard;
	}
	public void setTravingCard(String travingCard) {
		this.travingCard = travingCard;
	}
	public String getTouchProve() {
		return touchProve;
	}
	public void setTouchProve(String touchProve) {
		this.touchProve = touchProve;
	}
	public String getTravelAgencyAuthorization() {
		return travelAgencyAuthorization;
	}
	public void setTravelAgencyAuthorization(String travelAgencyAuthorization) {
		this.travelAgencyAuthorization = travelAgencyAuthorization;
	}
	public String getTravelAgencyInsurance() {
		return travelAgencyInsurance;
	}
	public void setTravelAgencyInsurance(String travelAgencyInsurance) {
		this.travelAgencyInsurance = travelAgencyInsurance;
	}
	public String getPrincipleName() {
		return principleName;
	}
	public void setPrincipleName(String principleName) {
		this.principleName = principleName;
	}

	public String getPrincipleCardId() {
		return principleCardId;
	}
	public void setPrincipleCardId(String principleCardId) {
		this.principleCardId = principleCardId;
	}
	public long getPrincipleTel() {
		return principleTel;
	}
	public void setPrincipleTel(long principleTel) {
		this.principleTel = principleTel;
	}
	public String getPrincipleMail() {
		return principleMail;
	}
	public void setPrincipleMail(String principleMail) {
		this.principleMail = principleMail;
	}
	public String getPrincipleCardUp() {
		return principleCardUp;
	}
	public void setPrincipleCardUp(String principleCardUp) {
		this.principleCardUp = principleCardUp;
	}
	public String getPrincipleCardDown() {
		return principleCardDown;
	}
	public void setPrincipleCardDown(String principleCardDown) {
		this.principleCardDown = principleCardDown;
	}

	public String getFinanceOpenBankName() {
		return financeOpenBankName;
	}
	public void setFinanceOpenBankName(String financeOpenBankName) {
		this.financeOpenBankName = financeOpenBankName;
	}
	public String getFinanceOpenName() {
		return financeOpenName;
	}
	public void setFinanceOpenName(String financeOpenName) {
		this.financeOpenName = financeOpenName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountBankProvince() {
		return accountBankProvince;
	}
	public void setAccountBankProvince(String accountBankProvince) {
		this.accountBankProvince = accountBankProvince;
	}

	public String getAccountBankCity() {
		return accountBankCity;
	}
	public void setAccountBankCity(String accountBankCity) {
		this.accountBankCity = accountBankCity;
	}

	public String getAccountBankName() {
		return accountBankName;
	}
	public void setAccountBankName(String accountBankName) {
		this.accountBankName = accountBankName;
	}
	public String getCooperation1() {
		return cooperation1;
	}
	public void setCooperation1(String cooperation1) {
		this.cooperation1 = cooperation1;
	}
	public String getCooperation2() {
		return cooperation2;
	}
	public void setCooperation2(String cooperation2) {
		this.cooperation2 = cooperation2;
	}
	public String getCooperation3() {
		return cooperation3;
	}
	public void setCooperation3(String cooperation3) {
		this.cooperation3 = cooperation3;
	}
	public String getCooperation4() {
		return cooperation4;
	}
	public void setCooperation4(String cooperation4) {
		this.cooperation4 = cooperation4;
	}
	public String getCooperation5() {
		return cooperation5;
	}
	public void setCooperation5(String cooperation5) {
		this.cooperation5 = cooperation5;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	
}

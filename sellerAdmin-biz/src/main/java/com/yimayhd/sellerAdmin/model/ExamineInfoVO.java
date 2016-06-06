package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;

import com.yimayhd.membercenter.enums.MerchantType;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;

/***
 * 
 * @author zhangxy
 * 
 *
 */
public class ExamineInfoVO  implements Serializable  {
	
	private static final long serialVersionUID = 8113499074191458166L;
	protected Logger log = LoggerFactory.getLogger(getClass());
	private String province;
	private String city;
	 //商户名称
    private String sellerName;
    private int domainId;
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
    //旅行社授权书
    private String travelAgencyAuthorization;
    //旅行社责任险证明正面
    private String travelAgencyInsurance;

    // *******************负责人信息****************************//
    // 商户负责人姓名
    private String principleName;
    // 负责人证件类型名称
    private String principleCard;
    // 负责人证件号码
    private String principleCardId;
    // 负责人手机号码
    private String principleTel;
    // 负责人邮箱
    private String principleMail;
    // 负责人身份证正面
    private String principleCardUp;
    // 负责人身份证反面
    private String principleCardDown;

    // ******************财务信息*********************//

    // 财务开户银行ID
    private String financeOpenBankId;
    // 财务开户银行NAME
    private String financeOpenBankName;
    // 财务开户名称
    private String financeOpenName;
    // 财务结算账号
    private String accountNum;
    // 财务结算开户行省份
    private String accountBankProvince;
    // 财务结算开户行省份code
    private String accountBankProvinceCode;
    // 财务结算开户行城市
    private String accountBankCity;
    // 财务结算开户行城市CODE
    private String accountBankCityCode;
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

    // ********************达人资质证明****************************//
    // 导游证
    private String touristCard;
    // 行驶证
    private String drivingLinence;
    // 潜水证
    private String divingLinence;
    // 摄影证
    private String photographyCertificate;
    // 登山证
    private String climbingCertificate;
    // 健身教练证
    private String trainingCertificate;
    // 教师证
    private String teacherCertificate;
    // 美术证
    private String artCertificate;
    //申请时间
    private Date createDate;
    //手持身份证
    private String cardInHand;
    
	private byte isDirectSale;//是否直营
	private long merchantCategoryId;//商家身份
	
	
	public long getMerchantCategoryId() {
		return merchantCategoryId;
	}
	public void setMerchantCategoryId(long merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}
	public byte getIsDirectSale() {
		return isDirectSale;
	}
	public void setIsDirectSale(byte isDirectSale) {
		this.isDirectSale = isDirectSale;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	public String getPrincipleCard() {
		return principleCard;
	}
	public void setPrincipleCard(String principleCard) {
		this.principleCard = principleCard;
	}
	public String getPrincipleCardId() {
		return principleCardId;
	}
	public void setPrincipleCardId(String principleCardId) {
		this.principleCardId = principleCardId;
	}
	public String getPrincipleTel() {
		return principleTel;
	}
	public void setPrincipleTel(String principleTel) {
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
	public String getFinanceOpenBankId() {
		return financeOpenBankId;
	}
	public void setFinanceOpenBankId(String financeOpenBankId) {
		this.financeOpenBankId = financeOpenBankId;
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
	public String getAccountBankProvinceCode() {
		return accountBankProvinceCode;
	}
	public void setAccountBankProvinceCode(String accountBankProvinceCode) {
		this.accountBankProvinceCode = accountBankProvinceCode;
	}
	public String getAccountBankCity() {
		return accountBankCity;
	}
	public void setAccountBankCity(String accountBankCity) {
		this.accountBankCity = accountBankCity;
	}
	public String getAccountBankCityCode() {
		return accountBankCityCode;
	}
	public void setAccountBankCityCode(String accountBankCityCode) {
		this.accountBankCityCode = accountBankCityCode;
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
	public String getTouristCard() {
		return touristCard;
	}
	public void setTouristCard(String touristCard) {
		this.touristCard = touristCard;
	}
	public String getDrivingLinence() {
		return drivingLinence;
	}
	public void setDrivingLinence(String drivingLinence) {
		this.drivingLinence = drivingLinence;
	}
	public String getDivingLinence() {
		return divingLinence;
	}
	public void setDivingLinence(String divingLinence) {
		this.divingLinence = divingLinence;
	}
	public String getPhotographyCertificate() {
		return photographyCertificate;
	}
	public void setPhotographyCertificate(String photographyCertificate) {
		this.photographyCertificate = photographyCertificate;
	}
	public String getClimbingCertificate() {
		return climbingCertificate;
	}
	public void setClimbingCertificate(String climbingCertificate) {
		this.climbingCertificate = climbingCertificate;
	}
	public String getTrainingCertificate() {
		return trainingCertificate;
	}
	public void setTrainingCertificate(String trainingCertificate) {
		this.trainingCertificate = trainingCertificate;
	}
	public String getTeacherCertificate() {
		return teacherCertificate;
	}
	public void setTeacherCertificate(String teacherCertificate) {
		this.teacherCertificate = teacherCertificate;
	}
	public String getArtCertificate() {
		return artCertificate;
	}
	public void setArtCertificate(String artCertificate) {
		this.artCertificate = artCertificate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCardInHand() {
		return cardInHand;
	}
	public void setCardInHand(String cardInHand) {
		this.cardInHand = cardInHand;
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
	public  ExamineInfoDTO getExamineInfoDTO(ExamineInfoVO vo,long userId) throws Exception {
		if (vo == null || userId <= 0 ) {
			log.error("get examineSubmitDTO params error :vo="+vo+"userId="+userId);
			throw new BaseException("参数错误");
		}
//		if (!vo.getPrincipleName().equals(vo.getFinanceOpenName())) {
//			log.error("达人姓名与开户名称不相同");
//			throw new BaseException("商户姓名与开户名称需相同");
//		}
		ExamineInfoDTO dto=new ExamineInfoDTO();
		//BeanUtils.copyProperties(dto, vo);
		dto.setAccountBankName(vo.getAccountBankName());
		dto.setAccountNum(vo.getAccountNum());
		dto.setAddress(vo.getAddress());
		dto.setAffairsCard(vo.getAffairsCard());
		dto.setArtCertificate(vo.getArtCertificate());
		dto.setBusinessLicense(vo.getBusinessLicense());
		dto.setCardInHand(vo.getCardInHand());
		dto.setClimbingCertificate(vo.getClimbingCertificate());
		dto.setCooperation1(vo.getCooperation1());
		dto.setCooperation2(vo.getCooperation2());
		dto.setCooperation3(vo.getCooperation3());
		dto.setCooperation4(vo.getCooperation4());
		dto.setCooperation5(vo.getCooperation5());
		dto.setDivingLinence(vo.getDivingLinence());
		dto.setDrivingLinence(vo.getDrivingLinence());
		dto.setFinanceOpenBankId(vo.getFinanceOpenBankId());
		dto.setFinanceOpenName(vo.getFinanceOpenName());
		dto.setLegralCardDown(vo.getLegralCardDown());
		dto.setLegralCardUp(vo.getLegralCardUp());
		dto.setLegralName(vo.getLegralName());
		dto.setOpenCard(vo.getOpenCard());
		dto.setPhotographyCertificate(vo.getPhotographyCertificate());
		dto.setOrgCard(vo.getOrgCard());
		dto.setPrincipleCard(vo.getPrincipleCard());
		dto.setSaleScope(vo.getSaleScope());
		dto.setSellerName(vo.getSellerName());
		dto.setTravingCard(vo.getTravingCard());
		dto.setPrincipleCardId(vo.getPrincipleCardId());
		dto.setPrincipleMail(vo.getPrincipleMail());
		dto.setPrincipleTel(vo.getPrincipleTel());
		dto.setTeacherCertificate(vo.getTeacherCertificate());
		dto.setTouchProve(vo.getTouchProve());
		dto.setTouristCard(vo.getTouristCard());
		dto.setTrainingCertificate(vo.getTrainingCertificate());
		dto.setPrincipleName(vo.getPrincipleName());
		dto.setPrincipleCardUp(vo.getPrincipleCardUp());
		dto.setPrincipleCardDown(vo.getPrincipleCardDown());
		dto.setDomainId(Constant.DOMAIN_JIUXIU);
		dto.setSellerId(userId);
		dto.setType(MerchantType.TALENT.getType());
		dto.setAccountBankProvinceCode(vo.getProvince());
		dto.setAccountBankCityCode(vo.getCity());
		//dto.setMerchantCategoryId(vo.getMerchantCategoryId());
		//dto.setIsDirectSale(vo.getIsDirectSale());
		return dto;
		
	}

	public InfoQueryDTO getInfoQueryDTO (long userId) {
		if (userId <= 0  ) {
			log.error("get infoQueryDTO error and params:userId="+userId);
			throw new BaseException("参数错误");
		}
		InfoQueryDTO infoQueryDTO = new InfoQueryDTO();
		infoQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		infoQueryDTO.setSellerId(userId);
		infoQueryDTO.setType(MerchantType.TALENT.getType());
		return infoQueryDTO;
		
	}
}

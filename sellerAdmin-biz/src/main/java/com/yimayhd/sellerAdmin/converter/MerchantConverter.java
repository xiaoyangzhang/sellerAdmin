package com.yimayhd.sellerAdmin.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.ExamineInfoVO;



/**
 * 
* @ClassName: MerchantConverter
* @Description: 商家入驻相关对象的类型转换
* @author zhangxy
* @date 2016年5月26日 下午8:42:34
*
 */
public class MerchantConverter {
	protected final static Logger log = LoggerFactory.getLogger(MerchantConverter.class);
	public static  ExamineInfoDTO convertVO2DTO(ExamineInfoVO vo,long userId)  {
		if (vo == null || userId <= 0 ) {
			log.error("get examineSubmitDTO params error :vo="+vo+"userId="+userId);
			throw new BaseException("参数错误");
		}

		ExamineInfoDTO dto=new ExamineInfoDTO();
		dto.setAccountBankName(vo.getAccountBankName());
		dto.setAccountNum(vo.getAccountNum());
		dto.setAddress(vo.getAddress());
		dto.setAffairsCard(vo.getAffairsCard());
		dto.setArtCertificate(vo.getArtCertificate());
		dto.setBusinessLicense(vo.getBusinessLicense());
		dto.setCardInHand(vo.getCardInHand());
		dto.setClimbingCertificate(vo.getClimbingCertificate());
//		dto.setCooperation1(vo.getCooperation1());
//		dto.setCooperation2(vo.getCooperation2());
//		dto.setCooperation3(vo.getCooperation3());
//		dto.setCooperation4(vo.getCooperation4());
//		dto.setCooperation5(vo.getCooperation5());
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
		//dto.setType(ExamineType.TALENT.getType());
		dto.setAccountBankProvinceCode(vo.getProvince());
		dto.setAccountBankCityCode(vo.getCity());
		dto.setMerchantCategoryId(vo.getMerchantCategoryId());
		dto.setIsDirectSale(vo.getIsDirectSale());
//		dto.setAmusementParkReport(vo.getAmusementParkReport());
//		dto.setWildlifeSale(vo.getWildlifeSale());
//		dto.setWaterWildlifeSale(vo.getWaterWildlifeSale());
//		dto.setSpecialSaleLicense(vo.getSpecialSaleLicense());
//		dto.setSpecialSaleAuthorization(vo.getSpecialSaleAuthorization());
//		dto.setSeaTransportationLicense(vo.getSeaTransportationLicense());
//		dto.setScenicTicketUpScanning(vo.getScenicTicketUpScanning());
//		dto.setScenicTicketDownScanning(vo.getScenicTicketDownScanning());
//		dto.setScenicQualityLevel(vo.getScenicQualityLevel());
//		dto.setScenicPriceRegister(vo.getScenicPriceRegister());
//		dto.setScenicGoodsAuthorization(vo.getScenicGoodsAuthorization());
//		dto.setRelationBetweenHotelAngGroup(vo.getRelationBetweenHotelAngGroup());
//		dto.setHotelGoodsAuthorization(vo.getHotelGoodsAuthorization());
		dto.setMerchantQualifications(vo.getMerchantQualifications());
		dto.setMerchantScopes(vo.getMerchantScopes());
		return dto;
		
	}
 
	public static ExamineInfoVO convertDTO2VO(ExamineInfoDTO examineInfoDTO) {
		ExamineInfoVO vo = new ExamineInfoVO();
		vo.setAccountBankName(examineInfoDTO.getAccountBankName());
		vo.setAccountNum(examineInfoDTO.getAccountNum());
		vo.setAddress(examineInfoDTO.getAddress());
		vo.setAffairsCard(examineInfoDTO.getAffairsCard());
		vo.setArtCertificate(examineInfoDTO.getArtCertificate());
		vo.setBusinessLicense(examineInfoDTO.getBusinessLicense());
		vo.setCardInHand(examineInfoDTO.getCardInHand());
		vo.setClimbingCertificate(examineInfoDTO.getClimbingCertificate());
		vo.setCooperation1(examineInfoDTO.getCooperation1());
		vo.setCooperation2(examineInfoDTO.getCooperation2());
		vo.setCooperation3(examineInfoDTO.getCooperation3());
		vo.setCooperation4(examineInfoDTO.getCooperation4());
		vo.setCooperation5(examineInfoDTO.getCooperation5());
		vo.setDivingLinence(examineInfoDTO.getDivingLinence());
		vo.setDrivingLinence(examineInfoDTO.getDrivingLinence());
		vo.setFinanceOpenBankId(examineInfoDTO.getFinanceOpenBankId());
		vo.setFinanceOpenName(examineInfoDTO.getFinanceOpenName());
		vo.setLegralCardDown(examineInfoDTO.getLegralCardDown());
		vo.setLegralCardUp(examineInfoDTO.getLegralCardUp());
		vo.setLegralName(examineInfoDTO.getLegralName());
		vo.setOpenCard(examineInfoDTO.getOpenCard());
		vo.setPhotographyCertificate(examineInfoDTO.getPhotographyCertificate());
		vo.setOrgCard(examineInfoDTO.getOrgCard());
		vo.setPrincipleCard(examineInfoDTO.getPrincipleCard());
		vo.setSaleScope(examineInfoDTO.getSaleScope());
		vo.setSellerName(examineInfoDTO.getSellerName());
		vo.setTravingCard(examineInfoDTO.getTravingCard());
		vo.setPrincipleCardId(examineInfoDTO.getPrincipleCardId());
		vo.setPrincipleMail(examineInfoDTO.getPrincipleMail());
		vo.setPrincipleTel(examineInfoDTO.getPrincipleTel());
		vo.setTeacherCertificate(examineInfoDTO.getTeacherCertificate());
		vo.setTouchProve(examineInfoDTO.getTouchProve());
		vo.setTouristCard(examineInfoDTO.getTouristCard());
		vo.setTrainingCertificate(examineInfoDTO.getTrainingCertificate());
		vo.setPrincipleName(examineInfoDTO.getPrincipleName());
		vo.setPrincipleCardUp(examineInfoDTO.getPrincipleCardUp());
		vo.setPrincipleCardDown(examineInfoDTO.getPrincipleCardDown());
		vo.setDomainId(Constant.DOMAIN_JIUXIU);
		//vo.setSellerId(examineInfoDTO.getSellerId());
		//vo.setType(ExamineType.TALENT.getType());
		vo.setProvince(examineInfoDTO.getAccountBankProvinceCode());
		vo.setCity(examineInfoDTO.getAccountBankCityCode());
		vo.setMerchantCategoryId(examineInfoDTO.getMerchantCategoryId());
		vo.setIsDirectSale(examineInfoDTO.getIsDirectSale());
//		vo.setMerchantQualifications(examineInfoDTO.getMerchantQualifications());
//		vo.setMerchantScopes(examineInfoDTO.getMerchantScopes());
//		dto.setAmusementParkReport(vo.getAmusementParkReport());
//		dto.setWildlifeSale(vo.getWildlifeSale());
//		dto.setWaterWildlifeSale(vo.getWaterWildlifeSale());
//		dto.setSpecialSaleLicense(vo.getSpecialSaleLicense());
//		dto.setSpecialSaleAuthorization(vo.getSpecialSaleAuthorization());
//		dto.setSeaTransportationLicense(vo.getSeaTransportationLicense());
//		dto.setScenicTicketUpScanning(vo.getScenicTicketUpScanning());
//		dto.setScenicTicketDownScanning(vo.getScenicTicketDownScanning());
//		dto.setScenicQualityLevel(vo.getScenicQualityLevel());
//		dto.setScenicPriceRegister(vo.getScenicPriceRegister());
//		dto.setScenicGoodsAuthorization(vo.getScenicGoodsAuthorization());
//		dto.setRelationBetweenHotelAngGroup(vo.getRelationBetweenHotelAngGroup());
//		dto.setHotelGoodsAuthorization(vo.getHotelGoodsAuthorization());
//		vo.setMerchantQualifications(examineInfoDTO.getMerchantQualifications());
//		vo.setMerchantScopes(examineInfoDTO.getMerchantScopes());
		return vo;
	}
}

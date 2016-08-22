package com.yimayhd.sellerAdmin.service.api;

import net.pocrd.dubboext.DubboExtProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.api.MerchantInfoApi;
import org.yimayhd.sellerAdmin.entity.merchant.HomePage;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantDesc;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantInfo;
import org.yimayhd.sellerAdmin.entity.merchant.Qualification;

import com.yimayhd.membercenter.client.query.MerchantQueryDTO;
import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.MerchantVO;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;

public class MerchantInfoApiImpl implements MerchantInfoApi {

	private static Logger log  = LoggerFactory.getLogger("MerchantInfoApiImpl");
	@Autowired
	private MerchantBiz merchantBiz;
	@Override
	public HomePage queryHomePage(int appId, int domainId, long deviceId,
			long userId, int versionCode) {
		boolean chkResult = chkParam(domainId, userId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(userId);
			WebResult<MerchantDO> queryResult = merchantBiz.queryMerchantBySellerId(merchantVO);
			if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_ERROR);
				return null;
			}
			MerchantDO merchantDO = queryResult.getValue();
			HomePage homePage = new HomePage();
			homePage.avater = merchantDO.getBackgroudImage();
			homePage.merchantName = merchantDO.getName();
			return homePage;
		} catch (Exception e) {
			log.error("error:{}",e);
			return null;
		}
	}

	@Override
	public MerchantInfo queryMerchantInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode) {
		boolean chkResult = chkParam(domainId, userId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(userId);
			WebResult<MerchantDO> queryResult = merchantBiz.queryMerchantBySellerId(merchantVO);
			if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_ERROR);
				return null;
			}
			MerchantDO merchantDO = queryResult.getValue();
			MerchantInfo merchantInfo = new MerchantInfo();
			HomePage homePage = new HomePage();
			homePage.avater = merchantDO.getBackgroudImage();
			homePage.merchantName = merchantDO.getName();
			merchantInfo.homePage = homePage;
			merchantInfo.serviceTel = merchantDO.getServiceTel();
			return merchantInfo;
		} catch (Exception e) {
			log.error("error:{}",e);
			return null;
		}
	}

	@Override
	public MerchantDesc queryMerchantDesc(int appId, int domainId,
			long deviceId, long userId, int versionCode) {
		
		boolean chkResult = chkParam(domainId, userId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(userId);
			WebResult<MerchantDO> queryResult = merchantBiz.queryMerchantBySellerId(merchantVO);
			if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_ERROR);
				return null;
			}
			MerchantDO merchantDO = queryResult.getValue();
			MerchantDesc merchantDesc = new MerchantDesc();
			merchantDesc.shopDesc = merchantDO.getTitle();
			return merchantDesc;
		} catch (Exception e) {
			log.error("error:{}",e);
			return null;
		}
	}

	@Override
	public Qualification queryMerchantQualification(int appId, int domainId,
			long deviceId, long userId, int versionCode) {
		boolean chkResult = chkParam(domainId, userId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			QualificationQueryDTO queryDTO = new QualificationQueryDTO();
			queryDTO.setDomainId(domainId);
			queryDTO.setSellerId(userId);
			queryDTO.setQualificationId(Constant.SALE_LICENSE);
			WebResult<Qualification> queryMerchantQualification = merchantBiz.queryMerchantQualification(queryDTO);
			if (queryMerchantQualification == null || !queryMerchantQualification.isSuccess() || queryMerchantQualification.getValue() == null) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_QUALIFICATION_ERROR);
				return null;
			}
			return queryMerchantQualification.getValue();
		} catch (Exception e) {
			log.error("error:{}",e);
			return null;
		}
	}

	private boolean chkParam(int domainId,long userId) {
		if (domainId <= 0 || userId <= 0) {
			return false;
		}
		return true;
	}

}

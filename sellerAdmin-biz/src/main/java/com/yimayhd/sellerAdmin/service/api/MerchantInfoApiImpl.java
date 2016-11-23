package com.yimayhd.sellerAdmin.service.api;

import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.MerchantVO;
import com.yimayhd.user.client.domain.MerchantDO;
import net.pocrd.dubboext.DubboExtProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.api.MerchantInfoApi;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantDesc;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantInfo;
import org.yimayhd.sellerAdmin.entity.merchant.Qualification;

public class MerchantInfoApiImpl implements MerchantInfoApi {

	private static Logger log  = LoggerFactory.getLogger("MerchantInfoApiImpl");
	@Autowired
	private MerchantBiz merchantBiz;
	/*@Override
	public HomePage queryHomePage(int appId, int domainId, long deviceId,
			long userId, int versionCode,long sellerId) {
		boolean chkResult = chkParam(domainId, sellerId,userId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(sellerId);
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
	}*/

	//FIXME 对外暴露方法入口需要加日志, 举个例子吧：发现某一天的数据不对，如果让你找原因，你怎么找到问题？
	@Override 
	public MerchantInfo queryMerchantInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,long sellerId) {
		boolean chkResult = chkParam(domainId, sellerId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(sellerId);
			WebResult<MerchantDO> queryResult = merchantBiz.queryMerchantBySellerId(merchantVO);
			if (queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_ERROR);
				return null;
			}
			MerchantDO merchantDO = queryResult.getValue();
			MerchantInfo merchantInfo = new MerchantInfo();
//			HomePage homePage = new HomePage();
//			homePage.avater = merchantDO.getBackgroudImage();
//			homePage.merchantName = merchantDO.getName();
			//merchantInfo.homePage = homePage;
			merchantInfo.serviceTel = merchantDO.getServiceTel();
			return merchantInfo;
		} catch (Exception e) {
			//FIXME 输出重要参数信息
			log.error("error:{}",e);
			return null;
		}
	}

	@Override
	public MerchantDesc queryMerchantDesc(int appId, int domainId,
			long deviceId, long userId, int versionCode,long sellerId) {
		
		boolean chkResult = chkParam(domainId, sellerId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setDomainId(domainId);
			merchantVO.setUserId(sellerId);
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
			long deviceId, long userId, int versionCode,long sellerId) {
		boolean chkResult = chkParam(domainId, sellerId);
		if (!chkResult) {
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			QualificationQueryDTO queryDTO = new QualificationQueryDTO();
			queryDTO.setDomainId(domainId);
			queryDTO.setSellerId(sellerId);
			//TODO 硬编码
			queryDTO.setQualificationId(Constant.SALE_LICENSE);
			WebResult<Qualification> queryMerchantQualification = merchantBiz.queryMerchantQualification(queryDTO);
			if (queryMerchantQualification == null || !queryMerchantQualification.isSuccess() ) {
				DubboExtProperty.setErrorCode(SellerReturnCode.QUERY_MERCHANT_QUALIFICATION_ERROR);
				return null;
			}
			return queryMerchantQualification.getValue();
		} catch (Exception e) {
			log.error("error:{}",e);
			return null;
		}
	}

	private boolean chkParam(int domainId,long sellerId) {
		if (domainId <= 0 || sellerId <= 0 ) {
			return false;
		}
		return true;
	}

}

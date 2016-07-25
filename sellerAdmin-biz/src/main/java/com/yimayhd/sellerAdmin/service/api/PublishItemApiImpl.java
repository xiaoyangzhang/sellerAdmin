package com.yimayhd.sellerAdmin.service.api;

import net.pocrd.dubboext.DubboExtProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.PublishItemBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;

public class PublishItemApiImpl implements PublishItemApi  {

	private static Logger log = LoggerFactory.getLogger("PublishItemApiImpl");
	@Autowired
	private PublishItemBiz publishItemBiz;
	@Override
	public boolean publishService(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			PublishServiceDO publishServiceDO) {
		return true;
	}

	@Override
	public ItemApiResult getGoodsManagementInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			ItemListPage itemListPage) {
		return null;
	}

	@Override
	public ItemApiResult getGoodsDetailInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode, ItemDetail goodsDetail) {
		return null;
	}

	@Override
	public boolean checkWhiteList(int appId, int domainId, long deviceId,
			long userId, int versionCode) {
		if (userId <= 0 || domainId <= 0) {
			log.error("params:userId={},domainId={}",userId,domainId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}
		ItemCategoryQuery query = new ItemCategoryQuery();
		query.setDomainId(domainId);
		query.setSellerId(userId);
		WebResult<Boolean> checkResult = publishItemBiz.checkWhiteList(query);
		if (checkResult == null || !checkResult.isSuccess()) {
			
			return false;
		}
		return true;
	}

	@Override
	public boolean updateState(int appId, int domainId, long deviceId,
			long userId, int versionCode, ItemManagement goodsManagement) {
		return false;
	}

	@Override
	public PublishServiceDO getPublishItemInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			PublishServiceDO publishService) {
		if (userId <= 0 || publishService == null || publishService.id <= 0) {
			log.error("params:userId={},domainId={}",userId,domainId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		return null;
	}

}

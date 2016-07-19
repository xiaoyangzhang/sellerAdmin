package com.yimayhd.sellerAdmin.service.api;

import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.GoodsDetail;
import org.yimayhd.sellerAdmin.entity.GoodsManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.result.GoodsApiResult;

public class PublishItemApiImpl implements PublishItemApi  {

	@Override
	public Boolean publishService(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			PublishServiceDO publishServiceDO) {
		return true;
	}

	@Override
	public GoodsApiResult getGoodsManagementInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			GoodsManagement goodsManagement) {
		return null;
	}

	@Override
	public GoodsApiResult getGoodsDetailInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode, GoodsDetail goodsDetail) {
		return null;
	}

	@Override
	public Boolean checkWhiteList(int appId, int domainId, long deviceId,
			long userId, int versionCode) {
		return false;
	}

}

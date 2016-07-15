package com.yimayhd.sellerAdmin.service.api;

import org.yimayhd.sellerAdmin.api.PublishGoodsApi;
import org.yimayhd.sellerAdmin.entity.GoodsDetail;
import org.yimayhd.sellerAdmin.entity.GoodsManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.result.GoodsApiResult;

public class PublishGoodsApiImpl implements PublishGoodsApi {

	@Override
	public GoodsApiResult publishService(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			PublishServiceDO publishServiceDO) {
		return null;
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

}

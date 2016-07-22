package com.yimayhd.sellerAdmin.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.yimayhd.sellerAdmin.biz.PublishItemBiz;

public class PublishItemApiImpl implements PublishItemApi  {

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
			ItemManagement goodsManagement) {
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
		return false;
	}

	@Override
	public boolean updateState(int appId, int domainId, long deviceId,
			long userId, int versionCode, ItemManagement goodsManagement) {
		return false;
	}

}

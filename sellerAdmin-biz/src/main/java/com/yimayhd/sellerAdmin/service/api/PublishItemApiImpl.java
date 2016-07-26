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

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
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
		if (userId <= 0 || publishServiceDO == null) {
			log.error("params:userId={},PublishServiceDO={}",userId,JSON.toJSONString(publishServiceDO));
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}
		WebResult<Boolean> result = null;
		if (publishServiceDO.id <= 0) {
			
			result = publishItemBiz.addItem(publishServiceDO);
		}else if (publishServiceDO.id > 0) {
			result = publishItemBiz.updateItem(publishServiceDO);
		}
		if (result == null ) {
			log.error("params:PublishServiceDO={},result:{}",JSON.toJSONString(publishServiceDO),result);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}else if (!result.isSuccess()) {
			log.error("params:PublishServiceDO={},result:{}",JSON.toJSONString(publishServiceDO),JSON.toJSONString(result));
			DubboExtProperty.setErrorCode(SellerReturnCode.ADD_ITEM_ERROR);
			return false;
		}
		return true;
	}

	@Override
	public ItemApiResult getGoodsManagementInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			ItemListPage itemListPage) {
		if (userId <= 0 || itemListPage == null || itemListPage.pageNo <= 0 || itemListPage.pageSize <= 0) {
			log.error("params:userId={},itemListPage={}",userId,JSON.toJSONString(itemListPage));
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		ItemApiResult itemApiResult = publishItemBiz.getItemList(itemListPage);
		if (itemApiResult == null ) {
			log.error("params:ItemListPage={},result:{}",JSON.toJSONString(itemListPage),itemApiResult);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		return itemApiResult;
	}

	@Override
	public ItemApiResult getGoodsDetailInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode, ItemDetail goodsDetail) {
		return null;
	}

	@Override
	public boolean checkWhiteList(int appId, int domainId, long deviceId,
			long userId, int versionCode) {
		log.info("--------------------------------------------------");
		if (userId <= 0 || domainId <= 0) {
			log.error("params:userId={},domainId={}",userId,domainId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}
		ItemCategoryQuery query = new ItemCategoryQuery();
		query.setDomainId(domainId);
		query.setSellerId(userId);
		WebResult<Boolean> checkResult = publishItemBiz.checkWhiteList(query);
		log.info("--------------------------------------------------"+JSON.toJSONString(checkResult));
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
			long deviceId, long userId, int versionCode,long itemId,long categoryId) {
		if (userId <= 0 || itemId <= 0 || categoryId <= 0) {
			log.error("params:userId={},domainId={},itemId={},categoryId={}",userId,domainId,itemId,categoryId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		return null;
	}

}

package com.yimayhd.sellerAdmin.service.api;

import java.util.List;

import net.pocrd.dubboext.DubboExtProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.ConsultCategoryInfo;
import org.yimayhd.sellerAdmin.entity.ItemProperty;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.query.ItemQueryParam;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.PublishItemBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;

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
		try {
			if (publishServiceDO.id <= 0) {
				
				result = publishItemBiz.addItem(publishServiceDO,userId);
			}else if (publishServiceDO.id > 0) {
				result = publishItemBiz.updateItem(publishServiceDO,userId);
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
		} catch (Exception e) {
			log.error("param:PublishServiceDO={},error:{}",JSON.toJSONString(publishServiceDO),e);
			return false;
		}
		return true;
	}

	@Override
	public ItemApiResult getItemList(int appId, int domainId,
			long deviceId, long userId, int versionCode,
			 ItemQueryParam itemQueryParam) {
		//log.info("ItemApiResult.getItemList============================");

		if (userId <= 0 || itemQueryParam == null || itemQueryParam.pageNo <= 0 || itemQueryParam.pageSize <= 0) {
			log.error("params:userId={},itemQueryParam={}",userId,JSON.toJSONString(itemQueryParam));
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			ItemCategoryQuery itemCategoryQuery = new ItemCategoryQuery(); 
			itemCategoryQuery.setSellerId(userId);
			itemCategoryQuery.setItemQueryParam(itemQueryParam);
			itemCategoryQuery.setCategoryId(Constant.CONSULT_SERVICE);
			itemCategoryQuery.setDomainId(domainId);
			ItemApiResult itemApiResult = publishItemBiz.getItemList(itemCategoryQuery);
			if (itemApiResult == null ) {
				log.error("params:ItemQueryParam={},result:{}",JSON.toJSONString(itemQueryParam),itemApiResult);
				DubboExtProperty.setErrorCode(SellerReturnCode.SYSTEM_ERROR);
				return null;
			}
			if (CollectionUtils.isEmpty(itemApiResult.itemManagements)) {
				return null;
			}
			return itemApiResult;
		} catch (Exception e) {
			log.error("param:ItemQueryParam={},error:{}",JSON.toJSONString(itemQueryParam),e);
			return null;
		}
	}

	@Override
	public ItemApiResult getItemDetailInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode, ItemQueryParam itemQueryParam) {
		if (userId <= 0 || itemQueryParam == null || itemQueryParam.id <= 0) {
			log.error("params:userId={},ItemQueryParam={}",userId,JSON.toJSONString(itemQueryParam));
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			ItemCategoryQuery query = new ItemCategoryQuery();
			query.setSellerId(userId);
			query.setItemId(itemQueryParam.id);
			ItemApiResult itemApiResult = publishItemBiz.getItemDetail(query);
			if (itemApiResult == null ) {
				log.error("params:userId={},ItemQueryParam={},result:{}",userId,JSON.toJSONString(itemQueryParam),JSON.toJSONString(itemApiResult));
				DubboExtProperty.setErrorCode(SellerReturnCode.SYSTEM_ERROR);
				return null;
				
			}
			return itemApiResult;
		} catch (Exception e) {
			log.error("param:ItemQueryParam={},error:{}",JSON.toJSONString(itemQueryParam),e);
			return null;
		}
	}

	@Override
	public boolean checkWhiteList(int appId, int domainId, long deviceId,
			long userId, int versionCode) {
		//log.info("--------------------------------------------------");
		if (userId <= 0 || domainId <= 0) {
			log.error("params:userId={},domainId={}",userId,domainId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}
		try {
			ItemCategoryQuery query = new ItemCategoryQuery();
			query.setDomainId(domainId);
			query.setSellerId(userId);
			WebResult<Boolean> checkResult = publishItemBiz.checkWhiteList(query);
			//log.info("--------------------------------------------------"+JSON.toJSONString(checkResult));
			if (checkResult == null || !checkResult.isSuccess()) {
				
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error("error:{}",e);
			return false;
		}
	}

	@Override
	public boolean updateState(int appId, int domainId, long deviceId,
			long userId, int versionCode, ItemQueryParam itemQueryParam) {
		log.info("param:userId={},ItemQueryParam={}",userId,JSON.toJSONString(itemQueryParam));
		if (userId <= 0 || itemQueryParam == null || itemQueryParam.id <= 0 || itemQueryParam.state <2) {
			log.error("params:userId={},ItemQueryParam={}",userId,JSON.toJSONString(itemQueryParam));
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return false;
		}
		try {
			ItemQueryDTO dto = new ItemQueryDTO();
			dto.setDomainId(domainId);
			dto.setItemId(itemQueryParam.id);
			dto.setSellerId(userId);
			dto.setState(itemQueryParam.state);
			log.info("param:ItemQueryDTO={}",JSON.toJSONString(dto));
			WebResult<Boolean> updateStateResult = publishItemBiz.updateItemState(dto);
			if (updateStateResult == null || !updateStateResult.isSuccess()) {
				log.error("param:ItemQueryParam={},ItemQueryDTO={},result:{}",JSON.toJSONString(itemQueryParam),JSON.toJSONString(dto),JSON.toJSONString(updateStateResult));
				DubboExtProperty.setErrorCode(SellerReturnCode.UPDATE_ITEM_ERROR);
				return false;
			}
			log.info("result:{}",JSON.toJSONString(updateStateResult));
			
			return true;
		} catch (Exception e) {
			log.error("param:ItemQueryParam={},error:{}",JSON.toJSONString(itemQueryParam),e);
			return false;
		}
	}

	@Override
	public PublishServiceDO getPublishItemInfo(int appId, int domainId,
			long deviceId, long userId, int versionCode,/*long itemId,long categoryId*/ItemQueryParam itemQueryParam) {
		
		//log.error("----------------------------------------------------------------------------------------");
		log.info("param:ItemQueryParam={}",JSON.toJSONString(itemQueryParam));
		if (userId <= 0 || itemQueryParam == null || itemQueryParam.id <= 0 || itemQueryParam.categoryId <= 0) {
			log.error("params:userId={},domainId={},itemId={},categoryId={}",userId,domainId,itemQueryParam.id,itemQueryParam.categoryId);
			DubboExtProperty.setErrorCode(SellerReturnCode.PRAM_ERROR);
			return null;
		}
		try {
			ItemCategoryQuery query = new ItemCategoryQuery();
			query.setDomainId(domainId);
			query.setItemId(itemQueryParam.id);
			query.setSellerId(userId);
			log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
			ItemApiResult itemApiResult = publishItemBiz.getPublishItemById(query);
			log.info("result:ItemApiResult={}",JSON.toJSONString(itemApiResult));
			if (itemApiResult == null ) {
				log.error("params:userId={},ItemQueryParam={},result:{}",userId,JSON.toJSONString(itemQueryParam),JSON.toJSONString(itemApiResult));
				return null;
				
			}
			return itemApiResult.itemDetail.itemManagement.publishServiceDO;
		} catch (Exception e) {
			log.error("param:ItemQueryParam={},error:{}",JSON.toJSONString(itemQueryParam),e);
			return null;
		}
	}

	@Override
	public ConsultCategoryInfo getConsultItemProperties(int appId, int domainId,
			long deviceId, long userId, int versionCode) {
		
		return null;
	}

}

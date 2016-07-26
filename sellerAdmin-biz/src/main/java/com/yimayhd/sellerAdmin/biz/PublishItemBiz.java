package com.yimayhd.sellerAdmin.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.PublishItemConverter;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;
import com.yimayhd.sellerAdmin.repo.PublishItemRepo;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
* @ClassName: PublishItemBiz
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年7月20日 下午3:05:45
*
 */
public class PublishItemBiz {

	private static Logger log = LoggerFactory.getLogger("PublishItemBiz");
	
	@Autowired
	private PublishItemRepo publishItemRepo;
	@Autowired
	private SessionManager sessionManager;
	
	public WebResult<Boolean> addItem(PublishServiceDO publishServiceDO) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			ConsultPublishAddDTO dto = PublishItemConverter.converterLocal2AddPublishConsult(publishServiceDO, sessionManager.getUserId());
			ItemPubResult addItemResult = publishItemRepo.addItem(dto);
			if (addItemResult == null) {
				log.error("result:ItemPubResult={}",addItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!addItemResult.isSuccess() || addItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
			}
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	public WebResult<Boolean> updateItem(PublishServiceDO publishServiceDO) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			ConsultPublishUpdateDTO dto = PublishItemConverter.converterLocal2UpdatePublishConsult(publishServiceDO, sessionManager.getUserId());
			ItemPubResult updateItemResult = publishItemRepo.updateItem(dto);
			if (updateItemResult == null) {
				log.error("result:ItemPubResult={}",updateItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!updateItemResult.isSuccess() || updateItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
			}
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public ItemApiResult getItemList(ItemListPage listPage) {
		//log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		ItemApiResult result = new ItemApiResult();
		return result;
	}
	
	public WebResult<Boolean> updateItemState(ItemQueryDTO dto) {
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (dto == null || dto.getDomainId() <= 0 || dto.getItemId() <= 0 ) {
			log.error("params:ItemQueryDTO={}",JSON.toJSONString(dto));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		dto.setSellerId(sessionManager.getUserId());
		ItemPublishDTO itemPublishDTO = PublishItemConverter.converterLocal2ItemPublishDTO(dto);
		
		try {
			if (dto.getState() == ItemStatus.valid.getValue()) {
				ItemPubResult pubResult = publishItemRepo.publishItem(itemPublishDTO);
				if (pubResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),pubResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!pubResult.isSuccess() || pubResult.getItemId() <= 0) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(pubResult));
					result.setWebReturnCode(WebReturnCode.ON_SALE_ERROR);
					return result;
				}
				return result;
			}else if (dto.getState() == ItemStatus.invalid.getValue()) {
				ItemCloseResult closeResult = publishItemRepo.closeItem(itemPublishDTO);
				if (closeResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),closeResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!closeResult.isSuccess() ) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(closeResult));
					result.setWebReturnCode(WebReturnCode.OFF_SALE_ERROR);
					return result;
				}
				return result;
			}else if (dto.getState() == ItemStatus.deleted.getValue()) {
				ItemDeleteResult deleteResult = publishItemRepo.deleteItem(itemPublishDTO);
				if (deleteResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),deleteResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!deleteResult.isSuccess() ) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(deleteResult));
					result.setWebReturnCode(WebReturnCode.DELETE_ERROR);
					return result;
				}
				return result;
			}
		} catch (Exception e) {
			log.error("param:ItemQueryDTO={},error:{}",JSON.toJSONString(dto),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	public WebResult<Boolean> checkWhiteList(ItemCategoryQuery query) {
		WebResult<Boolean> result = new WebResult<Boolean>();
		try {
			MemResult<List<MerchantItemCategoryDO>> itemCaetgoryListResult = publishItemRepo.getMerchantItemCaetgoryList(query);
			if (itemCaetgoryListResult == null) {
				log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),itemCaetgoryListResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!itemCaetgoryListResult.isSuccess() || CollectionUtils.isEmpty(itemCaetgoryListResult.getValue())) {
				log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),JSON.toJSONString(itemCaetgoryListResult));
				result.setWebReturnCode(WebReturnCode.QUERY_FAILURE);
				return result;
			}
			int count = 0;
			for (MerchantItemCategoryDO item : itemCaetgoryListResult.getValue()) {
				if (item.getItemCategoryId() == Constant.CONSULT_SERVICE) {
					count ++ ;
				}
			}
			if (count > 0) {
				return result;
			}else {
				result.setWebReturnCode(WebReturnCode.NO_AUTHORITY);
				return result;
			}
		} catch (Exception e) {
			log.error("param:ItemCategoryQuery={},error:{}",JSON.toJSONString(query),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public PublishServiceDO getPublishItemById(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
		PublishServiceDO publishService = new PublishServiceDO();
		SingleItemQueryResult queryResult = publishItemRepo.queryPublishItem(query);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getItemDO() == null) {
			log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),queryResult);
			return null;
		}
		publishService.avater = queryResult.getItemDO().getPicUrlsString();
		//publishService.discountPrice = queryResult.getItemDO().get
		return publishService;
	}
}

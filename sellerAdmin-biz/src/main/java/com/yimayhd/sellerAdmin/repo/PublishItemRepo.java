package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;

/**
 * 
* @ClassName: PublishItemRepo
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年7月20日 下午2:48:55
*
 */
public class PublishItemRepo {

	private static Logger log  = LoggerFactory.getLogger("PublishItemRepo");
	@Autowired
	private ItemPublishService itemPublishService;
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private MerchantItemCategoryService merchantItemCategoryService;
	@Autowired
	private CategoryService categoryServiceRef;
	public ItemPubResult addItem(ConsultPublishAddDTO dto) {
		log.info("param:ConsultPublishAddDTO={}",JSON.toJSONString(dto));
		ItemPubResult addPublishConsult = itemPublishService.addPublishConsult(dto);
		if (addPublishConsult != null) {
			
			log.info("result:ItemPubResult={}",JSON.toJSONString(addPublishConsult));
		}
		return addPublishConsult;
		
	}
	
	public ItemPubResult updateItem(ConsultPublishUpdateDTO dto) {
		log.info("param:ConsultPublishUpdateDTO={}",JSON.toJSONString(dto));
		ItemPubResult updatePublishConsult = itemPublishService.updatePublishConsult(dto);
		if (updatePublishConsult != null) {
		log.info("result:ItemPubResult={}",JSON.toJSONString(updatePublishConsult));
		}
		return updatePublishConsult;
		
	}
	
	public ItemPageResult getItemList(ItemQryDTO dto) {
		log.info("param:ItemQryDTO={}",JSON.toJSONString(dto));
		ItemPageResult itemPageResult = itemQueryService.getItem(dto);
		if (itemPageResult != null) {
		log.info("result:ItemPageResult={}",JSON.toJSONString(itemPageResult));
		}
		return itemPageResult;
	}
	
	public ItemPubResult publishItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemPubResult publishResult = itemPublishService.publish(dto);
		if (publishResult != null) {
		log.info("param:ItemPubResult={}",JSON.toJSONString(publishResult));
		}
		return publishResult;
	}
	public ItemCloseResult closeItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemCloseResult closeResult = itemPublishService.close(dto);
		if (closeResult != null) {
			log.info("param:ItemCloseResult={}",JSON.toJSONString(closeResult));
		}
		return closeResult;
	}
	public ItemDeleteResult deleteItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemDeleteResult deleteResult = itemPublishService.delete(dto);
		if (deleteResult != null) {
			log.info("param:ItemDeleteResult={}",JSON.toJSONString(deleteResult));
		}
		return deleteResult;
	}
	
	public MemResult<List<MerchantItemCategoryDO>> getMerchantItemCaetgoryList(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));

		MemResult<List<MerchantItemCategoryDO>> queryResult = merchantItemCategoryService.findMerchantItemCategoriesBySellerId(query.getDomainId(), query.getSellerId());
		if (queryResult != null) {
			
			log.info("param:MemResult<List<MerchantItemCategoryDO>>={}",JSON.toJSONString(queryResult));
		}
		return queryResult;
	}
	
	public SingleItemQueryResult queryPublishItem(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
		SingleItemQueryResult itemResult = itemQueryService.querySingleItem(query.getItemId(), query.getItemOptionDTO(query.getSellerId()));
		if (itemResult != null) {
			log.info("param:SingleItemQueryResult={}",JSON.toJSONString(itemResult));
		}
		return itemResult;
	}
	
	public CategoryResult getItemProperties(long categoryId) {
		return categoryServiceRef.getCategory(categoryId);
	}
}

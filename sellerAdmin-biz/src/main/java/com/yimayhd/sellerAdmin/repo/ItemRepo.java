package com.yimayhd.sellerAdmin.repo;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

public class ItemRepo {
	private Logger				log	= LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService	itemQueryServiceRef;
	@Autowired
	private ItemPublishService	itemPublishServiceRef;
	@Autowired
	private ItemBizQueryService itemBizQueryServiceRef;
	@Autowired
    private MerchantItemCategoryService merchantItemCategoryService;

/*	public ItemPageResult getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		itemQryDTO.setDomains(Arrays.asList(Constant.DOMAIN_JIUXIU));
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItem", itemQryDTO);
		ItemPageResult itemPageResult = itemQueryServiceRef.getItem(itemQryDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItem", itemPageResult);
		return itemPageResult;
	}*/
	public ICPageResult<ItemInfo> getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		itemQryDTO.setDomains(Arrays.asList(Constant.DOMAIN_JIUXIU));
		RepoUtils.requestLog(log, "itemBizQueryServiceRef.getItem", itemQryDTO);
		ICPageResult<ItemInfo> pageResult = itemBizQueryServiceRef.getItem(itemQryDTO);
		//RepoUtils.resultLog(log, "itemBizQueryServiceRef.getItem", pageResult);
		log.info("itemBizQueryServiceRef.getItem--:"+ JSON.toJSONString(pageResult.getList()));
		return pageResult;
	}
	public ItemResult getItemDetail(long sellerId, long itemId) {
		ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		// 全部设置成true
		itemOptionDTO.setCreditFade(true);
		itemOptionDTO.setNeedCategory(true);
		itemOptionDTO.setNeedSku(true);
		itemOptionDTO.setUserId(sellerId);
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItem", itemOptionDTO);
		ItemResult itemResult = itemQueryServiceRef.getItem(itemId, itemOptionDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItem", itemResult);
		ItemDO item = itemResult.getItem();
		return item.getSellerId() == sellerId ? itemResult : null;
	}

	public void shelve(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.shelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.publish", itemPublishDTO);
		ItemPubResult itemPubResult = itemPublishServiceRef.publish(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.publish", itemPubResult);
	}

	public void unshelve(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.unshelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.close", itemPublishDTO);
		ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.close", itemCloseResult);
	}

	public void delete(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.delete(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.delete", itemPublishDTO);
		ItemDeleteResult delete = itemPublishServiceRef.delete(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.delete", delete);
	}

	public void batchShelve(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchShelve(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchPublish", itemBatchPublishDTO);
		ItemPubResult batchPublish = itemPublishServiceRef.batchPublish(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchPublish", batchPublish);
	}

	public void batchUnshelve(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchUnshelve(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchClose", itemBatchPublishDTO);
		ItemCloseResult batchClose = itemPublishServiceRef.batchClose(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchClose", batchClose);
	}

	public void batchDelete(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchDelete(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchClose", itemBatchPublishDTO);
		ItemDeleteResult batchDelete = itemPublishServiceRef.batchDelete(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchClose", batchDelete);
	}
	
	public MemResult<MerchantItemCategoryDO> getMerchantItemCategory(int domainId,long categoryId,long sellerId){
		return merchantItemCategoryService.selectObjByCategoryIdAndSellerId(domainId, categoryId, sellerId);
	}

}

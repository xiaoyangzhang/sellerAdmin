package com.yimayhd.sellerAdmin.repo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

public class ItemRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;

	public ItemPageResult getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		itemQryDTO.setDomains(Arrays.asList(Constant.DOMAIN_JIUXIU));
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItem", itemQryDTO);
		ItemPageResult itemPageResult = itemQueryServiceRef.getItem(itemQryDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItem", itemPageResult);
		return itemPageResult;
	}

	public void shelve(ItemPublishDTO itemPublishDTO) throws Exception {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.shelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.publish", itemPublishDTO);
		ItemPubResult itemPubResult = itemPublishServiceRef.publish(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.publish", itemPubResult);
	}

	public void unshelve(ItemPublishDTO itemPublishDTO) throws Exception {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.unshelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.close", itemPublishDTO);
		ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.close", itemCloseResult);
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

}

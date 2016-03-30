package com.yimayhd.sellerAdmin.repo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

public class ItemRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;

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
}

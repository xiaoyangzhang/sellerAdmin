package com.yimayhd.sellerAdmin.converter;

import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.util.ItemUtil;

/**
 * 商品转换器
 * 
 * @author yebin
 *
 */
public class ItemConverter {

	public static ItemQryDTO toItemQryDTO(long sellerId, ItemListQuery query) {
		if (sellerId <= 0 || query == null) {
			return null;
		}
		ItemQryDTO itemQryDTO = new ItemQryDTO();
		itemQryDTO.setName(query.getName());
		itemQryDTO.setId(query.getId());
		itemQryDTO.setStatus(Arrays.asList(query.getStatus()));
		itemQryDTO.setItemType(query.getItemType());
		itemQryDTO.setBeginDate(query.getBeginDate());
		itemQryDTO.setEndDate(query.getEndDate());
		return itemQryDTO;
	}

	public static ItemListItemVO toItemListItemVO(ItemDO itemDO) {
		if (itemDO == null) {
			return null;
		}
		ItemListItemVO itemListItemVO = new ItemListItemVO();
		itemListItemVO.setId(itemDO.getId());
		List<String> itemMainPics = PicUrlsUtil.getItemMainPics(itemDO);
		if (CollectionUtils.isNotEmpty(itemMainPics)) {
			itemListItemVO.setPicture(itemMainPics.get(0));
		}
		itemListItemVO.setName(itemDO.getTitle());
		itemListItemVO.setCode(itemDO.getCode());
		itemListItemVO.setPrice(itemDO.getPrice());
		itemListItemVO.setType(itemDO.getItemType());
		itemListItemVO.setStatus(itemDO.getStatus());
		itemListItemVO.setOperates(ItemUtil.getItemOperates(itemDO.getItemType(), itemDO.getStatus()));
		return itemListItemVO;
	}

}

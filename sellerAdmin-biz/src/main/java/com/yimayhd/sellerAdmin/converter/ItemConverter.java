package com.yimayhd.sellerAdmin.converter;

import java.util.Arrays;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.util.ItemUtils;

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
		itemListItemVO.setOperates(ItemUtils.getItemOperates(itemDO.getItemType(), itemDO.getStatus()));
		return itemListItemVO;
	}

}

package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.sellerAdmin.model.ItemSkuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author weiwenliang@20160406
 *
 */
public class ItemSkuConverter {
	public static ItemSkuVO convertVO(ItemSkuDO itemSkuDO) {
		if(itemSkuDO == null) {
			return null;
		}
		return ItemSkuVO.getItemSkuVO(itemSkuDO);
	}

	public static List<ItemSkuVO> convertVOList(List<ItemSkuDO> itemSkuDOList) {
		if(CollectionUtils.isEmpty(itemSkuDOList)) {
			return null;
		}
		List<ItemSkuVO> itemSkuVOList = new ArrayList<>();
		for(ItemSkuDO itemSkuDO : itemSkuDOList) {
			ItemSkuVO itemSkuVO = convertVO(itemSkuDO);
			if(itemSkuVO != null){
				itemSkuVOList.add(itemSkuVO);
			}
		}
		return itemSkuVOList;
	}

	public static ItemSkuPubUpdateDTO convertPubUpdateDTO(ItemSkuVO itemSkuVO) {
		if(itemSkuVO == null) {
			return null;
		}
		ItemSkuPubUpdateDTO itemSkuPubUpdateDTO = new ItemSkuPubUpdateDTO();
		BeanUtils.copyProperties(itemSkuVO, itemSkuPubUpdateDTO);
		return itemSkuPubUpdateDTO;
	}
}

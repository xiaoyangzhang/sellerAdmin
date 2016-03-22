package com.yimayhd.sellerAdmin.model.line.price;

import java.util.List;

import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfoDTO {
	private List<ItemSkuDO> addItemSkuList;
	private List<Long> delItemSkuList;
	private List<ItemSkuPubUpdateDTO> updItemSkuList;

	public List<ItemSkuDO> getAddItemSkuList() {
		return addItemSkuList;
	}

	public void setAddItemSkuList(List<ItemSkuDO> addItemSkuList) {
		this.addItemSkuList = addItemSkuList;
	}

	public List<Long> getDelItemSkuList() {
		return delItemSkuList;
	}

	public void setDelItemSkuList(List<Long> delItemSkuList) {
		this.delItemSkuList = delItemSkuList;
	}

	public List<ItemSkuPubUpdateDTO> getUpdItemSkuList() {
		return updItemSkuList;
	}

	public void setUpdItemSkuList(List<ItemSkuPubUpdateDTO> updItemSkuList) {
		this.updItemSkuList = updItemSkuList;
	}
}

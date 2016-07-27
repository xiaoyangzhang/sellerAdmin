package com.yimayhd.sellerAdmin.model.query;

import java.io.Serializable;

import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.query.ItemQueryParam;

import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;

public class ItemCategoryQuery implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1940636224765318842L;

	private int domainId;
	private long sellerId;
	private long itemId;
	private long categoryId;
	private ItemQueryParam itemQueryParam;
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public ItemQueryParam getItemQueryParam() {
		return itemQueryParam;
	}
	public void setItemQueryParam(ItemQueryParam itemQueryParam) {
		this.itemQueryParam = itemQueryParam;
	}
	private ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
	private ItemListPage itemListPage;
	
	public ItemListPage getItemListPage() {
		return itemListPage;
	}
	public void setItemListPage(ItemListPage itemListPage) {
		this.itemListPage = itemListPage;
	}
	public ItemOptionDTO getItemOptionDTO(long userId) {
		itemOptionDTO.setUserId(userId);
		return itemOptionDTO;
	}
//	public void setItemOptionDTO(long userId) {
//		itemOptionDTO.setUserId(userId);
		//this.itemOptionDTO = itemOptionDTO;
	//}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	
}

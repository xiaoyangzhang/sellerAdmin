package com.yimayhd.sellerAdmin.model.item;

import java.util.Date;
import java.util.List;

import com.yimayhd.sellerAdmin.model.enums.ItemOperate;
import com.yimayhd.sellerAdmin.model.line.CityVO;

/**
 * 商品列表
 * 
 * @author yebin
 *
 */
public class ItemListItemVO {
	private long id;
	private String picture;
	private String name;
	private String code;
	private List<CityVO> dests;
	private int itemType;
	private long price;
	private long status;
	private Date publishDate;
	private List<ItemOperate> operates;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<CityVO> getDests() {
		return dests;
	}

	public void setDests(List<CityVO> dests) {
		this.dests = dests;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<ItemOperate> getOperates() {
		return operates;
	}

	public void setOperates(List<ItemOperate> operates) {
		this.operates = operates;
	}
}

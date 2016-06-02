package com.yimayhd.sellerAdmin.model.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

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
	private int type;
	private long price;
	private int status;
	private Date publishDate;
	private List<String> operates;

	private ItemHotelVO itemHotelVO;//酒店vo
	private ItemScenicVO itemScenicVO;//景区vo
	private ItemRoomVO itemRoomVO;//酒店房间
	private ItemTicketVO itemTicketVO;//景区门票
	private String payMode;//支付方式
	private long categoryId;

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

	public String getDestString() {
		List<String> names = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(dests)) {
			for (CityVO cityVO : dests) {
				names.add(cityVO.getName());
			}
		}
		return StringUtils.join(names, "，");
	}

	public void setDests(List<CityVO> dests) {
		this.dests = dests;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<String> getOperates() {
		return operates;
	}

	public void setOperates(List<String> operates) {
		this.operates = operates;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean containsOperate(String code) {
		if (CollectionUtils.isEmpty(getOperates())) {
			return false;
		}
		return getOperates().contains(code.toUpperCase());
	}


	public ItemHotelVO getItemHotelVO() {
		return itemHotelVO;
	}

	public ItemListItemVO setItemHotelVO(ItemHotelVO itemHotelVO) {
		this.itemHotelVO = itemHotelVO;
		return this;
	}

	public ItemScenicVO getItemScenicVO() {
		return itemScenicVO;
	}

	public ItemListItemVO setItemScenicVO(ItemScenicVO itemScenicVO) {
		this.itemScenicVO = itemScenicVO;
		return this;
	}

	public ItemRoomVO getItemRoomVO() {
		return itemRoomVO;
	}

	public ItemListItemVO setItemRoomVO(ItemRoomVO itemRoomVO) {
		this.itemRoomVO = itemRoomVO;
		return this;
	}

	public ItemTicketVO getItemTicketVO() {
		return itemTicketVO;
	}

	public ItemListItemVO setItemTicketVO(ItemTicketVO itemTicketVO) {
		this.itemTicketVO = itemTicketVO;
		return this;
	}

	public String getPayMode() {
		return payMode;
	}

	public ItemListItemVO setPayMode(String payMode) {
		this.payMode = payMode;
		return this;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public ItemListItemVO setCategoryId(long categoryId) {
		this.categoryId = categoryId;
		return this;
	}
}

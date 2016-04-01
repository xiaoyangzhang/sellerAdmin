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
		for (CityVO cityVO : dests) {
			names.add(cityVO.getName());
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
}

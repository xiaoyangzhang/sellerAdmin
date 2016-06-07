package com.yimayhd.sellerAdmin.model.line.base;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.sellerAdmin.model.line.CityVO;

/**
 * 基本信息
 * 
 * @author yebin
 * 
 */
public class BaseInfoVO implements Serializable {
	private static final long serialVersionUID = 780489526645045937L;
	private long lineId;// 线路ID
	private long itemId;// 商品ID
	private long categoryId;// 分类
	private int type;// 类型
	private String name;// 产品名称
	private String code; // 商品代码
	private int days;// 行程天数
	private String description;// 亮点
	private List<Long> themes;// 主题
	private boolean allDeparts;
	private List<CityVO> departs; // 出发地
	private List<CityVO> dests; // 目的地
	private List<String> picUrls; // App详情轮播图
	private int itemStatus; // 商品状态

	/**
	 * 包含某个tag
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTag(long id) {
		if (id <= 0 || themes == null || themes.size() <= 0) {
			return false;
		}
		return themes.contains(id);
	}

	public long getLineId() {
		return lineId;
	}

	public void setLineId(long lineId) {
		this.lineId = lineId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setThemes(List<Long> themes) {
		this.themes = themes;
	}

	public List<Long> getThemes() {
		return themes;
	}

	public List<CityVO> getDeparts() {
		return departs;
	}

	public void setDeparts(List<CityVO> departs) {
		this.departs = departs;
	}

	public void setDests(List<CityVO> dests) {
		this.dests = dests;
	}

	public List<CityVO> getDests() {
		return dests;
	}

	public boolean isAllDeparts() {
		return allDeparts;
	}

	public void setAllDeparts(boolean allDeparts) {
		this.allDeparts = allDeparts;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

}

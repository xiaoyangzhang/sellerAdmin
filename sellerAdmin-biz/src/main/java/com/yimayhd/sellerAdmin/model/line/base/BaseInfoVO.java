package com.yimayhd.sellerAdmin.model.line.base;

import java.io.Serializable;
import java.util.List;

/**
 * 基本信息
 * 
 * @author yebin
 * 
 */
public class BaseInfoVO implements Serializable {
	private static final long serialVersionUID = 780489526645045937L;
	private long lineId;// ID
	private int type;// 类型
	private String name;// 产品名称
	private String code; // 商品代码
	private int days;// 行程天数
	private String highlight1;// 亮点1
	private String highlight2;// 亮点2
	private String highlight3;// 亮点3
	private List<Long> themes;// 主题
	private List<Long> departs; // 出发地
	private List<Long> dests; // 目的地
	private String productImageApp;// App产品封面图
	private String orderImage;// 订单封面
	private List<String> detailAppImages; // App详情轮播图

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

	public List<Long> getThemes() {
		return themes;
	}

	public void setThemes(List<Long> themes) {
		this.themes = themes;
	}

	public List<String> getDetailAppImages() {
		return detailAppImages;
	}

	public void setDetailAppImages(List<String> detailAppImages) {
		this.detailAppImages = detailAppImages;
	}

	public String getOrderImage() {
		return orderImage;
	}

	public void setOrderImage(String orderImage) {
		this.orderImage = orderImage;
	}

	public String getProductImageApp() {
		return productImageApp;
	}

	public void setProductImageApp(String productImageApp) {
		this.productImageApp = productImageApp;
	}

	public List<Long> getDeparts() {
		return departs;
	}

	public void setDeparts(List<Long> departs) {
		this.departs = departs;
	}

	public List<Long> getDests() {
		return dests;
	}

	public void setDests(List<Long> dests) {
		this.dests = dests;
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

	public String getHighlight1() {
		return highlight1;
	}

	public void setHighlight1(String highlight1) {
		this.highlight1 = highlight1;
	}

	public String getHighlight2() {
		return highlight2;
	}

	public void setHighlight2(String highlight2) {
		this.highlight2 = highlight2;
	}

	public String getHighlight3() {
		return highlight3;
	}

	public void setHighlight3(String highlight3) {
		this.highlight3 = highlight3;
	}
}

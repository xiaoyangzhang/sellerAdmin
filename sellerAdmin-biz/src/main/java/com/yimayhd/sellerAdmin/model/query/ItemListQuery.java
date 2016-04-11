package com.yimayhd.sellerAdmin.model.query;

import java.util.Date;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * 商品列表查询
 * 
 * @author yebin
 *
 */
public class ItemListQuery extends BaseQuery {
	private static final long serialVersionUID = -4699187319874706808L;
	private String name;// 商品名称
	private Long itemId;// 商品编码
	private Integer itemType;// 商品类型
	private Integer status;// 状态
	private Date BeginDate;// 发布开始时间
	private Date endDate;// 发布结束时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return BeginDate;
	}

	public void setBeginDate(Date beginDate) {
		BeginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

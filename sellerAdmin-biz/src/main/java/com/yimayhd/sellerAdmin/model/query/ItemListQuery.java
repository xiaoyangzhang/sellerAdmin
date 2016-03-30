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
	private int itemType;// 商品类型
	private String name;// 商品名称
	private long id;// 商品编码
	private int status;// 状态
	private Date BeginDate;// 发布开始时间
	private Date endDate;// 发布结束时间

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

}

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
	private String itemId;// 商品编码
	private Integer itemType;// 商品类型
	private Integer status;// 状态
	private Date BeginDate;// 发布开始时间
	private Date endDate;// 发布结束时间
	private long outId; // 景区酒店 资源id
	private int outType;// 景区酒店资源类型

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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
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

	public long getOutId() {
		return outId;
	}

	public void setOutId(long outId) {
		this.outId = outId;
	}

	public int getOutType() {
		return outType;
	}

	public void setOutType(int outType) {
		this.outType = outType;
	}
}

package com.yimayhd.sellerAdmin.base;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author wenfeng zhang @ 14-10-10
 */
public class PageQuery<T> implements Serializable {

	private static final long serialVersionUID = 3144038956308932204L;

	private static final int DEFAULT__SIZE = 10;
	private static final int DEFAULT__PAGE = 1;
	public static final int PAGING_YES = 1;
	public static final int PAGING_NO = 0;

	/**
	 * 分页时的第几页
	 */
	private Integer currentPage = DEFAULT__PAGE;
	/**
	 * 每页数目，默认为 DEFAULT__SIZE
	 */
	private Integer pageSize = DEFAULT__SIZE;
	/**
	 * 查询结果总数
	 */
	private Long totalSum;
	/**
	 * 是否分页，默认是
	 */
	@JsonIgnore
	private Integer isPaging = PAGING_YES;
	/**
	 * 查询参数
	 */
	@JsonIgnore
	private T entity;

	private List<T> st;

	public Integer getIsPaging() {
		return isPaging;
	}

	public void setIsPaging(Integer isPaging) {
		this.isPaging = isPaging;
	}

	@JsonIgnore
	public Long getFrom() {
		return pageSize.longValue() * (currentPage - 1);
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public List<T> getList() {
		return st;
	}

	public void setList(List<T> list) {
		this.st = list;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage > 0 ? currentPage : DEFAULT__PAGE;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Long totalSum) {
		this.totalSum = totalSum;
	}
}

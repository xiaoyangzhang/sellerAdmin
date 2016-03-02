package com.yimayhd.sellerAdmin.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseQuery implements Serializable {

	private static final long serialVersionUID = 7184354135734117464L;
	public static final int DEFAULT_SIZE = 10;
	public static final int DEFAULT_PAGE = 1;
	public static final int PAGING_YES = 1;
	public static final int PAGING_NO = 0;

	public Integer pageNumber = DEFAULT_PAGE;
	protected Integer pageSize = DEFAULT_SIZE;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

package com.yimayhd.sellerAdmin.model.query;

import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * 
 */
public class JiuxiuVoucherListQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private int status;
    private int useStatus;
    private int putStatus;
    private List<Integer> ids;
    private long userId;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}
	public int getPutStatus() {
		return putStatus;
	}
	public void setPutStatus(int putStatus) {
		this.putStatus = putStatus;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
    
    
}

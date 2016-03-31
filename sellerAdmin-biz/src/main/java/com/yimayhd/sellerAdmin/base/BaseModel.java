package com.yimayhd.sellerAdmin.base;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1034671331121855153L;

	/**
	 * 主键
	 */
	protected long id;

	/**
	 * 创建时间
	 */
	protected Date gmtCreated;

	/**
	 * 更新时间
	 */
	protected Date gmtModified;

	/**
	 * 1-正常0-删除
	 */
	protected int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

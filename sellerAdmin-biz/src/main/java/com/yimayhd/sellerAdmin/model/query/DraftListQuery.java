package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * 草稿箱列表查询条件
 * @author liuxp
 *
 */
public class DraftListQuery extends BaseQuery {

	/**
	 * serialVersionUID = -2428067804226802107L;
	 */
	private static final long serialVersionUID = -2428067804226802107L;
	
	/**
	 * 主类型
	 */
	private int mainType;
	
	/**
	 * 子类型
	 */
	private int subType;

	public int getMainType() {
		return mainType;
	}

	public void setMainType(int mainType) {
		this.mainType = mainType;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}
}

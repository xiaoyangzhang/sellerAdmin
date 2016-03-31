package com.yimayhd.sellerAdmin.model.line.nk;

import java.io.Serializable;
import java.util.List;

/**
 * 预定须知
 * 
 * @author yebin
 *
 */
public class NeedKnowVO implements Serializable {
	private static final long serialVersionUID = -5148937080557537706L;

	private List<NeedKnowItemVo> needKnowItems;

	public List<NeedKnowItemVo> getNeedKnowItems() {
		return needKnowItems;
	}

	public void setNeedKnowItems(List<NeedKnowItemVo> needKnowItems) {
		this.needKnowItems = needKnowItems;
	}
}

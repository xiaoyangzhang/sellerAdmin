package com.yimayhd.sellerAdmin.model.line.nk;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.sellerAdmin.model.line.KeyValuePair;

/**
 * 预定须知
 * 
 * @author yebin
 *
 */
public class NeedKnowVO implements Serializable {
	private static final long serialVersionUID = -5148937080557537706L;

	private List<KeyValuePair<String, String>> needKnowItems;

	public List<KeyValuePair<String, String>> getNeedKnowItems() {
		return needKnowItems;
	}

	public void setNeedKnowItems(List<KeyValuePair<String, String>> needKnowItems) {
		this.needKnowItems = needKnowItems;
	}

}

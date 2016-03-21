package com.yimayhd.sellerAdmin.model.line.detail;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.sellerAdmin.model.line.KeyValuePair;

/**
 * 图文详情
 * 
 * @author yebin
 *
 */
public class PictureTextVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<KeyValuePair<Integer, String>> values;

	public List<KeyValuePair<Integer, String>> getValues() {
		return values;
	}

	public void setValues(List<KeyValuePair<Integer, String>> values) {
		this.values = values;
	}
}

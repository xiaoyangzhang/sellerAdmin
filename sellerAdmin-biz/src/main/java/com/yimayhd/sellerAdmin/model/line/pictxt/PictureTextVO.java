package com.yimayhd.sellerAdmin.model.line.pictxt;

import java.io.Serializable;
import java.util.List;

/**
 * 图文详情
 * 
 * @author yebin
 *
 */
public class PictureTextVO implements Serializable {
	private static final long serialVersionUID = -7489327287432185333L;
	private long picTextId;
	private List<PictureTextItemVo> pictureTextItems;

	public List<PictureTextItemVo> getPictureTextItems() {
		return pictureTextItems;
	}

	public void setPictureTextItems(List<PictureTextItemVo> pictureTextItems) {
		this.pictureTextItems = pictureTextItems;
	}

	public long getPicTextId() {
		return picTextId;
	}

	public void setPicTextId(long picTextId) {
		this.picTextId = picTextId;
	}
}

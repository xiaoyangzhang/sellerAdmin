package com.yimayhd.sellerAdmin.model.line.pictxt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 图文详情
 * 
 * @author yebin
 *
 */
public class PictureTextVO implements Serializable {
	private static final long serialVersionUID = -7489327287432185333L;
	private long outId;
	private List<PictureTextItemVo> pictureTextItems;

	public List<PictureTextItemVo> getPictureTextItems() {
		return pictureTextItems;
	}

	public void setPictureTextItems(List<PictureTextItemVo> pictureTextItems) {
		this.pictureTextItems = pictureTextItems;
	}

	public long getOutId() {
		return outId;
	}

	public void setOutId(long outId) {
		this.outId = outId;
	}

	public String getPictureTextItemsJson() {
		if (pictureTextItems == null) {
			pictureTextItems = new ArrayList<PictureTextItemVo>();
		}
		return JSON.toJSONString(pictureTextItems);
	}
}

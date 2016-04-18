package com.yimayhd.sellerAdmin.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 图文项数据类型
 * 
 * @author yebin
 *
 */
public enum PictureTextItemType {
	TEXT, IMG;

	public static PictureTextItemType findByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (PictureTextItemType type : values()) {
			if (type.name().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}

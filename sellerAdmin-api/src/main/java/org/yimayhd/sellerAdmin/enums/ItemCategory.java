package org.yimayhd.sellerAdmin.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 
* @ClassName: ServiceCategory
* @Description: 商品类目
* @author zhangxiaoyang
* @date 2016年7月15日 上午9:52:42
*
 */
public enum ItemCategory {

	TALENT_CONSULT(1,"DRZX","达人咨询");
	
	private int type;
	private String code;
	private String name;
	private ItemCategory(int type, String code, String name) {
		this.type = type;
		this.code = code;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public ItemCategory getServiceCategoryByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (ItemCategory sc : ItemCategory.values()) {
			if (sc.getCode().equalsIgnoreCase(code)) {
				return sc;
			}
		}
		return null;
	}
	public ItemCategory getServiceCategoryByType(int type) {
		if (type <= 0) {
			return null;
		}
		for (ItemCategory sc : ItemCategory.values()) {
			if (sc.getType() == type) {
				return sc;
			}
		}
		return null;
	}
}

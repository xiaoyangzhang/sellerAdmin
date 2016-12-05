package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.ItemType;

/**
 * 九休商品类型
 * 
 * @author yebin
 *
 */
public enum BizItemExportType {
	TOUR_LINE("TOUR_LINE","国内跟团游"),
	FREE_LINE("FREE_LINE","国内自由行"),
	TOUR_LINE_ABOARD("TOUR_LINE_ABOARD","境外跟团游"),
	FREE_LINE_ABOARD("FREE_LINE_ABOARD","境外自由行"),
	CITY_ACTIVITY("CITY_ACTIVITY","国内当地玩乐"),
	SPOTS("SPOTS","国内景点门票"),
	HOTEL("HOTEL","酒店客栈"),
	HOTEL_OFFLINE("HOTEL_OFFLINE","酒店客栈"),
	MUST_BUY("NORMAL", "特产商品"),
	POINT_MALL("POINT_MALL", "积分商城");
	private String		text;
	private String	itemType;

	private BizItemExportType(String text, String itemType) {
		this.itemType = itemType;
		this.text = text;
	}

	public String getShowText() {
		return itemType;
	}

	public String getText() {
		return text;
	}

	public boolean isEqual(String text) {
		return getText().equals(text);
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static BizItemExportType get(String enumValue) {
		for (BizItemExportType em : BizItemExportType.values()) {
			if (em.getText().equals(enumValue)) {
				return em;
			}
		}
		return null;
	}

	public static BizItemExportType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizItemExportType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

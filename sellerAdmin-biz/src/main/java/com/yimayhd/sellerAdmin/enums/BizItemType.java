package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.ItemType;

/**
 * 九休商品类型
 * 
 * @author yebin
 *
 */
public enum BizItemType {
	MUST_BUY(ItemType.NORMAL, "必买商品"),
	TOUR_LINE(ItemType.TOUR_LINE),
	FREE_LINE(ItemType.FREE_LINE),
	FREE_LINE_ABOARD(ItemType.FREE_LINE_ABOARD),
	TOUR_LINE_ABOARD(ItemType.TOUR_LINE_ABOARD),
	CITY_ACTIVITY(ItemType.CITY_ACTIVITY);
	private String		text;
	private ItemType	itemType;

	private BizItemType(ItemType itemType) {
		this.itemType = itemType;
		this.text = itemType.getText();
	}

	private BizItemType(ItemType itemType, String text) {
		this.itemType = itemType;
		this.text = text;
	}

	public int getValue() {
		return itemType.getValue();
	}

	public String getText() {
		return text;
	}

	public boolean isEqual(int value) {
		return getValue() == value;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static BizItemType get(int enumValue) {
		for (BizItemType em : BizItemType.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizItemType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizItemType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

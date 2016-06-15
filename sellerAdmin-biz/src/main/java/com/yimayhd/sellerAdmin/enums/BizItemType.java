package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.ItemType;

/**
 * 九休商品类型
 * 
 * @author yebin
 *
 */
public enum BizItemType {
	TOUR_LINE(ItemType.TOUR_LINE,"国内跟团游"),
	FREE_LINE(ItemType.FREE_LINE,"国内自由行"),
	TOUR_LINE_ABOARD(ItemType.TOUR_LINE_ABOARD),
	FREE_LINE_ABOARD(ItemType.FREE_LINE_ABOARD,"境外自由行"),
	CITY_ACTIVITY(ItemType.CITY_ACTIVITY,"国内当地玩乐"),
	SPOTS(ItemType.SPOTS,"国内景点门票"),
	HOTEL(ItemType.HOTEL,"酒店客栈"),
	MUST_BUY(ItemType.NORMAL, "特产商品");
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

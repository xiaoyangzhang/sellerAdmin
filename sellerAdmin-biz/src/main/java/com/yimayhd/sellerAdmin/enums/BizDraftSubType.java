package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.ItemType;

/**
 * 九休草稿箱类型
 * 
 * @author xiemingna
 *
 */
public enum BizDraftSubType {
	TOUR_LINE(ItemType.TOUR_LINE,"国内跟团游"), 
	FREE_LINE(ItemType.FREE_LINE,"国内自由行"), 
	TOUR_LINE_ABORD(ItemType.TOUR_LINE_ABOARD,"境外跟团游"),
	FREE_LINE_ABORD(ItemType.FREE_LINE_ABOARD,"境外自由行"),
	CITY_ACTIVITY(ItemType.CITY_ACTIVITY,"国内当地玩乐"),
	SPOTS(ItemType.SPOTS,"国内景点门票"),
	HOTEL(ItemType.HOTEL,"酒店客栈"),
	NORMAL(ItemType.NORMAL,"特产商品");
	private String text;
	private ItemType itemType;

	private BizDraftSubType(ItemType itemType) {
		this.itemType = itemType;
		this.text = itemType.getText();
	}

	private BizDraftSubType(ItemType itemType, String text) {
		this.itemType = itemType;
		this.text = text;
	}

	public ItemType getValue() {
		return itemType;
	}

	public int getValues() {
		return itemType.getValue();
	}
	
	public String getText() {
		return text;
	}


	/**
	 * 
	 * @param
	 * @return
	 */
	public static BizDraftSubType get(int enumValue) {
		for (BizDraftSubType em : BizDraftSubType.values()) {
			if (em.getValue().getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizDraftSubType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizDraftSubType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

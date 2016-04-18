package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.ItemStatus;

/**
 * 九休商品类型
 * 
 * @author yebin
 *
 */
public enum BizItemStatus {
	CREATE(ItemStatus.create, "待上架"),
	VALID(ItemStatus.valid),
	INVALID(ItemStatus.invalid);

	private String		text;
	private ItemStatus	itemStatus;

	private BizItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
		this.text = itemStatus.getText();
	}

	private BizItemStatus(ItemStatus itemStatus, String text) {
		this.itemStatus = itemStatus;
		this.text = text;
	}

	public int getValue() {
		return itemStatus.getValue();
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
	public static BizItemStatus get(int enumValue) {
		for (BizItemStatus em : BizItemStatus.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizItemStatus getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizItemStatus userStatus : values()) {
			if (userStatus.name().equals(name)) {
				return userStatus;
			}
		}
		return null;
	}
}

package com.yimayhd.sellerAdmin.enums;

public enum BizCommonItemStatusType {
	WAITING_PAY("WAITING_PAY","待付款"),
	WAITING_DELIVERY("WAITING_DELIVERY","已付款"),
	SHIPPING("SHIPPING","已发货"),
	FINISH("FINISH","已完成"),
	CANCEL("CANCEL","已取消");
	private String text;
	private String status;

	private BizCommonItemStatusType(String status, String text) {
		this.status = status;
		this.text = text;
	}

	public String getValue() {
		return status;
	}

	public String getText() {
		return text;
	}


	/**
	 * 
	 * @param
	 * @return
	 */
	public static BizCommonItemStatusType get(String text) {
		for (BizCommonItemStatusType em : BizCommonItemStatusType.values()) {
			if (em.getValue().equals(text)) {
				return em;
			}
		}
		return null;
	}

	public static BizCommonItemStatusType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizCommonItemStatusType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

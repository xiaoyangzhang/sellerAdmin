package com.yimayhd.sellerAdmin.enums;

public enum BizJingQuStatusType {
	WAITING_PAY("WAITING_PAY","待付款"),
	WAITING_DELIVERY("WAITING_DELIVERY","待确认"),
	SHIPPING("SHIPPING","待使用"),
	FINISH("FINISH","已完成"),
	CANCEL("CANCEL","已取消");
	private String text;
	private String status;

	private BizJingQuStatusType(String status, String text) {
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
	public static BizJingQuStatusType get(String text) {
		for (BizJingQuStatusType em : BizJingQuStatusType.values()) {
			if (em.getValue().equals(text)) {
				return em;
			}
		}
		return null;
	}

	public static BizJingQuStatusType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizJingQuStatusType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

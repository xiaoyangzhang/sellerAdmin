package com.yimayhd.sellerAdmin.enums;

public enum BizLineStatusType {
	WAITING_PAY("WAITING_PAY","待付款"),
	WAITING_DELIVERY("WAITING_DELIVERY","处理中"),
	SHIPPING("SHIPPING","待出行"),
	FINISH("FINISH","已完成"),
	CANCEL("CANCEL","已取消");
	private String text;
	private String status;

	private BizLineStatusType(String status, String text) {
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
	public static BizLineStatusType get(String text) {
		for (BizLineStatusType em : BizLineStatusType.values()) {
			if (em.getValue().equals(text)) {
				return em;
			}
		}
		return null;
	}

	public static BizLineStatusType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizLineStatusType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

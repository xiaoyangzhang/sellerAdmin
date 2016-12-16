package com.yimayhd.sellerAdmin.enums;

public enum BizHotelStatusType {
	WAITING_PAY("WAITING_PAY","待付款"),
	WAITING_DELIVERY("WAITING_DELIVERY","待确认"),
	SHIPPING("SHIPPING","预订成功"),
	FINISH("FINISH","已完成"),
	CONFIRMED_CLOSE("CONFIRMED_CLOSE","未入住"),
	CANCEL("CANCEL","已取消");
	private String text;
	private String status;

	private BizHotelStatusType(String status, String text) {
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
	public static BizHotelStatusType get(String text) {
		for (BizHotelStatusType em : BizHotelStatusType.values()) {
			if (em.getValue() == text) {
				return em;
			}
		}
		return null;
	}

	public static BizHotelStatusType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizHotelStatusType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

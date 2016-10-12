package com.yimayhd.sellerAdmin.client.enums;

public enum OrderOperationLogStatus {

	FINISH(1,"已完成"),
	CANCEL(2,"已取消");
	private int type;
	private String name;
	private OrderOperationLogStatus(int type,String name) {
		this.name = name;
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public static OrderOperationLogStatus getByType(int enumValue) {
		for (OrderOperationLogStatus em : OrderOperationLogStatus.values()) {
			if (em.type == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static OrderOperationLogStatus getByName(String name) {
		if (name == null) {
			return null;
		}
		for (OrderOperationLogStatus userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
	
	
}

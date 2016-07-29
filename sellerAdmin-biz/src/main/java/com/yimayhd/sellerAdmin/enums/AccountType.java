package com.yimayhd.sellerAdmin.enums;

public enum AccountType {

	PUBLIC_ACCOUNT(1,"对公账户"),
	PRIVATE_ACCOUNT(2,"对私账户");
	private int type;
	private String name;
	private AccountType(int type,String name) {
		this.name = name;
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	
}

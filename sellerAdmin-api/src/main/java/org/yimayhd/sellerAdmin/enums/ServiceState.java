package org.yimayhd.sellerAdmin.enums;

import org.apache.commons.lang.StringUtils;

public enum ServiceState {

	ON_SALE(2,"ONSALE","已发布"),
	OFF_SALE(3,"OFFSALE","待发布");
	
	private int state;
	private String code;
	private String name;
	public int getState() {
		return state;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	private ServiceState(int state, String code, String name) {
		this.state = state;
		this.code = code;
		this.name = name;
	}
	public ServiceState getServiceStateByState(int state) {
		if (state <= 0) {
			return null;
		}
		for (ServiceState ss : ServiceState.values()) {
			if (ss.getState() == state) {
				return ss;
			}
		}
		return null;
	}
	public ServiceState getServiceStateByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (ServiceState ss : ServiceState.values()) {
			if (ss.getCode().equalsIgnoreCase(code)) {
				return ss;
			}
		}
		return null;
	}
}

package com.yimayhd.sellerAdmin.error;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * 业务异常
 * 
 * @author yebin
 *
 */
public enum BizErrorCode {
	ParametersValidateError(1,"参数验证失败"),
	PARAMS_NO_FULL(2,"参数不完整"),
	SYSTEM_ERROR(999,"系统错误");
	private int code;
	private String msg;

	BizErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}

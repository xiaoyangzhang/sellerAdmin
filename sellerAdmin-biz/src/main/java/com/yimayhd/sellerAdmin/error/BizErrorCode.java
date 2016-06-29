package com.yimayhd.sellerAdmin.error;

/**
 * 业务异常
 * 
 * @author yebin
 *
 */
public enum BizErrorCode {
	ParametersValidateError(1,"参数验证失败");
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

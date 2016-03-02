package com.yimayhd.sellerAdmin.result;

import java.io.Serializable;

import com.yimayhd.sellerAdmin.error.BizErrorCode;

/**
 * 业务支持
 * 
 * @author yebin
 *
 */
public class BizResultSupport implements Serializable {
	private static final long serialVersionUID = -2235152751651905167L;
	protected static final int SUCCESS_CODE = 200;
	private boolean success = true;
	private int code;
	private String msg;

	public BizResultSupport() {
	}

	public void init(boolean success, int code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public void initFailure(BizErrorCode errorCode) {
		this.success = false;
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}

	public void initSuccess(String msg) {
		this.success = true;
		this.code = SUCCESS_CODE;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

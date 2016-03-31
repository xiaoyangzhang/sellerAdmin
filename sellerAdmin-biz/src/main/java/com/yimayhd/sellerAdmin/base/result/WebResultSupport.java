package com.yimayhd.sellerAdmin.base.result;

import java.io.Serializable;

/**
 * Web服务支持
 * 
 * @author yebin
 *
 */
public class WebResultSupport implements Serializable {
	private static final long serialVersionUID = -2235152751651905167L;
	private boolean success = true;
	private int errorCode;
	private String resultMsg;
	private WebReturnCode webReturnCode;

	public boolean isSuccess() {
		return success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public WebReturnCode getWebReturnCode() {
		return webReturnCode;
	}

	public void setWebReturnCode(WebReturnCode webReturnCode) {
		this.webReturnCode = webReturnCode;
		if (webReturnCode != null) {
			success = false;
			errorCode = webReturnCode.getErrorCode();
			resultMsg = webReturnCode.getErrorMsg();
		}
	}

	public void initFailure(WebReturnCode webReturnCode, String message) {
		this.success = false;
		this.errorCode = webReturnCode.getErrorCode();
		this.resultMsg = webReturnCode.getErrorMsg() + ":" + message;
	}

	public void initFailure(WebReturnCode webReturnCode) {
		this.success = false;
		this.errorCode = webReturnCode.getErrorCode();
		this.resultMsg = webReturnCode.getErrorMsg();
	}

	public void initSuccess(String message) {
		this.success = true;
		this.errorCode = 0;
		this.resultMsg = message;
	}

}

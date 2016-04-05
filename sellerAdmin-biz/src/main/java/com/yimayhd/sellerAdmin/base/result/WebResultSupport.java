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
	private String errorMsg;
	private WebReturnCode webReturnCode;
	private String url;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getErrorCode() {
		return errorCode;
	}


	public String getErrorMsg() {
		return errorMsg;
	}

	public WebReturnCode getWebReturnCode() {
		return webReturnCode;
	}

	public void setWebReturnCode(WebReturnCode webReturnCode) {
		this.webReturnCode = webReturnCode;
		if (webReturnCode != null) {
			success = false;
			errorCode = webReturnCode.getErrorCode();
			errorMsg = webReturnCode.getErrorMsg();
		}
	}

}

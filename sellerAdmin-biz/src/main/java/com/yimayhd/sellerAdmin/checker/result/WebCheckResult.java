package com.yimayhd.sellerAdmin.checker.result;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;

/**
 * 参数校验返回值
 * 
 * @author yebin
 *
 */
public class WebCheckResult extends WebResultSupport {
	private static final long serialVersionUID = -3672364961175610633L;

	/**
	 * 成功
	 * 
	 * @return
	 */
	public static WebCheckResult success() {
		WebCheckResult checkResult = new WebCheckResult();
		checkResult.initSuccess("验证成功");
		return checkResult;
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public static WebCheckResult error() {
		WebCheckResult checkResult = new WebCheckResult();
		checkResult.initFailure(WebReturnCode.PARAM_ERROR);
		return checkResult;
	}

	/**
	 * 失败自定义错误信息
	 * 
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static WebCheckResult error(String msg) {
		WebCheckResult checkResult = new WebCheckResult();
		checkResult.initFailure(WebReturnCode.PARAM_ERROR, msg);
		return checkResult;
	}
}

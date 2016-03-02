package com.yimayhd.sellerAdmin.checker.result;

import com.yimayhd.sellerAdmin.error.BizErrorCode;
import com.yimayhd.sellerAdmin.result.BizResultSupport;

/**
 * 参数校验返回值
 * 
 * @author yebin
 *
 */
public class CheckResult extends BizResultSupport {
	private static final long serialVersionUID = -3672364961175610633L;

	/**
	 * 成功
	 * 
	 * @return
	 */
	public static CheckResult success() {
		CheckResult checkResult = new CheckResult();
		checkResult.initSuccess("验证成功");
		return checkResult;
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public static CheckResult error() {
		CheckResult checkResult = new CheckResult();
		checkResult.initFailure(BizErrorCode.ParametersValidateError);
		return checkResult;
	}

	/**
	 * 失败自定义错误信息
	 * 
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static CheckResult error(String msg) {
		CheckResult checkResult = error();
		checkResult.setMsg(msg);
		return checkResult;
	}
}

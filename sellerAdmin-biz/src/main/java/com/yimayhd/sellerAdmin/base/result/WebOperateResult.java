package com.yimayhd.sellerAdmin.base.result;

/**
 * 操作结果
 * 
 * @author yebin
 *
 */
public class WebOperateResult extends WebResultSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作成功
	 * 
	 * @return
	 */
	public static WebOperateResult success() {
		return success("操作成功");
	}

	public static WebOperateResult success(String msg) {
		WebOperateResult operateResult = new WebOperateResult();
		operateResult.initSuccess(msg);
		return operateResult;
	}

	/**
	 * 操作失败
	 * 
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static WebOperateResult failure(WebReturnCode returnCode, String msg) {
		WebOperateResult operateResult = new WebOperateResult();
		operateResult.initFailure(returnCode, msg);
		return operateResult;
	}

}

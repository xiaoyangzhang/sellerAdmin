package com.yimayhd.sellerAdmin.base.result;

/**
 * 
 * @author wzf
 *
 * @param <T>
 */
public class WebResult<T> extends WebResultSupport {
	private static final long serialVersionUID = 4999091548448313101L;
	protected T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public static <T> WebResult<T> success(T value) {
		return success(value, "操作成功");
	}

	public static <T> WebResult<T> success(T value, String msg) {
		WebResult<T> result = new WebResult<T>();
		result.initSuccess(msg);
		result.setValue(value);
		return result;
	}

	/**
	 * 操作失败
	 * 
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static <T> WebResult<T> failure(WebReturnCode returnCode, String msg) {
		WebResult<T> result = new WebResult<T>();
		result.initFailure(returnCode, msg);
		return result;
	}

}

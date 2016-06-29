package com.yimayhd.sellerAdmin.result;

/**
 * 结果
 * 
 * @author yebin
 *
 * @param <T>
 */
public class BizResult<T> extends BizResultSupport {
	private static final long serialVersionUID = 4999091548448313101L;
	protected T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public static <U> BizResult<U> buildFailResult(int code, String msg, U value) {
		BizResult<U> baseResult = new BizResult<U>();
		baseResult.setSuccess(false);
		baseResult.setCode(code);
		baseResult.setMsg(msg);
		baseResult.setValue(value);
		return baseResult;
	}

	public static <U> BizResult<U> buildSuccessResult(U value) {
		BizResult<U> baseResult = new BizResult<U>();
		baseResult.setSuccess(true);
		baseResult.setCode(SUCCESS_CODE);
		baseResult.setMsg("操作成功");
		baseResult.setValue(value);
		return baseResult;
	}
}

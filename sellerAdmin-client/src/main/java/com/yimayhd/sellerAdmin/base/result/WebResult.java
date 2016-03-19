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

	
}

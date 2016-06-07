package com.yimayhd.sellerAdmin.service;

import java.io.Serializable;

public class ServiceResult<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3398449285760979059L;
	
	//执行结果
	private boolean result = false;
	//错误信息
	private String errorMsg;
	//错误编码
	private String errorCode;
	
	private Object value;
	
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T)value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public ServiceResult(boolean result) {
		this(result,null,null);
	}
	
	public ServiceResult(boolean result, String errorMsg, String errorCode) {
		super();
		this.result = result;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	
	/**
	public static ServiceResult success(){
		return new ServiceResult(true);
	}
	
	public static ServiceResult fail(String errorMsg,String errorCode){
		return new ServiceResult(false,errorMsg,errorCode);
	}
	
	public static ServiceResult fail(String errorMsg){
		return new ServiceResult(false,errorMsg,null);
	}
	
	public static ServiceResult fail(){
		return new ServiceResult(false);
	}
	*/
	public boolean isSuccess() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}

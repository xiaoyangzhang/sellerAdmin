package com.yimayhd.sellerAdmin.base.result;

import java.io.Serializable;

/**
 * 取值[24000000 , 25000000)
 * 
 * @author wzf
 *
 */
public class WebReturnCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMsg;

	private WebReturnCode(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public static final WebReturnCode REMOTE_CALL_FAILED = new WebReturnCode(23000000, "远程调用失败!");
	public static final WebReturnCode SYSTEM_ERROR = new WebReturnCode(23000001, "系统错误!");
	public static final WebReturnCode PARAM_ERROR = new WebReturnCode(23000002, "参数错误!");
	public static final WebReturnCode DATA_NOT_FOUND = new WebReturnCode(23000002, "数据不存在");
	/******************************************* 用户相关 ******************************************************/
	public static final WebReturnCode USER_NOT_FOUND = new WebReturnCode(24001000, "用户不存在!");
	public static final WebReturnCode USER_LOCKED_TO_MANY_TIMES_FAILED = new WebReturnCode(24001001, "登陆失败次数过多!");
	public static final WebReturnCode USERNAME_OR_PASSWORD_ERROR = new WebReturnCode(24001002, "用户名或者密码错误!");
	public final static WebReturnCode MOBILE_REGISTED = new WebReturnCode(24001003, "手机号码已注册");
	public final static WebReturnCode SMS_SEND_FAILED = new WebReturnCode(24001004, "短信发送失败");
	public final static WebReturnCode SMS_ALREADY_SEND = new WebReturnCode(24001006, "短信已发送");
	public final static WebReturnCode SMS_VERIFY_CODE_ERROR = new WebReturnCode(24001007, "短信验证码错误");
	public final static WebReturnCode MOBILE_NUM_ERROR = new WebReturnCode(24001008, "手机号码错误");
	public final static WebReturnCode MODIFY_DISABLE = new WebReturnCode(24001010, "手机号码未进过验证，不能修改密码");
	public final static WebReturnCode USERNAME_EMPTY = new WebReturnCode(24001011, "用户名不能为空");
	public final static WebReturnCode PASSWORD_EMPTY = new WebReturnCode(24001012, "密码不能为空");
	public final static WebReturnCode IMAGE_VERIFY_CODE_ERROR = new WebReturnCode(24001013, "图片验证码错误");
	public final static WebReturnCode MOBILE_FORMAT_ERROR = new WebReturnCode(24001014, "手机号码格式错误");
	public final static WebReturnCode USER_NICKNAME_EXIT = new WebReturnCode(24001015, "此昵称已存在");
	public final static WebReturnCode UPDATE_USER_ERROR = new WebReturnCode(24001016, "修改用户信息失败");

	/***************************************** 商户相关 ********************************************************/
	public static final WebReturnCode MERCHANT_BASIC_SAVE_FAILURE = new WebReturnCode(24002001, "商户基本信息保存失败");
	public static final WebReturnCode MERCHANT_BASIC_EDIT_FAILURE = new WebReturnCode(24002002, "商户基本信息修改失败");
	public static final WebReturnCode MERCHANT_INFO_EDIT_FAILURE = new WebReturnCode(24002003, "商户入驻填写信息修改失败");
	public final static WebReturnCode PASSWORD_ERROR = new WebReturnCode(24001015, "手机号码错误");
	/***************************************** 商品相关 ********************************************************/
}

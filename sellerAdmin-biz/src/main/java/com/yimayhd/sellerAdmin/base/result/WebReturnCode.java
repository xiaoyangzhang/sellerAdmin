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

	public static final WebReturnCode REMOTE_CALL_FAILED = new WebReturnCode(23000000, "远程调用失败");
	public static final WebReturnCode SYSTEM_ERROR = new WebReturnCode(23000001, "系统错误");
	public static final WebReturnCode PARAM_ERROR = new WebReturnCode(23000002, "参数错误");
	public static final WebReturnCode DATA_NOT_FOUND = new WebReturnCode(23000003, "数据不存在");
	public static final WebReturnCode FILE_TO_BIG = new WebReturnCode(23000004, "文件超过最大限制");
	public static final WebReturnCode UPLOAD_FILE_FAILED = new WebReturnCode(23000005, "上传文件失败");
	public static final WebReturnCode DB_ERROR = new WebReturnCode(23000006, "数据库异常");
	/******************************************* 用户相关 ******************************************************/
	public static final WebReturnCode USER_NOT_FOUND = new WebReturnCode(24001000, "手机号未注册");
	public static final WebReturnCode USER_LOCKED_TO_MANY_TIMES_FAILED = new WebReturnCode(24001001, "登陆失败次数过多");
	public static final WebReturnCode USERNAME_OR_PASSWORD_ERROR = new WebReturnCode(24001002, "用户名或者密码错误");
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
	public final static WebReturnCode USER_NICKNAME_EXIT = new WebReturnCode(24001015, "该昵称已存在，换一个试试吧");
	public final static WebReturnCode UPDATE_USER_ERROR = new WebReturnCode(24001016, "修改用户信息失败");
	public final static WebReturnCode OLD_PASSWORD_EMPTY = new WebReturnCode(24001017, "旧密码为空");
	public final static WebReturnCode NEW_PASSWORD_EMPTY = new WebReturnCode(24001018, "新密码为空");
	public final static WebReturnCode NEW_OLD_PASSWORD_EQUAL = new WebReturnCode(24001019, "新旧密码相同");
	public final static WebReturnCode SYSTEM_ERROR_MERCHANT_TALENT = new WebReturnCode(24001020, "服务器出现错误，请稍后重新登录");
	public final static WebReturnCode NOT_FOUNT_MERCHANT = new WebReturnCode(24003007, "未找到该用户");
	public final static WebReturnCode LOGIN_IP_FREQUENTF = new WebReturnCode(24001021, "IP频繁错误登录");

	/***************************************** 商户相关 ********************************************************/
	public static final WebReturnCode MERCHANT_BASIC_SAVE_FAILURE = new WebReturnCode(24002001, "商户基本信息保存失败");
	public static final WebReturnCode MERCHANT_BASIC_EDIT_FAILURE = new WebReturnCode(24002002, "商户基本信息修改失败");
	public static final WebReturnCode MERCHANT_INFO_EDIT_FAILURE = new WebReturnCode(24002003, "商户入驻填写信息修改失败");
	public final static WebReturnCode PASSWORD_ERROR = new WebReturnCode(24001015, "当前密码错误");
	public final static WebReturnCode QUERY_EXAMINFO_FAILED = new WebReturnCode(24003003, "获取店铺入驻信息失败");
	public final static WebReturnCode QUERY_MERCHANT_QUALIFICATION_FAILED = new WebReturnCode(24003004, "获取商家资质失败");
	public final static WebReturnCode QUERY_MERCHANT_SCOPE_FAILED = new WebReturnCode(24003005, "获取商家经营范围失败");
	public final static WebReturnCode QUERY_MERCHANT_CATEGORY_SCOPE_FAILED = new WebReturnCode(24003006, "获取商家身份对应经营范围失败");
	public final static WebReturnCode QUERY_MERCHANT_CATEGORY_QUALIFICATION_FAILED = new WebReturnCode(24003007, "获取商家身份对应的资质失败");
	public final static WebReturnCode UPLOAD_QUALIFICATION_FAILED = new WebReturnCode(24003008, "请上传必填的资质");
	public final static WebReturnCode QUERY_MERCHANT_FAILED = new WebReturnCode(24003009, "获取该店铺信息失败");
	/***************************************** 达人相关 ********************************************************/
	public static final WebReturnCode TALENT_BASIC_SAVE_FAILURE = new WebReturnCode(24002004, "达人基本信息保存失败");
	public static final WebReturnCode TALENT_BASIC_EDIT_FAILURE = new WebReturnCode(24002005, "达人基本信息修改失败");
	public static final WebReturnCode TALENT_INFO_SAVE_FAILURE = new WebReturnCode(24002006, "达人入驻填写信息保存失败");
	public static final WebReturnCode TALENT_INFO_EDIT_FAILURE = new WebReturnCode(24002007, "达人入驻填写信息修改失败");
	//FIXME 张晓阳
	public static final WebReturnCode TALENT_CHECKRESULT_FAILURE = new WebReturnCode(24002011, "达人入驻审核结果获取失败");
	public static final WebReturnCode UPDATE_CHECKRESULT_FAILURE = new WebReturnCode(24002012, "更新审核状态失败");
	public static final WebReturnCode MERCHANT_NAME_EXIST = new WebReturnCode(24002008, "店铺名称已存在");
	public static final WebReturnCode APPROVE_PASSED_DISABLE_MODIFY = new WebReturnCode(24002009, "已经修改通过，禁止修改");
	public static final WebReturnCode TALENT_MERCHANT_NAME_EXIST = new WebReturnCode(240020010, "商户名称已存在");
	//public final static WebReturnCode PASSWORD_ERROR = new WebReturnCode(24001015, "手机号码错误");
	/***************************************** 商品相关 ********************************************************/

	public final static WebReturnCode UPDATE_ERROR = new WebReturnCode(24003001, "更新失败");
	public final static WebReturnCode REPEAT_ERROR = new WebReturnCode(24003003, "商品重复添加");
	public final static WebReturnCode DRAFTNAME_REPEAT_ERROR = new WebReturnCode(24003003, "草稿名称重复");
	/***************************************** 美食相关 ********************************************************/
	public final static WebReturnCode ADD_FAILURE = new WebReturnCode(24003002, "新增失败");
	public final static WebReturnCode QUERY_FAILURE = new WebReturnCode(24003003, "查询失败");
	/***************************************** 发布商品********************************************************/
	public final static WebReturnCode ON_SALE_ERROR = new WebReturnCode(24004000, "上架失败");
	public final static WebReturnCode OFF_SALE_ERROR = new WebReturnCode(24004001, "下架失败");
	public final static WebReturnCode DELETE_ERROR = new WebReturnCode(24004002, "删除失败");
	public final static WebReturnCode NO_AUTHORITY = new WebReturnCode(24004003, "没有该权限");
	public final static WebReturnCode ADD_TALENT_ERROR = new WebReturnCode(24004004, "添加达人失败");

	/***************************************** 优惠劵相关 ********************************************************/
	public final static WebReturnCode VOUVHER_TITLE_ERROR = new WebReturnCode(24003020, "优惠劵名称长度不正确");
	public final static WebReturnCode VOUVHER_REQUERMENT_ERROR = new WebReturnCode(24003021, "优惠劵满减金额不正确");
	public final static WebReturnCode VOUVHER_COUNT_ERROR = new WebReturnCode(24003022, "优惠劵个人领取数量不正确");
	public final static WebReturnCode VOUVHER_TOTAL_NUM_ERROR = new WebReturnCode(24003023, "优惠劵发劵数量不正确");
	public final static WebReturnCode VOUVHER_PUT_TIME_ERROR = new WebReturnCode(24003024, "优惠劵发劵时间不正确");
	public final static WebReturnCode VOUVHER_USE_TIME_ERROR = new WebReturnCode(24003025, "优惠劵使用时间不正确");
	public final static WebReturnCode VOUVHER_USE_PUT_TIME_ERROR = new WebReturnCode(24003026, "优惠劵使用开始或结束时间必须大于等于发放开始时间或结束时间");
	public final static WebReturnCode VOUVHER_ADD_ERROR = new WebReturnCode(24003027, "优惠劵添加异常");
	public final static WebReturnCode VOUVHER_EDIT_ERROR = new WebReturnCode(24003028, "优惠劵修改异常");
	public final static WebReturnCode VOUVHER_ADD_REPET_ERROR = new WebReturnCode(24003029, "优惠劵重复添加");
	public final static WebReturnCode VOUVHER_DEL_ERROR = new WebReturnCode(24003030, "优惠劵删除异常");
	public final static WebReturnCode VOUVHER_PUT_ERROR = new WebReturnCode(24003031, "优惠劵上架异常");
	public final static WebReturnCode VOUVHER_GET_ERROR = new WebReturnCode(24003032, "优惠劵下架异常");
	public final static WebReturnCode VOUVHER_LIMIT_ERROR = new WebReturnCode(24003033, "当前上架优惠劵已超过5张");
	public final static WebReturnCode VOUVHER_PUT_SET_ERROR = new WebReturnCode(24003034, "优惠劵发布数量不能小于已领取数量！");
	
}

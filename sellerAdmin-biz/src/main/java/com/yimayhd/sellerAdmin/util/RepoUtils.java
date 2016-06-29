package com.yimayhd.sellerAdmin.util;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.result.ResultSupport;
import com.yimayhd.ic.client.model.result.ICErrorCode;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.sellerAdmin.base.BaseException;

/**
 * Repo工具
 * 
 * @author yebin
 *
 */
public class RepoUtils {
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;

	private static String RESULT_NULL = "{} result is null";
	private static String RESULT_FAILURE = "{} error {}: {}";
	private static String RESULT_SUCCESS = "{} success";

	/**
	 * 处理IC服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void resultLog(Logger log, String method, ICResultSupport result) {
		String prefix = "ItemCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			String code = null;
			String msg = null;
			ICErrorCode errorCode = result.getErrorCode();
			if (errorCode != null) {
				code = errorCode.getErrorCode() + "";
				msg = errorCode.getErrorMsg();
			} else {
				code = result.getResultCode() + "";
				msg = result.getResultMsg();
			}
			log.error(RESULT_FAILURE, method, code, msg);
			throw new BaseException(prefix + msg);
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * CommentCenter服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void resultLog(Logger log, String method, ResultSupport result) {
		String prefix = "CommentCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			log.error(RESULT_FAILURE, method, result.getErrorCode(), result.getResultMsg());
			throw new BaseException(prefix + result.getResultMsg());
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * ResourceCenter服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void resultLog(Logger log, String method,
			com.yimayhd.resourcecenter.model.result.ResultSupport result) {
		String prefix = "ResourceCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			log.error(RESULT_FAILURE, method, result.getErrorCode(), result.getResultMsg());
			throw new BaseException(prefix + result.getResultMsg());
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * 请求的log（有参）
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void requestLog(Logger log, String method, Object... param) {
		log.info("Request {} Param: {}", method, JSON.toJSONString(param));
	}

	/**
	 * 请求的log（无参）
	 * 
	 * @param log
	 * @param method
	 */
	public static void requestLog(Logger log, String method) {
		log.info("Request {}", method);
	}

	/**
	 * SnsCenter服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void resultLog(Logger log, String method, com.yimayhd.snscenter.client.result.ResultSupport result) {
		String prefix = "SnsCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			log.error(RESULT_FAILURE, method, result.getErrorCode(), result.getResultMsg());
			throw new BaseException(prefix + result.getResultMsg());
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * MemCenter服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void resultLog(Logger log, String method, MemResultSupport result) {
		String prefix = "MemCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			log.error(RESULT_FAILURE, method, result.getErrorCode(), result.getErrorMsg());
			throw new BaseException(prefix + result.getErrorMsg());
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}
}

package com.yimayhd.sellerAdmin.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
 * @author wzf
 *
 */
public class BaseController {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	// protected HttpServletRequest request;

	@Autowired
	protected SessionManager sessionManager;

	protected long getCurrentUserId() {
		return sessionManager.getUserId();
	}

	@Value("${env}")
	private String env;

	public boolean isTest() {
		if (Constant.ENV_PROD.equalsIgnoreCase(env)) {
			return false;
		}
		return true;
	}

	// 正式发布，异常不这样处理
	/*
	 * @ExceptionHandler(Exception.class)
	 * 
	 * @ResponseBody protected ResponseVo handleException(Exception e) {
	 * logger.error(e.getMessage(), e); if (e instanceof
	 * HttpMessageNotReadableException || e instanceof NumberFormatException ||
	 * e instanceof InvalidPropertyException) { return new
	 * ResponseVo(ResponseStatus.DATA_PARSE_ERROR.VALUE,
	 * ResponseStatus.DATA_PARSE_ERROR.MESSAGE + e.getLocalizedMessage()); }
	 * else if (e instanceof NoticeException) { return new
	 * ResponseVo(ResponseStatus.FORBIDDEN.VALUE, e.getMessage()); } else if (e
	 * instanceof BaseException) { return new
	 * ResponseVo(ResponseStatus.FORBIDDEN.VALUE, e.getMessage()); } return new
	 * ResponseVo(ResponseStatus.ERROR.VALUE, ResponseStatus.ERROR.MESSAGE +
	 * e.getLocalizedMessage()); }
	 */

	@ExceptionHandler(Exception.class)
	protected ModelAndView handleException(Exception e) {
		log.error(e.getMessage(), e);
		ModelAndView modelAndView = new ModelAndView("/error");
		if (e instanceof NoticeException || e instanceof BaseException) {
			modelAndView.addObject("message", e.getMessage() + "，请联系管理员");
		} else {
			modelAndView.addObject("message", "服务器未知错误，请联系管理员");
		}
		return modelAndView;

	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(new
		// SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}

	private HttpServletRequest getRequest() {
		return SessionHelper.getRequest();
	}

	/**
	 * 等价于request.getParameter(name).
	 */
	protected String get(final String name) {
		return getRequest().getParameter(name);
	}

	protected String getCallbackUrl(final String name) {
		String returnUrl = get(name);
		if (StringUtils.isBlank(returnUrl)) {
			returnUrl = "/";
		}
		return returnUrl;
	}

	protected Integer getInteger(final String name) {
		final String str = getRequest().getParameter(name);
		if (StringUtils.isNotBlank(str)) {
			return Integer.valueOf(str);
		}
		return null;
	}

	protected Long getLong(final String name) {
		final String str = getRequest().getParameter(name);
		if (StringUtils.isNotBlank(str)) {
			return Long.valueOf(str);
		}
		return null;
	}

	/**
	 * 等价于request.setAttribute(key, value).
	 */
	protected void put(final String key, final Object value) {
		getRequest().setAttribute(key, value);
	}

	protected String redirect(String path) {
		return "redirect:" + path;
	}
}

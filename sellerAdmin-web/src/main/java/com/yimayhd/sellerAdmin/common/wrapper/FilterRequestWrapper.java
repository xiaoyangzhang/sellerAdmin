package com.yimayhd.sellerAdmin.common.wrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 请求参数过滤
 * 
 * @author yebin
 *
 */
public class FilterRequestWrapper extends HttpServletRequestWrapper {

	public FilterRequestWrapper(ServletRequest request) {
		super((HttpServletRequest) request);
	}

	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		return filter(parameter);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameterValues = super.getParameterValues(name);
		if (parameterValues != null) {
			for (int i = 0; i < parameterValues.length; i++) {
				String value = filter(parameterValues[i]);
				// XXX Yebin 去首尾空格
				if (value != null) {
					value = StringUtils.trim(value);
				}
				parameterValues[i] = value;
			}
		}
		return parameterValues;
	}

	/**
	 * 过滤条件
	 * 
	 * @param value
	 * @return
	 */
	private String filter(String value) {
		String temp = value;
		if (StringUtils.isNotBlank(temp)) {
			// sql转义
			temp = StringEscapeUtils.escapeSql(temp);
		}
		return temp;
	}

}

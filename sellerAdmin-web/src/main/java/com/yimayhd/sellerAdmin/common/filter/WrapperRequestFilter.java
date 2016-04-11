package com.yimayhd.sellerAdmin.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.yimayhd.sellerAdmin.common.wrapper.FilterRequestWrapper;

/**
 * 封装请求过滤器
 * 
 * @author yebin
 *
 */
public class WrapperRequestFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(new FilterRequestWrapper(request), response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}

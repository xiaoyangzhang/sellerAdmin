package com.yimayhd.sellerAdmin.helper;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.user.session.manager.constant.SessionConstant;


public class SessionHelper {
	private static final Logger logger = LoggerFactory.getLogger("SessionHelper");
	
	
	public static void setCookies(HttpServletResponse response, String token, String domain) {
		if (StringUtils.isBlank(token)) {
			return;
		}
		Cookie cookie = new Cookie(SessionConstant.TOKEN_SERVER, token);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
//		if( StringUtils.isNotBlank(domain)){
//			cookie.setDomain(domain);
//		}

		String token2 = UUID.randomUUID().toString();
		Cookie cookie2 = new Cookie(SessionConstant.TOKEN_CLIENT, token2);
		cookie2.setPath("/");
//		if( StringUtils.isNotBlank(domain) ){
//			cookie.setDomain(domain);
//		}

		response.addCookie(cookie);
		response.addCookie(cookie2);
	}
	
	public static void cleanCookies(HttpServletResponse response) {
		Cookie cookie = new Cookie(SessionConstant.TOKEN_SERVER, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
//		if( StringUtils.isNotBlank(domain) ){
//			cookie.setDomain(domain);
//		}

		Cookie cookie2 = new Cookie(SessionConstant.TOKEN_CLIENT, null);
		cookie2.setMaxAge(0);
		cookie2.setPath("/");
//		if( StringUtils.isNotBlank(domain) ){
//			cookie.setDomain(domain);
//		}
		
		response.addCookie(cookie);
		response.addCookie(cookie2);
		
	}

}

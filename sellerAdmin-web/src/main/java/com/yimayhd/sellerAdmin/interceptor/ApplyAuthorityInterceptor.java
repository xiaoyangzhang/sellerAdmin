package com.yimayhd.sellerAdmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.helper.AuthorityHelper;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;

public class ApplyAuthorityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger("BasicInfoAuthorityInterceptor");

	@Autowired
	private SessionManager sessionManager;
	
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	@Autowired
	private TalentBiz talentBiz;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserDO userDO = sessionManager.getUser(request);
		String url = AuthorityHelper.getRedirectUrl(userDO);
		if( StringUtils.isNotEmpty( url )){
			url = UrlHelper.getUrl( rootPath, url) ;
			response.sendRedirect(url);
			return false;
		}
		return true;
	}
}

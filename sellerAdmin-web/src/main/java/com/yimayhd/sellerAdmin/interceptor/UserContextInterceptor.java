package com.yimayhd.sellerAdmin.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.biz.MenuBiz;
import com.yimayhd.sellerAdmin.biz.helper.MenuHelper;
import com.yimayhd.sellerAdmin.cache.MenuCacheMananger;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.vo.menu.MenuVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.constant.SessionConstant;

public class UserContextInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger("UserContextInterceptor");

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private MenuCacheMananger menuCacheMananger ;
	@Autowired
	private MenuBiz menuBiz;
	
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		UserDO userDO = sessionManager.getUser(request);
		if (userDO != null) {
			request.setAttribute(SessionConstant.REQ_ATTR_USERID, userDO.getId());
			request.setAttribute(SessionConstant.REQ_ATTR_USER, userDO);
			long userId = userDO.getId() ;
			String pathInfo = request.getPathInfo() ; 
			String method = request.getMethod();
//			System.err.println(pathInfo);
			menuBiz.cacheUserMenus2Tair(userId);
			
			List<MenuVO> menus = menuCacheMananger.getUserMenus(userId);
//			System.err.println(JSON.toJSONString(menus));
			MenuVO menu = MenuHelper.getSelectedMenu(menus, pathInfo, method);
			/* wdtest 上线前放开
			if(RequestMethod.GET.name().equalsIgnoreCase(method)  && menu == null  && !pathInfo.toLowerCase().contains("home") ){
				String url = UrlHelper.getUrl( rootPath, "/error/lackPermission") ;
				response.sendRedirect(url);
				return false;
			}*/
			
			request.setAttribute("menus", menus);
			request.setAttribute("currentMenu", menu);
		} else {
			// 非登陆状态的时候，清空token
			SessionHelper.cleanCookies(response);
		}
		return true;

	}
}

package com.yimayhd.sellerAdmin.interceptor;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.biz.MenuBiz;
import com.yimayhd.sellerAdmin.biz.helper.MenuHelper;
import com.yimayhd.sellerAdmin.cache.MenuCacheMananger;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.vo.menu.MenuVO;
import com.yimayhd.sellerAdmin.repo.MenuRepo;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.constant.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class UserContextInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger("UserContextInterceptor");

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private MenuCacheMananger menuCacheMananger;
    @Autowired
    private MenuBiz menuBiz;

    @Autowired
    private MenuRepo menuRepo;

    @Value("${sellerAdmin.rootPath}")
    private String rootPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        UserDO userDO = sessionManager.getUser(request);
        if (userDO != null) {
            request.setAttribute(SessionConstant.REQ_ATTR_USERID, userDO.getId());
            request.setAttribute(SessionConstant.REQ_ATTR_USER, userDO);
            long userId = userDO.getId();
            String pathInfo = request.getServletPath();
            String method = request.getMethod();
//			System.err.println(pathInfo);
            //menuBiz.cacheUserMenus2Tair(userId);
            List<MenuVO> menus = null;
            MenuVO menu = null;
           /* if (WebResourceConfigUtil.isTestMode()) {

                //权限整合
                menus = menuRepo.getUserMenus(userId, false);
//				System.err.println(JSON.toJSONString(menus));
                //menu = MenuHelper.getSelectedMenu(menus, pathInfo, method);
            } else {
                //menus = menuCacheMananger.getUserMenus(userId);
                menus = menuRepo.getUserMenus(userId, true);
                //menu = MenuHelper.getSelectedMenu(menus, pathInfo, method);
            }*/
            // List<MenuVO> menus2 = menuRepo.getUserMenus(userId, false);

            HashMap<String, MenuVO> allMenus = menuCacheMananger.getAllMenus();
            MenuVO checkMenu = MenuHelper.checkMenu(allMenus, pathInfo);
            menus = menuRepo.getUserMenus(userId, true);
            //menu = MenuHelper.getSelectedMenu(menus, pathInfo, method);
            menu = MenuHelper.getSelectedMenu(menus, pathInfo, method);
            if (checkMenu != null && RequestMethod.GET.name().equalsIgnoreCase(method) && menu == null && !pathInfo.toLowerCase().contains("home")) {
                logger.error("lackPermission userId={}, method={}  pathInfo={}  uri={},checkMenu={}", userDO.getId(), method, pathInfo, request.getRequestURI(), JSON.toJSONString(checkMenu));
                String url = UrlHelper.getUrl(rootPath, "/error/lackPermission");
                response.sendRedirect(url);
                return false;
            }
            request.setAttribute("menus", menus);
            request.setAttribute("currentMenu", menu);
        } else {
            // 非登陆状态的时候，清空token
            SessionHelper.cleanCookies(response);

        }
        return true;

    }
}

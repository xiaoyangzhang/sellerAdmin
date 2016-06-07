package com.yimayhd.sellerAdmin.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;


/**
 * Created by Administrator on 2015/10/26.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	// 0:不限;1：GET;2:POST;3:PUT;4:DELETE 
	
	public static Map<String,Integer> map = new HashMap<String, Integer>();
	static {
		map.put("ALL", 0);
		map.put("GET", 1);
		map.put("POST", 2);
		map.put("PUT", 3);
		map.put("DELETE", 4);
	}
	
    @Autowired
    private UserService service;

	@Autowired
	private SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	//TODO:待功能完善后放开以下注释即可
		String url = new UrlPathHelper().getLookupPathForRequest(request);
        //String url = request.getRequestURI();
        int reqType= map.get(request.getMethod());
		if(Pattern.compile("^/resources/.*").matcher(url).matches()){
			return true;
		}
        if(!url.equals("/user/noPower") && !url.equals("/toLogin") && !url.equals("/login") && !url.equals("/main")){
            long userId = sessionManager.getUserId();
            List<HaMenuDO> haMenuList = service.getUrlListByUserId(userId);
            if(null !=haMenuList && haMenuList.size()>0){
            	for (HaMenuDO haMenuDO : haMenuList) {

					String dbURL =haMenuDO.getUrl();
					int dbReqType=haMenuDO.getReqType();

					if(0 !=dbReqType && reqType !=dbReqType){
						continue;
					}
					if(this.matchURL(url,dbURL)){
						return true;
					}
				}
            }
			response.sendRedirect("/user/noPower?urlName=" + url);
			return false;
        }
		return true;

    }


	/**
	 * 判断两个url是否相匹配
	 * @param url 请求url
	 * @param dbUrl mapping url
	 * @return 是否匹配
	 */
    public boolean matchURL(String url,String dbUrl){
		String dbUrlRegex = this.createUrlRegex(dbUrl);
		Pattern pattern = Pattern.compile(dbUrlRegex);
		if(pattern.matcher(url).matches()){
			return true;
		}
		return false;
	}

	/**
	 * 构建url匹配正则
	 * @param dbUrl
	 * @return 正则
	 */
	private String createUrlRegex(String dbUrl){
		String dbUrlRegex = dbUrl;
		Pattern pattern = Pattern.compile("(\\{\\S+?\\})");
		Matcher matcher = pattern.matcher(dbUrl);
		while(matcher.find()){
			System.out.println(matcher.group());
			dbUrlRegex = dbUrlRegex.replace(matcher.group(),"[^\\s\\/]+?");
		}
		return dbUrlRegex;
	}



}

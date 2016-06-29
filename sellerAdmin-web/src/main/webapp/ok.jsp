<%@page import="java.io.PrintWriter"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.yimayhd.user.client.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	UserService userService = (UserService) wac.getBean("userServiceRef");
	int code = 0;
	if (userService == null) {
		code = -1;
	}
	PrintWriter write = response.getWriter() ;
	write.println(code);
	write.flush();
	write.close();


%>


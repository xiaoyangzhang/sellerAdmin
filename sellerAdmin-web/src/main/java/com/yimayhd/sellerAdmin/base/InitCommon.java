package com.yimayhd.sellerAdmin.base;

import javax.servlet.http.HttpServlet;

/**
 * Created by Administrator on 2015/10/26.
 */
public class InitCommon extends HttpServlet{

    public void init() {
        this.getServletContext().setAttribute("testData","hello");
    }

}

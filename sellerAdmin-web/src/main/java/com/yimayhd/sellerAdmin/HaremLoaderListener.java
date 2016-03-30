package com.yimayhd.sellerAdmin;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yimayhd.sellerAdmin.config.ResourceConfig;

/**
 * Created by czf on 2015/11/2.
 */
public class HaremLoaderListener implements ServletContextListener {

    public final static String CONFIG_BEAN_NAME = "properties";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //注入配置文件
        WebApplicationContext webApplication = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        Properties configProperties = (Properties) webApplication
                .getBean(CONFIG_BEAN_NAME);
        ResourceConfig.getInstance().init(configProperties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

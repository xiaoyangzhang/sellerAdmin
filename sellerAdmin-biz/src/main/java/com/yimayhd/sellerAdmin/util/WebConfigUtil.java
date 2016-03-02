package com.yimayhd.sellerAdmin.util;

import com.yimayhd.sellerAdmin.config.ResourceConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * 由WebResourceConfig工具类代替,弃用
 * Created by Administrator on 2015/10/31.
 */
public class WebConfigUtil extends WebApplicationContextUtils {

    public final static String CONFIG_BEAN_NAME = "properties";

    private final static String TFS_ROOT_PATH_KEY = "sellerAdmin.tfsRootPath";

    public static Object getConfigByKey(String key, ServletContext sc) {
        WebApplicationContext webApplication = getRequiredWebApplicationContext(sc);
        Properties configProperties = (Properties) webApplication
                .getBean(CONFIG_BEAN_NAME);
        if (configProperties == null) {
            throw new IllegalStateException(
                    "No Properties found: no properties bean registered?");
        }
        return configProperties.get(key);
    }

    public static Object getConfigByKey(String key, HttpServletRequest request) {
        WebApplicationContext webApplication = getRequiredWebApplicationContext(request
                .getServletContext());
        Properties configProperties = (Properties) webApplication
                .getBean(CONFIG_BEAN_NAME);
        if (configProperties == null) {
            throw new IllegalStateException(
                    "No Properties found: no properties bean registered?");
        }
        return configProperties.get(key);
    }

    public static String getTfsRootPath(HttpServletRequest request) {
        return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
    }

    public static String getPropertyByKey(String key,HttpServletRequest request) {
        return (String) getConfigByKey(key, request);
    }

}

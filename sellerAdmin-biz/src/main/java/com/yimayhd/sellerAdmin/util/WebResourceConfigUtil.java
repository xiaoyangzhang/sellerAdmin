package com.yimayhd.sellerAdmin.util;

import com.yimayhd.sellerAdmin.config.ResourceConfig;

/**
 * 暴露给view用的
 * Created by Administrator on 2015/11/3.
 */
public class WebResourceConfigUtil {
    private final static String TFS_ROOT_PATH_KEY = "sellerAdmin.tfsRootPath";
    private final static String STATIC_RESOURCE_PAHT_KEY = "sellerAdmin.staticResourcesPath";
    private final static String ACTION_DEFAULT_FONT_PATH_KEY= "sellerAdmin.actionDefaultFontPath";
    private final static String ACTION_UPLOAD_FILE_PATH_KEY = "actionUploadFilePath";
    private final static String ACTION_UPLOAD_FILES_PATH_KEY = "actionUploadFilesPath";

    //分销
    private final static String ITEM_IMG_URI_PATH = "item.img.uri";
    
    public static String getTfsRootPath() {
        return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
    }

    public static String getStaticResourcesPath(){
        return ResourceConfig.getInstance().getValueByKey(STATIC_RESOURCE_PAHT_KEY);
    }

    public static String getActionDefaultFontPath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_DEFAULT_FONT_PATH_KEY);
    }

    public static String getActionUploadFilePath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILE_PATH_KEY);
    }

    public static String getActionUploadFilesPath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILES_PATH_KEY);
    }
    public static String getResourceVersion(){
        return "20151227";
    }
    public static String getItemImgUrlPath(){
    	return ResourceConfig.getInstance().getValueByKey(ITEM_IMG_URI_PATH);
    }
}

package com.yimayhd.sellerAdmin.util;

import com.yimayhd.sellerAdmin.config.ResourceConfig;
import com.yimayhd.sellerAdmin.constant.Constant;

/**
 * 暴露给view用的 Created by Administrator on 2015/11/3.
 */
public class WebResourceConfigUtil {
	private final static String ROOT_PATH_KEY = "sellerAdmin.rootPath";
	private final static String TFS_ROOT_PATH_KEY = "sellerAdmin.tfsRootPath";
	private final static String STATIC_RESOURCE_PAHT_KEY = "sellerAdmin.staticResourcesPath";
	private final static String ACTION_DEFAULT_FONT_PATH_KEY = "sellerAdmin.actionDefaultFontPath";
	private final static String ACTION_UPLOAD_FILE_PATH_KEY = "actionUploadFilePath";
	private final static String ACTION_UPLOAD_FILES_PATH_KEY = "actionUploadFilesPath";

	// 分销
	private final static String ITEM_IMG_URI_PATH = "item.img.uri";
	private final static String DOMAIN = "domain";
	private final static String ENV = "env";
	private final static String RESOURCE_PATH_JIUXIU = "resource.path.jiuxiu";
	private final static String RESOURCE_PATH_ADMIN = "resource.path.admin";
//	private final static String RESOURCE_PATH = "resource.path";
	private final static String JIUXIU_OFFICIAL_SITE = "jiuxiu.official.site";
	private final static String FILEGW_DOMAIN = "filegw.domain";

	private final static String filegwPath = "filegw.path";
	private final static String JIUXIU_IM_SITE="jiuxiu.im.site";
	private final static String CONSULT_CATEGORY="consult.catetory.id";
	private final static String LOGIN_CHECK_IP_COUNT="login.check.ip.count";
	private final static String LOGIN_CHECK_USER_COUNT="login.check.user.count";
	public static String getConsultCategory() {
		return ResourceConfig.getInstance().getValueByKey(CONSULT_CATEGORY);
	}
	public static String getIMPath() {
		return ResourceConfig.getInstance().getValueByKey(JIUXIU_IM_SITE);
	}
	public static String getRootPath() {
		return ResourceConfig.getInstance().getValueByKey(ROOT_PATH_KEY);
	}
	public static String getFilegwPath() {
		return ResourceConfig.getInstance().getValueByKey(FILEGW_DOMAIN)+"/file/upload_string";
	}
	public static String getFilegwDomain() {
		return ResourceConfig.getInstance().getValueByKey(FILEGW_DOMAIN);
	}
	public static String getJiuXiuOfficialSite() {
		return ResourceConfig.getInstance().getValueByKey(JIUXIU_OFFICIAL_SITE);
	}

	public static String getResourcePathJiuXiu() {
		return ResourceConfig.getInstance().getValueByKey(RESOURCE_PATH_JIUXIU);
	}

	public static String getResourcePathAdmin() {
		return ResourceConfig.getInstance().getValueByKey(RESOURCE_PATH_ADMIN);
	}

//	public static String getResourcePath() {
//		return ResourceConfig.getInstance().getValueByKey(RESOURCE_PATH);
//	}

	public static String getTfsRootPath() {
		return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
	}

	public static String getStaticResourcesPath() {
		return ResourceConfig.getInstance().getValueByKey(STATIC_RESOURCE_PAHT_KEY);
	}

	public static String getActionDefaultFontPath() {
		return ResourceConfig.getInstance().getValueByKey(ACTION_DEFAULT_FONT_PATH_KEY);
	}

	public static String getActionUploadFilePath() {
		return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILE_PATH_KEY);
	}

	public static String getActionUploadFilesPath() {
		return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILES_PATH_KEY);
	}

	public static String getResourceVersion() {
		return "20160414";
	}

	public static String getItemImgUrlPath() {
		return ResourceConfig.getInstance().getValueByKey(ITEM_IMG_URI_PATH);
	}

	public static String getDomain() {
		return ResourceConfig.getInstance().getValueByKey(DOMAIN);
	}

	public static boolean isTestMode() {
		String env = ResourceConfig.getInstance().getValueByKey(ENV);
		if (Constant.ENV_PROD.equalsIgnoreCase(env) || Constant.ENV_PRE.equalsIgnoreCase(env) ) {
			return false;
		}
		return true;
	}
	public static String getLoginCheckIPCount() {
		return ResourceConfig.getInstance().getValueByKey(LOGIN_CHECK_IP_COUNT);
	}
	public static String getLoginCheckUserCount() {
		return ResourceConfig.getInstance().getValueByKey(LOGIN_CHECK_USER_COUNT);
	}
}

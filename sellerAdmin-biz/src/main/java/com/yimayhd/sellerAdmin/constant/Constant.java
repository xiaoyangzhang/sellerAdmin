
package com.yimayhd.sellerAdmin.constant;

import com.yimayhd.user.session.manager.constant.SessionConstant;

/**
 * Created by czf on 2015/12/22.
 */
public class Constant {
	public static final int MIN_CODE = 23000000;//错误码最小
    public static final int MAX_CODE = 25000000;//错误码最大
	public static final long YIMAY_OFFICIAL_ID = 1000 * 10000;// 商贸项目的官方用户id
	public static final String DEFAULT_CONTRACT_TFS_CODE = "T1WtJTBXWT1RXx1p6K.html"; // 默认合同
	public static final String YIMAY_CUSTOMER_SERVICE = "4000696888";

	public static final int GF_OFFICIAL_ID = 1000;
	public static final int GF_DOMAIN = 1100;

	public static final String TOKEN_SERVER = SessionConstant.TOKEN_SERVER;

	public static final String TOKEN_CLIENT = SessionConstant.TOKEN_CLIENT;

	// public static final String LOGIN_URL = "/user/login";

	public static final int DOMAIN_B2CPC = 1000;

	public static final int DOMAIN_JIUXIU = 1200;

	public static final String ENV_PROD = "prod";
	public static final int APPID_JIUXIU = 26;//商户后台应用id
	
	
//	public static final String TFS_URL = "http://img.test.yimayholiday.com/v1/tfs/";

	public static final String MOBILE_PRE = "+86";

	public static final String APP = "selleradmin";

	public static final String MENU_PARENT_FLAG = "#";

	public static final String SYMBOL_SEMIONLON = ";";

	public static final int MERCHANT_TYPE_ACCESS = 1;

	public static final int MERCHANT_TYPE_WAIT = 2;

	public static final int MERCHANT_TYPE_NOTTHROW = 3;
	
	public static final int MERCHANT_TYPE_HALF = 4;
	// 达人基本信息店铺头图数量
	public static final int TALENT_SHOP_PICNUM = 5;

	public static final String MERCHANT_NAME_CN = "商户";

	public static final String TALENT_NAME_CN = "达人";

	public static final String DOT = ".";

	public static final String ALL_PLACE_CODE = "-1";
	
	public static final String SITE_DOMAIN = "jiuxiulvxing.com";
	
	public static final String JIUXIU_NICKNAME_HEAD = "JX";

	public static final String UUIDKEY="uuidkey_";//+uuid

	/**
	 * 正则表达式：手机号码
	 */
	public static final String REGEX_MOBILE = "^1\\d{10}$";
	 
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([\\s\\S]*)@([\\s\\S]*)$";
    /**
     * 身份证
     */
    public static final String ID_CARD="0";
    /**
     * 护照
     */
    public static final String PASSPORT="2";
    /**
     * 驾驶证
     */
    public static final String CAR_LICENSE="1";
    /**
     * 导游证
     */
    public static final String GUIDE_CERTIFICATE="3";
    /**
     * 国内社总社
     */
    public static final int HOME_HEAD_AGENCY=12;
    /**
     * 国内社分社
     */
    public static final int HOME_BRANCH_AGENCY=13;
    /**
     * 出境社总社
     */
    public static final int BROAD_HEAD_AGENCY=14;
    /**
     * 出境社分社
     */
    public static final int BROAD_BRANCH_AGENCY=15;
	/**
	 * 商家类目校验信息
	 */
	public static final String SELLER_CATEGORY_CHECK="SELLER_CATEGORY_CHECK_";
	/**
	 * 优惠劵标题长度限制
	 */
	public static final int VOUCHET_TITILE_LIMIT=10;
	/**
	 * 优惠劵没人领卷限制
	 */
	public static final int VOUCHET_GET_MAX_LIMIT=10;
	/**
	 * 优惠劵发劵限制
	 */
	public static final int VOUCHET_PUT_MAX_LIMIT=10000;
	/**
	 * 优惠劵领卷时间间隔
	 */
	public static final int VOUCHET_TIME_LIMIT=60;
	 
	/**
	 * 九休商品类目根节点
	 */
	public static final int JX_ITEM_ROOT = 200;
	public static final int LAYER_NUM = 5;
}

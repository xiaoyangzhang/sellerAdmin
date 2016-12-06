package org.yimayhd.sellerAdmin;

import java.io.Serializable;




import net.pocrd.entity.AbstractReturnCode;

public class SellerReturnCode extends AbstractReturnCode implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -6889188638812671186L;

	public SellerReturnCode(int code,String desc) {
		super(desc, code);
	}
	public static final int PARAM_ERROR_C = 24005000;
    public static final SellerReturnCode PRAM_ERROR = new SellerReturnCode(PARAM_ERROR_C, "参数错误");
    public static final int SYSTEM_ERROR_C = 24005001;
    public static final SellerReturnCode SYSTEM_ERROR = new SellerReturnCode(SYSTEM_ERROR_C, "系统错误");
    public static final int ON_SALE_ERROR_C = 24005002;
    public static final SellerReturnCode ON_SALE_ERROR= new SellerReturnCode(ON_SALE_ERROR_C, "上架失败");
    public static final int OFF_SALE_ERROR_C = 24005003;
    public static final SellerReturnCode OFF_SALE_ERROR = new SellerReturnCode(OFF_SALE_ERROR_C, "下架失败");
    public static final int DELETE_ERROR_C = 24005004;
    public static final SellerReturnCode DELETE_ERROR = new SellerReturnCode(DELETE_ERROR_C, "删除失败");
    public static final int ON_SALE_SUCCESS_C = 24005005;
    public static final SellerReturnCode ON_SALE_SUCCESS= new SellerReturnCode(ON_SALE_SUCCESS_C, "上架成功");
    public static final int OFF_SALE_SUCCESS_C = 24005006;
    public static final SellerReturnCode OFF_SALE_SUCCESS = new SellerReturnCode(OFF_SALE_SUCCESS_C, "下架成功");
    public static final int DELETE_SUCCESS_C = 24005007;
    public static final SellerReturnCode DELETE_SUCCESS = new SellerReturnCode(DELETE_SUCCESS_C, "删除成功");
    public static final int ADD_ITEM_ERROR_C = 24005008;
    public static final SellerReturnCode ADD_ITEM_ERROR = new SellerReturnCode(ADD_ITEM_ERROR_C, "发布失败");
    public static final int UPDATE_ITEM_ERROR_C = 24005009;
    public static final SellerReturnCode UPDATE_ITEM_ERROR = new SellerReturnCode(UPDATE_ITEM_ERROR_C, "更新失败");
    public static final int QUERY_MERCHANT_ERROR_C = 24005010;
    public static final SellerReturnCode QUERY_MERCHANT_ERROR = new SellerReturnCode(QUERY_MERCHANT_ERROR_C, "获取该店铺信息失败");
    public static final int QUERY_MERCHANT_QUALIFICATION_ERROR_C = 24005011;
    public static final  SellerReturnCode QUERY_MERCHANT_QUALIFICATION_ERROR = new SellerReturnCode(QUERY_MERCHANT_QUALIFICATION_ERROR_C, "获取该店铺资质信息失败");
    public static final int QUERY_PROPERTY_ERROR_C = 24005012;
    public static final SellerReturnCode QUERY_PROPERTY_ERROR = new SellerReturnCode(QUERY_PROPERTY_ERROR_C, "获取属性失败");
 public static final int NO_AUTHORITY_ERROR_C = 24005013;
    public static final SellerReturnCode NO_AUTHORITY_ERROR = new SellerReturnCode(QUERY_PROPERTY_ERROR_C, "对不起，您没有权限");
}

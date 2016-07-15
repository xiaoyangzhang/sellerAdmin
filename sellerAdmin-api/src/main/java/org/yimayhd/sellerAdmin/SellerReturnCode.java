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
	public static final int PARAM_ERROR_C = 24004000;
    public static final SellerReturnCode PRAM_ERROR = new SellerReturnCode(PARAM_ERROR_C, "参数错误");
    public static final int SYSTEM_ERROR_C = 24004001;
    public static final SellerReturnCode SYSTEM_ERROR = new SellerReturnCode(SYSTEM_ERROR_C, "系统错误");
}

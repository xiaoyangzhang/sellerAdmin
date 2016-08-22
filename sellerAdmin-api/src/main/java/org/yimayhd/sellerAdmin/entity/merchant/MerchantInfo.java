package org.yimayhd.sellerAdmin.entity.merchant;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("店铺信息")
public class MerchantInfo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -3027214571608596395L;
	@Description("主页信息")
	public HomePage homePage;
	@Description("客服电话")
	public String serviceTel;
	@Description("营业执照号")
	public String saleLicenseNum;
//	@Description("店铺简介")
//	public String merchantDesc;
}

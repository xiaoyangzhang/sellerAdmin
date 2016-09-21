package org.yimayhd.sellerAdmin.entity.merchant;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("店铺简介")
public class MerchantDesc implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 3945659028456003361L;
	@Description("店铺简介")
	public String shopDesc;
}

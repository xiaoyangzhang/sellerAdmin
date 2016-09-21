package org.yimayhd.sellerAdmin.entity.merchant;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("首页信息")
public class HomePage implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -1724965450955200760L;
	@Description("店铺名称")
	public String merchantName;
	@Description("店铺头像")
	public String avater;
	
}

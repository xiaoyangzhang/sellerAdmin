package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("商品详情")
public class ItemDetail implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 3886089390572328400L;
	@Description("商品管理")
	public ItemManagement goodsManagement;
	@Description("商品id")
	public long id;
//	@Description("商品信息")
//	public PublishServiceDO publishServiceDO;
}

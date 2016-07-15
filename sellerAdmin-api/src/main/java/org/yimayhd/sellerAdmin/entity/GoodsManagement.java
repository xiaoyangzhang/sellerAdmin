package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;

@Description("商品管理")
public class GoodsManagement implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 3640262198164481539L;
	@Description("商品状态")
	public int state;
	@Description("发布中的商品数量")
	public int onSaleCount;
	@Description("待发布的商品数量")
	public int offSaleCount;
	@Description("已售数量")
	public int saleVolume;
	@Description("商品信息")
	public PublishServiceDO publishServiceDO;
}

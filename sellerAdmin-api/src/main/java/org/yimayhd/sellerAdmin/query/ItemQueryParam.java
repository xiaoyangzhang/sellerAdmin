package org.yimayhd.sellerAdmin.query;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("查询商品")
public class ItemQueryParam implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -1116253137327217010L;
	@Description("商品id")
	public long id;
	@Description("类目id")
	public long categoryId;
	@Description("页码")
	public int pageNo;
	@Description("每页显示条数")
	public int pageSize;
	@Description("商品发布状态2:已发布3:待发布")
	public int serviceState;
	@Description("商品状态")
	public int state;
}

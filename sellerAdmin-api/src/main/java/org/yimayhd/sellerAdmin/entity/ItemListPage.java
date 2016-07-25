package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;
import java.util.List;

import net.pocrd.annotation.Description;

public class ItemListPage implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1933074532898407002L;

	@Description("页码")
	public int pageNo;
	@Description("每页显示条数")
	public int pageSize;
	@Description("商品管理列表")
	public List<ItemManagement> ItemManagements;
}

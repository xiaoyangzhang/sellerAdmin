package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;
import java.util.List;

import net.pocrd.annotation.Description;
@Description("咨询商品属性")
public class ConsultCategoryInfo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8778326246640682081L;
	@Description("咨询商品属性集合")
	public List<ItemProperty> itemProperties;
}

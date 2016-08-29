package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("咨询商品属性")
public class ItemProperty implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8465160917954383395L;
	@Description("id")
	public long id;
	@Description("属性名称")
	public String text;
	@Description("属性类型")
	public String type;
	@Description("属性值")
	public String value;
	@Description("属性默认文案")
	public String defaultDesc;
	
}

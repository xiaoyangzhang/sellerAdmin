package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.pocrd.annotation.Description;
@Description("服务区域")
public class ServiceArea implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 4774361673355367500L;
	@Description("用户id")
	public long outId;
	@Description("地区code")
	public long areaCode;
	@Description("地区名称")
	public String areaName;
	@Description("标签类型")
	public int outType;
	
	@Description("系统code")
	public int domain;

	
	
}

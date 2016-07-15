package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("发布服务的信息")
public class PublishServiceDO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1247048066341573956L;

	@Description("头图")
	public String avater;
	@Description("标题")
	public String title;
	@Description("类目")
	public int categoryType;
	@Description("服务区域")
	public String serviceAreas;
	@Description("图文详情")
	public String pictureTextString;
	@Description("费用说明")
	public String feeDesc;
	@Description("预定说明")
	public String bookingTip;
	@Description("退改规定")
	public String refundRule;
	@Description("原价")
	public int oldPrice;
	@Description("原价时间")
	public int oldTime;
	@Description("优惠价")
	public double discountPrice;
	@Description("优惠时间")
	public double discountTime;
}

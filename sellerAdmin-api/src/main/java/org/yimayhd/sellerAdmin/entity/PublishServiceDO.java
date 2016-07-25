package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;
import java.util.List;

import net.pocrd.annotation.Description;
@Description("发布服务的信息")
public class PublishServiceDO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1247048066341573956L;
	@Description("商品id")
	public long id;
	@Description("头图")
	public String avater;
	@Description("标题")
	public String title;
	@Description("类目")
	public int categoryType;
	@Description("服务区域")
	public List<TagRelationInfoVO> TagRelationInfoVOs;
	@Description("图文详情")
	public List<PictureTextItemVo> pictureTextItems;
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
	public int discountPrice;
	@Description("优惠时间")
	public int discountTime;
	@Description("商品发布状态1:已发布2:待发布")
	public int serviceState;
}

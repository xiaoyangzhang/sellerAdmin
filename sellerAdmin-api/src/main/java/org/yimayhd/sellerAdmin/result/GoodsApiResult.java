package org.yimayhd.sellerAdmin.result;

import java.io.Serializable;

import org.yimayhd.sellerAdmin.entity.EvaluateInfo;
import org.yimayhd.sellerAdmin.entity.GoodsDetail;
import org.yimayhd.sellerAdmin.entity.GoodsManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.entity.TalentInfo;

import net.pocrd.annotation.Description;
@Description("商品信息")
public class GoodsApiResult implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8660801869518465676L;
	@Description("发布是商品详情")
	public PublishServiceDO publishServiceDO;
	@Description("商品管理")
	public GoodsManagement goodsManagement;
	@Description("商品详情")
	public GoodsDetail goodsDetail;
	@Description("达人信息")
	public TalentInfo talentInfo;
	@Description("评价")
	public EvaluateInfo evaluateInfo;
}

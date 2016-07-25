package org.yimayhd.sellerAdmin.result;

import java.io.Serializable;
import java.util.List;

import org.yimayhd.sellerAdmin.entity.EvaluateInfo;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.entity.TalentInfo;

import net.pocrd.annotation.Description;
@Description("商品信息")
public class ItemApiResult implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8660801869518465676L;
//	@Description("发布是商品详情")
//	public PublishServiceDO publishServiceDO;
	@Description("商品管理列表")
	public List<ItemManagement> ItemManagements;
	@Description("商品详情")
	public ItemDetail ItemDetail;
	@Description("达人信息")
	public TalentInfo talentInfo;
	@Description("评价")
	public EvaluateInfo evaluateInfo;
}

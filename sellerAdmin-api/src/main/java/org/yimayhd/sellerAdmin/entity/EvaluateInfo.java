package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;
import java.util.List;

import net.pocrd.annotation.Description;
@Description("评价信息")
public class EvaluateInfo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 7928979168493434613L;
	@Description("头像")
	public String avater;
	@Description("昵称")
	public String nickName;
	@Description("星级")
	public String level;
	@Description("评价时间")
	public String dateTime;
	@Description("评价信息")
	public List<PictureTextItemVo> PictureTextItems;
}

package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;
@Description("达人信息")
public class TalentInfo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 5270839900414109431L;
	@Description("头像")
	public String avater;
	@Description("昵称")
	public String nickName;
	@Description("身份认证状态")
	public int certificateSate;
}

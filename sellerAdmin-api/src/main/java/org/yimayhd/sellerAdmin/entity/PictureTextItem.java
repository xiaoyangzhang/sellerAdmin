package org.yimayhd.sellerAdmin.entity;

import java.io.Serializable;

import net.pocrd.annotation.Description;

/**
 * 图文项
 * 
 * @author yebin
 *
 */
@Description("评价中的图文信息")
public class PictureTextItem implements Serializable{
	private static final long serialVersionUID = -6988747828375320041L;
	@Description("图文类型")
	public String type;
	@Description("图文的值")
	public String value;
	
}

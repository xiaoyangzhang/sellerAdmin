package com.yimayhd.sellerAdmin.model;

import java.util.List;
import java.util.Map;

import com.yimayhd.sellerAdmin.base.BaseModel;

public class ProductInfoVO extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876789964482574818L;

	private int type;// 产品类型
	private String name;// 产品名称
	private String image;// 图片地址
	private List<Integer> themes; // 主题
	private List<Integer> tags; // 标签
	private long publisher;// 发布者
	private List<String> fromAddress; // 出发地
	private List<String> toAddress; // 目的地
	private String highlights; // 亮点（跟团游），特色（自由行）
	private String recommond; // 代言（跟团游），推荐（自由行）
	private Map<String, String> faq;// 须知
}

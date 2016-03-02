package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.List;

public class RelevanceRecommended implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;//枚举的name
	private String descName;
	private int type;//类型，如景区，酒店 按枚举取
	private int cityCode;//关联的城市名称
	List<Long> resourceId;//数组类型的 关联的推荐类id
	List<SpecialShowCase> listSpecialShowCase;
	private String subhead;//副标题
	
	public List<SpecialShowCase> getListSpecialShowCase() {
		return listSpecialShowCase;
	}
	public void setListSpecialShowCase(List<SpecialShowCase> listSpecialShowCase) {
		this.listSpecialShowCase = listSpecialShowCase;
	}
	public String getDescName() {
		return descName;
	}
	public void setDescName(String descName) {
		this.descName = descName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getSubhead() {
		return subhead;
	}
	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}
	
	public List<Long> getResourceId() {
		return resourceId;
	}
	public void setResourceId(List<Long> resourceId) {
		this.resourceId = resourceId;
	}





	public class SpecialShowCase implements Serializable{//关联直播的时候需要保存直播的图片url,比较特殊
		private static final long serialVersionUID = 1L;
		
		int id;
		String url;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
		
	}
	
	

}

package com.yimayhd.sellerAdmin.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: QualificationVO
* @Description: 管理商户后台的资质
* @author zhangxy
* @date 2016年6月2日 下午9:07:04
*
 */
public class QualificationVO {

	protected static final Logger log = LoggerFactory.getLogger("QualificationVO");
	
	private String tip;
	private boolean required;
	private long id;
	private String title;
	private String content;
	private int type ;
	private int num ;//一种资质图片的数量
	private String overallNote;//整体备注
	private long qualificationId;
	
	public long getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(long qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getOverallNote() {
		return overallNote;
	}
	public void setOverallNote(String overallNote) {
		this.overallNote = overallNote;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getContentList(){
		List<String> list = new ArrayList<>() ;
		if( content == null ){
			content = "" ;
		}
		String[] arrays = content.split(",") ;
		int length = arrays.length ;
		for(int i=0 ; i<num ; i++ ){
			if( i < length ){
				list.add(arrays[i]) ;
			}else{
				list.add("") ;
			}
		}
		return list ;
	}
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}

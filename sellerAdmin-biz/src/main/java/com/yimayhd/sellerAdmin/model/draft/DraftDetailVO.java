package com.yimayhd.sellerAdmin.model.draft;

import java.io.Serializable;

/**
 * Created by liuxp on 2016/6/7.
 */
public class DraftDetailVO implements Serializable{

	/**
	 * serialVersionUID = -1310971913489620869L;
	 */
	private static final long serialVersionUID = -1310971913489620869L;
    
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * json数据字符串
	 */
	private String JSONStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJSONStr() {
		return JSONStr;
	}

	public void setJSONStr(String jSONStr) {
		JSONStr = jSONStr;
	}
}

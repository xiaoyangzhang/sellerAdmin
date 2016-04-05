package com.yimayhd.sellerAdmin.model.line.nk;

import java.io.Serializable;

/**
 * 图文项
 * 
 * @author yebin
 *
 */
public class NeedKnowItemVo implements Serializable {
	private static final long serialVersionUID = -3039187338157956470L;
	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

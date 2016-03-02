package com.yimayhd.sellerAdmin.model.travel;

/**
 * 额外信息
 * 
 * @author yebin
 *
 */
public class ExtraInfo {
	private String title;
	private String content;

	public ExtraInfo(String title, String content) {
		this.title = title;
		this.content = content;
	}

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

package com.yimayhd.sellerAdmin.model.line;

import java.io.Serializable;

/**
 * 标签
 * 
 * @author yebin
 *
 */
public class TagDTO implements Serializable{
	private long id;
	private String name;

	public TagDTO() {
	}

	public TagDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

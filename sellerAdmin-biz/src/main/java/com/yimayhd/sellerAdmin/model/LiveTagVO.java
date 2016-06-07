package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.enums.TagType;

public class LiveTagVO {
	private long id;
	private String name;
	private int score;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public TagInfoAddDTO toTagInfo() {
		TagInfoAddDTO tagInfo = new TagInfoAddDTO();
		BeanUtils.copyProperties(this, tagInfo);
		tagInfo.setTagId(this.id);
		tagInfo.setOutType(TagType.LIVESUPTAG.getType());
		return tagInfo;
	}
}

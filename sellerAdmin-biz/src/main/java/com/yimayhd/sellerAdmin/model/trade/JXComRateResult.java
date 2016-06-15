package com.yimayhd.sellerAdmin.model.trade;

import com.yimayhd.commentcenter.client.result.ComRateResult;

public class JXComRateResult extends ComRateResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickName; // 用户昵称

	private String userPhoto; // 用户头像

	private String itemName;
	
	private double score_;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getScore_() {
		return score_;
	}

	public void setScore_(double score_) {
		this.score_ = score_;
	}

	
}

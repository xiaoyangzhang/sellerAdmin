package com.yimayhd.sellerAdmin.model.query;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.snscenter.client.domain.ClubInfoDO;

public class ClubInfo extends ClubInfoDO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public String createUserName;
	
	public List<Long> listThemeId ;/**关联的主题id*/
	
	public List<Long> getListThemeId() {
		return listThemeId;
	}
	public void setListThemeId(List<Long> listThemeId) {
		this.listThemeId = listThemeId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
}

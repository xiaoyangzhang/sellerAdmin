package com.yimayhd.sellerAdmin.model.vo.user;

import java.io.Serializable;

import com.yimayhd.user.entity.UserContact;

/**
 * 联系人vo
 * 
 * @author yebin
 *
 */
public class UserContactVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String contactName;
	private String contactPhone;
	private String contactEmail;

	public UserContactVo(UserContact userContact) {
		this.id = userContact.id;
		this.contactName = userContact.contactName;
		this.contactPhone = userContact.contactPhone;
		this.contactEmail = userContact.contactEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

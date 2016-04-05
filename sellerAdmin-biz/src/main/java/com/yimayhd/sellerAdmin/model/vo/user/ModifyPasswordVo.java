/**  
 * Project Name:b2cpc-web  
 * File Name:RetrievePasswordVo.java  
 * Package Name:com.yimayhd.b2cpc.web.model.user  
 * Date:2016年2月15日下午3:17:20    
 *  
*/

package com.yimayhd.sellerAdmin.model.vo.user;

import java.io.Serializable;

/**
 * 
 * @author wzf
 *
 */
public class ModifyPasswordVo implements Serializable {

	private static final long serialVersionUID = 3865192747999056809L;

	private String newPassword;
	private String oldPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}

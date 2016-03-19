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
 * ClassName:RetrievePasswordVo <br/>
 * Date: 2016年2月15日 下午3:17:20 <br/>
 * 
 * @author zhangjian
 * @version
 * @see
 */
public class RetrievePasswordVo implements Serializable{

	private static final long serialVersionUID = 3865192747999056809L;

	private String username;

	private String verifyCode;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

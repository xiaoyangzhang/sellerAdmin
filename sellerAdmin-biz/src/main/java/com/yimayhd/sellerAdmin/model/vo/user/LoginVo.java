package com.yimayhd.sellerAdmin.model.vo.user;

import java.io.Serializable;

/**
 * 
 * ClassName: LoginVo <br/>
 * Description: 登录VO . <br/>
 * date: 2016年2月15日 下午2:14:36 <br/>
 * 
 * @author zhangjian
 * @version
 */
public class LoginVo implements Serializable{
	
	private static final long serialVersionUID = -1161917511529535967L;

	private String username;

	private String password;

	private String token;

	private String imageCode;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
}

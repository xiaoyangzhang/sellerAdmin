package com.yimayhd.sellerAdmin.model.vo.user;

import java.io.Serializable;

/**
 * 
 * ClassName: RegisterVo <br/>
 * Description: 注册VO . <br/>
 * date: 2016年2月15日 下午2:14:15 <br/>
 * 
 * @author zhangjian
 * @version
 */
public class RegisterVo implements Serializable {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 7441987595037533893L;
	// 昵称
	private String nickname;
	// 用户名(手机号码)
	private String username;
	// 密码
	private String password;
	
	private String verifyCode;
	/**
	 *是否会员
	 */
	private Boolean VIP;
	
	

	public Boolean getVIP() {
		return VIP;
	}

	public void setVIP(Boolean vIP) {
		VIP = vIP;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

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

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}

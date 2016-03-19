/**  
 * Project Name:b2cpc-web  
 * File Name:SessionUserVo.java  
 * Package Name:com.yimayhd.b2cpc.web.model.user  
 * Date:2016年2月17日下午2:35:28    
 *  
*/

package com.yimayhd.sellerAdmin.model.vo.user;

import java.io.Serializable;

/**
 * ClassName:SessionUserVo <br/>
 * Date: 2016年2月17日 下午2:35:28 <br/>
 * 
 * @author zhangjian
 * @version
 * @see
 */
public class UserVo implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 7984483775726618433L;
	private Long userId;
	private String nickname;
	private String headImg;
	private String signature;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	

}

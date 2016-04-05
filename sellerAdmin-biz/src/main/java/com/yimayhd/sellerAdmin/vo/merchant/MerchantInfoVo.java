package com.yimayhd.sellerAdmin.vo.merchant;

import java.io.Serializable;
import java.util.List;

public class MerchantInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		//昵称
		private String nickName;
		
		private long id ;
		// 店铺名称
		private String name;
		// 商户负责人电话
		private String merchantPrincipalTel;
		// 客服电话
		private String serviceTel;
		//店铺地址
		private String address;
		// 店铺店招
		private List<String> djImage;
		//商户头像
		private String txImage;
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMerchantPrincipalTel() {
			return merchantPrincipalTel;
		}
		public void setMerchantPrincipalTel(String merchantPrincipalTel) {
			this.merchantPrincipalTel = merchantPrincipalTel;
		}
		public String getServiceTel() {
			return serviceTel;
		}
		public void setServiceTel(String serviceTel) {
			this.serviceTel = serviceTel;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	
		public List<String> getDjImage() {
			return djImage;
		}
		public void setDjImage(List<String> djImage) {
			this.djImage = djImage;
		}
		public String getTxImage() {
			return txImage;
		}
		public void setTxImage(String txImage) {
			this.txImage = txImage;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}

		
		
		
	
}

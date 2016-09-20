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
		//店铺头图
		private String ttImage;
		//店铺店标
		private String dbImage;
//		private int type;
//		
//		public int getType() {
//			return type;
//		}
//		public void setType(int type) {
//			this.type = type;
//		}
		private long sellerId;
		
		public long getSellerId() {
			return sellerId;
		}
		public void setSellerId(long sellerId) {
			this.sellerId = sellerId;
		}
		private String merchantDesc;
		
		public String getMerchantDesc() {
			return merchantDesc;
		}
		public void setMerchantDesc(String merchantDesc) {
			this.merchantDesc = merchantDesc;
		}
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
		public String getTtImage() {
			return ttImage;
		}
		public void setTtImage(String ttImage) {
			this.ttImage = ttImage;
		}
		public String getDbImage() {
			return dbImage;
		}
		public void setDbImage(String dbImage) {
			this.dbImage = dbImage;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}

		
		
		
	
}

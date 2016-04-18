package com.yimayhd.sellerAdmin.model.line.price;

import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 套餐价格行
 * 
 * @author yebin
 *
 */
public class PackageBlock {		
	private long skuId;
	private long id;
	private int type;
	private String name;
	private long PId;
	private int PType;
	private String PTxt;
	private long price;
	private int stock;
	private long discount;

	public PackageBlock() {
	}

	public PackageBlock(long skuId, ItemSkuPVPair itemSkuPVPair, long price, int stock, long discount) {
		this.skuId = skuId;
		this.id = itemSkuPVPair.getVId();
		this.name = itemSkuPVPair.getVTxt();
		this.PId = itemSkuPVPair.getPId();
		this.PType = itemSkuPVPair.getPType();
		this.PTxt = itemSkuPVPair.getPTxt();
		this.price = price;
		this.stock = stock;
		this.discount = discount;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPId() {
		return PId;
	}

	public void setPId(long pId) {
		PId = pId;
	}

	public int getPType() {
		return PType;
	}

	public void setPType(int pType) {
		PType = pType;
	}

	public String getPTxt() {
		return PTxt;
	}

	public void setPTxt(String pTxt) {
		PTxt = pTxt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 生成ItemSkuPVPair
	 * 
	 * @return
	 */
	public ItemSkuPVPair toItemSkuPVPair() {
		ItemSkuPVPair SKU = new ItemSkuPVPair();
		SKU.setPId(this.PId);
		SKU.setPType(this.PType);
		SKU.setPTxt(this.PTxt);
		SKU.setVId(this.id);
		SKU.setVTxt(this.name);
		SKU.setVType(this.type);
		return SKU;
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

package com.yimayhd.sellerAdmin.model.travel;

import java.util.Date;
import java.util.List;

import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 套餐-日
 * 
 * @author yebin
 *
 */
public class PackageDay {
	private long id;
	private long PId;
	private int PType;
	private String PTxt;
	private long time;// 日期
	private List<PackageBlock> blocks;// 内容块

	public PackageDay() {
	}

	public PackageDay(ItemSkuPVPair itemSkuPVPair, long time, List<PackageBlock> blocks) {
		this.id = itemSkuPVPair.getVId();
		this.time = time;
		this.PId = itemSkuPVPair.getPId();
		this.PType = itemSkuPVPair.getPType();
		this.PTxt = itemSkuPVPair.getPTxt();
		this.blocks = blocks;
	}

	public List<PackageBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<PackageBlock> blocks) {
		this.blocks = blocks;
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

	public long getTime() {
		return time;
	}

	public Date getDate() {
		return new Date(this.time);
	}

	public void setTime(long time) {
		this.time = time;
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
		SKU.setVTxt(this.time + "");
		return SKU;
	}

}

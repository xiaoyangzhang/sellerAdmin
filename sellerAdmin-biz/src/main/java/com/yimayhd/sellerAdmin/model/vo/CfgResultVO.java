package com.yimayhd.sellerAdmin.model.vo;

import java.io.Serializable;
import java.util.List;

/**
  * @autuor : xusq
  * @date : 2015年11月30日
  * @description : booth信息集合
  */
public class CfgResultVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3894408522360799520L;

	
	private long boothId;
	
	private String boothCode;
	
	private String boothDesc;
	
	private List<CfgResultInfo> cfgInfoList;

	public long getBoothId() {
		return boothId;
	}

	public void setBoothId(long boothId) {
		this.boothId = boothId;
	}

	public String getBoothCode() {
		return boothCode;
	}

	public void setBoothCode(String boothCode) {
		this.boothCode = boothCode;
	}

	public String getBoothDesc() {
		return boothDesc;
	}

	public void setBoothDesc(String boothDesc) {
		this.boothDesc = boothDesc;
	}

	public List<CfgResultInfo> getCfgInfoList() {
		return cfgInfoList;
	}

	public void setCfgInfoList(List<CfgResultInfo> cfgInfoList) {
		this.cfgInfoList = cfgInfoList;
	}

}

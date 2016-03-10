package com.yimayhd.sellerAdmin.model.line.price;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
	private long id;
	private String name;
	private long PId;
	private int PType;
	private String PTxt;
	private List<PackageMonth> months;

	public PackageInfo() {
	}

	public PackageInfo(ItemSkuPVPair itemSkuPVPair, List<PackageDay> days) {
		this.id = itemSkuPVPair.getVId();
		this.name = itemSkuPVPair.getVTxt();
		this.PId = itemSkuPVPair.getPId();
		this.PType = itemSkuPVPair.getPType();
		this.PTxt = itemSkuPVPair.getPTxt();
		Map<Long, List<PackageDay>> monthMap = new LinkedHashMap<Long, List<PackageDay>>();
		for (PackageDay packageDay : days) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(packageDay.getTime());
			c.set(Calendar.DAY_OF_MONTH, 1);
			long monthTime = c.getTimeInMillis();
			if (monthMap.containsKey(monthTime)) {
				monthMap.get(monthTime).add(packageDay);
			} else {
				List<PackageDay> packageDays = new ArrayList<PackageDay>();
				packageDays.add(packageDay);
				monthMap.put(monthTime, packageDays);
			}
		}
		this.months = new ArrayList<PackageMonth>();
		for (Entry<Long, List<PackageDay>> month : monthMap.entrySet()) {
			this.months.add(new PackageMonth(month.getKey(), month.getValue()));
		}
	}

	public String getName() {
		if (name != null) {
			return StringUtils.trim(name);
		}
		return "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PackageMonth> getMonths() {
		return months;
	}

	public void setMonths(List<PackageMonth> months) {
		this.months = months;
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
		return SKU;
	}
}

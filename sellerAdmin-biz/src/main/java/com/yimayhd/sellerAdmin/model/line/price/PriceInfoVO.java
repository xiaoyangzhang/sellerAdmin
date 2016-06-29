package com.yimayhd.sellerAdmin.model.line.price;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfoVO implements Serializable {
	private static final long serialVersionUID = -4308401988236168804L;

	public static final int LIMIT_UNIT = 3600 * 24;

	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天
	private Set<Long> updatedSKU;
	private Set<Long> deletedSKU;

	public List<PackageInfo> getTcs() {
		return tcs;
	}

	public void setTcs(List<PackageInfo> tcs) {
		this.tcs = tcs;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimitBySecond(long limit) {
		this.limit = Math.round(limit / LIMIT_UNIT);
	}

	public int getLimitBySecond() {
		return limit * LIMIT_UNIT;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(0).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(0).getTime()));
				}
			}
		}
		return "";
	}

	public String getEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(months.size() - 1).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(days.size() - 1).getTime()));
				}
			}
		}
		return "";
	}

	public Set<Long> getUpdatedSKU() {
		return updatedSKU;
	}

	public void setUpdatedSKU(Set<Long> updatedSKU) {
		this.updatedSKU = updatedSKU;
	}

	public Set<Long> getDeletedSKU() {
		return deletedSKU;
	}

	public void setDeletedSKU(Set<Long> deletedSKU) {
		this.deletedSKU = deletedSKU;
	}
}

package com.yimayhd.sellerAdmin.model.travel;

import java.util.Date;
import java.util.List;

/**
 * 套餐-月
 * 
 * @author yebin
 *
 */
public class PackageMonth {
	private long time;// 日期
	private List<PackageDay> days;// 日期列表

	public PackageMonth() {
	}

	public PackageMonth(long time, List<PackageDay> days) {
		this.time = time;
		this.days = days;
	}

	public List<PackageDay> getDays() {
		return days;
	}

	public void setDays(List<PackageDay> days) {
		this.days = days;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getDate() {
		return new Date(this.time);
	}

}

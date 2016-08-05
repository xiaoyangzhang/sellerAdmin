package com.yimayhd.sellerAdmin.model.vo;


import com.yimayhd.voucher.client.domain.VoucherTemplateDO;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherTemplateVO extends VoucherTemplateDO {
	private String voucherStatus;
	
	private double requirement_;
	private double value_;

	public String getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public double getRequirement_() {
		return requirement_;
	}

	public void setRequirement_(double requirement_) {
		this.requirement_ = requirement_;
	}

	public double getValue_() {
		return value_;
	}

	public void setValue_(double value_) {
		this.value_ = value_;
	}

	
}

package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;

/**
 * Created by wangdi on 16/5/27.
 */
public class SupplierCalendarTemplate implements Serializable {
    private long seller_id;//商户ID
    private String hotel_id;//酒店ID
    private BizSkuInfo [] bizSkuInfos;

    private static final long serialVersionUID = -2261577874092027812L;

    public SupplierCalendarTemplate(){

    }

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public BizSkuInfo[] getBizSkuInfos() {
        return bizSkuInfos;
    }

    public void setBizSkuInfos(BizSkuInfo[] bizSkuInfos) {
        this.bizSkuInfos = bizSkuInfos;
    }
}

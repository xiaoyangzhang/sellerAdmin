package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangdi on 16/5/27.
 */
public class BizSkuInfo implements Serializable{

    private static final long serialVersionUID = 1883526593562308487L;

    private long sku_id;//
    private String state;//当前状态 更新 删除 添加
    private int stock_num;//库存
    private BigDecimal price;//价格
    private String vTxt;// 日期
    private long vPrize;



    public BizSkuInfo(){

    }

    public long getSku_id() {
        return sku_id;
    }

    public void setSku_id(long sku_id) {
        this.sku_id = sku_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStock_num() {
        return stock_num;
    }

    public void setStock_num(int stock_num) {
        this.stock_num = stock_num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getvTxt() {
        return vTxt;
    }

    public void setvTxt(String vTxt) {
        this.vTxt = vTxt;
    }

    public long getvPrize() {
        return vPrize;
    }

    public void setvPrize(long vPrize) {
        this.vPrize = vPrize;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        if(price!=null){
           this.setvPrize(price.longValue()*10);
        }
    }


    public void setvPrize(Long vPrize) {
        this.vPrize = vPrize;
        if(vPrize!=0){
            this.setPrice(new BigDecimal(vPrize/10));
        }
    }


}

package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangdi on 16/5/27.
 */
public class BizSkuInfo implements Serializable{

    private static final long serialVersionUID = 1883526593562308487L;

    private Long sku_id;//
    private String state;//当前状态 更新 删除 添加
    private Integer stock_num;//库存
    private BigDecimal price;//价格
    private String vTxt;// 日期
    private Long vPrize;

    public BizSkuInfo(){

    }


    public Long getSku_id() {
        return sku_id;
    }

    public void setSku_id(Long sku_id) {
        this.sku_id = sku_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        if(price!=null){
           this.setvPrize(price.longValue()*10);
        }
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

    public void setvPrize(Long vPrize) {
        this.vPrize = vPrize;
        if(vPrize!=0){
            this.setPrice(new BigDecimal(vPrize/10));
        }
    }

    public Integer getStock_num() {
        return stock_num;
    }

    public void setStock_num(Integer stock_num) {
        this.stock_num = stock_num;
    }
}

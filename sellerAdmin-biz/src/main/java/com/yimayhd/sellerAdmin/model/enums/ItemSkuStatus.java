package com.yimayhd.sellerAdmin.model.enums;

/**
 * Created by czf on 2015/12/19.
 */
public enum  ItemSkuStatus {
    ADD(1,"新增"),
    MODIFY(2,"修改"),
    DELETE(3,"删除");
    private int status;
    private String des;
    private ItemSkuStatus(int status,String des){
        this.status = status;
        this.des = des;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isEquals(int status) {
        return this.status == status;
    }

    public static String getDescByStatus(int status) {
        for (ItemSkuStatus itemSkuStatus : values()) {
            if (itemSkuStatus.isEquals(status)) {
                return itemSkuStatus.getDes();
            }
        }
        return null;
    }

    public static ItemSkuStatus getByStatus(int status){
        for (ItemSkuStatus itemSkuStatus : values()) {
            if (itemSkuStatus.status == status ) {
                return itemSkuStatus;
            }
        }
        return null;
    }
}

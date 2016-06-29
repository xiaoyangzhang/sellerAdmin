package com.yimayhd.sellerAdmin.model.enums;

//仅在商贸系统中使用:交易状态变更为    交易创建、交易成功和交易关闭
public enum PayStatus {
    NOT_PAY(1,"交易创建"),
    PAID(2,"已付款"),
    REFUNDED(4,"退款后交易关闭"),
    SUCCESS(6,"交易成功"),
    NOT_PAY_CLOSE(8,"交易关闭"),
    WAIT_BUSINESS_CONFIRM(9,"待商户确认");
    
    private int status;
    private String des;

    private PayStatus(int status, String des) {
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
        for (PayStatus payStatus : values()) {
            if (payStatus.isEquals(status)) {
                return payStatus.getDes();
            }
        }
        return null;
    }
    
    public static PayStatus getByStatus(int status){
    	for (PayStatus payStatus : values()) {
            if (payStatus.status == status ) {
                return payStatus;
            }
        }
        return null;
    }
}

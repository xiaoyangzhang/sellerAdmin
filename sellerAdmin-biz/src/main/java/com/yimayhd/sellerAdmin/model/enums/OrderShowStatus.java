package com.yimayhd.sellerAdmin.model.enums;

public enum OrderShowStatus {
    NOTING(1,"待付款"),
    PAID(2,"待发货/已付款"),
    SHIPPED(3,"待收货/已发货"),
    FINISH(4,"已完成"),
    TRADE_CLOSE(5,"已取消"),
    PENDING(6,"待处理"),
    REFUNDED(7,"已退款"),
    WAITING_EVALUATION(8,"待评价");

    private int status;
    private String des;

    private OrderShowStatus(int status, String des) {
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
        for (OrderShowStatus payStatus : values()) {
            if (payStatus.isEquals(status)) {
                return payStatus.getDes();
            }
        }
        return null;
    }
    
    public static OrderShowStatus getByStatus(int status){
    	for (OrderShowStatus payStatus : values()) {
            if (payStatus.status == status ) {
                return payStatus;
            }
        }
        return null;
    }
}

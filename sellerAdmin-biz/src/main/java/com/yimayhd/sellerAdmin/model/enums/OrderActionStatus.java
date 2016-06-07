package com.yimayhd.sellerAdmin.model.enums;

public enum OrderActionStatus {
    NOTING(1,"无操作"),
    AFFIRM(2,"确认"),
    REFUND(3,"退款"),
    FINISH(4,"完成"),
    CONSIGN(5,"发货"),
    CANCEL(6,"取消订单"),
    AFFIRM_REFUND(7,"确认+退款"),
    FINISH_REFUND(8,"完成+退款"),
    CONSIGN_REFUND(9,"发货+退款"),
    UPDATE_ADDRESS_CANCEL(10,"修改地址+取消订单"),
    OVERTIME(11,"延长收货");


    private int status;
    private String des;

    private OrderActionStatus(int status, String des) {
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
        for (OrderActionStatus payStatus : values()) {
            if (payStatus.isEquals(status)) {
                return payStatus.getDes();
            }
        }
        return null;
    }
    
    public static OrderActionStatus getByStatus(int status){
    	for (OrderActionStatus payStatus : values()) {
            if (payStatus.status == status ) {
                return payStatus;
            }
        }
        return null;
    }
}

package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;

/**
 * Created by wangdi on 16/5/27.
 */
public class MultiChoice implements Serializable {

    private static final long serialVersionUID = -4538006116100105040L;
    private long id ;
    private String name;//名称
    private String title;//标题
    private String value;//显示内容
    private long tValue;//日期等
    private boolean isChoice;//是否选中



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTValue() {
        return tValue;
    }

    public void setTValue(long tValue) {
        this.tValue = tValue;
    }

    public String geTName() {
        return name;
    }

    public void seTName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }
}

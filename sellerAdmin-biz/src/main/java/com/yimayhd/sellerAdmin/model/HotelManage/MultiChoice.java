package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;

/**
 * Created by wangdi on 16/5/27.
 */
public class MultiChoice implements Serializable {

    private static final long serialVersionUID = -4538006116100105040L;
    private int id ;
    private String name;//名称
    private String title;//标题
    private String value;//显示内容
    private int tValue;//日期等
    private boolean isChoice;//是否选中


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int gettValue() {
        return tValue;
    }

    public void settValue(int tValue) {
        this.tValue = tValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }
}

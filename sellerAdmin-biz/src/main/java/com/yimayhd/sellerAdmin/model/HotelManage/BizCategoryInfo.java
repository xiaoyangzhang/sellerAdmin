package com.yimayhd.sellerAdmin.model.HotelManage;

import java.io.Serializable;

/**
 * Created by wangdi on 16/5/29.
 */
public class BizCategoryInfo implements Serializable{

    private static final long serialVersionUID = 7829846377004493415L;

    private long pId;
    private String pTxt;//文本
    private int pType;

    private String vTxt;//value
    private long categoryId;

    public long getPId() {
        return pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
    }

    public String getPTxt() {
        return pTxt;
    }

    public void setPTxt(String pTxt) {
        this.pTxt = pTxt;
    }

    public int getPType() {
        return pType;
    }

    public void setPType(int pType) {
        this.pType = pType;
    }

    public String getVTxt() {
        return vTxt;
    }

    public void setVTxt(String vTxt) {
        this.vTxt = vTxt;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}

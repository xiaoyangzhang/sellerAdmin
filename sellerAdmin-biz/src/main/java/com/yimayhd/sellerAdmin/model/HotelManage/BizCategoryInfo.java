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
    private boolean flag;
    private int maxLength;//字数最大限制
    private String hint;//默认显示文案

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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

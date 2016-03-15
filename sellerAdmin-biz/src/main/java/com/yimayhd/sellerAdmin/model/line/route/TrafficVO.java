package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;

/**
 * Created by czf on 2016/3/15.
 */
public class TrafficVO implements Serializable {
    private static final long serialVersionUID = -492091641597506573L;
    //去程交通方式
    private Integer goType;
    //去程详细描述
    private String goDescription;
    //返程交通方式
    private Integer backType;
    //返程详细描述
    private String backDescription;

    public Integer getGoType() {
        return goType;
    }

    public void setGoType(Integer goType) {
        this.goType = goType;
    }

    public String getGoDescription() {
        return goDescription;
    }

    public void setGoDescription(String goDescription) {
        this.goDescription = goDescription;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public String getBackDescription() {
        return backDescription;
    }

    public void setBackDescription(String backDescription) {
        this.backDescription = backDescription;
    }
}

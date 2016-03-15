package com.yimayhd.sellerAdmin.model.line.route;

import java.io.Serializable;

/**
 * Created by czf on 2016/3/15.
 */
public class HotelAndScenic implements Serializable {
    private static final long serialVersionUID = 7525609562331378030L;
    //类型
    private int Type;
    //信息
    private String description;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

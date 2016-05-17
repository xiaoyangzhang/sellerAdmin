package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageVO extends BaseQuery {

    private Long id ;// '自增id',
    private String name ;// '景点名称'

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

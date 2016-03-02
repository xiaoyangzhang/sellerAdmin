package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * 角色菜单表
 * @table ha_role_menu
 * @author czf
 **/
public class HaRoleMenuDO extends BaseModel {


    private static final long serialVersionUID = -2564673778661573399L;
    private Long haMenuId; //菜单ID

    private Long haRoleId; //角色ID

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getHaMenuId() {
        return haMenuId;
    }

    public void setHaMenuId(Long haMenuId) {
        this.haMenuId = haMenuId;
    }

    public Long getHaRoleId() {
        return haRoleId;
    }

    public void setHaRoleId(Long haRoleId) {
        this.haRoleId = haRoleId;
    }
}
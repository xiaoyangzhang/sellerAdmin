package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class Menu extends BaseModel {
    private String name;
    private String linkUrl;
    private Integer level;
    private Long parentId;
    private List<Menu> menuList;

    public Menu(){

    }

    public Menu(Long id,String name,String linkUrl,Integer level,Long parentId){
        this.id = id;
        this.name = name;
        this.linkUrl = linkUrl;
        this.level = level;
        this.parentId = parentId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}

package com.yimayhd.sellerAdmin.model;

import com.yimayhd.sellerAdmin.base.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * controller路径表
 * @table ha_menu
 * @author czf
 **/
public class HaMenuDO extends BaseModel {


    private static final long serialVersionUID = 832142812705236148L;
    private long id; // ID

    private String name; // 

    private String url; // controller路径

    private int reqType; // 请求方式（0:不限;1：GET;2:POST;3:PUT;4:DELETE）

    private int type; // 0:功能；1：菜单

    private int level; // 菜单级别

    private int leaf; // 是否叶子节点

    private long parentId; // 伏击菜单ID

    private Date gmtCreated; // 创建时间

    private Date gmtModified; // 更新时间

    private int status; // 状态（0：删除；1：正常）

    private List<HaMenuDO> haMenuDOList;


    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public void setType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLeaf(int leaf){
        this.leaf = leaf;
    }

    public int getLeaf() {
        return leaf;
    }


    public void setGmtCreated(Date gmtCreated){
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<HaMenuDO> getHaMenuDOList() {
        return haMenuDOList;
    }

    public void setHaMenuDOList(List<HaMenuDO> haMenuDOList) {
        this.haMenuDOList = haMenuDOList;
    }
}
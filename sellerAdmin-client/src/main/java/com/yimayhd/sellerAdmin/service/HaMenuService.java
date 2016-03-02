package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.BaseService;
import com.yimayhd.sellerAdmin.model.HaMenuDO;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
public interface HaMenuService extends BaseService<HaMenuDO>{

    /**
     * 根据用户ID获取菜单权限列表
     * @param id
     * @return
     * @throws Exception
     */
    List<HaMenuDO> getMenuListByUserId(long id)throws Exception;

    /**
     * 根据用户ID获取权限url
     * @param id
     * @return
     * @throws Exception
     */
    List<HaMenuDO> getUrlListByUserId(long id)throws Exception;
    
    List<HaMenuDO> getMenuList()throws Exception;
}

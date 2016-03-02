package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.BaseServiceImpl;
import com.yimayhd.sellerAdmin.mapper.HaRoleMenuMapper;
import com.yimayhd.sellerAdmin.model.HaRoleMenuDO;
import com.yimayhd.sellerAdmin.service.HaRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色菜单表
 * @author czf
 */
//@Service
public class HaRoleMenuServiceImpl extends BaseServiceImpl<HaRoleMenuDO> implements HaRoleMenuService{

    @Autowired
    private HaRoleMenuMapper haRoleMenuMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haRoleMenuMapper);
    }
}

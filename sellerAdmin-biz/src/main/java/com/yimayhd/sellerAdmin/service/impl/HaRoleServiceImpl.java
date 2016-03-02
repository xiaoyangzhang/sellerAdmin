package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.BaseServiceImpl;
import com.yimayhd.sellerAdmin.mapper.HaRoleMapper;
import com.yimayhd.sellerAdmin.model.HaRoleDO;
import com.yimayhd.sellerAdmin.service.HaRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色表（菜单）
 * @author czf
 */
//@Service
public class HaRoleServiceImpl extends BaseServiceImpl<HaRoleDO> implements HaRoleService{

    @Autowired
    private HaRoleMapper haRoleMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haRoleMapper);
    }
}

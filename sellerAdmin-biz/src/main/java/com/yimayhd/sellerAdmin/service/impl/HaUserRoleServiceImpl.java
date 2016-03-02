package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.BaseServiceImpl;
import com.yimayhd.sellerAdmin.mapper.HaUserRoleMapper;
import com.yimayhd.sellerAdmin.model.HaUserRoleDO;
import com.yimayhd.sellerAdmin.service.HaUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户角色表
 * @author czf
 */
//@Service
public class HaUserRoleServiceImpl extends BaseServiceImpl<HaUserRoleDO> implements HaUserRoleService{

    @Autowired
    private HaUserRoleMapper haUserRoleMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haUserRoleMapper);
    }
}

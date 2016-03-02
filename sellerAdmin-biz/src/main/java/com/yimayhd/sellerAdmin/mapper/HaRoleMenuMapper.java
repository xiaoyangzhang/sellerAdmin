package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.base.BaseMapper;
import com.yimayhd.sellerAdmin.model.HaRoleMenuDO;

/**
 * 角色菜单表
 * @author czf
 */
public interface HaRoleMenuMapper extends BaseMapper<HaRoleMenuDO> {

	public boolean addRoleMenu(HaRoleMenuDO haRoleMenuDO);
	
	public HaRoleMenuDO getHaRoleMenuById(long id);
	
}

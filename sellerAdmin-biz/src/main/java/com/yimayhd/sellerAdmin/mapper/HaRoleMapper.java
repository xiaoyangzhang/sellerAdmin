package com.yimayhd.sellerAdmin.mapper;

import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseMapper;
import com.yimayhd.sellerAdmin.model.HaRoleDO;
import com.yimayhd.sellerAdmin.model.HaRoleDetail;
import com.yimayhd.sellerAdmin.model.query.RoleListQuery;

/**
 * 角色表（菜单）
 * @author czf
 */
public interface HaRoleMapper extends BaseMapper<HaRoleDO>{

	public List<HaRoleDO> getListNew(RoleListQuery roleListQuery);

	public List<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery);
	
	public Long totalCount(RoleListQuery roleListQuery);
		
	public Long roleDetailCount();
	
	public boolean updateRoleStatus(HaRoleDO haRoleDO);
}

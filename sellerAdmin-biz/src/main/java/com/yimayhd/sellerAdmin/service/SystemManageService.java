package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.HaRoleDO;
import com.yimayhd.sellerAdmin.model.HaRoleDetail;
import com.yimayhd.sellerAdmin.model.query.RoleListQuery;

public interface SystemManageService {

	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) throws Exception;
	
	public PageVO<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery) throws Exception;
	
	public boolean updateRoleStatus(HaRoleDO haRoleDO);
	
	public boolean addOrUpdateRoleDetaiStatus(long roleMenuId, int roleStatus, long roleId);
	
}

package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.mapper.HaRoleMapper;
import com.yimayhd.sellerAdmin.mapper.HaRoleMenuMapper;
import com.yimayhd.sellerAdmin.model.HaRoleDO;
import com.yimayhd.sellerAdmin.model.HaRoleDetail;
import com.yimayhd.sellerAdmin.model.HaRoleMenuDO;
import com.yimayhd.sellerAdmin.model.query.RoleListQuery;
import com.yimayhd.sellerAdmin.service.SystemManageService;

public class SystemManageServiceImpl implements SystemManageService {

	@Autowired
	private HaRoleMapper haRoleMapper;

	@Autowired
	private HaRoleMenuMapper haRoleMenuMapper;

	@Override
	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) {

		List<HaRoleDO> roleList = haRoleMapper.getListNew(roleListQuery);
		long totalCount = haRoleMapper.totalCount(roleListQuery);
		PageVO<HaRoleDO> pageVo = new PageVO<HaRoleDO>(
				roleListQuery.getPageNumber(), roleListQuery.getPageSize(),
				(int) totalCount, roleList);

		return pageVo;
	}

	@Override
	public PageVO<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery)
			throws Exception {

		List<HaRoleDetail> roleDetailList = haRoleMapper
				.roleDetailById(roleListQuery);
		long totalCount = haRoleMapper.roleDetailCount();

		PageVO<HaRoleDetail> pageVo = new PageVO<HaRoleDetail>(
				roleListQuery.getPageNumber(), roleListQuery.getPageSize(),
				(int) totalCount, roleDetailList);

		return pageVo;
	}

	@Override
	public boolean updateRoleStatus(HaRoleDO haRoleDO) {

		return haRoleMapper.updateRoleStatus(haRoleDO);
	}

	@Override
	public boolean addOrUpdateRoleDetaiStatus(long roleMenuId, int roleStatus,
			long roleId) {

		HaRoleMenuDO roleMenuDO = haRoleMenuMapper
				.getHaRoleMenuById(roleMenuId);
		boolean result = false;

		// 停用
		if (roleStatus == 0) {

			roleMenuDO.setStatus(roleStatus);
			try {
				haRoleMenuMapper.modify(roleMenuDO);
				result = true;
			} catch (Exception e) {
				result = false;
				e.printStackTrace();
			}

		} else if (roleStatus == 1) { // 启用

			roleMenuDO.setStatus(roleStatus);
			if (roleMenuDO.getHaRoleId() == roleId) {
				try {
					haRoleMenuMapper.modify(roleMenuDO);
					result = true;
				} catch (Exception e) {
					result = false;
					e.printStackTrace();
				}
			} else {
				roleMenuDO.setStatus(roleStatus);
				roleMenuDO.setId(0);
				roleMenuDO.setHaRoleId(roleId);
				result = haRoleMenuMapper.addRoleMenu(roleMenuDO);
			}
		}

		return result;
	}

}

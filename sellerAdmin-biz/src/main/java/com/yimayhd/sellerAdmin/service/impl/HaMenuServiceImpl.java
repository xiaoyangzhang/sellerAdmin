package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.BaseServiceImpl;
import com.yimayhd.sellerAdmin.mapper.HaMenuMapper;
import com.yimayhd.sellerAdmin.model.HaMenuDO;
import com.yimayhd.sellerAdmin.service.HaMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单表
 * 
 * @author czf
 */
// @Service
public class HaMenuServiceImpl extends BaseServiceImpl<HaMenuDO> implements HaMenuService {

	@Autowired
	private HaMenuMapper haMenuMapper;

	@Override
	protected void initBaseMapper() {
		setBaseMapper(haMenuMapper);
	}

	@Override
	public List<HaMenuDO> getMenuListByUserId(long id) throws Exception {
		return haMenuMapper.getMenuListByUserId(id);
	}

	@Override
	public List<HaMenuDO> getUrlListByUserId(long id) throws Exception {
		return haMenuMapper.getUrlListByUserId(id);
	}

	@Override
	public List<HaMenuDO> getMenuList() throws Exception {
		return haMenuMapper.getMenuList();
	}
}

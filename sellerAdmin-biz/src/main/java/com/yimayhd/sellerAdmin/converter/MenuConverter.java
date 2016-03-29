package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.base.menu.MenuVO;

public class MenuConverter {
	
	public static MenuVO getMenu(HaMenuDO haMenuDO){
		if( haMenuDO == null ){
			return null;
		}
		MenuVO menu = new MenuVO() ;
		menu.setName(haMenuDO.getName());
		menu.setLeaf(haMenuDO.getLeaf() == 0);
		menu.setParentId(haMenuDO.getParentId());
		return menu ;
	}
	public static List<MenuVO> getMenus(List<HaMenuDO> menuDOs){
		if( CollectionUtils.isEmpty(menuDOs) ){
			return null ;
		}
		List<MenuVO> menus = new ArrayList<MenuVO>();
		for( HaMenuDO menuDO : menuDOs ){
			MenuVO menu = getMenu(menuDO);
			if( menu != null ){
				menus.add(menu);
			}
		}
		return menus ;
	}
	
}

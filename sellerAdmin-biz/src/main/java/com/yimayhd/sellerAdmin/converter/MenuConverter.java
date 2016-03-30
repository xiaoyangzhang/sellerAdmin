package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileManager.Location;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.vo.menu.LocationVO;
import com.yimayhd.sellerAdmin.vo.menu.MenuVO;

public class MenuConverter {
	
	public static MenuVO do2MenuVO(String parentName, HaMenuDO haMenuDO){
		if( haMenuDO == null ){
			return null;
		}
		MenuVO menu = new MenuVO() ;
		menu.setId(haMenuDO.getId());
		menu.setName(haMenuDO.getName());
		menu.setLeaf(haMenuDO.getLeaf() == 0);
		menu.setParentId(haMenuDO.getParentId());
		menu.setParentName(parentName);
		menu.setUrl(haMenuDO.getUrl());
		List<HaMenuDO> childrenDOs = haMenuDO.getHaMenuDOList() ;
		List<MenuVO> children = do2MenuVOs(haMenuDO.getName(), childrenDOs);
		menu.setChildren(children);
		return menu ;
	}
	public static List<MenuVO> do2MenuVOs(List<HaMenuDO> menuDOs){
		return do2MenuVOs(null, menuDOs) ;
	}
	public static List<MenuVO> do2MenuVOs(String parentName, List<HaMenuDO> menuDOs){
		if( CollectionUtils.isEmpty(menuDOs) ){
			return null ;
		}
		List<MenuVO> menus = new ArrayList<MenuVO>();
		for( HaMenuDO menuDO : menuDOs ){
			MenuVO menu = do2MenuVO(parentName, menuDO);
			if( menu != null ){
				menus.add(menu);
			}
		}
		return menus ;
	}
	
	
//	public static LocationVO menu2Location(MenuVO menu){
//		return menu2Location(menu, null) ;
//	}
//	
//	public static LocationVO menu2Location(MenuVO menu, LocationVO next){
//		if( menu == null ){
//			return null;
//		}
//		LocationVO location = new LocationVO() ;
//		location.setId(menu.getId());
//		location.setName(menu.getName());
//		location.setUrl(menu.getUrl());
//		location.setNext(next);
//		return location ;
//	}
	
}

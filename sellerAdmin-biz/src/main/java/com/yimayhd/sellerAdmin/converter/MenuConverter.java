package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.model.vo.menu.MenuVO;

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
		menu.setType(haMenuDO.getType());
		menu.setRequestType(haMenuDO.getReqType());
		List<HaMenuDO> childrenDOs = haMenuDO.getHaMenuDOList() ;
		List<MenuVO> children = do2MenuVOs(childrenDOs);
		menu.setChildren(children);
		return menu ;
	}
	public static List<MenuVO> do2MenuVOs(List<HaMenuDO> menuDOs){
		if( CollectionUtils.isEmpty(menuDOs) ){
			return null ;
		}
		List<MenuVO> menus = new ArrayList<MenuVO>();
		for( HaMenuDO menuDO : menuDOs ){
			String parentName = null;
			HaMenuDO parent = getMenuDOById(menuDOs, menuDO.getParentId());
			if( parent != null ){
				parentName = parent.getName() ;
			}
			MenuVO menu = do2MenuVO(parentName, menuDO);
			if( menu != null ){
				menus.add(menu);
			}
		}
		return menus ;
	}
	
	public static HaMenuDO getMenuDOById(List<HaMenuDO> menuDOs, long id){
		if( CollectionUtils.isEmpty(menuDOs) || id <=0 ){
			return null ;
		}
		for( HaMenuDO menuDO : menuDOs ){
			if( menuDO.getId() == id ){
				return menuDO ;
			}
		}
		return null ;
	}
	
	
}

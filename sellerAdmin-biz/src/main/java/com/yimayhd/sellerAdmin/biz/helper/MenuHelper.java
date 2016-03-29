package com.yimayhd.sellerAdmin.biz.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.sellerAdmin.base.menu.MenuVO;

public class MenuHelper {
	
	public static void selectMenu(List<MenuVO> menus, String menuUrl){
		if( CollectionUtils.isEmpty(menus) || StringUtils.isBlank(menuUrl) ){
			return ;
		}
		String url = menuUrl.trim() ;
		for( MenuVO menu : menus ){
			String ml = menu.getUrl() ;
			if( ml != null && url.equalsIgnoreCase(ml.trim()) ){
				menu.setSelected(true);
				break;
			}
		}
	}
	
	/**
	 * 将菜单合并成具有层级结构的数据
	 * @param menus
	 * @return
	 */
	public static ArrayList<MenuVO> mergeMenus(List<MenuVO> menus){
		if( CollectionUtils.isEmpty(menus) ){
			return null ;
		}
		ArrayList<MenuVO> list = new ArrayList<MenuVO>();
		Map<Long, List<MenuVO>> map = groupMenus(menus);
		for( MenuVO menu: menus ){
			if( !menu.hasParent() ){
				long menuId = menu.getId() ;
				List<MenuVO> childrens = map.get(menuId);
				menu.setChildrens(childrens);
				list.add(menu);
			}
		}
		return list;
	}
	
	
	/**
	 * 将菜单根据parentId分组
	 * @param menus
	 * @return
	 */
	private static Map<Long, List<MenuVO>> groupMenus(List<MenuVO> menus){
		if( CollectionUtils.isEmpty(menus) ){
			return null ;
		}
		Map<Long, List<MenuVO>> map = new HashMap<Long, List<MenuVO>>() ;
		for( MenuVO menu: menus ){
			long parentId = menu.getParentId() ;
			long menuId = menu.getId() ;
			long key = menu.hasParent() ? parentId : menuId  ;
			List<MenuVO> list = map.get(parentId);
			if( list == null ){
				list = new ArrayList<MenuVO>() ;
			}
			list.add(menu);
			map.put(key, list);
		}
		return map;
	}
}

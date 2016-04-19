package com.yimayhd.sellerAdmin.biz.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.enums.HaMenuRequestType;
import com.yimayhd.membercenter.client.enums.HaMenuType;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.vo.menu.MenuVO;

public class MenuHelper {
	private static AntPathMatcher matcher = new AntPathMatcher() ;
	
	public static MenuVO getSelectedMenu(List<MenuVO> menus, String pathUrl, String method){
		if( CollectionUtils.isEmpty(menus) || StringUtils.isBlank(pathUrl) ){
			return null;
		}
		String path = pathUrl.trim() ;
		String last =  path.substring(path.length()-1, path.length()) ;
		if( "/".equals(last) ){
			path = path.substring(0, path.length()-1) ;
		}
		
		for( MenuVO menu : menus ){
			List<MenuVO> children = menu.getChildren() ;
			if( CollectionUtils.isEmpty(children) ){
				continue ;
			}
			for( MenuVO child : children ){
				String urlPattern = child.getUrl().trim() ;
				if( urlPattern.contains("/upload/file") ){
					System.err.println();
				}
				boolean match = matcher.match(urlPattern, path);
	        	if( match ){
//	        		if( ml != null && url.equalsIgnoreCase(ml.trim()) ){
	        			HaMenuRequestType type = HaMenuRequestType.getRequestType(child.getRequestType());
	        			if( type == null ){
	        				continue;
	        			}else if( HaMenuRequestType.ALL == type ){
	        				return child ;
	        			}else if( type.name().equalsIgnoreCase(method) ){
	        				return child ;
	        			}
//	        		}
	        		
	        	}
			}
		}
		return null;
	}
	
	public static HashMap<String, MenuVO> mapMenus(List<MenuVO> menus){
		if( CollectionUtils.isEmpty(menus) ){
			return null;
		}
		HashMap<String, MenuVO> map = new HashMap<String, MenuVO>() ;
		for( MenuVO menu : menus ){
			String url = menu.getUrl() ;
			if( Constant.MENU_PARENT_FLAG.equals(url) ){
				continue ;
			}
			map.put(url, menu);
		}
		return map;
	}
	
//	public static MenuTreeVO getMenuTree(List<MenuVO> menus, String currentUrl){
//		
//		if( CollectionUtils.isEmpty(menus) || StringUtils.isBlank(currentUrl) ){
//			return null;
//		}
//		for( MenuVO menu : menus ){
//			List<MenuVO> children = menu.getChildren() ;
//			for( MenuVO child : children ){
//				//叶子节点
//				String mUrl = child.getUrl() ;
//				if( mUrl != null && currentUrl.equalsIgnoreCase(mUrl.trim()) ){
//					MenuTreeVO currentTree = new MenuTreeVO(child, menu) ;
//					return currentTree;
//				}
//			}
//		}
//		return null;
//	}
	
	
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
		System.err.println("111="+JSON.toJSONString(map)+"\n\n");
		for( MenuVO menu: menus ){
			if( !menu.hasParent() ){
				long menuId = menu.getId() ;
				List<MenuVO> children = map.get(menuId);
				menu.setChildren(children);
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
//			if( HaMenuType.MENU.getType() == menu.getType() && menu.hasParent() ){
			if( menu.hasParent() ){
				List<MenuVO> list = map.get(parentId);
				if( list == null ){
					list = new ArrayList<MenuVO>() ;
				}
				list.add(menu);
				map.put(parentId, list);
			}
		}
		return map;
	}
}

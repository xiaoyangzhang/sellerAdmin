package com.yimayhd.sellerAdmin.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.vo.menu.MenuVO;

public class MenuCacheMananger {
	@Autowired
	private CacheManager cacheManager ;
	
	public String getKey4UserMenu(long userId){
		return Constant.APP+"_menu_"+userId;
	}
	
	public List<MenuVO> getMenus(long userId){
		String key = getKey4UserMenu(userId);
		return (List<MenuVO>)cacheManager.getFormTair(key);
	}
	
	public boolean cacheMenus(long userId, ArrayList<MenuVO> menus){
		String key = getKey4UserMenu(userId);
		return cacheManager.addToTair(key, menus);
	}
	
}

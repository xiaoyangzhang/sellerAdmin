package com.yimayhd.sellerAdmin.cache;

import java.util.ArrayList;
import java.util.HashMap;
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
	public String getKey4AllMenu(){
		return Constant.APP+"_all_menu";
	}
	
	public List<MenuVO> getUserMenus(long userId){
		String key = getKey4UserMenu(userId);
		return (List<MenuVO>)cacheManager.getFormTair(key);
	}
	
	public boolean cacheUserMenus(long userId, ArrayList<MenuVO> menus){
		String key = getKey4UserMenu(userId);
		return cacheManager.addToTair(key, menus);
	}
	public HashMap<String, MenuVO> getAllMenus(){
		String key = getKey4AllMenu();
		return (HashMap<String, MenuVO>)cacheManager.getFormTair(key);
	}
	
	public boolean cacheAllMenus(HashMap<String, MenuVO> map){
		String key = getKey4AllMenu();
		return cacheManager.addToTair(key, map);
	}
	
}

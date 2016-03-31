package com.yimayhd.sellerAdmin.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.helper.MenuHelper;
import com.yimayhd.sellerAdmin.cache.MenuCacheMananger;
import com.yimayhd.sellerAdmin.converter.MenuConverter;
import com.yimayhd.sellerAdmin.repo.MenuRepo;
import com.yimayhd.sellerAdmin.vo.menu.MenuVO;

public class MenuBiz {
	@Autowired
	private MenuRepo menuRepo ;
	@Autowired
	private MenuCacheMananger menuCacheMananger; 
	
	public List<MenuVO> getMenusFromTair(long userId){
		List<MenuVO> menus = menuCacheMananger.getUserMenus(userId);
		return menus ;
	}
	
	
	public boolean cacheUserMenus2Tair(long userId){
		WebResult<List<HaMenuDO>>  result = menuRepo.getMenuListByUserId(userId);
		if( result == null || !result.isSuccess() ){
			return false;
		}
		List<HaMenuDO> menuDOs = result.getValue();
		if( CollectionUtils.isEmpty(menuDOs) ){
			return true;
		}
		List<MenuVO> menus = MenuConverter.do2MenuVOs(menuDOs);
		//分组
		ArrayList<MenuVO> ms = MenuHelper.mergeMenus(menus);
		return menuCacheMananger.cacheUserMenus(userId, ms);
	}
	
	public boolean cacheAllMenusToTair(){
		WebResult<List<HaMenuDO>>  result = menuRepo.getAllMenus();
		if( result == null || !result.isSuccess() ){
			return false;
		}
		List<HaMenuDO> menuDOs = result.getValue();
		if( CollectionUtils.isEmpty(menuDOs) ){
			return true;
		}
		List<MenuVO> menus = MenuConverter.do2MenuVOs(menuDOs);
		HashMap<String, MenuVO> map = MenuHelper.mapMenus(menus);
		return menuCacheMananger.cacheAllMenus(map);
	}
}

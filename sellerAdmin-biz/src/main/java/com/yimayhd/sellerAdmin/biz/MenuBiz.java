package com.yimayhd.sellerAdmin.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.sellerAdmin.base.menu.MenuVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.helper.MenuHelper;
import com.yimayhd.sellerAdmin.cache.MenuCacheMananger;
import com.yimayhd.sellerAdmin.converter.MenuConverter;
import com.yimayhd.sellerAdmin.repo.MenuRepo;

public class MenuBiz {
	@Autowired
	private MenuRepo menuRepo ;
	@Autowired
	private MenuCacheMananger menuCacheMananger; 
	
	public List<MenuVO> getMenusByUserId(long userId){
		List<MenuVO> menus = menuCacheMananger.getMenus(userId);
		return menus ;
	}
	
	public boolean cacheMenus2Tair(long userId){
		WebResult<List<HaMenuDO>>  result = menuRepo.getMenuListByUserId(userId);
		if( result == null || !result.isSuccess() ){
			return false;
		}
		List<HaMenuDO> menuDOs = result.getValue();
		if( CollectionUtils.isEmpty(menuDOs) ){
			return true;
		}
//		System.err.println();
//		System.err.println(JSON.toJSON(menuDOs));
		List<MenuVO> menus = MenuConverter.getMenus(menuDOs);
//		System.err.println("\n\n");
//		System.err.println(JSON.toJSON(menus));
		//分组
		ArrayList<MenuVO> ms = MenuHelper.mergeMenus(menus);
		return menuCacheMananger.cacheMenus(userId, ms);
	}
	
	public List<MenuVO> getMenusFromTair(long userId){
		return menuCacheMananger.getMenus(userId);
	}
	
}

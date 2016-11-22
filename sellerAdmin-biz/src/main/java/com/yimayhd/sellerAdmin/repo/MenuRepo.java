package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.enums.HaMenuProjectCode;
import com.yimayhd.sellerAdmin.biz.helper.MenuHelper;
import com.yimayhd.sellerAdmin.converter.MenuConverter;
import com.yimayhd.sellerAdmin.model.vo.menu.MenuVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.membercenter.client.dto.UserMenuOptionDTO;
import com.yimayhd.membercenter.client.query.MenuQuery;
import com.yimayhd.membercenter.client.query.UserMenuQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.UserPermissionService;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;

public class MenuRepo {
	private static final Logger logger = LoggerFactory.getLogger("MenuRepo");

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private SessionManager sessionManager;

	public WebResult<List<HaMenuDO>> getMenuListByUserId(long userId){
		WebResult<List<HaMenuDO>> result = new WebResult<List<HaMenuDO>>();
		UserMenuQuery userMenuQuery = new UserMenuQuery();
		userMenuQuery.setUserId(userId);
		userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
		userMenuQuery.setProjectCode(HaMenuProjectCode.SEELER.getCode());
		UserMenuOptionDTO dto = new UserMenuOptionDTO();
		dto.setContainUrl(true);
		MemPageResult<HaMenuDO> queryResult = userPermissionService.getMenuListByUserId(userMenuQuery,dto);
		if (queryResult == null || !queryResult.isSuccess() ) {
			logger.error("getMenuListByUserId failed  userId={}, Result={}",userId, JSON.toJSONString(queryResult));
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		List<HaMenuDO> menus = queryResult.getList();
		result.setValue(menus);
		return result ;
	}
	public WebResult<List<HaMenuDO>> getAllMenus(){
		WebResult<List<HaMenuDO>> result = new WebResult<List<HaMenuDO>>();
		MenuQuery menuQuery = new MenuQuery();
		menuQuery.setDomain(Constant.DOMAIN_JIUXIU);
		UserMenuOptionDTO dto = new UserMenuOptionDTO();
		dto.setContainUrl(true);
		MemResult<List<HaMenuDO>> queryResult = userPermissionService.getMenuList(menuQuery,dto);
		if (queryResult == null || !queryResult.isSuccess() ) {
			logger.error("getMenuList failed  menuQuery={} UserMenuOptionDTO={}",JSON.toJSONString(menuQuery), JSON.toJSONString(dto));
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		List<HaMenuDO> menus = queryResult.getValue();
		result.setValue(menus);
		return result ;
	}
	/**
	 * 更新用户菜单缓存
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public boolean cacheMenuListByUserId(String token) throws Exception {
		UserDO userDO = sessionManager.getUser(token);
		if (userDO != null) {
			UserMenuQuery userMenuQuery = new UserMenuQuery();
			userMenuQuery.setUserId(userDO.getId());
			//userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
			userMenuQuery.setProjectCode(HaMenuProjectCode.SEELER.getCode());
			UserMenuOptionDTO dto = new UserMenuOptionDTO();
			dto.setContainUrl(true);
			boolean queryResult = userPermissionService.catchUserMenu(userMenuQuery, dto);

			return queryResult;
		} else {
			logger.error("cacheMenuListByUserId  getUser(token) failed  token={}", token);
			return false;
		}
	}

	public List<MenuVO> getUserMenus(long userId,boolean fromCatch) {
		List<HaMenuDO> menuDOs = null;
		try {
			if(fromCatch){
				menuDOs = getMenuListByUserIdFromCatch(userId).getValue();
			}else {
				menuDOs = getMenuListByUserId(userId).getValue();
			}
		} catch (Exception e) {
			logger.error("getUserMenus   failed  userId={}", userId);
		}
//		System.err.println(JSON.toJSONString(menuDOs));
		if (!CollectionUtils.isEmpty(menuDOs)) {
			List<MenuVO> menus = MenuConverter.do2MenuVOs(menuDOs);
//		System.err.println(JSON.toJSONString(menus));
			//分组
			ArrayList<MenuVO> ms = MenuHelper.mergeMenus(menus);
			return ms;
		} else {
			return null;
		}
	}


	public WebResult<List<HaMenuDO>> getMenuListByUserIdFromCatch(long userId){
		WebResult<List<HaMenuDO>> result = new WebResult<List<HaMenuDO>>();
		UserMenuQuery userMenuQuery = new UserMenuQuery();
		userMenuQuery.setUserId(userId);
		//userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
		userMenuQuery.setProjectCode(HaMenuProjectCode.SEELER.getCode());
		UserMenuOptionDTO dto = new UserMenuOptionDTO();
		dto.setContainUrl(true);
		MemPageResult<HaMenuDO> queryResult = userPermissionService.getMenuListByUserIdFromCatch(userMenuQuery,dto);
		if (queryResult == null || !queryResult.isSuccess() ) {
			logger.error("getMenuListByUserId failed  userId={}, Result={}",userId, JSON.toJSONString(queryResult));
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		List<HaMenuDO> menus = queryResult.getList();
		result.setValue(menus);
		return result ;
	}
}

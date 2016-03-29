package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.membercenter.client.dto.UserMenuOptionDTO;
import com.yimayhd.membercenter.client.query.UserMenuQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.service.UserPermissionService;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;

public class MenuRepo {
	private static final Logger logger = LoggerFactory.getLogger("MenuRepo");

	@Autowired
	private UserPermissionService userPermissionService;

	public WebResult<List<HaMenuDO>> getMenuListByUserId(long userId){
		WebResult<List<HaMenuDO>> result = new WebResult<List<HaMenuDO>>();
		UserMenuQuery userMenuQuery = new UserMenuQuery();
		userMenuQuery.setUserId(userId);
		userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
		MemPageResult<HaMenuDO> queryResult = userPermissionService.getMenuListByUserId(userMenuQuery,
				new UserMenuOptionDTO());
		if (queryResult == null || !queryResult.isSuccess() ) {
			logger.error("getMenuListByUserId failed  userId={}",userId);
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		List<HaMenuDO> menus = queryResult.getList();
		result.setValue(menus);
		return result ;
	}
}

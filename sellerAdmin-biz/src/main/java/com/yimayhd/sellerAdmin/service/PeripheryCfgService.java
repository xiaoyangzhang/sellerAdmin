package com.yimayhd.sellerAdmin.service;

import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;

/**
  * @autuor : xusq
  * @date : 2015年12月2日
  * @description : 周边配置信息
  */
public interface PeripheryCfgService {

	/**
	 * 添加俱乐部类别信息
	 */
	public RcResult<Boolean> addClubCategoryList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加热门周边信息
	 */
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加活动信息
	 */
	public RcResult<Boolean> addActivityList(CfgBaseVO cfgBaseVO);
	
	/**
	 * 获取俱乐部类别信息
	 */
	public CfgResultVO getClubCattegoryList();
	
	/**
	 * 获取活动信息
	 */
	public CfgResultVO getActivityList();
	
	/**
	 * 获取热门周边信息
	 */
	public CfgResultVO getCityList();
	
}

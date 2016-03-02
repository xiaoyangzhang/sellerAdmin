package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
  * @autuor : xusq
  * @date : 2015年12月4日
  * @description : 发现管理
  */
public interface DiscoveryCfgService {

	/**
	 * 添加伴手礼信息
	 */
	public RcResult<Boolean> addItemList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加游记信息
	 */
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加直播信息
	 */
	public RcResult<Boolean> addSubjectList(CfgBaseVO cfgBaseVO);
	
	/**
	 * 获取伴手礼信息
	 */
	public CfgResultVO getItemList();
	
	/**
	 * 获取游记信息
	 */
	public CfgResultVO getTravelSpecialList();
	
	/**
	 * 获取直播信息
	 */
	public CfgResultVO getSubjectList();

}

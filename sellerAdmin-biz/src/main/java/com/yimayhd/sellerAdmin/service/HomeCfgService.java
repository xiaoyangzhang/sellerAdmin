package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.model.vo.ShowCaseVO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.user.client.domain.UserDO;

/**
 * 首页配置信息
 * @author xusq
 *
 */
public interface HomeCfgService {

	/**
	 * 添加会员专享信息
	 */
	public RcResult<Boolean> addVipList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加线路信息
	 */
	public RcResult<Boolean> addLineList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加旅游咖信息
	 */
	public RcResult<Boolean> addTravelKaList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加目的地信息
	 */
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加游记信息
	 */
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO);
	
	/**
	 * 批量增加showcase信息
	 * @param list
	 * @param boothCode
	 * @return
	 */
	public ServiceResult<Boolean> multiShowCaseOperate(List<ShowCaseVO> list,String boothCode);
	
	/**
	 * 批量更新showcase
	 * @param list
	 * @param boothCode
	 * @return
	 */
	public ServiceResult<Boolean> updateShowCaseBatch(List<ShowcaseDO> list);
	
	/**
	 * 批量增加showcase
	 * @param list
	 * @param boothCode
	 * @return
	 */
	public ServiceResult<Boolean> addShowCaseBatch(List<ShowcaseDO> list);
	
	/**
	 * 增加推荐信息
	 * @return
	 */
	public ServiceResult<Boolean> addRecommends(List<ShowCaseVO> list);
	
	/**
	 * 获取推荐列表
	 * @return
	 */
	public ServiceResult<List<ShowcaseDO>> getRecommends();
	
	/**
	 * 获取旅游大卡
	 * @return
	 */
	public ServiceResult<List<ShowcaseDO>> getTravelKa();
	
	/**
	 * 根据博boothcode获取showcase列表信息
	 * @param bootCode
	 * @return
	 */
	public ServiceResult<List<ShowcaseDO>> getShowCases(String boothCode);
	
	/**
	 * 获取广告位的showcase信息
	 * @return
	 */
	public ServiceResult<List<ShowcaseDO>> getAdvertiseShowcase();
	
	
	public ServiceResult<LineDO> getLineDetail(Long id);
	
	public ServiceResult<SnsActivityDO> getActivityDetail(Long id);
	
	public ServiceResult<UserDO> getTravelKaDetail(Long id);
	
	
	/**
	 * 增加广告展位信息
	 * @param list
	 * @return
	 */
	public ServiceResult<Boolean> addAdvertise(List<ShowCaseVO> list);
	
	/**
	 * 
	 */
	
	public ServiceResult<Boolean> addTravelKaList(List<ShowCaseVO> list);
	
	/**
	 * 获取会员专享信息
	 */
	public CfgResultVO getVipList();
	
	/**
	 * 获取线路信息
	 */
	public CfgResultVO getLineList();
	
	/**
	 * 获取旅游咖信息
	 */
	public CfgResultVO getTravelKaList();
	
	/**
	 * 获取目的地信息
	 */
	public CfgResultVO getCityList();
	
	/**
	 * 获取游记信息
	 */
	public CfgResultVO getTravelSpecialList();

	/**
	 * 查询精彩推荐
	 * @return
	 */
	public CfgResultVO getGreatRecommentList();
	
	
}

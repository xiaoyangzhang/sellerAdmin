package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.RelevanceRecommended;
import com.yimayhd.sellerAdmin.model.TripBo;
import com.yimayhd.sellerAdmin.model.TripBoQuery;
import com.yimayhd.sellerAdmin.model.query.LiveListQuery;
import com.yimayhd.sellerAdmin.model.query.ScenicListQuery;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;

/** 
* @ClassName: TripAService 
* @Description: (出发地 目的地) 
* @author create by yushengwei @ 2015年12月10日 下午7:58:28 
*/
public interface TripService {
	/**
	* @Title: saveTrip 
	* @Description:(保存 修改 出发地 目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:41:35 
	* @param @param tripBo
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	RegionDO saveOrUpdate(TripBo tripBo) throws Exception;
	
	/**
	* @Title: saveOrUpdateDetail 
	* @Description:(保存目的地的的详细信息) 
	* @author create by yushengwei @ 2015年12月22日 上午11:01:25 
	* @param @param tripBo
	* @param @return
	* @param @throws Exception 
	* @return TripBo 返回类型 
	* @throws
	 */
	RegionDO saveOrUpdateDetail(TripBo tripBo,boolean isEdit) throws Exception;
	
	/**
	* @Title: delTrip 
	* @Description:(删除出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:41:58 
	* @param @param long id
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean delTrip(long id);
	
	/**
	* @Title: RegionDO 
	* @Description:(根据id查询TripBo) 
	* @author create by yushengwei @ 2015年12月10日 下午8:43:57 
	* @param @param id
	* @param @return 
	* @return TripBo 返回类型 
	* @throws
	 */
	TripBo getTripBo(long  id);
	
	/**
	* @Title: editTripBo 
	* @Description:(修改出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:43:06 
	* @param @param tripBo
	* @param @return 
	* @return TripBo 返回类型 
	* @throws
	 */
	boolean editTripBo(TripBo tripBo);
	
	/**
	* @Title: relevanceRecommended 
	* @Description:(出发地目的地关联各种推荐) 
	* @author create by yushengwei @ 2015年12月10日 下午8:42:14 
	* @param @param type
	* @param @param destinationId
	* @param @param showCaseId
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean relevanceRecommended(int type,int cityCode,int resourceId[])throws Exception;
	
	boolean relevanceRecommended(List<RelevanceRecommended> list ,boolean isEdit)throws Exception;
	
	/**
	* @Title: selectRegion 
	* @Description:(获取地区列表，用户用户创建出发地目的地的时候选择对应的城市名称) 
	* @author create by yushengwei @ 2015年12月16日 上午10:07:23 
	* @param @param type
	* @param @return 
	* @return List<RegionDO> 返回类型 
	* @throws
	 */
	PageVO<RegionDO> selectRegion(TripBoQuery tripBoQuery);
	
	/**
	* @Title: selectScenicDO 
	* @Description:(获取景区列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:28:57 
	* @param @param scenicPageQuery
	* @param @return
	* @param @throws Exception 
	* @return PageVO<ScenicDO> 返回类型 
	* @throws
	 */
	PageVO<ScenicDO> selectScenicDO(ScenicListQuery scenicPageQuery) throws Exception;
	
	/**
	* @Title: getListShowCaseResult 
	* @Description:(获取RegionIntroduceDO列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:33:32 
	* @param @param type
	* @param @return 
	* @return List<ShowCaseResult> 返回类型 
	* @throws
	 */
	List<RegionIntroduceDO> getListShowCaseResult(int type);
	
	/**
	* @Title: getListShowCaseResult 
	* @Description:(获取跟目的地关联的RegionIntroduceDO) 
	* @author create by yushengwei @ 2015年12月19日 下午7:06:31 
	* @param @param type
	* @param @param cityCode
	* @param @return 
	* @return List<RegionIntroduceDO> 返回类型 
	* @throws
	 */
	PageVO<RegionIntroduceDO> getPageRegionIntroduceDO(RegionIntroduceQuery regionIntroduceQuery);
	
	/**
	* @Title: selecthotelDO 
	* @Description:(获取酒店列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:46:21 
	* @param @param hotelListQuery
	* @param @return 
	* @return List<HotelDO> 返回类型 
	* @throws
	 */
	List<HotelDO> getListHotelDO(String cityCode)throws Exception;
	
	/**
	* @Title: blockOrUnBlock 
	* @Description:(这里用一句话描述这个方法的作用) 
	* @author create by yushengwei @ 2015年12月22日 上午11:36:05 
	* @param @param id
	* @param @param cityCode
	* @param @param type
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean blockOrUnBlock(List<Long> ids,int status)throws Exception;
	
	
	List<RegionDO> selectRegion(int type,boolean isAll);
	
	/**
	* @Title: getPageSnsSubjectDO 
	* @Description:(获取直播列表) 
	* @author create by yushengwei @ 2015年12月29日 上午11:47:33 
	* @param @return 
	* @return PageVO<SnsSubjectDO> 返回类型 
	* @throws
	 */
	PageVO<SnsSubjectDO> getPageSnsSubjectDO(LiveListQuery liveListQuery);
}

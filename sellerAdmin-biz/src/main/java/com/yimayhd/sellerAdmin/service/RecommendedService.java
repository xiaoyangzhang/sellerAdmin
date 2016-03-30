package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.sellerAdmin.base.PageVO;

/**
* @ClassName: RecommendedService 
* @Description: (推荐相关的东西,买必推荐，比体验路线等) 
* @author create by yushengwei @ 2015年12月21日 下午1:54:55
 */
public interface RecommendedService {
	
	boolean saveOrUpdate(RegionIntroduceDO regionIntroduce ) throws Exception; 
	
	List<RegionIntroduceDO> queryRegionIntroduceList(RegionIntroduceQuery regionIntroduceQuery);
	
	PageVO<RegionIntroduceDO> pageVORegionIntroduceDO(RegionIntroduceQuery regionIntroduceQuery);
	
	boolean delete(long id);
	
	RegionIntroduceDO getRegionIntroduceDO(long id);
	
}

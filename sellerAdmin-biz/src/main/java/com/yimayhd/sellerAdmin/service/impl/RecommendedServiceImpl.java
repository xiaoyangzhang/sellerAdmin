package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.service.RecommendedService;

public class RecommendedServiceImpl implements RecommendedService {

	@Autowired RegionIntroduceClientService RegionIntroduceClientServiceRef;
	
	public boolean saveOrUpdate(RegionIntroduceDO regionIntroduce) throws Exception {
		if(null == regionIntroduce){
			throw new Exception("parameters [regionIntroduceDO] cannot be empty,RegionIntroduceDO="+JSON.toJSONString(regionIntroduce));
		}
		/*RegionIntroduceDO regionIntroduceDO = new RegionIntroduceDO();
		regionIntroduceDO.setCityCode(regionIntroduce.getCityCode());
		regionIntroduceDO.setContent(regionIntroduce.getContent());
		regionIntroduceDO.setDesc(regionIntroduce.getDesc());
		regionIntroduceDO.setGmtCreate(new Date());
		regionIntroduceDO.setId(regionIntroduce.getId());
		regionIntroduceDO.setTitle(regionIntroduce.getTitle());
		regionIntroduceDO.setType(regionIntroduce.getType());*/
		if(0 == regionIntroduce.getId()){
			return RegionIntroduceClientServiceRef.add(regionIntroduce);
		}else{
			return RegionIntroduceClientServiceRef.update(regionIntroduce);
		}
	}

	@Override
	public List<RegionIntroduceDO> queryRegionIntroduceList(RegionIntroduceQuery regionIntroduceQuery) {
		return RegionIntroduceClientServiceRef.queryRegionIntroduceList(regionIntroduceQuery);
	}

	@Override
	public boolean delete(long id) {
		return false;
	}

	@Override
	public RegionIntroduceDO getRegionIntroduceDO(long id) {
		RegionIntroduceDO regionIntroduceDO = RegionIntroduceClientServiceRef.getRegionIntroduceById(id);
		return regionIntroduceDO;
	}

	@Override
	public PageVO<RegionIntroduceDO> pageVORegionIntroduceDO(RegionIntroduceQuery regionIntroduceQuery) {
		regionIntroduceQuery.setNeedCount(true);
		int totalCount = 0;
		List<RegionIntroduceDO> list = new ArrayList<RegionIntroduceDO>();
		RCPageResult<RegionIntroduceDO> res = RegionIntroduceClientServiceRef.queryPageRegionIntroduceList(regionIntroduceQuery);
		if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())){
			 list = res.getList();
			 totalCount = res.getTotalCount();
		}
		return new PageVO<RegionIntroduceDO>(regionIntroduceQuery.getPageNo(), regionIntroduceQuery.getPageSize(), totalCount, list);
	}

}

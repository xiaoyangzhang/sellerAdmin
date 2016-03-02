package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.model.Region;
import com.yimayhd.sellerAdmin.service.RegionService;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionClientService;

/**
 * Created by Administrator on 2015/11/13.
 */
public class RegionServiceImpl implements RegionService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private RegionClientService regionClientServiceRef;

	@Override
	public List<Region> getProvince() throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		for (int i = 0; i < 5; i++) {
			Region regionData = new Region();
			regionData.setId((long) (i * 10 + 10));
			regionData.setName("省份" + i);
			regionList.add(regionData);
		}
		return regionList;
	}

	@Override
	public List<Region> getRegionByParentId(long id) throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		for (int i = 0; i < 5; i++) {
			Region regionData = new Region();
			regionData.setId((long) (i * 10 + 100));
			regionData.setName("城市" + i);
			regionData.setParentId(id);
			regionList.add(regionData);
		}
		return regionList;
	}

	@Override
	public List<Region> getRegions(RegionType regionType) throws BaseException {
		List<Region> regions = new ArrayList<Region>();
		RepoUtils.requestLog(log, "regionClientServiceRef.getRegionDOListByType");
		RCPageResult<RegionDO> pageResult = regionClientServiceRef.getRegionDOListByType(regionType.getType());
		RepoUtils.resultLog(log, "regionClientServiceRef.getRegionDOListByType", pageResult);
		List<RegionDO> regionDOs = pageResult.getList();
		if (CollectionUtils.isNotEmpty(regionDOs)) {
			for (RegionDO regionDO : regionDOs) {
				regions.add(new Region(regionDO));
			}
		}
		return regions;
	}

}

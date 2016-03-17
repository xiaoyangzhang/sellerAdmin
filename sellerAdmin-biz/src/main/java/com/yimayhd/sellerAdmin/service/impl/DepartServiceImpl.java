package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;
import java.util.Map;

import com.yimayhd.sellerAdmin.model.line.IdNamePair;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.service.DepartService;
import com.yimayhd.user.client.cache.CityDataCacheClient;

public class DepartServiceImpl implements DepartService {
	private CityRepo cityRepo;
	private CommentRepo commentRepo;

	@Override
	public Map<String, List<IdNamePair>> queryAllDepartGroupByInitial() {
		return null;
	}

}

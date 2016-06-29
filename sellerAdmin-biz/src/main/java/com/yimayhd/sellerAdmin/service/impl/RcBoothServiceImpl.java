package com.yimayhd.sellerAdmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.sellerAdmin.service.RcBoothService;

public class RcBoothServiceImpl implements RcBoothService {
	
	@Autowired
	private BoothClientServer boothClientService;

	@Override
	public RCPageResult<BoothDO> getBoothResult(BoothQuery boothQuery) {
		return boothClientService.getBoothResult(boothQuery);
	}

	@Override
	public RcResult<BoothDO> getBoothById(long id) {
		return boothClientService.getBoothById(id);
	}

	@Override
	public RcResult<Boolean> save(BoothDO boothDO) {
		//return boothClientService.insert(boothDO);
		//TODO 报错注释
		return null;
	}

	@Override
	public RcResult<Boolean> update(BoothDO boothDO) {
		return boothClientService.update(boothDO);
	}

}

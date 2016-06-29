package com.yimayhd.sellerAdmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.sellerAdmin.service.RcShowcaseService;

public class RcShowcaseServiceImpl implements RcShowcaseService{

	@Autowired
	private ShowcaseClientServer showcaseService;
	
	@Override
	public RCPageResult<ShowCaseResult> queryShowcaseResult(ShowcaseQuery showcaseQuery) {
		return showcaseService.getShowcaseResult(showcaseQuery);
	}

	@Override
	public RcResult<ShowCaseResult> getShowcaseResult(ShowCaseDTO showCaseDTO) {
		return showcaseService.getShowcaseResult(showCaseDTO);
	}

	@Override
	public RcResult<Boolean> save(ShowcaseDO showcaseDO) {
		return showcaseService.insert(showcaseDO);
	}

	@Override
	public RcResult<Boolean> update(ShowcaseDO showcaseDO) {
		return showcaseService.update(showcaseDO);
	}

	@Override
	public RcResult<Boolean> delete(long id) {
		return showcaseService.delete(id);
	}

}

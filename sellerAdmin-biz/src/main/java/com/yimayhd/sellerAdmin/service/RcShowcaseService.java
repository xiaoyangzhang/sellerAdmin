package com.yimayhd.sellerAdmin.service;

import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;

public interface RcShowcaseService {

	public RCPageResult<ShowCaseResult> queryShowcaseResult(ShowcaseQuery showcaseQuery);
	
	public RcResult<ShowCaseResult> getShowcaseResult(ShowCaseDTO showCaseDTO);
	
	public RcResult<Boolean> save(ShowcaseDO showcaseDO);
	
	public RcResult<Boolean> update(ShowcaseDO showcaseDO);
	
	public RcResult<Boolean> delete(long id);
	
}

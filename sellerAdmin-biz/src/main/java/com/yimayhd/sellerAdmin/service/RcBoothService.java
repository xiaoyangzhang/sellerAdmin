package com.yimayhd.sellerAdmin.service;

import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;

public interface RcBoothService {

	
	public RCPageResult<BoothDO> getBoothResult(BoothQuery boothQuery);
	
	public RcResult<BoothDO> getBoothById(long id);
	
	public RcResult<Boolean> save(BoothDO boothDO);
	
	public RcResult<Boolean> update(BoothDO boothDO);
	
}

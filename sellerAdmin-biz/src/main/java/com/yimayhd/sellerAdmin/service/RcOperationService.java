package com.yimayhd.sellerAdmin.service;

import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;

public interface RcOperationService {

	public RcResult<OperationDO> getOperationById(long id);
	
	public RCPageResult<OperationDO> getOperationResult(OperationQuery operationQuery);
	
	public RcResult<Boolean> save(OperationDO operationDO);
	
	public RcResult<Boolean> update(OperationDO operationDO);
	
}

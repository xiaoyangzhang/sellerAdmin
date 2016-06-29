package com.yimayhd.sellerAdmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.OperationClientServer;
import com.yimayhd.sellerAdmin.service.RcOperationService;

public class RcOperationServiceImpl implements RcOperationService {

	@Autowired
	private OperationClientServer operationService;
	
	@Override
	public RcResult<OperationDO> getOperationById(long id) {
		return operationService.getOperationById(id);
	}

	@Override
	public RCPageResult<OperationDO> getOperationResult(OperationQuery operationQuery) {
		return operationService.getOperationResult(operationQuery);
	}

	@Override
	public RcResult<Boolean> save(OperationDO operationDO) {
		return operationService.insert(operationDO);
	}

	@Override
	public RcResult<Boolean> update(OperationDO operationDO) {
		return operationService.update(operationDO);
	}

}

package com.yimayhd.sellerAdmin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.service.CommLineService;

public class CommLineServiceImpl implements CommLineService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected LineRepo lineRepo;

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		return lineRepo.pageQueryLine(query);
	}
}

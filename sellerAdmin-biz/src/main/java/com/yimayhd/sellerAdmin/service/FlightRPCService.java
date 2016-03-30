package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.FlightCompanyDO;
import com.yimayhd.ic.client.model.query.FlightCompanyPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;

/**
 * 航班查询接口
 * 
 * @author yebin
 *
 */
public interface FlightRPCService {
	PageVO<FlightCompanyDO> pageQueryFlightCompany(FlightCompanyPageQuery query);

	List<FlightCompanyDO> findAllFlightCompany();
}

package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommTravelService {
	/**
	 * 发布线路
	 * 
	 * @param selfServiceTravel
	 * @return
	 * @throws Exception
	 */
	<T extends BaseTravel> long publishLine(T travel);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<T extends BaseTravel> T getById(long id, Class<T> clazz);

	/**
	 * 查询线路
	 * 
	 * @return
	 */
	PageVO<LineDO> pageQueryLine(LinePageQuery query);
}

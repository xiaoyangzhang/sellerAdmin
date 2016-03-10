package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommLineService {

	/**
	 * 查询线路
	 * 
	 * @return
	 */
	PageVO<LineDO> pageQueryLine(LinePageQuery query);
}

package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.line.BaseLineVO;
import com.yimayhd.sellerAdmin.model.line.free.FreeLineVO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommFreeLineService {
	/**
	 * 发布线路
	 * 
	 * @param line
	 * @return
	 */
	long publishLine(FreeLineVO line);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 */
	FreeLineVO getById(long id);
}

package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.line.LineContextConfig;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.LineVO;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommLineService {

	/**
	 * 发布线路
	 * 
	 * @param line
	 * @return
	 */
	void update(long sellerId, LineVO line);

	long save(long sellerId, LineVO line);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 */
	LineVO getById(long id);

	/**
	 * 查询线路
	 * 
	 * @return
	 */
	PageVO<LineDO> pageQueryLine(LinePageQuery query);

	/**
	 * 获取线路上下文配置
	 * 
	 * @return
	 */
	LineContextConfig getLineContextConfig();

	/**
	 * 获取线路Property配置
	 * 
	 * @return
	 */
	LinePropertyConfig getLinePropertyConfig(long categoryId);
}

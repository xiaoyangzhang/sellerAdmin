package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.model.line.tour.TourLineVO;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommTourLineService {
	/**
	 * 发布线路
	 * 
	 * @param line
	 * @return
	 */
	long publishLine(TourLineVO line);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 */
	TourLineVO getById(long id);
}

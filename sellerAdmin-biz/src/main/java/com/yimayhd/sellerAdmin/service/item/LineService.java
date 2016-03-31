package com.yimayhd.sellerAdmin.service.item;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LineCategoryConfig;
import com.yimayhd.sellerAdmin.model.line.LinePropertyConfig;
import com.yimayhd.sellerAdmin.model.line.LineVO;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface LineService {

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
	 * 获取线路类目配置
	 * 
	 * @return
	 */
	LineCategoryConfig getLineCategoryConfig();

	/**
	 * 获取全部主题
	 * 
	 * @return
	 */
	List<ComTagDO> getAllLineThemes();

	/**
	 * 获取全部出发地
	 * 
	 * @return
	 */
	List<CityVO> getAllLineDeparts();

	/**
	 * 获取全部目的地
	 * 
	 * @return
	 */
	List<CityVO> getAllLineDests();

	/**
	 * 获取线路Property配置
	 * 
	 * @return
	 */
	LinePropertyConfig getLinePropertyConfig(long categoryId);
}

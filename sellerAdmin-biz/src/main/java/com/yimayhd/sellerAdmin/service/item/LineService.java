package com.yimayhd.sellerAdmin.service.item;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
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
	WebOperateResult update(long sellerId, LineVO line);

	WebResult<Long> save(long sellerId, LineVO line);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 */
	WebResult<LineVO> getByItemId(long id);

	/**
	 * 查询线路
	 * 
	 * @return
	 */
	WebResult<PageVO<LineDO>> pageQueryLine(LinePageQuery query);

	/**
	 * 获取线路类目配置
	 * 
	 * @return
	 */
	WebResult<LineCategoryConfig> getLineCategoryConfig();

	/**
	 * 获取全部主题
	 * 
	 * @return
	 */
	WebResult<List<ComTagDO>> getAllLineThemes();

	/**
	 * 获取全部出发地
	 * 
	 * @return
	 */
	WebResult<List<CityVO>> getAllLineDeparts();

	/**
	 * 获取全部目的地
	 * 
	 * @return
	 */
	WebResult<List<CityVO>> getAllLineDests();

	/**
	 * 获取线路Property配置
	 * 
	 * @return
	 */
	WebResult<LinePropertyConfig> getLinePropertyConfig(long categoryId);
}

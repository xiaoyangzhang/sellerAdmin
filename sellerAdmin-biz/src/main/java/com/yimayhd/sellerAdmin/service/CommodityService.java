package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;

/**
 * Created by Administrator on 2015/11/24.
 */
public interface CommodityService {
	/**
	 * 获取商品列表
	 * 
	 * @param commodityListQuery
	 * @return
	 * @throws Exception
	 */
	PageVO<ItemVO> getList(CommodityListQuery commodityListQuery) throws Exception;

	/**
	 * 根据id获取商品信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ItemResultVO getCommodityById(long id) throws Exception;

	ItemResultVO getCommodityById(long sellerId, long id) throws Exception;

	/**
	 * 根据id获取酒店商品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ItemDO getCommHotelById(long id) throws Exception;

	/**
	 * 新增酒店商品
	 * 
	 * @param itemVO
	 * @return
	 * @throws Exception
	 */
	ItemDO addCommHotel(ItemVO itemVO) throws Exception;

	/**
	 * 修改酒店商品
	 * 
	 * @param itemVO
	 * @throws Exception
	 */
	void modifyCommHotel(ItemVO itemVO) throws Exception;

	/**
	 * 批量修改商品状态
	 * 
	 * @param idList
	 *            商品idList
	 * @param status
	 *            状态
	 * @throws Exception
	 */
	void setCommStatusList(List<Long> idList, int status) throws Exception;

	/**
	 * 商品上架
	 * 
	 * @param sellerId
	 *            商家id
	 * @param id
	 *            商品ID
	 */
	void publish(long sellerId, long id) throws Exception;

	/**
	 * 商品上架
	 * 
	 * @param sellerId
	 *            商家id
	 * @param id
	 *            商品ID
	 */
	void close(long sellerId, long id) throws Exception;

	/**
	 *
	 * @param sellerId
	 * @param idList
	 */
	void batchPublish(long sellerId, List<Long> idList);

	/**
	 *
	 * @param sellerId
	 * @param idList
	 */
	void batchClose(long sellerId, List<Long> idList);

	/**
	 * 新增普通商品或积分商品
	 * 
	 * @param itemVO
	 *            普通商品表单对象
	 * @throws Exception
	 */
	void addCommonItem(ItemVO itemVO) throws Exception;

	/**
	 * 修改普通商品或积分商品
	 * 
	 * @param itemVO
	 *            普通商品表单对象
	 * @throws Exception
	 */
	void modifyCommonItem(ItemVO itemVO) throws Exception;

}

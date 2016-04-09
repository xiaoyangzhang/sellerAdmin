package com.yimayhd.sellerAdmin.service.item;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;

/**
 * 商品服务
 * 
 * @author yebin
 *
 */
public interface ItemService {
	/**
	 * 查询商家商品列表
	 * 
	 * @param sellerId
	 * @param query
	 * @return
	 */
	WebResult<PageVO<ItemListItemVO>> getItemList(long sellerId, ItemListQuery query);

	/**
	 * 下架
	 * 
	 * @param itemId
	 * @return
	 */
	WebOperateResult shelve(long sellerId, long itemId);

	/**
	 * 上架
	 * 
	 * @param itemId
	 * @return
	 */
	WebOperateResult unshelve(long sellerId, long itemId);

	/**
	 * 删除
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 */
	WebOperateResult delete(long sellerId, long itemId);

	/**
	 * 批量下架
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	WebOperateResult batchShelve(long sellerId, List<Long> itemIds);

	/**
	 * 批量下架
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	WebOperateResult batchUnshelve(long sellerId, List<Long> itemIds);

	/**
	 * 批量删除
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	WebOperateResult batchDelete(long sellerId, List<Long> itemIds);
}

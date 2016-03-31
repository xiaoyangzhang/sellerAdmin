package com.yimayhd.sellerAdmin.service.item;

import com.yimayhd.sellerAdmin.base.PageVO;
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
}

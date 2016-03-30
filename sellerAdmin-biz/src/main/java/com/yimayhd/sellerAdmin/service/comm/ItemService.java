package com.yimayhd.sellerAdmin.service.comm;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
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
	PageVO<ItemListItemVO> getItemList(long sellerId, ItemListQuery query);
}

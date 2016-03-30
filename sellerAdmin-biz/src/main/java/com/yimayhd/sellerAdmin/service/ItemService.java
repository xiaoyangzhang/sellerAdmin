package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;

/**
 * 商品服务
 * 
 * @author yebin
 *
 */
public interface ItemService {
	PageVO<ItemVO> getItemList(ItemListQuery query);
}

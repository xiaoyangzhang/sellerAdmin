package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.model.ItemVO;
public interface CommActivityService {

	/**
	 * 保存景区商品
	 * @param itemVO
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult add(ItemVO itemVO) throws Exception;

	public void update(ItemVO itemVO)throws Exception;
	
	

}

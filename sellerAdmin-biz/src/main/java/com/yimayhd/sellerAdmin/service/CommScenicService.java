package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.model.CommScenicVO;
public interface CommScenicService {

	/**
	 * 保存景区商品
	 * @param itemDo
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult save(CommScenicVO commScenic) throws Exception;
	
	

}

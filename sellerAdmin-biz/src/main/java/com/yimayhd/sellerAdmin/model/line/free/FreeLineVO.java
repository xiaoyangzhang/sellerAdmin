package com.yimayhd.sellerAdmin.model.line.free;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.model.line.BaseLineVO;

/**
 * 自由行
 * 
 * @author yebin
 *
 */
public class FreeLineVO extends BaseLineVO {

	@Override
	public int getItemType() {
		return ItemType.FLIGHT_HOTEL.getValue();
	}

}

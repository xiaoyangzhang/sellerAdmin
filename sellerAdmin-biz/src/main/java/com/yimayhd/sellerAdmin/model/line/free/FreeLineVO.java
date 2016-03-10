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
	private static final long serialVersionUID = 4414792322352254786L;

	@Override
	public int getItemType() {
		return ItemType.FLIGHT_HOTEL.getValue();
	}

}

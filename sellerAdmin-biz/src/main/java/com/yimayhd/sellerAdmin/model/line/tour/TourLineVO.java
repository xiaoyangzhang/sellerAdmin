package com.yimayhd.sellerAdmin.model.line.tour;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.model.line.BaseLineVO;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class TourLineVO extends BaseLineVO {
	private static final long serialVersionUID = 5839870323566486318L;

	@Override
	public int getItemType() {
		return ItemType.LINE.getValue();
	}

}

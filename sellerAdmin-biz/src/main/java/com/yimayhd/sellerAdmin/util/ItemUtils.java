package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;

/**
 * 商品工具类
 * 
 * @author yebinL
 *
 */
public class ItemUtils {
	public static List<ItemOperate> getItemOperates(int ItemType, int status) {
		List<ItemOperate> operates = new ArrayList<ItemOperate>();
		if (ItemStatus.create.getValue() == status) {
			operates.add(ItemOperate.EDIT);
			operates.add(ItemOperate.SHELVE);
			operates.add(ItemOperate.DELETE);
		} else if (ItemStatus.invalid.getValue() == status) {
			operates.add(ItemOperate.EDIT);
			operates.add(ItemOperate.SHELVE);
			operates.add(ItemOperate.DELETE);
		} else if (ItemStatus.valid.getValue() == status) {
			operates.add(ItemOperate.VIEW);
			operates.add(ItemOperate.UNSHELVE);
		}
		return operates;
	}
}

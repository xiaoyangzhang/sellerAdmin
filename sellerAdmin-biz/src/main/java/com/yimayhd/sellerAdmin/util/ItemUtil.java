package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;

/**
 * 商品工具类
 * 
 * @author yebinL
 *
 */
public class ItemUtil {
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

	public static String getItemTypeName(int itemType) {
		if (itemType <= 0) {
			return null;
		}
		ItemType it = ItemType.get(itemType);
		if (it != null) {
			return it.getText();
		}
		return null;
	}

	public static String getItemStatusName(int status) {
		if (status <= 0) {
			return null;
		}
		ItemStatus is = ItemStatus.get(status);
		if (is != null) {
			return is.getText();
		}
		return null;
	}
}

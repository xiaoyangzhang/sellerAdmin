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
	public static List<String> getItemOperates(int ItemType, int status) {
		List<String> operates = new ArrayList<String>();
		if (ItemStatus.create.getValue() == status) {
			operates.add(ItemOperate.EDIT.name());
			operates.add(ItemOperate.SHELVE.name());
			operates.add(ItemOperate.DELETE.name());
		} else if (ItemStatus.invalid.getValue() == status) {
			operates.add(ItemOperate.EDIT.name());
			operates.add(ItemOperate.SHELVE.name());
			operates.add(ItemOperate.DELETE.name());
		} else if (ItemStatus.valid.getValue() == status) {
			operates.add(ItemOperate.VIEW.name());
			operates.add(ItemOperate.UNSHELVE.name());
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

	public static boolean isFreeLine(ItemType itemType) {
		return ItemType.FREE_LINE.equals(itemType);
	}
}

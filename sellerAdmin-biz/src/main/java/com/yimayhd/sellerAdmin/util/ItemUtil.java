package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;
import com.yimayhd.sellerAdmin.enums.BizItemType;
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
			operates.add(ItemOperate.VIEW.name());
			operates.add(ItemOperate.EDIT.name());
			operates.add(ItemOperate.SHELVE.name());
			operates.add(ItemOperate.DELETE.name());
		} else if (ItemStatus.invalid.getValue() == status) {
			operates.add(ItemOperate.VIEW.name());
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
		BizItemType it = BizItemType.get(itemType);
		if (it != null) {
			return it.getText();
		}
		return "未知类型";
	}

	public static String getItemStatusName(int status) {
		if (status <= 0) {
			return null;
		}
		BizItemStatus is = BizItemStatus.get(status);
		if (is != null) {
			return is.getText();
		}
		return "异常状态";
	}

	public static boolean isFreeLine(ItemType itemType) {
		return ItemType.FREE_LINE.equals(itemType);
	}
}

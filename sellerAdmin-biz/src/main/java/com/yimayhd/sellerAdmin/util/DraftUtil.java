package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.sellerAdmin.enums.BizDraftSubType;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;

/**
 * 草稿箱工具类
 * 
 * @author xiemingna
 *
 */
public class DraftUtil {
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

	public static String getDraftSubTypeName(int itemType) {
		if (itemType <= 0) {
			return null;
		}
		BizDraftSubType it = BizDraftSubType.get(itemType);
		if (it != null) {
			return it.getText();
		}
		return "未知类型";
	}

	public static String getDraftSubStatusName(int status) {
		if (status <= 0) {
			return null;
		}
		BizItemStatus is = BizItemStatus.get(status);
		if (is != null) {
			return is.getText();
		}
		return "异常状态";
	}
}

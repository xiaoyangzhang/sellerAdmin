package com.yimayhd.sellerAdmin.util;

import com.yimayhd.sellerAdmin.enums.BizDraftSubType;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;

/**
 * 草稿箱工具类
 * 
 * @author xiemingna
 *
 */
public class DraftUtil {
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

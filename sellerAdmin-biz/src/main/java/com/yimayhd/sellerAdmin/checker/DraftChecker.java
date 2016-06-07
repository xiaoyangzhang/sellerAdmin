package com.yimayhd.sellerAdmin.checker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class DraftChecker {
	private static final List<Integer> supportItemTypes = new ArrayList<Integer>();
	// private static final Pattern NAME_PATTERN =
	// Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]{1,38}$");
	static {
		supportItemTypes.add(ItemType.ACTIVITY.getValue());
		supportItemTypes.add(ItemType.CITY_ACTIVITY.getValue());
		supportItemTypes.add(ItemType.FLIGHT_HOTEL.getValue());
		supportItemTypes.add(ItemType.GF.getValue());
		supportItemTypes.add(ItemType.HOTEL.getValue());
		supportItemTypes.add(ItemType.LINE.getValue());
		supportItemTypes.add(ItemType.MEMBER_RECHARGE.getValue());
		supportItemTypes.add(ItemType.NORMAL.getValue());
		supportItemTypes.add(ItemType.SPOTS.getValue());
		supportItemTypes.add(ItemType.SPOTS_HOTEL.getValue());
		supportItemTypes.add(ItemType.TEMP_PRODUCT_CARD.getValue());
		supportItemTypes.add(ItemType.TOUR_LINE.getValue());
		supportItemTypes.add(ItemType.FREE_LINE.getValue());
	}

	public static WebCheckResult checkLine(DraftVO draftVo) {
		WebCheckResult checkBaseInfo = checkBaseInfo(draftVo);
		if (!checkBaseInfo.isSuccess()) {
			return checkBaseInfo;
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkBaseInfo(DraftVO draftVo) {
		if (draftVo == null) {
			return WebCheckResult.error("基础信息不能为空");
		}
		int type = draftVo.getSubType();
		if (!supportItemTypes.contains(type)) {
			return WebCheckResult.error("未知商品类型");
		}
		return WebCheckResult.success();
	}

}

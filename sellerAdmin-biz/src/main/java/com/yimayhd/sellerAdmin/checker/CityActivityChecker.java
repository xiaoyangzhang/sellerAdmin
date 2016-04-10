package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.CityActivityVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class CityActivityChecker {

	public static WebCheckResult checkCityActivity(CityActivityItemVO cityActivity) {
		WebCheckResult checkItemInfo = checkItemInfo(cityActivity.getItemVO());
		if (!checkItemInfo.isSuccess()) {
			return checkItemInfo;
		}
		WebCheckResult checkCityActivityInfo = checkCityActivityInfo(cityActivity.getCityActivityVO());
		if (!checkCityActivityInfo.isSuccess()) {
			return checkCityActivityInfo;
		}
//		WebCheckResult checkPictureText = checkPictureText(cityActivity.getPictureText());
//		if (!checkPictureText.isSuccess()) {
//			return checkPictureText;
//		}
//		WebCheckResult checkNeedKnow = checkNeedKnow(cityActivity.getNeedKnow());
//		if (!checkNeedKnow.isSuccess()) {
//			return checkNeedKnow;
//		}
//		if (CollectionUtils.isEmpty(cityActivity.getThemes())) {
//			return WebCheckResult.error("主题不能为空");
//		}
		if (cityActivity.getDest() == null) {
			return WebCheckResult.error("活动城市不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkNeedKnow(NeedKnowVO needKnow) {
		List<NeedKnowItemVo> needKnowItems = needKnow.getNeedKnowItems();
		if (CollectionUtils.isNotEmpty(needKnowItems)) {
			for (NeedKnowItemVo needKnowItem : needKnowItems) {
				if (StringUtils.isBlank(needKnowItem.getTitle())) {
					return WebCheckResult.error("预定须知标题为空");
				}
				if (StringUtils.isBlank(needKnowItem.getContent())) {
					return WebCheckResult.error(needKnowItem.getTitle() + "内容不能为空");
				}
			}
		} else {
			return WebCheckResult.error("预定须知不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkItemInfo(ItemVO itemInfo) {
		if (StringUtils.isBlank(itemInfo.getTitle())) {
			return WebCheckResult.error("商品标题不能为空");
		} else if (itemInfo.getTitle().length() > 38) {
			return WebCheckResult.error("商品标题不能超过38个字");
		}
		if (StringUtils.isBlank(itemInfo.getDescription())) {
			return WebCheckResult.error("活动亮点不能为空");
		}
//		if (CollectionUtils.isEmpty(itemInfo.getItemMainPics())) {
//			return WebCheckResult.error("商品图不能为空");
//		}
		if (itemInfo.getLatitudeVO() == null || itemInfo.getLongitudeVO() == null) {
			return WebCheckResult.error("经纬度不能为空");
		}
		if (CollectionUtils.isEmpty(itemInfo.getItemSkuVOListByStr())) {
			return WebCheckResult.error("价格信息不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkCityActivityInfo(CityActivityVO cityActivityVO) {
		if (StringUtils.isBlank(cityActivityVO.getLocationText())) {
			return WebCheckResult.error("详细地址不能为空");
		} else if (cityActivityVO.getLocationText().length() > 38) {
			return WebCheckResult.error("详细地址不能超过38个字");
		}
		return WebCheckResult.success();
	}


	public static WebCheckResult checkPictureText(PictureTextVO pictureText) {
		// TODO YEBIN 待开发
		return WebCheckResult.success();
	}

	public static WebCheckResult checkPictureTextItem(PictureTextItemVo pictureTextItem) {
		return WebCheckResult.success();
	}

}

package com.yimayhd.sellerAdmin.checker;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.CityActivityVO;
import com.yimayhd.sellerAdmin.model.ItemSkuVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;

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
		WebCheckResult checkPictureText = PictureTextChecker.checkPictureText(cityActivity.getPictureTextVO());
		if (!checkPictureText.isSuccess()) {
			return checkPictureText;
		}
		WebCheckResult checkNeedKnow = checkNeedKnow(cityActivity.getCityActivityVO().getNeedKnowVO());
		if (!checkNeedKnow.isSuccess()) {
			return checkNeedKnow;
		}
		if (CollectionUtils.isEmpty(cityActivity.getThemes())) {
			return WebCheckResult.error("活动主题不能为空");
		}
		if (cityActivity.getThemes().size() > 3) {
			return WebCheckResult.error("活动主题最多选3个");
		}
		if (cityActivity.getDest() == null || cityActivity.getDest().getId() <= 0) {
			return WebCheckResult.error("活动城市不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkNeedKnow(NeedKnowVO needKnow) {
		if (needKnow == null) {
			return WebCheckResult.error("预定须知不能为空");
		}
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
		if (!StringUtils.isBlank(itemInfo.getCode()) && itemInfo.getCode().length() > 20) {
			return WebCheckResult.error("商品代码不能超过20个字");
		}
		if (StringUtils.isBlank(itemInfo.getDescription())) {
			return WebCheckResult.error("活动亮点不能为空");
		}
		if (StringUtils.isBlank(itemInfo.getEndDateStr())) {
			return WebCheckResult.error("截止报名日期不能为空");
		}
		if (CollectionUtils.isEmpty(itemInfo.getItemMainPics())) {
			return WebCheckResult.error("商品图不能为空");
		}
		if (itemInfo.getLatitudeVO() == null || itemInfo.getLongitudeVO() == null) {
			return WebCheckResult.error("经纬度不能为空");
		}
		List<ItemSkuVO> skuVOs = itemInfo.getItemSkuVOListByStr();
		if (CollectionUtils.isEmpty(skuVOs)) {
			return WebCheckResult.error("价格信息不能为空");
		}
		boolean hasChecked = false;
		for( ItemSkuVO skuVO : skuVOs ){
			boolean checked = skuVO.isChecked() ;
			if( checked ){
				hasChecked = true;
				break;
			}
		}
		if( !hasChecked ){
			return WebCheckResult.error("活动的套餐不能为空");
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

}

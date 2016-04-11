package com.yimayhd.sellerAdmin.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.model.line.price.PackageBlock;
import com.yimayhd.sellerAdmin.model.line.price.PackageDay;
import com.yimayhd.sellerAdmin.model.line.price.PackageInfo;
import com.yimayhd.sellerAdmin.model.line.price.PackageMonth;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteDayVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RoutePlanVo;
import com.yimayhd.sellerAdmin.model.line.route.RouteTrafficVO;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class LineChecker {
	private static final List<Integer> supportItemTypes = new ArrayList<Integer>();
	private static final List<String> supportTrafficTypes = new ArrayList<String>();

	static {
		supportItemTypes.add(ItemType.FREE_LINE.getValue());
		supportItemTypes.add(ItemType.TOUR_LINE.getValue());

		supportTrafficTypes.add(RouteItemType.PLANE.name());
		supportTrafficTypes.add(RouteItemType.TRAIN.name());
		supportTrafficTypes.add(RouteItemType.BUS.name());
		supportTrafficTypes.add(RouteItemType.BOAT.name());
	}

	public static WebCheckResult checkLine(LineVO line) {
		WebCheckResult checkBaseInfo = checkBaseInfo(line.getBaseInfo());
		if (!checkBaseInfo.isSuccess()) {
			return checkBaseInfo;
		}
		int itemType = line.getBaseInfo().getType();
		if (itemType == ItemType.FREE_LINE.getValue()) {
			WebCheckResult checkRoutePlan = checkRoutePlan(line.getRoutePlan());
			if (!checkRoutePlan.isSuccess()) {
				return checkRoutePlan;
			}
		}
		WebCheckResult checkTripInfoForSave = checkRouteInfo(itemType, line.getRouteInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		WebCheckResult checkPictureText = checkPictureText(line.getPictureText());
		if (!checkPictureText.isSuccess()) {
			return checkPictureText;
		}
		WebCheckResult checkNeedKnow = checkNeedKnow(line.getNeedKnow());
		if (!checkNeedKnow.isSuccess()) {
			return checkNeedKnow;
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

	public static WebCheckResult checkRoutePlan(RoutePlanVo routePlan) {
		RouteTrafficVO go = routePlan.getGo();
		RouteTrafficVO back = routePlan.getBack();
		if (go == null && back == null && StringUtils.isBlank(routePlan.getScenicInfo())
				&& StringUtils.isBlank(routePlan.getHotelInfo())) {
			return WebCheckResult.error("机酒景信息不能为空");
		} else {
			if (go != null) {
				if (StringUtils.isBlank(go.getType())) {
					return WebCheckResult.error("去程交通方式不能为空");
				} else if (!supportTrafficTypes.contains(go.getType().toUpperCase())) {
					return WebCheckResult.error("未知去程交通方式");
				}
				if (StringUtils.isBlank(go.getDescription())) {
					return WebCheckResult.error("去程详细描述不能为空");
				} else if (go.getDescription().length() > 200) {
					return WebCheckResult.error("去程详细描述不超过200字");
				}
			}
			if (back != null) {
				if (StringUtils.isBlank(back.getType())) {
					return WebCheckResult.error("回程交通方式不能为空");
				} else if (!supportTrafficTypes.contains(back.getType().toUpperCase())) {
					return WebCheckResult.error("未知回程交通方式");
				}
				if (StringUtils.isBlank(back.getDescription())) {
					return WebCheckResult.error("回程详细描述不能为空");
				} else if (back.getDescription().length() > 200) {
					return WebCheckResult.error("回程详细描述不超过200字");
				}
			}
			if (StringUtils.isNotBlank(routePlan.getHotelInfo()) && routePlan.getHotelInfo().length() > 1000) {
				return WebCheckResult.error("酒店信息不超过1000字");
			}
			if (StringUtils.isNotBlank(routePlan.getScenicInfo()) && routePlan.getScenicInfo().length() > 1000) {
				return WebCheckResult.error("景点信息不超过1000字");
			}
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkBaseInfo(BaseInfoVO baseInfo) {
		int type = baseInfo.getType();
		if (!supportItemTypes.contains(type)) {
			return WebCheckResult.error("未知商品类型");
		}
		if (StringUtils.isBlank(baseInfo.getName())) {
			return WebCheckResult.error("商品标题不能为空");
		} else if (baseInfo.getName().length() > 38) {
			return WebCheckResult.error("商品标题不能超过38个字");
		}
		if (StringUtils.isNotBlank(baseInfo.getCode()) && baseInfo.getCode().length() > 20) {
			return WebCheckResult.error("商品代码不能超过20个字");
		}
		if (!baseInfo.isAllDeparts() && CollectionUtils.isEmpty(baseInfo.getDeparts())) {
			return WebCheckResult.error("出发地不能为空");
		} else if (!baseInfo.isAllDeparts() && baseInfo.getDeparts().size() > 15) {
			return WebCheckResult.error("出发地不能超过15个");
		}
		if (CollectionUtils.isEmpty(baseInfo.getDests())) {
			return WebCheckResult.error("目的地不能为空");
		} else if (baseInfo.getDests().size() > 15) {
			return WebCheckResult.error("出发地不能超过15个");
		}
		if (baseInfo.getDays() <= 0) {
			return WebCheckResult.error("行程天数不能小于0");
		} else if (baseInfo.getDays() > 10000) {
			return WebCheckResult.error("行程天数不能大于10000");
		}
		if (StringUtils.isBlank(baseInfo.getDescription())) {
			return WebCheckResult.error("线路亮点不能为空");
		} else if (baseInfo.getDescription().length() > 200) {
			return WebCheckResult.error("线路亮点不能超过200字");
		}
		if (CollectionUtils.isEmpty(baseInfo.getThemes())) {
			return WebCheckResult.error("主题不能为空");
		} else if (baseInfo.getThemes().size() > 3) {
			return WebCheckResult.error("主题不能超过3个");
		}
		if (CollectionUtils.isEmpty(baseInfo.getPicUrls())) {
			return WebCheckResult.error("商品图不能为空");
		} else if (baseInfo.getPicUrls().size() > 5) {
			return WebCheckResult.error("商品图不能超过五张");
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

	public static WebCheckResult checkPriceInfo(PriceInfoVO priceInfo) {
		List<PackageInfo> tcs = priceInfo.getTcs();
		if (CollectionUtils.isEmpty(tcs)) {
			return WebCheckResult.error("线路套餐不能为空");
		} else if (tcs.size() > 10) {
			return WebCheckResult.error("线路套餐不能超过10个");
		} else {
			Set<String> tcSet = new HashSet<String>();
			for (PackageInfo tc : tcs) {
				String name = tc.getName();
				if (!tcSet.contains(name)) {
					tcSet.add(name);
				} else {
					return WebCheckResult.error("线路套餐名称不能重复");
				}
			}
		}
		for (PackageInfo tc : tcs) {
			WebCheckResult packageCheckResult = checkPackageInfo(tc);
			if (!packageCheckResult.isSuccess()) {
				return packageCheckResult;
			}
		}
		if (priceInfo.getLimit() <= 0) {
			return WebCheckResult.error("提前报名天数不能小于0");
		} else if (priceInfo.getLimit() > 10000) {
			return WebCheckResult.error("提前报名天数不能大于10000");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkPackageInfo(PackageInfo tc) {
		List<PackageMonth> months = tc.getMonths();
		if (StringUtils.isBlank(tc.getName())) {
			return WebCheckResult.error("线路套餐名称不能为空");
		} else if (tc.getName().length() > 15) {
			return WebCheckResult.error("线路套餐名称不能超过15个字");
		}
		if (CollectionUtils.isEmpty(months)) {
			return WebCheckResult.error("套餐月份不能为空");
		}
		for (PackageMonth packageMonth : months) {
			WebCheckResult checkPackageMonth = checkPackageMonth(packageMonth);
			if (!checkPackageMonth.isSuccess()) {
				return checkPackageMonth;
			}
		}
		return PropertyChecker.checkProperty(tc.getPId(), tc.getPType(), tc.getPTxt());
	}

	public static WebCheckResult checkPackageMonth(PackageMonth month) {
		List<PackageDay> days = month.getDays();
		if (CollectionUtils.isEmpty(days)) {
			return WebCheckResult.error("套餐日期项不能为空");
		}
		for (PackageDay packageDay : days) {
			WebCheckResult checkPackageDay = checkPackageDay(packageDay);
			if (!checkPackageDay.isSuccess()) {
				return checkPackageDay;
			}
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkPackageDay(PackageDay day) {
		List<PackageBlock> blocks = day.getBlocks();
		if (CollectionUtils.isEmpty(blocks)) {
			return WebCheckResult.error("套餐sku不能为空");
		}
		for (PackageBlock packageBlock : blocks) {
			WebCheckResult checkPackageBlock = checkPackageBlock(packageBlock);
			if (!checkPackageBlock.isSuccess()) {
				return checkPackageBlock;
			}
		}
		return PropertyChecker.checkProperty(day.getPId(), day.getPType(), day.getPTxt());
	}

	public static WebCheckResult checkPackageBlock(PackageBlock block) {
		if (block.getPrice() < 0) {
			return WebCheckResult.error("无效套餐sku价格");
		}
		if (block.getStock() < 0) {
			return WebCheckResult.error("无效套餐sku库存");
		}
		if (block.getDiscount() < 0) {
			return WebCheckResult.error("无效套餐sku会员优惠");
		}
		if (StringUtils.isBlank(block.getName())) {
			return WebCheckResult.error("套餐sku名称不能为空");
		}
		return PropertyChecker.checkProperty(block.getPId(), block.getPType(), block.getPTxt());
	}

	public static WebCheckResult checkRouteInfo(int itemType, RouteInfoVO tripInfo) {
		List<RouteDayVO> routeDays = tripInfo.getRouteDays();
		if (CollectionUtils.isNotEmpty(routeDays)) {
			for (RouteDayVO tripDay : routeDays) {
				WebCheckResult checkTripDay = checkRouteDay(tripDay);
				if (!checkTripDay.isSuccess()) {
					return checkTripDay;
				}
			}
		} else {
			if (itemType == ItemType.TOUR_LINE.getValue()) {
				return WebCheckResult.error("行程信息不能为空");
			}
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkRouteDay(RouteDayVO tripDay) {
		if (StringUtils.isBlank(tripDay.getTitle())) {
			return WebCheckResult.error("行程标题不能为空");
		}
		if (StringUtils.isBlank(tripDay.getDescription())) {
			return WebCheckResult.error("行程描述不能为空");
		}
		if (CollectionUtils.isNotEmpty(tripDay.getPicUrls()) && tripDay.getPicUrls().size() > 5) {
			return WebCheckResult.error("行程图片不能超过5张");
		}
		return WebCheckResult.success();
	}
}

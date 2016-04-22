package com.yimayhd.sellerAdmin.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
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
	private static final List<Integer>	supportItemTypes	= new ArrayList<Integer>();
	private static final List<String>	supportTrafficTypes	= new ArrayList<String>();
	// private static final Pattern NAME_PATTERN =
	// Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]{1,38}$");
	private static final Pattern		CODE_PATTERN		= Pattern.compile("^[0-9]{1,20}$");
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
		WebCheckResult checkPictureText = PictureTextChecker.checkPictureText(line.getPictureText());
		if (!checkPictureText.isSuccess()) {
			return checkPictureText;
		}
		int itemType = line.getBaseInfo().getType();
		if (itemType == ItemType.FREE_LINE.getValue()) {
			WebCheckResult checkRoutePlan = checkRoutePlan(itemType, line.getRoutePlan());
			if (!checkRoutePlan.isSuccess()) {
				return checkRoutePlan;
			}
		}
		WebCheckResult checkTripInfoForSave = checkRouteInfo(itemType, line.getRouteInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		WebCheckResult checkPriceInfo = checkPriceInfo(line.getPriceInfo());
		if (!checkPriceInfo.isSuccess()) {
			return checkPriceInfo;
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
				String title = needKnowItem.getTitle();
				if (StringUtils.isBlank(title)) {
					return WebCheckResult.error("预定须知标题为空");
				} else {
					String content = needKnowItem.getContent();
					if (StringUtils.isBlank(content)) {
						return WebCheckResult.error(title + "内容不能为空");
					} else {
						if ("费用包含".endsWith(title)) {
							if (content.length() > 2000) {
								return WebCheckResult.error(title + "内容不能超过2000个字符");
							}
						} else if ("费用不含".endsWith(title)) {
							if (content.length() > 500) {
								return WebCheckResult.error(title + "内容不能超过500个字符");
							}
						} else if ("预订说明".endsWith(title)) {
							if (content.length() > 2000) {
								return WebCheckResult.error(title + "内容不能超过2000个字符");
							}
						} else if ("退改规定".endsWith(title)) {
							if (content.length() > 500) {
								return WebCheckResult.error(title + "内容不能超过500个字符");
							}
						} else {
							return WebCheckResult.error("错误的须知标题");
						}
					}
				}

			}
		} else {
			return WebCheckResult.error("预定须知不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkRoutePlan(int itemType, RoutePlanVo routePlan) {
		if (routePlan == null) {
			return WebCheckResult.error("机酒景信息不能为空");
		}
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
				} else if (back.getDescription().length() > 500) {
					return WebCheckResult.error("回程详细描述不超过500字");
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
		if (baseInfo == null) {
			return WebCheckResult.error("基础信息不能为空");
		}
		int type = baseInfo.getType();
		if (!supportItemTypes.contains(type)) {
			return WebCheckResult.error("未知商品类型");
		}
		String name = baseInfo.getName();
		if (StringUtils.isBlank(name)) {
			return WebCheckResult.error("商品名称不能为空");
		} else if (name.length() > 38) {
			return WebCheckResult.error("商品名称不能超过38个字符");
		}
		String code = baseInfo.getCode();
		if (StringUtils.isNotBlank(code) && !CODE_PATTERN.matcher(code).matches()) {
			return WebCheckResult.error("请输入正确的商品代码，1-20位数字");
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
		} else if (baseInfo.getDays() > 100) {
			return WebCheckResult.error("行程天数不能大于100");
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

	public static WebCheckResult checkPriceInfo(PriceInfoVO priceInfo) {
		if (priceInfo == null) {
			return WebCheckResult.error("价格信息不能为空");
		}
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
		if (tc == null) {
			return WebCheckResult.error("套餐信息不能为空");
		}
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
		if (month == null) {
			return WebCheckResult.error("月份信息不能为空");
		}
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
		if (day == null) {
			return WebCheckResult.error("日期信息不能为空");
		}
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
		if (block == null) {
			return WebCheckResult.error("价格单元不能为空");
		}
		if (block.getPrice() < 0) {
			return WebCheckResult.error("无效套餐sku价格");
		}
		if (block.getStock() < 0) {
			return WebCheckResult.error("无效套餐sku库存");
		} else if (block.getStock() > 10000) {
			return WebCheckResult.error("库存不能大于10000");
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
		if (tripInfo == null) {
			return WebCheckResult.error("行程信息不能为空");
		}
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
		if (tripDay == null) {
			return WebCheckResult.error("行程每日信息不能为空");
		}
		if (StringUtils.isBlank(tripDay.getTitle())) {
			return WebCheckResult.error("行程标题不能为空");
		} else if (tripDay.getTitle().length() > 38) {
			return WebCheckResult.error("行程标题不能超过38个字");
		}
		if (StringUtils.isBlank(tripDay.getDescription())) {
			return WebCheckResult.error("行程描述不能为空");
		} else if (tripDay.getDescription().length() > 500) {
			return WebCheckResult.error("行程描述不能超过500个字");
		}
		if (CollectionUtils.isNotEmpty(tripDay.getPicUrls()) && tripDay.getPicUrls().size() > 5) {
			return WebCheckResult.error("行程图片不能超过5张");
		}
		return WebCheckResult.success();
	}

	public static void main(String[] args) {
		Pattern p = Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]+$");
		Matcher matcher = p.matcher("ssss");
		System.out.println(matcher.matches());
	}
}

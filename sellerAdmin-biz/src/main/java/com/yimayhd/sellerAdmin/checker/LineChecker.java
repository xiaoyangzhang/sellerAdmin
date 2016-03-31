package com.yimayhd.sellerAdmin.checker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.price.PackageBlock;
import com.yimayhd.sellerAdmin.model.line.price.PackageDay;
import com.yimayhd.sellerAdmin.model.line.price.PackageInfo;
import com.yimayhd.sellerAdmin.model.line.price.PackageMonth;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteDayVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class LineChecker {
	private static final Logger log = LoggerFactory.getLogger(LineChecker.class);

	public static WebCheckResult checkForSave(LineVO travel) {
		WebCheckResult checkBaseInfoForSave = checkBaseInfoForSave(travel.getBaseInfo());
		if (!checkBaseInfoForSave.isSuccess()) {
			return checkBaseInfoForSave;
		}
		WebCheckResult checkPriceInfoForSave = checkPriceInfoForSave(travel.getPriceInfo());
		if (!checkPriceInfoForSave.isSuccess()) {
			return checkPriceInfoForSave;
		}
		WebCheckResult checkTripInfoForSave = checkTripInfo(travel.getRouteInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkForUpdate(LineVO travel) {
		WebCheckResult checkBaseInfoForUpdate = checkBaseInfoForUpdate(travel.getBaseInfo());
		if (!checkBaseInfoForUpdate.isSuccess()) {
			return checkBaseInfoForUpdate;
		}
		WebCheckResult checkPriceInfoForUpdate = checkPriceInfoForUpdate(travel.getPriceInfo());
		if (!checkPriceInfoForUpdate.isSuccess()) {
			return checkPriceInfoForUpdate;
		}
		WebCheckResult checkTripInfoForSave = checkTripInfo(travel.getRouteInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkBaseInfoForSave(BaseInfoVO baseInfo) {
		String temp = "线路基本信息验证失败: {}";
		int type = baseInfo.getType();
		if (LineType.getByType(type) == null) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return WebCheckResult.error("未知线路类型");
		}
		if (StringUtils.isBlank(baseInfo.getName())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return WebCheckResult.error("线路名称不能为空");
		} else if (baseInfo.getName().length() > 50) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return WebCheckResult.error("线路名称不能超过50个字");
		}
		if (CollectionUtils.isEmpty(baseInfo.getThemes())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return WebCheckResult.error("主题不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkBaseInfoForUpdate(BaseInfoVO baseInfo) {
		String temp = "线路基本信息验证失败: {}";
		if (baseInfo.getLineId() <= 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return WebCheckResult.error("无效线路ID");
		}
		return checkBaseInfoForSave(baseInfo);
	}

	public static WebCheckResult checkPriceInfoForSave(PriceInfoVO priceInfo) {
		String temp = "线路价格信息验证失败: {}";
		List<PackageInfo> tcs = priceInfo.getTcs();
		if (CollectionUtils.isEmpty(tcs)) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return WebCheckResult.error("线路套餐不能为空");
		} else if (tcs.size() > 10) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return WebCheckResult.error("线路套餐不能超过10个");
		} else {
			Set<String> tcSet = new HashSet<String>();
			for (PackageInfo tc : tcs) {
				String name = tc.getName();
				if (!tcSet.contains(name)) {
					tcSet.add(name);
				} else {
					log.warn(temp, JSON.toJSONString(priceInfo));
					return WebCheckResult.error("线路套餐名称不能重复");
				}
			}
		}
		for (PackageInfo tc : tcs) {
			WebCheckResult packageCheckResult = checkPackageInfoForSave(tc);
			if (!packageCheckResult.isSuccess()) {
				return packageCheckResult;
			}
		}
		if (priceInfo.getLimit() <= 0) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return WebCheckResult.error("无效提前报名天数");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkPackageInfoForSave(PackageInfo tc) {
		String temp = "线路套餐验证失败: {}";
		List<PackageMonth> months = tc.getMonths();
		if (StringUtils.isBlank(tc.getName())) {
			log.warn(temp, JSON.toJSONString(tc));
			return WebCheckResult.error("线路套餐名称不能为空");
		} else if (tc.getName().length() > 15) {
			log.warn(temp, JSON.toJSONString(tc));
			return WebCheckResult.error("线路套餐名称不能超过15个字");
		}
		if (CollectionUtils.isEmpty(months)) {
			log.warn(temp, JSON.toJSONString(tc));
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
		String temp = "套餐月份验证失败: {}";
		List<PackageDay> days = month.getDays();
		if (CollectionUtils.isEmpty(days)) {
			log.warn(temp, JSON.toJSONString(month));
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
		String temp = "套餐日期项验证失败: {}";
		List<PackageBlock> blocks = day.getBlocks();
		if (CollectionUtils.isEmpty(blocks)) {
			log.warn(temp, JSON.toJSONString(day));
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
		String temp = "套餐sku验证失败: {}";
		if (block.getPrice() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return WebCheckResult.error("无效套餐sku价格");
		}
		if (block.getStock() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return WebCheckResult.error("无效套餐sku库存");
		}
		if (block.getDiscount() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return WebCheckResult.error("无效套餐sku会员优惠");
		}
		if (StringUtils.isBlank(block.getName())) {
			log.warn(temp, JSON.toJSONString(block));
			return WebCheckResult.error("套餐sku名称不能为空");
		}
		return PropertyChecker.checkProperty(block.getPId(), block.getPType(), block.getPTxt());
	}

	public static WebCheckResult checkPriceInfoForUpdate(PriceInfoVO priceInfo) {
		return checkPriceInfoForSave(priceInfo);
	}

	public static WebCheckResult checkTripInfo(RouteInfoVO tripInfo) {
		String temp = "行程信息验证失败: {}";
		List<RouteDayVO> routeDays = tripInfo.getRouteDays();
		if (CollectionUtils.isEmpty(routeDays)) {
			log.warn(temp, JSON.toJSONString(tripInfo));
			return WebCheckResult.error("行程信息不能为空");
		}
		for (RouteDayVO tripDay : routeDays) {
			WebCheckResult checkTripDay = checkTripDay(tripDay);
			if (!checkTripDay.isSuccess()) {
				return checkTripDay;
			}
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkTripDay(RouteDayVO tripDay) {
		// TODO YEBIN
		return WebCheckResult.success();
	}
}

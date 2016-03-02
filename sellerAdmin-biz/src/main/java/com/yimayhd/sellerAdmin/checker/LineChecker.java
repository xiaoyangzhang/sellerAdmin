package com.yimayhd.sellerAdmin.checker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.HotelShortItem;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.checker.result.CheckResult;
import com.yimayhd.sellerAdmin.model.travel.BaseInfo;
import com.yimayhd.sellerAdmin.model.travel.BaseTravel;
import com.yimayhd.sellerAdmin.model.travel.PackageBlock;
import com.yimayhd.sellerAdmin.model.travel.PackageDay;
import com.yimayhd.sellerAdmin.model.travel.PackageInfo;
import com.yimayhd.sellerAdmin.model.travel.PackageMonth;
import com.yimayhd.sellerAdmin.model.travel.PriceInfo;
import com.yimayhd.sellerAdmin.model.travel.flightHotelTravel.FlightHotelTravel;
import com.yimayhd.sellerAdmin.model.travel.flightHotelTravel.TripPackageInfo;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.GroupTravel;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.TripDay;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class LineChecker {
	private static final Logger log = LoggerFactory.getLogger(LineChecker.class);

	public static <T extends BaseTravel> CheckResult checkForSave(T travel) {
		CheckResult checkBaseInfoForSave = checkBaseInfoForSave(travel.getBaseInfo());
		if (!checkBaseInfoForSave.isSuccess()) {
			return checkBaseInfoForSave;
		}
		CheckResult checkPriceInfoForSave = checkPriceInfoForSave(travel.getPriceInfo());
		if (!checkPriceInfoForSave.isSuccess()) {
			return checkPriceInfoForSave;
		}
		if (travel instanceof GroupTravel) {
			CheckResult checkGroupTravelForSave = checkGroupTravelForSave((GroupTravel) travel);
			if (!checkGroupTravelForSave.isSuccess()) {
				return checkGroupTravelForSave;
			}
		} else if (travel instanceof FlightHotelTravel) {
			CheckResult checkFlightHotelTravel = checkFlightHotelTravel((FlightHotelTravel) travel);
			if (!checkFlightHotelTravel.isSuccess()) {
				return checkFlightHotelTravel;
			}
		}
		return CheckResult.success();
	}

	public static <T extends BaseTravel> CheckResult checkForUpdate(T travel) {
		CheckResult checkBaseInfoForUpdate = checkBaseInfoForUpdate(travel.getBaseInfo());
		if (!checkBaseInfoForUpdate.isSuccess()) {
			return checkBaseInfoForUpdate;
		}
		CheckResult checkPriceInfoForUpdate = checkPriceInfoForUpdate(travel.getPriceInfo());
		if (!checkPriceInfoForUpdate.isSuccess()) {
			return checkPriceInfoForUpdate;
		}
		if (travel instanceof GroupTravel) {
			CheckResult checkGroupTravelForUpdate = checkGroupTravelForUpdate((GroupTravel) travel);
			if (!checkGroupTravelForUpdate.isSuccess()) {
				return checkGroupTravelForUpdate;
			}
		} else if (travel instanceof FlightHotelTravel) {
			CheckResult checkFlightHotelTravel = checkFlightHotelTravel((FlightHotelTravel) travel);
			if (!checkFlightHotelTravel.isSuccess()) {
				return checkFlightHotelTravel;
			}
		}
		return CheckResult.success();
	}

	public static CheckResult checkGroupTravelForSave(GroupTravel gt) {
		CheckResult checkTripInfoForSave = checkTripInfo(gt.getTripInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		return CheckResult.success();
	}

	public static CheckResult checkGroupTravelForUpdate(GroupTravel gt) {
		String temp = "行程信息验证失败: {}";
		if (gt.getRouteId() <= 0) {
			log.warn(temp, JSON.toJSONString(gt));
			return CheckResult.error("无效行程ID");
		}
		return checkGroupTravelForSave(gt);
	}

	public static CheckResult checkFlightHotelTravel(FlightHotelTravel fht) {
		return checkTripPackageInfo(fht.getTripPackageInfo());
	}

	public static CheckResult checkTripPackageInfo(TripPackageInfo tripPackageInfo) {
		String temp = "机酒套餐信息验证失败: {}";
		List<HotelShortItem> hotels = tripPackageInfo.getHotels();
		if (CollectionUtils.isEmpty(hotels)) {
			log.warn(temp, JSON.toJSONString(tripPackageInfo));
			return CheckResult.error("机酒套餐中酒店信息不能为空");
		}
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForSave(BaseInfo baseInfo) {
		String temp = "线路基本信息验证失败: {}";
		int type = baseInfo.getType();
		if (LineType.getByType(type) == null) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("未知线路类型");
		}
		if (StringUtils.isBlank(baseInfo.getName())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("线路名称不能为空");
		} else if (baseInfo.getName().length() > 50) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("线路名称不能超过50个字");
		}
		if (StringUtils.isBlank(baseInfo.getProductImageApp())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("APP产品封面图不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getProductImagePc())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("PC产品封面图不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getTripImage())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("行程封面图不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getOrderImage())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("商品订单图不能为空");
		}
		if (CollectionUtils.isEmpty(baseInfo.getTags())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("产品标签不能为空");
		}
		if (baseInfo.getPublisherId() <= 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效发布者");
		}
		if (baseInfo.getToId() <= 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效目的地");
		}
		if (StringUtils.isBlank(baseInfo.getToName())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("目的地名称不能为空");
		}
		if (baseInfo.getMemberPrice() < 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效会员价");
		}
		if (baseInfo.getPrice() < 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效会员价");
		}
		String highlights = baseInfo.getHighlights();
		if (StringUtils.isBlank(highlights)) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("线路设计亮点不能为空");
		} else if (highlights.length() > 500) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("线路设计亮点不能超过500个字");
		}
		MasterRecommend recommond = baseInfo.getRecommond();
		if (StringUtils.isBlank(recommond.getTitle())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("推荐理由标题不能为空");
		}
		if (StringUtils.isBlank(recommond.getDescription())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("推荐理由内容不能为空");
		} else if (recommond.getDescription().length() > 300) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("推荐理由不能超过300个字");
		}
		if (recommond.getUserId() <= 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效线路推荐人");
		}
		if (StringUtils.isBlank(recommond.getName())) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("线路推荐人名称不能为空");
		}
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForUpdate(BaseInfo baseInfo) {
		String temp = "线路基本信息验证失败: {}";
		if (baseInfo.getId() <= 0) {
			log.warn(temp, JSON.toJSONString(baseInfo));
			return CheckResult.error("无效线路ID");
		}
		return checkBaseInfoForSave(baseInfo);
	}

	public static CheckResult checkPriceInfoForSave(PriceInfo priceInfo) {
		String temp = "线路价格信息验证失败: {}";
		List<PackageInfo> tcs = priceInfo.getTcs();
		if (CollectionUtils.isEmpty(tcs)) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return CheckResult.error("线路套餐不能为空");
		} else if (tcs.size() > 10) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return CheckResult.error("线路套餐不能超过10个");
		} else {
			Set<String> tcSet = new HashSet<String>();
			for (PackageInfo tc : tcs) {
				String name = tc.getName();
				if (!tcSet.contains(name)) {
					tcSet.add(name);
				} else {
					log.warn(temp, JSON.toJSONString(priceInfo));
					return CheckResult.error("线路套餐名称不能重复");
				}
			}
		}
		for (PackageInfo tc : tcs) {
			CheckResult packageCheckResult = checkPackageInfoForSave(tc);
			if (!packageCheckResult.isSuccess()) {
				return packageCheckResult;
			}
		}
		if (priceInfo.getLimit() <= 0) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return CheckResult.error("无效提前报名天数");
		}
		return CheckResult.success();
	}

	public static CheckResult checkPackageInfoForSave(PackageInfo tc) {
		String temp = "线路套餐验证失败: {}";
		List<PackageMonth> months = tc.getMonths();
		if (StringUtils.isBlank(tc.getName())) {
			log.warn(temp, JSON.toJSONString(tc));
			return CheckResult.error("线路套餐名称不能为空");
		} else if (tc.getName().length() > 15) {
			log.warn(temp, JSON.toJSONString(tc));
			return CheckResult.error("线路套餐名称不能超过15个字");
		}
		if (CollectionUtils.isEmpty(months)) {
			log.warn(temp, JSON.toJSONString(tc));
			return CheckResult.error("套餐月份不能为空");
		}
		for (PackageMonth packageMonth : months) {
			CheckResult checkPackageMonth = checkPackageMonth(packageMonth);
			if (!checkPackageMonth.isSuccess()) {
				return checkPackageMonth;
			}
		}
		return PropertyChecker.checkProperty(tc.getPId(), tc.getPType(), tc.getPTxt());
	}

	public static CheckResult checkPackageMonth(PackageMonth month) {
		String temp = "套餐月份验证失败: {}";
		List<PackageDay> days = month.getDays();
		if (CollectionUtils.isEmpty(days)) {
			log.warn(temp, JSON.toJSONString(month));
			return CheckResult.error("套餐日期项不能为空");
		}
		for (PackageDay packageDay : days) {
			CheckResult checkPackageDay = checkPackageDay(packageDay);
			if (!checkPackageDay.isSuccess()) {
				return checkPackageDay;
			}
		}
		return CheckResult.success();
	}

	public static CheckResult checkPackageDay(PackageDay day) {
		String temp = "套餐日期项验证失败: {}";
		List<PackageBlock> blocks = day.getBlocks();
		if (CollectionUtils.isEmpty(blocks)) {
			log.warn(temp, JSON.toJSONString(day));
			return CheckResult.error("套餐sku不能为空");
		}
		for (PackageBlock packageBlock : blocks) {
			CheckResult checkPackageBlock = checkPackageBlock(packageBlock);
			if (!checkPackageBlock.isSuccess()) {
				return checkPackageBlock;
			}
		}
		return PropertyChecker.checkProperty(day.getPId(), day.getPType(), day.getPTxt());
	}

	public static CheckResult checkPackageBlock(PackageBlock block) {
		String temp = "套餐sku验证失败: {}";
		if (block.getPrice() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return CheckResult.error("无效套餐sku价格");
		}
		if (block.getStock() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return CheckResult.error("无效套餐sku库存");
		}
		if (block.getDiscount() < 0) {
			log.warn(temp, JSON.toJSONString(block));
			return CheckResult.error("无效套餐sku会员优惠");
		}
		if (StringUtils.isBlank(block.getName())) {
			log.warn(temp, JSON.toJSONString(block));
			return CheckResult.error("套餐sku名称不能为空");
		}
		return PropertyChecker.checkProperty(block.getPId(), block.getPType(), block.getPTxt());
	}

	public static CheckResult checkPriceInfoForUpdate(PriceInfo priceInfo) {
		String temp = "线路价格信息验证失败: {}";
		if (priceInfo.getItemId() <= 0) {
			log.warn(temp, JSON.toJSONString(priceInfo));
			return CheckResult.error("无效商品ID");
		}
		return checkPriceInfoForSave(priceInfo);
	}

	public static CheckResult checkTripInfo(List<TripDay> tripInfo) {
		String temp = "行程信息验证失败: {}";
		if (CollectionUtils.isEmpty(tripInfo)) {
			log.warn(temp, JSON.toJSONString(tripInfo));
			return CheckResult.error("行程信息不能为空");
		}
		for (TripDay tripDay : tripInfo) {
			CheckResult checkTripDay = checkTripDay(tripDay);
			if (!checkTripDay.isSuccess()) {
				return checkTripDay;
			}
		}
		return CheckResult.success();
	}

	public static CheckResult checkTripDay(TripDay tripDay) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripPackageInfoForSave(TripPackageInfo tripPackageInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripPackageInfoForUpdate(TripPackageInfo tripPackageInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}
}

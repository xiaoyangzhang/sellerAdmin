package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.line.BaseLineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.detail.DetailInfoVO;
import com.yimayhd.sellerAdmin.model.line.free.FreeLineVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.price.PackageBlock;
import com.yimayhd.sellerAdmin.model.line.price.PackageDay;
import com.yimayhd.sellerAdmin.model.line.price.PackageInfo;
import com.yimayhd.sellerAdmin.model.line.price.PackageMonth;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteDayVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;
import com.yimayhd.sellerAdmin.model.line.tour.TourLineVO;

/**
 * 线路转换器
 * 
 * @author yebin
 *
 */
public class LineConverter {
	public static LineDO toLineDO(BaseInfoVO baseInfo) {
		// 初始化
		LineDO line = new LineDO();
		line.setId(baseInfo.getLineId());
		return merge(baseInfo, line);
	}

	public static LineDO merge(BaseInfoVO baseInfo, LineDO target) {
		target.setType(baseInfo.getType());
		target.setName(baseInfo.getName());
		// image
		target.setLogoUrl(baseInfo.getProductImageApp());
		target.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, baseInfo.getProductImageApp());
		target.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, baseInfo.getOrderImage());
		target.addPicUrls(ItemPicUrlsKey.LINE_DETAIL_PICS, PicUrlsUtil.parsePicsString(baseInfo.getDetailAppImages()));
		return target;
	}

	public static BaseInfoVO toBaseInfoVO(LineDO line, List<ComTagDO> tags) {
		BaseInfoVO baseInfo = new BaseInfoVO();
		baseInfo.setLineId(line.getId());
		baseInfo.setType(line.getType());
		baseInfo.setName(line.getName());
		String productImageApp = line.getPicUrls(ItemPicUrlsKey.BIG_LIST_PIC);
		if (StringUtils.isNotBlank(productImageApp)) {
			baseInfo.setProductImageApp(productImageApp);
		} else {
			baseInfo.setProductImageApp(line.getLogoUrl());
		}
		String orderImage = line.getPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC);
		if (StringUtils.isNotBlank(orderImage)) {
			baseInfo.setOrderImage(orderImage);
		}
		String detailAppImages = line.getPicUrls(ItemPicUrlsKey.LINE_DETAIL_PICS);
		if (StringUtils.isNotBlank(detailAppImages)) {
			baseInfo.setDetailAppImages(PicUrlsUtil.parsePicList(detailAppImages));
		}
		List<Long> themes = new ArrayList<Long>();
		if (tags != null) {
			for (ComTagDO tag : tags) {
				themes.add(tag.getId());
			}
		}
		baseInfo.setThemes(themes);
		return baseInfo;
	}

	public static PriceInfoVO toPriceInfoVO(ItemDO itemDO, List<ItemSkuDO> itemSkuList) {
		PriceInfoVO priceInfo = new PriceInfoVO();
		priceInfo.setItemId(itemDO.getId());
		if (itemDO != null && StringUtils.isNotBlank(itemDO.getFeature())) {
			ItemFeature feature = new ItemFeature(itemDO.getFeature());
			priceInfo.setLimit(feature.getStartBookTimeLimit() / PriceInfoVO.LIMIT_UNIT);
		}
		List<PackageInfo> tcs = new ArrayList<PackageInfo>();
		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			Map<String, ItemSkuPVPair> piMap = new LinkedHashMap<String, ItemSkuPVPair>();
			Map<Long, ItemSkuPVPair> pdMap = new LinkedHashMap<Long, ItemSkuPVPair>();
			Map<Long, ItemSkuPVPair> pbMap = new LinkedHashMap<Long, ItemSkuPVPair>();
			Map<String, Map<Long, Map<Long, ItemSkuDO>>> treeMap = new LinkedHashMap<String, Map<Long, Map<Long, ItemSkuDO>>>();
			for (ItemSkuDO sku : itemSkuList) {
				ItemSkuPVPair tcPair = null;
				ItemSkuPVPair dayPair = null;
				ItemSkuPVPair personPair = null;
				if (StringUtils.isNotBlank(sku.getProperty())) {
					List<ItemSkuPVPair> pairs = sku.getItemSkuPVPairList();
					for (ItemSkuPVPair itemSkuPVPair : pairs) {
						if (itemSkuPVPair.getPId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
							tcPair = itemSkuPVPair;
							if (!piMap.containsKey(tcPair.getVTxt())) {
								piMap.put(tcPair.getVTxt(), tcPair);
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.DEPART_DATE.getType()) {
							dayPair = itemSkuPVPair;
							if (!pdMap.containsKey(dayPair.getVTxt())) {
								long time = Long.parseLong(dayPair.getVTxt());
								pdMap.put(time, dayPair);
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.PERSON.getType()) {
							personPair = itemSkuPVPair;
							if (!pbMap.containsKey(personPair.getVId())) {
								pbMap.put(personPair.getVId(), personPair);
							}
						}
					}
					Map<Long, Map<Long, ItemSkuDO>> tc = null;
					if (treeMap.containsKey(tcPair.getVTxt())) {
						tc = treeMap.get(tcPair.getVTxt());
					} else {
						tc = new LinkedHashMap<Long, Map<Long, ItemSkuDO>>();
						treeMap.put(tcPair.getVTxt(), tc);
					}
					Map<Long, ItemSkuDO> day = null;
					long time = Long.parseLong(dayPair.getVTxt());
					if (tc.containsKey(time)) {
						day = tc.get(time);
					} else {
						day = new LinkedHashMap<Long, ItemSkuDO>();
						tc.put(time, day);
					}
					day.put(personPair.getVId(), sku);
				}
			}
			// 完成数据组装
			for (Entry<String, Map<Long, Map<Long, ItemSkuDO>>> pi : treeMap.entrySet()) {
				ItemSkuPVPair tcPair = piMap.get(pi.getKey());
				List<PackageDay> packageDays = new ArrayList<PackageDay>();
				for (Entry<Long, Map<Long, ItemSkuDO>> pd : pi.getValue().entrySet()) {
					ItemSkuPVPair dayPair = pdMap.get(pd.getKey());
					List<PackageBlock> packageBlocks = new ArrayList<PackageBlock>();
					for (Entry<Long, ItemSkuDO> pb : pd.getValue().entrySet()) {
						ItemSkuPVPair personPair = pbMap.get(pb.getKey());
						ItemSkuDO sku = pb.getValue();
						// TODO discount暂不实现
						packageBlocks
								.add(new PackageBlock(sku.getId(), personPair, sku.getPrice(), sku.getStockNum(), 0));
					}
					packageDays.add(new PackageDay(dayPair, pd.getKey(), packageBlocks));
				}
				tcs.add(new PackageInfo(tcPair, packageDays));
			}
		}
		priceInfo.setTcs(tcs);
		return priceInfo;
	}

	public static List<ItemSkuDO> toItemSkuDOList(long categoryId, long sellerId, PriceInfoVO priceInfo) {
		List<ItemSkuDO> itemSkuDOs = new ArrayList<ItemSkuDO>();
		List<PackageInfo> tcs = priceInfo.getTcs();
		if (CollectionUtils.isNotEmpty(tcs)) {
			for (PackageInfo packageInfo : tcs) {
				ItemSkuPVPair itemSkuPVPair1 = packageInfo.toItemSkuPVPair();
				if (CollectionUtils.isNotEmpty(packageInfo.getMonths())) {
					for (PackageMonth packageMonth : packageInfo.getMonths()) {
						if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
							for (PackageDay packageDay : packageMonth.getDays()) {
								ItemSkuPVPair itemSkuPVPair2 = packageDay.toItemSkuPVPair();
								if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
									for (PackageBlock packageBlock : packageDay.getBlocks()) {
										ItemSkuPVPair itemSkuPVPair3 = packageBlock.toItemSkuPVPair();
										List<ItemSkuPVPair> itemSkuPVPairs = new ArrayList<ItemSkuPVPair>();
										itemSkuPVPairs.add(itemSkuPVPair1);
										itemSkuPVPairs.add(itemSkuPVPair2);
										itemSkuPVPairs.add(itemSkuPVPair3);
										ItemSkuDO itemSkuDO = new ItemSkuDO();
										itemSkuDO.setId(packageBlock.getSkuId());
										itemSkuDO.setTitle(itemSkuPVPair1.getVTxt() + "," + itemSkuPVPair3.getVTxt());
										itemSkuDO.setCategoryId(categoryId);
										itemSkuDO.setSellerId(sellerId);
										itemSkuDO.setItemSkuPVPairList(itemSkuPVPairs);
										itemSkuDO.setPrice(packageBlock.getPrice());
										itemSkuDO.setStockNum(packageBlock.getStock());
										itemSkuDOs.add(itemSkuDO);
									}
								}
							}
						}
					}
				}
			}
		}
		return itemSkuDOs;
	}

	public static DetailInfoVO toDetailInfoVO(LineResult lineResult) {
		// TODO YEBIN 待开发
		return null;
	}

	public static NeedKnowVO toNeedKnowVO(LineResult lineResult) {
		// TODO YEBIN 待开发
		return null;
	}

	public static TourLineVO toTourLineVO(LineResult lineResult, List<ComTagDO> tags) {
		TourLineVO result = new TourLineVO();
		LineDO line = lineResult.getLineDO();
		BaseInfoVO baseInfo = toBaseInfoVO(line, tags);
		result.setBaseInfo(baseInfo);
		DetailInfoVO detailInfo = toDetailInfoVO(lineResult);
		result.setDetailInfo(detailInfo);
		// 线路个性化部分 start
		RouteInfoVO routeInfo = toRouteInfoVO(lineResult);
		result.setRouteInfo(routeInfo);
		// 线路个性化部分 end
		ItemDO itemDO = lineResult.getItemDO();
		result.setCategoryId(itemDO.getCategoryId());
		result.setOptions(itemDO.getOptions());
		result.setReadonly(itemDO.getStatus() == ItemStatus.valid.getValue());
		PriceInfoVO priceInfo = toPriceInfoVO(itemDO, lineResult.getItemSkuDOList());
		result.setPriceInfo(priceInfo);
		String picUrl = itemDO.getPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC);
		if (StringUtils.isNotBlank(picUrl)) {
			baseInfo.setOrderImage(picUrl);
		}
		NeedKnowVO needKnow = toNeedKnowVO(lineResult);
		result.setNeedKnow(needKnow);
		return result;
	}

	public static RouteInfoVO toRouteInfoVO(LineResult lineResult) {
		RouteInfoVO routeInfo = new RouteInfoVO();
		RouteDO routeDO = lineResult.getRouteDO();
		routeInfo.setRouteId(routeDO.getId());
		List<RouteDayVO> tripDays = new ArrayList<RouteDayVO>();
		Set<Integer> days = new HashSet<Integer>();
		Map<Integer, RouteItemDO> desMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> trafficMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> breakfastMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> lunchMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> dinnerMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> scenicMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> hotelMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> restaurantDetailMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> scenicDetailMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> hotelDetailMap = new HashMap<Integer, RouteItemDO>();
		List<RouteItemDO> routeItems = lineResult.getRouteItemDOList();
		if (CollectionUtils.isNotEmpty(routeItems)) {
			for (RouteItemDO routeItem : routeItems) {
				days.add(routeItem.getDay());
				if (routeItem.getType() == RouteItemBizType.DESCRIPTION.getType()) {
					desMap.put(routeItem.getDay(), routeItem);
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_TRAFFIC_INFO.getType()) {
					trafficMap.put(routeItem.getDay(), routeItem);
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DESC.getType()) {
					RouteItemDesc desc = routeItem.getRouteItemDesc();
					if (desc != null) {
						if (RouteItemType.BREAKFAST.name().equals(desc.getType())) {
							breakfastMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.LUNCH.name().equals(desc.getType())) {
							lunchMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.DINNER.name().equals(desc.getType())) {
							dinnerMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.SCENIC.name().equals(desc.getType())) {
							scenicMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.HOTEL.name().equals(desc.getType())) {
							hotelMap.put(routeItem.getDay(), routeItem);
						}
					}
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DETAIL.getType()) {
					RouteItemDetail detail = routeItem.getRouteItemDetail();
					if (detail != null) {
						if (RouteItemType.RESTAURANT.name().equals(detail.getType())) {
							restaurantDetailMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.SCENIC.name().equals(detail.getType())) {
							scenicDetailMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.HOTEL.name().equals(detail.getType())) {
							hotelDetailMap.put(routeItem.getDay(), routeItem);
						}
					}
				}
			}
		}
		List<Integer> dayList = new ArrayList<Integer>(days);
		Collections.sort(dayList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		for (Integer day : dayList) {
			tripDays.add(new RouteDayVO(trafficMap.get(day), desMap.get(day), breakfastMap.get(day), lunchMap.get(day),
					dinnerMap.get(day), scenicMap.get(day), hotelMap.get(day), restaurantDetailMap.get(day),
					scenicDetailMap.get(day), hotelDetailMap.get(day)));
		}
		routeInfo.setRouteDays(tripDays);
		return routeInfo;
	}

	public static RouteDO merge(RouteInfoVO routeInfo, RouteDO target) {
		target.setId(routeInfo.getRouteId());
		return target;
	}

	public static LinePublishDTO toLinePublishDTOForSave(BaseLineVO line) {
		LinePublishDTO dto = new LinePublishDTO();
		LineDO lineDO = toLineDO(line.getBaseInfo());
		dto.setLineDO(lineDO);
		RouteInfoVO routeInfo = line.getRouteInfo();
		RouteDO routeDO = new RouteDO();
		RouteDO routeDTO = merge(routeInfo, routeDO);
		dto.setRouteDO(routeDTO);
		List<RouteDayVO> routeDays = routeInfo.getRouteDays();
		List<RouteItemDO> routeItemDOList = toRouteItemDOs(routeDays);
		dto.setRouteItemDOList(routeItemDOList);
		long categoryId = line.getCategoryId();
		long options = line.getOptions();
		dto.setItemDO(toItemDO(categoryId, options, line));
		PriceInfoVO priceInfo = line.getPriceInfo();
		List<ItemSkuDO> itemSkuDOList = toItemSkuDOList(line.getCategoryId(), Constant.YIMAY_OFFICIAL_ID, priceInfo);
		dto.setItemSkuDOList(itemSkuDOList);
		return dto;
	}

	public static LinePublishDTO toLinePublishDTOForSave(TourLineVO line) {
		LinePublishDTO linePublishDTOForSave = toLinePublishDTOForSave(line);
		return linePublishDTOForSave;
	}

	public static LinePublishDTO toLinePublishDTOForSave(FreeLineVO line) {
		LinePublishDTO linePublishDTOForSave = toLinePublishDTOForSave(line);
		return linePublishDTOForSave;
	}

	public static List<RouteItemDO> toRouteItemDOs(List<RouteDayVO> routeDays) {
		List<RouteItemDO> routeItemDOList = new ArrayList<RouteItemDO>();
		for (int i = 1; i <= routeDays.size(); i++) {
			RouteDayVO tripDay = routeDays.get(i - 1);
			// 交通
			RouteItemDO routeItemTrafficInfo = tripDay.getRouteItemTrafficInfo(i);
			if (routeItemTrafficInfo != null) {
				routeItemDOList.add(routeItemTrafficInfo);
			}
			// 描述
			RouteItemDO routeItemDescription = tripDay.getRouteItemDescription(i);
			if (routeItemDescription != null) {
				routeItemDOList.add(routeItemDescription);
			}
			// 早餐
			RouteItemDO routeItemBreakfast = tripDay.getRouteItemBreakfast(i);
			if (routeItemBreakfast != null) {
				routeItemDOList.add(routeItemBreakfast);
			}
			// 午餐
			RouteItemDO routeItemLunch = tripDay.getRouteItemLunch(i);
			if (routeItemLunch != null) {
				routeItemDOList.add(routeItemLunch);
			}
			// 晚餐
			RouteItemDO routeItemDinner = tripDay.getRouteItemDinner(i);
			if (routeItemDinner != null) {
				routeItemDOList.add(routeItemDinner);
			}
			// 景区
			RouteItemDO routeItemScenic = tripDay.getRouteItemScenic(i);
			if (routeItemScenic != null) {
				routeItemDOList.add(routeItemScenic);
			}
			// 酒店
			RouteItemDO routeItemHotel = tripDay.getRouteItemHotel(i);
			if (routeItemHotel != null) {
				routeItemDOList.add(routeItemHotel);
			}
			// 餐厅详情
			RouteItemDO routeItemRestaurantDetail = tripDay.getRouteItemRestaurantDetail(i);
			if (routeItemRestaurantDetail != null) {
				routeItemDOList.add(routeItemRestaurantDetail);
			}
			// 景区详情
			RouteItemDO routeItemScenicDetail = tripDay.getRouteItemScenicDetail(i);
			if (routeItemScenicDetail != null) {
				routeItemDOList.add(routeItemScenicDetail);
			}
			// 酒店详情
			RouteItemDO routeItemHotelDetail = tripDay.getRouteItemHotelDetail(i);
			if (routeItemHotelDetail != null) {
				routeItemDOList.add(routeItemHotelDetail);
			}
		}
		return routeItemDOList;
	}

	public static ItemDO toItemDO(long categoryId, long options, BaseLineVO line) {
		PriceInfoVO priceInfo = line.getPriceInfo();
		// 初始化
		ItemDO itemDO = new ItemDO();
		itemDO.setId(priceInfo.getItemId());
		itemDO.setDomain(Constant.B2C_DOMAIN);
		itemDO.setCategoryId(categoryId);
		itemDO.setOptions(options);
		itemDO.setPayType(1);
		itemDO.setSource(1);
		itemDO.setVersion(1);
		itemDO.setStockNum(0);
		itemDO.setSubTitle("");
		itemDO.setOneWord("");
		itemDO.setDescription("");
		itemDO.setDetailUrl("");
		itemDO.setItemFeature(new ItemFeature(null));
		return merge(line, itemDO);
	}

	public static ItemDO merge(BaseLineVO line, ItemDO target) {
		BaseInfoVO baseInfo = line.getBaseInfo();
		PriceInfoVO priceInfo = line.getPriceInfo();
		target.setSellerId(Constant.YIMAY_OFFICIAL_ID);
		ItemFeature itemFeature = target.getItemFeature();
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, priceInfo.getLimitBySecond());
		itemFeature.put(ItemFeatureKey.LINE_ADULT_VID, 1);
		itemFeature.put(ItemFeatureKey.LINE_SINGLE_ROOM_VID, 4);
		target.setItemFeature(itemFeature);
		target.setItemType(line.getItemType());
		target.setTitle(baseInfo.getName());
		if (StringUtils.isNotBlank(baseInfo.getProductImageApp())) {
			target.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, baseInfo.getProductImageApp());
		}
		if (StringUtils.isNotBlank(baseInfo.getOrderImage())) {
			target.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, baseInfo.getOrderImage());
		}
		return target;
	}

	public static LinePublishDTO mergeRoute(RouteInfoVO routeInfo, RouteDO routeDO, List<RouteItemDO> routeItemDOs,
			LinePublishDTO target) {
		// RouteDO
		RouteDO routeDTO = merge(routeInfo, routeDO);
		target.setRouteDO(routeDTO);
		// RouteItemDO List
		// SKU分离
		List<RouteItemDO> routeItemVOs = toRouteItemDOs(routeInfo.getRouteDays());
		Map<Long, RouteItemDO> routeItemVOMap = new HashMap<Long, RouteItemDO>();
		for (RouteItemDO routeItemVO : routeItemVOs) {
			if (routeItemVO.getId() > 0) {
				routeItemVOMap.put(routeItemVO.getId(), routeItemVO);
			}
		}
		Map<Long, RouteItemDO> routeItemDOMap = new HashMap<Long, RouteItemDO>();
		for (RouteItemDO routeItemDO : routeItemDOs) {
			routeItemDOMap.put(routeItemDO.getId(), routeItemDO);
		}
		List<RouteItemDO> addRouteItemList = new ArrayList<RouteItemDO>();
		List<RouteItemDO> updateRouteItemList = new ArrayList<RouteItemDO>();
		List<Long> deleteRouteItemList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(routeItemVOs)) {
			// 新增
			for (RouteItemDO routeItemDO : routeItemVOs) {
				if (routeItemDO.getId() <= 0) {
					// 新增的没有RouteId要补上
					routeItemDO.setRouteId(routeDTO.getId());
					addRouteItemList.add(routeItemDO);
				}
			}
			// 删除
			Set<Long> deletedRouteItems = routeInfo.getDeletedRouteItems();
			Set<Long> updatedRouteItems = routeInfo.getUpdatedRouteItems();
			if (CollectionUtils.isNotEmpty(deletedRouteItems)) {
				deleteRouteItemList.addAll(deletedRouteItems);
			}
			// 修改
			if (CollectionUtils.isNotEmpty(updatedRouteItems)) {
				updatedRouteItems.removeAll(deletedRouteItems);
				for (long routeItemId : updatedRouteItems) {
					if (routeItemId > 0) {
						RouteItemDO routeItemVO = routeItemVOMap.get(routeItemId);
						RouteItemDO routeItemDO = routeItemDOMap.get(routeItemId);
						if (routeItemVO == null) {
							deleteRouteItemList.add(routeItemId);
						} else {
							if (routeItemDO != null) {
								// 更新
								routeItemDO.setDay(routeItemVO.getDay());
								routeItemDO.setName(routeItemVO.getName());
								routeItemDO.setOrderNum(routeItemVO.getOrderNum());
								routeItemDO.setType(routeItemVO.getType());
								if (routeItemVO.getType() == RouteItemBizType.DESCRIPTION.getType()) {
									routeItemDO.setDescription(routeItemVO.getDescription());
								} else if (routeItemVO.getType() == RouteItemBizType.ROUTE_TRAFFIC_INFO.getType()) {
									routeItemDO.setRouteTrafficInfo(routeItemVO.getRouteTrafficInfo());
								} else if (routeItemVO.getType() == RouteItemBizType.ROUTE_ITEM_DESC.getType()) {
									routeItemDO.setRouteItemDesc(routeItemVO.getRouteItemDesc());
								} else if (routeItemVO.getType() == RouteItemBizType.ROUTE_ITEM_DETAIL.getType()) {
									routeItemDO.setRouteItemDetail(routeItemVO.getRouteItemDetail());
								}
								routeItemDO.setStatus(routeItemVO.getStatus());
								updateRouteItemList.add(routeItemDO);
							}
						}
					}
				}
			}
		}
		target.setAddRouteItemList(addRouteItemList);
		target.setUpdrouteItemList(updateRouteItemList);
		target.setDelRouteItemList(deleteRouteItemList);
		return target;
	}

	public static LinePublishDTO toLinePublishDTOForUpdate(BaseLineVO line, LineResult lineResult) {
		BaseInfoVO baseInfo = line.getBaseInfo();
		LinePublishDTO dto = new LinePublishDTO();
		// LineDO
		LineDO lineDO = lineResult.getLineDO();
		LineDO lineDTO = LineConverter.merge(baseInfo, lineDO);
		dto.setLineDO(lineDTO);
		// 行程信息
		RouteInfoVO routeInfo = line.getRouteInfo();
		RouteDO routeDO = lineResult.getRouteDO();
		List<RouteItemDO> routeItemDOList = lineResult.getRouteItemDOList();
		dto = mergeRoute(routeInfo, routeDO, routeItemDOList, dto);
		// 价格信息
		ItemDO itemDO = lineResult.getItemDO();
		List<ItemSkuDO> itemSkuDOs = lineResult.getItemSkuDOList();
		dto = mergeItem(line, itemDO, itemSkuDOs, dto);
		return dto;
	}

	public static LinePublishDTO toLinePublishDTOForUpdate(TourLineVO line, LineResult lineResult) {
		LinePublishDTO linePublishDTOForUpdate = toLinePublishDTOForUpdate(line, lineResult);
		return linePublishDTOForUpdate;
	}

	public static LinePublishDTO toLinePublishDTOForUpdate(FreeLineVO line, LineResult lineResult) {
		LinePublishDTO linePublishDTOForUpdate = toLinePublishDTOForUpdate(line, lineResult);
		return linePublishDTOForUpdate;
	}

	public static LinePublishDTO mergeItem(BaseLineVO line, ItemDO itemDO, List<ItemSkuDO> itemSkuDOs,
			LinePublishDTO target) {
		// ItemDO
		ItemDO ItemDTO = merge(line, itemDO);
		target.setItemDO(ItemDTO);
		// SKU List
		Map<Long, ItemSkuDO> skuDOMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuDOs) {
			skuDOMap.put(itemSkuDO.getId(), itemSkuDO);
		}
		// SKU分离
		long categoryId = line.getCategoryId();
		PriceInfoVO priceInfo = line.getPriceInfo();
		List<ItemSkuDO> itemSkuVOs = toItemSkuDOList(categoryId, Constant.YIMAY_OFFICIAL_ID, priceInfo);
		Map<Long, ItemSkuDO> skuVOMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuVOs) {
			if (itemSkuDO.getId() > 0) {
				skuVOMap.put(itemSkuDO.getId(), itemSkuDO);
			}
		}
		List<ItemSkuDO> addSkuList = new ArrayList<ItemSkuDO>();
		List<ItemSkuDO> updateSkuList = new ArrayList<ItemSkuDO>();
		List<Long> deleteSkuList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(itemSkuVOs)) {
			// 新增
			for (ItemSkuDO itemSkuDO : itemSkuVOs) {
				if (itemSkuDO.getId() <= 0) {
					// 新增的没有ItemId要补上
					itemSkuDO.setItemId(ItemDTO.getId());
					addSkuList.add(itemSkuDO);
				}
			}
			// 删除
			Set<Long> deletedSKUSet = priceInfo.getDeletedSKU();
			if (CollectionUtils.isNotEmpty(deletedSKUSet)) {
				deleteSkuList.addAll(deletedSKUSet);
			}
			// 修改
			Set<Long> updatedSKUSet = priceInfo.getUpdatedSKU();
			if (CollectionUtils.isNotEmpty(updatedSKUSet)) {
				updatedSKUSet.removeAll(deletedSKUSet);
				for (long skuId : updatedSKUSet) {
					if (skuId > 0) {
						ItemSkuDO skuVO = skuVOMap.get(skuId);
						ItemSkuDO skuDO = skuDOMap.get(skuId);
						if (skuVO == null) {
							deleteSkuList.add(skuId);
						} else {
							if (skuDO != null) {
								// 更新
								skuDO.setPrice(skuVO.getPrice());
								skuDO.setStockNum(skuVO.getStockNum());
								skuDO.setFeature(skuVO.getFeature());
								skuDO.setTitle(skuVO.getTitle());
								skuDO.setProperty(skuVO.getProperty());
								updateSkuList.add(skuDO);
							}
						}
					}
				}
			}
		}
		target.setAddItemSkuList(addSkuList);
		target.setUpdItemSkuList(updateSkuList);
		target.setDelItemSkuList(deleteSkuList);
		return target;
	}

	public static FreeLineVO toFreeLineVO(LineResult lineResult, List<ComTagDO> comTagDOs) {
		FreeLineVO result = new FreeLineVO();
		LineDO line = lineResult.getLineDO();
		BaseInfoVO baseInfo = toBaseInfoVO(line, comTagDOs);
		result.setBaseInfo(baseInfo);
		DetailInfoVO detailInfo = toDetailInfoVO(lineResult);
		result.setDetailInfo(detailInfo);
		// 线路个性化部分 start
		RouteInfoVO routeInfo = toRouteInfoVO(lineResult);
		result.setRouteInfo(routeInfo);
		// 线路个性化部分 end
		ItemDO itemDO = lineResult.getItemDO();
		result.setCategoryId(itemDO.getCategoryId());
		result.setOptions(itemDO.getOptions());
		result.setReadonly(itemDO.getStatus() == ItemStatus.valid.getValue());
		PriceInfoVO priceInfo = toPriceInfoVO(itemDO, lineResult.getItemSkuDOList());
		result.setPriceInfo(priceInfo);
		String picUrl = itemDO.getPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC);
		if (StringUtils.isNotBlank(picUrl)) {
			baseInfo.setOrderImage(picUrl);
		}
		NeedKnowVO needKnow = toNeedKnowVO(lineResult);
		result.setNeedKnow(needKnow);
		return result;
	}
}

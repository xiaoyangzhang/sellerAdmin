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
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.domain.share_json.RoutePlan;
import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.PropertyType;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.LinePubAddDTO;
import com.yimayhd.ic.client.model.param.item.line.LinePubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.LineUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.RouteItemUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.RouteUpdateDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.dto.DestinationNode;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.DestinationNodeVO;
import com.yimayhd.sellerAdmin.model.line.DestinationVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.model.line.price.PackageBlock;
import com.yimayhd.sellerAdmin.model.line.price.PackageDay;
import com.yimayhd.sellerAdmin.model.line.price.PackageInfo;
import com.yimayhd.sellerAdmin.model.line.price.PackageMonth;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoDTO;
import com.yimayhd.sellerAdmin.model.line.price.PriceInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteDayVO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoDTO;
import com.yimayhd.sellerAdmin.model.line.route.RouteInfoVO;
import com.yimayhd.sellerAdmin.model.line.route.RoutePlanVo;
import com.yimayhd.sellerAdmin.model.line.route.RouteTrafficVO;

/**
 * 线路转换器
 * 
 * @author yebin
 *
 */
public class LineConverter {
	public static LineDO toLineDO(BaseInfoVO baseInfo, NeedKnowVO needKnow) {
		if (baseInfo == null) {
			return null;
		}
		// 初始化
		LineDO line = new LineDO();
		line.setId(baseInfo.getLineId());
		line.setName(baseInfo.getName());
		if (needKnow != null) {
			line.setNeedKnow(toNeedKnow(needKnow));
		}
		return line;
	}

	public static LineUpdateDTO toLineUpdateDTO(BaseInfoVO baseInfo, NeedKnowVO needKnow) {
		if (baseInfo == null) {
			return null;
		}
		LineUpdateDTO lineUpdateDTO = new LineUpdateDTO();
		lineUpdateDTO.setId(baseInfo.getLineId());
		if (needKnow != null) {
			lineUpdateDTO.setNeedKnow(toNeedKnow(needKnow));
		}
		return lineUpdateDTO;
	}

	public static BaseInfoVO toBaseInfoVO(LineDO lineDO, ItemDO itemDO, List<TagDTO> themes, List<CityVO> departs,
			List<CityVO> dests) {
		if (lineDO == null || itemDO == null) {
			return null;
		}
		BaseInfoVO baseInfo = new BaseInfoVO();
		baseInfo.setLineId(lineDO.getId());
		baseInfo.setItemId(itemDO.getId());
		baseInfo.setCategoryId(itemDO.getCategoryId());
		baseInfo.setType(itemDO.getItemType());
		baseInfo.setName(itemDO.getTitle());
		baseInfo.setCode(itemDO.getCode());
		baseInfo.setDays(itemDO.getDays());
		baseInfo.setDescription(itemDO.getDescription());
		baseInfo.setPicUrls(PicUrlsUtil.getItemMainPics(itemDO));
		baseInfo.setItemStatus(itemDO.getStatus());
		// themes
		if (CollectionUtils.isNotEmpty(themes)) {
			List<Long> themeIds = new ArrayList<Long>();
			for (TagDTO tagDTO : themes) {
				themeIds.add(tagDTO.getId());
			}
			baseInfo.setThemes(themeIds);
		}
		// departs
		if (CollectionUtils.isNotEmpty(departs)) {
			baseInfo.setDeparts(departs);
		}
		// dests
		if (CollectionUtils.isNotEmpty(dests)) {
			baseInfo.setDests(dests);
		}
		return baseInfo;
	}

	public static PriceInfoVO toPriceInfoVO(List<ItemSkuDO> itemSkuList, ItemDO itemDO) {
		PriceInfoVO priceInfo = new PriceInfoVO();
		List<PackageInfo> tcs = new ArrayList<PackageInfo>();
		if (itemDO != null) {
			ItemFeature itemFeature = itemDO.getItemFeature();
			if (itemFeature != null) {
				priceInfo.setLimitBySecond(itemFeature.getStartBookTimeLimit());
			}
		}
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
						if (itemSkuPVPair.getPType() == PropertyType.LINE_PACKAGE.getType()) {
							tcPair = itemSkuPVPair;
							if (!piMap.containsKey(tcPair.getVTxt())) {
								piMap.put(tcPair.getVTxt(), tcPair);
							}
						}
						if (itemSkuPVPair.getPType() == PropertyType.START_DATE.getType()) {
							dayPair = itemSkuPVPair;
							if (!pdMap.containsKey(dayPair.getVTxt())) {
								long time = Long.parseLong(dayPair.getVTxt());
								pdMap.put(time, dayPair);
							}
						}
						if (itemSkuPVPair.getPType() == PropertyType.PERSON_TYPE.getType()) {
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
					if( day == null || personPair == null ){
						System.err.println();
					}
					if( personPair != null ){
						day.put(personPair.getVId(), sku);
					}
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

	public static List<ItemSkuDO> toItemSkuDOList(long categoryId, long sellerId, List<PackageInfo> tcs) {
		if (CollectionUtils.isEmpty(tcs)) {
			return new ArrayList<ItemSkuDO>(0);
		}
		List<Long> packageNameIds = new ArrayList<Long>();
		long nextId = -1;
		for (PackageInfo packageInfo : tcs) {
			long id = packageInfo.getId();
			if (id < 0) {
				packageNameIds.add(id);
				if (id < nextId) {
					nextId = id - 1;
				}
			}
		}
		List<ItemSkuDO> itemSkuDOs = new ArrayList<ItemSkuDO>();
		for (PackageInfo packageInfo : tcs) {
			if (packageInfo.getId() >= 0) {
				packageInfo.setId(nextId);
				nextId--;
			}
			ItemSkuPVPair itemSkuPVPair1 = packageInfo.toItemSkuPVPair();
			if (CollectionUtils.isNotEmpty(packageInfo.getMonths())) {
				for (PackageMonth packageMonth : packageInfo.getMonths()) {
					if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
						for (PackageDay packageDay : packageMonth.getDays()) {
							ItemSkuPVPair itemSkuPVPair2 = packageDay.toItemSkuPVPair();
							itemSkuPVPair2.setVId(-1 * packageDay.getTime());
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
									itemSkuDO.setStockNum(Long.valueOf(packageBlock.getStock()).intValue());
									itemSkuDOs.add(itemSkuDO);
								}
							}
						}
					}
				}
			}
		}
		return itemSkuDOs;
	}

	public static RoutePlanVo toRoutePlanVo(RoutePlan routePlan) {
		if (routePlan == null) {
			return null;
		}
		RoutePlanVo routePlanVo = new RoutePlanVo();
		routePlanVo.setGo(toRouteTrafficVO(routePlan.getGo()));
		routePlanVo.setBack(toRouteTrafficVO(routePlan.getBack()));
		routePlanVo.setHotelInfo(routePlan.getHotelInfo());
		routePlanVo.setScenicInfo(routePlan.getScenicInfo());
		return routePlanVo;
	}

	public static RoutePlan toRoutePlan(RoutePlanVo routePlanVo) {
		if (routePlanVo == null) {
			return null;
		}
		RoutePlan routePlan = new RoutePlan();
		routePlan.setGo(toRouteTrafficInfo(routePlanVo.getGo()));
		routePlan.setBack(toRouteTrafficInfo(routePlanVo.getBack()));
		routePlan.setHotelInfo(routePlanVo.getHotelInfo());
		routePlan.setScenicInfo(routePlanVo.getScenicInfo());
		return routePlan;
	}

	public static NeedKnowVO toNeedKnowVO(NeedKnow needKnow) {
		if (needKnow == null) {
			return null;
		}
		NeedKnowVO needKnowVO = new NeedKnowVO();
		List<NeedKnowItemVo> needKnowItems = new ArrayList<NeedKnowItemVo>();
		List<TextItem> frontNeedKnows = needKnow.getFrontNeedKnow();
		if (CollectionUtils.isNotEmpty(frontNeedKnows)) {
			for (TextItem frontNeedKnow : frontNeedKnows) {
				NeedKnowItemVo needKnowItemVo = toNeedKnowItemVo(frontNeedKnow);
				if (needKnowItemVo != null) {
					needKnowItems.add(needKnowItemVo);
				}
			}
		}
		needKnowVO.setNeedKnowItems(needKnowItems);
		return needKnowVO;
	}

	public static NeedKnowItemVo toNeedKnowItemVo(TextItem frontNeedKnow) {
		if (frontNeedKnow == null) {
			return null;
		}
		NeedKnowItemVo needKnowItemVo = new NeedKnowItemVo();
		needKnowItemVo.setTitle(frontNeedKnow.getTitle());
		needKnowItemVo.setContent(frontNeedKnow.getContent());
		return needKnowItemVo;
	}

	public static NeedKnow toNeedKnow(NeedKnowVO needKnowVO) {
		if (needKnowVO == null) {
			return null;
		}
		NeedKnow needKnow = new NeedKnow();
		List<TextItem> frontNeedKnows = new ArrayList<TextItem>();
		List<NeedKnowItemVo> needKnowItems = needKnowVO.getNeedKnowItems();
		if (CollectionUtils.isNotEmpty(needKnowItems)) {
			for (NeedKnowItemVo needKnowItem : needKnowItems) {
				TextItem textItem = new TextItem();
				textItem.setTitle(needKnowItem.getTitle());
				textItem.setContent(needKnowItem.getContent());
				frontNeedKnows.add(textItem);
			}
		}
		needKnow.setFrontNeedKnow(frontNeedKnows);
		return needKnow;
	}

	public static LineVO toLineVO(LineResult lineResult, PicTextResult picTextResult, List<TagDTO> themes,
			List<CityVO> departs, List<CityVO> dests) {
		if (lineResult == null) {
			return null;
		}
		LineVO result = new LineVO();
		LineDO line = lineResult.getLineDO();
		ItemDO item = lineResult.getItemDO();
		BaseInfoVO baseInfo = toBaseInfoVO(line, item, themes, departs, dests);
		result.setBaseInfo(baseInfo);
		// 线路个性化部分 start
		if (item.getItemType() == ItemType.FREE_LINE.getValue()||item.getItemType() == ItemType.FREE_LINE_ABOARD.getValue()) {
			RouteDO routeDO = lineResult.getRouteDO();
			if (routeDO != null) {
				RoutePlanVo routePlan = toRoutePlanVo(routeDO.getRoutePlan());
				result.setRoutePlan(routePlan);
			}
		}
		// 行程信息
		RouteInfoVO routeInfo = toRouteInfoVO(lineResult.getRouteDO(), lineResult.getRouteItemDOList());
		result.setRouteInfo(routeInfo);
		
		//查询类目的所有销售属性
		//过滤lineResult.getItemSkuDOList()中的属性，如果销售属性已被删除，页面就不在显示了
		
		// 价格信息
		result.setPriceInfo(toPriceInfoVO(lineResult.getItemSkuDOList(), item));
		// 购买须知
		result.setNeedKnow(toNeedKnowVO(line.getNeedKnow()));
		// 图文详情
		PictureTextVO pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
		result.setPictureText(pictureTextVO);
		return result;
	}

	public static RouteInfoVO toRouteInfoVO(RouteDO routeDO, List<RouteItemDO> routeItems) {
		if (routeDO == null) {
			return null;
		}
		RouteInfoVO routeInfo = new RouteInfoVO();
		routeInfo.setRouteId(routeDO.getId());
		List<RouteDayVO> tripDays = new ArrayList<RouteDayVO>();
		Set<Integer> days = new HashSet<Integer>();
		Map<Integer, RouteItemDO> detailMap = new HashMap<Integer, RouteItemDO>();
		if (CollectionUtils.isNotEmpty(routeItems)) {
			for (RouteItemDO routeItem : routeItems) {
				days.add(routeItem.getDay());
				if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DETAIL.getType()) {
					RouteItemDetail detail = routeItem.getRouteItemDetail();
					if (detail != null) {
						if (RouteItemType.DETAIL.name().equals(detail.getType())) {
							detailMap.put(routeItem.getDay(), routeItem);
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
			RouteItemDO routeItemDO = detailMap.get(day);
			tripDays.add(new RouteDayVO(routeItemDO.getId(), routeItemDO.getRouteItemDetail()));
		}
		routeInfo.setRouteDays(tripDays);
		return routeInfo;
	}

	public static List<RouteItemDO> toRouteItemDOs(List<RouteDayVO> routeDays) {
		if (CollectionUtils.isEmpty(routeDays)) {
			return new ArrayList<RouteItemDO>(0);
		}
		List<RouteItemDO> routeItemDOList = new ArrayList<RouteItemDO>();
		for (int i = 1; i <= routeDays.size(); i++) {
			RouteDayVO tripDay = routeDays.get(i - 1);
			routeItemDOList.addAll(toRouteItemDO(i, tripDay));
		}
		return routeItemDOList;
	}

	public static List<RouteItemDO> toRouteItemDO(int day, RouteDayVO routeDay) {
		if (routeDay == null) {
			return new ArrayList<RouteItemDO>(0);
		}
		RouteItemDetail routeItemDetail = new RouteItemDetail();
		routeItemDetail.setType(RouteItemType.DETAIL.name());
		routeItemDetail.setName(routeDay.getTitle());
		routeItemDetail.setShortDesc(routeDay.getDescription());
		routeItemDetail.setPics(routeDay.getPicUrls());
		RouteItemDO routeItemDO = new RouteItemDO();
		routeItemDO.setId(routeDay.getRouteItemId());
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DETAIL.getType());
		routeItemDO.setRouteItemDetail(routeItemDetail);
		routeItemDO.setDay(day);
		List<RouteItemDO> result = new ArrayList<RouteItemDO>();
		result.add(routeItemDO);
		return result;
	}

	public static RouteUpdateDTO toRouteUpdateDTO(LineVO line) {
		RouteInfoVO routeInfo = line.getRouteInfo();
		BaseInfoVO baseInfo = line.getBaseInfo();
		if (routeInfo == null || baseInfo == null) {
			return null;
		}
		RouteUpdateDTO routeUpdateDTO = new RouteUpdateDTO();
		routeUpdateDTO.setId(routeInfo.getRouteId());
		routeUpdateDTO.setName(baseInfo.getName());
		routeUpdateDTO.setRoutePlan(toRoutePlan(line.getRoutePlan()));
		return routeUpdateDTO;
	}

	public static LinePubUpdateDTO toLinePublishDTOForUpdate(long sellerId, LineVO line) {
		if (sellerId <= 0 || line == null) {
			return null;
		}
		BaseInfoVO baseInfo = line.getBaseInfo();
		if (baseInfo == null) {
			return null;
		}
		LinePubUpdateDTO dto = new LinePubUpdateDTO();
		dto.setItemId(baseInfo.getItemId());
		dto.setSellerId(sellerId);
		// LineDO
		LineUpdateDTO lineUpdateDTO = toLineUpdateDTO(baseInfo, line.getNeedKnow());
		dto.setLine(lineUpdateDTO);
		// 行程信息
		RouteInfoVO routeInfo = line.getRouteInfo();
		if (routeInfo != null) {
			RouteUpdateDTO routeUpdateDTO = toRouteUpdateDTO(line);
			dto.setRoute(routeUpdateDTO);
			RouteInfoDTO routeInfoDTO = toRouteInfoDTO(line.getRouteInfo());
			if (routeInfoDTO != null) {
				dto.setAddRouteItemList(routeInfoDTO.getAddRouteItemList());
				dto.setUpdrouteItemList(routeInfoDTO.getUpdrouteItemList());
				dto.setDelRouteItemList(routeInfoDTO.getDelRouteItemList());
			}
		}
		// 价格信息
		// ItemDO
		PriceInfoVO priceInfo = line.getPriceInfo();
		if (priceInfo != null) {
			ItemPubUpdateDTO itemPubUpdateDTO = toItemPubUpdateDTO(baseInfo, priceInfo.getLimitBySecond());
			dto.setItem(itemPubUpdateDTO);
			PriceInfoDTO priceInfoDTO = toPriceInfoDTO(baseInfo.getCategoryId(), sellerId, priceInfo);
			if (priceInfoDTO != null) {
				dto.setAddItemSkuList(priceInfoDTO.getAddItemSkuList());
				dto.setUpdItemSkuList(priceInfoDTO.getUpdItemSkuList());
				dto.setDelItemSkuList(priceInfoDTO.getDelItemSkuList());
			}
		}
		return dto;
	}

	public static RouteInfoDTO toRouteInfoDTO(RouteInfoVO routeInfo) {
		if (routeInfo == null) {
			return null;
		}
		RouteInfoDTO target = new RouteInfoDTO();
		// RouteItemDO List
		// SKU分离
		List<RouteItemDO> routeItemVOs = toRouteItemDOs(routeInfo.getRouteDays());
		Map<Long, RouteItemDO> routeItemVOMap = new HashMap<Long, RouteItemDO>();
		for (RouteItemDO routeItemVO : routeItemVOs) {
			if (routeItemVO.getId() > 0) {
				routeItemVOMap.put(routeItemVO.getId(), routeItemVO);
			}
		}
		List<RouteItemDO> addRouteItemList = new ArrayList<RouteItemDO>();
		List<RouteItemUpdateDTO> updateRouteItemList = new ArrayList<RouteItemUpdateDTO>();
		List<Long> deleteRouteItemList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(routeItemVOs)) {
			// 新增
			for (RouteItemDO routeItemDO : routeItemVOs) {
				if (routeItemDO.getId() <= 0) {
					// 新增的没有RouteId要补上
					addRouteItemList.add(routeItemDO);
				}
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
				if (routeItemId > 0 && routeItemVOMap.containsKey(routeItemId)) {
					updateRouteItemList.add(toRouteItemUpdateDTO(routeItemVOMap.get(routeItemId)));
				}
			}
		}
		target.setAddRouteItemList(addRouteItemList);
		target.setUpdrouteItemList(updateRouteItemList);
		target.setDelRouteItemList(deleteRouteItemList);
		return target;
	}

	public static RouteItemUpdateDTO toRouteItemUpdateDTO(RouteItemDO routeItemVO) {
		if (routeItemVO == null) {
			return null;
		}
		RouteItemUpdateDTO routeItemUpdateDTO = new RouteItemUpdateDTO();
		BeanUtils.copyProperties(routeItemVO, routeItemUpdateDTO);
		return routeItemUpdateDTO;
	}

	public static ItemDO toItemDO(long categoryId, long sellerId, BaseInfoVO baseInfo, PriceInfoVO priceInfo) {
		if (categoryId <= 0 || sellerId <= 0 || baseInfo == null) {
			return null;
		}
		// 初始化
		ItemDO itemDO = new ItemDO();
		// 赋值
		itemDO.setId(baseInfo.getItemId());
		itemDO.setCategoryId(categoryId);
		itemDO.setSellerId(sellerId);
		itemDO.setTitle(baseInfo.getName());
		itemDO.setItemType(baseInfo.getType());
		itemDO.setCode(baseInfo.getCode());
		itemDO.setDays(baseInfo.getDays());
		itemDO.setDescription(baseInfo.getDescription());
		itemDO.addPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS, PicUrlsUtil.parsePicsString(baseInfo.getPicUrls()));
		itemDO.setDays(baseInfo.getDays());
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, priceInfo.getLimitBySecond());
		itemFeature.put(ItemFeatureKey.SUBJECTS, baseInfo.getThemesIcs());
		itemFeature.put(ItemFeatureKey.START_CITIES, baseInfo.getDepartsIcs());
		itemFeature.put(ItemFeatureKey.DEST_CITIES, baseInfo.getDestsIcs());
		itemDO.setItemFeature(itemFeature);
		return itemDO;
	}

	public static ItemPubUpdateDTO toItemPubUpdateDTO(BaseInfoVO baseInfo, int startBookTimeLimit) {
		if (baseInfo == null) {
			return null;
		}
		ItemPubUpdateDTO itemUpdateDTO = new ItemPubUpdateDTO();
		itemUpdateDTO.setId(baseInfo.getItemId());
		itemUpdateDTO.setCode(baseInfo.getCode());
		// 赋值
		itemUpdateDTO.setTitle(baseInfo.getName());
		itemUpdateDTO.setDays(baseInfo.getDays());
		itemUpdateDTO.setDescription(baseInfo.getDescription());
		itemUpdateDTO.setItemMainPics(baseInfo.getPicUrls());
		itemUpdateDTO.setStartBookTimeLimit(startBookTimeLimit);
		itemUpdateDTO.setDays(baseInfo.getDays());
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.SUBJECTS, baseInfo.getThemesIcs());
		itemFeature.put(ItemFeatureKey.START_CITIES, baseInfo.getDepartsIcs());
		itemFeature.put(ItemFeatureKey.DEST_CITIES, baseInfo.getDestsIcs());
		itemUpdateDTO.setItemFeature(itemFeature);
		return itemUpdateDTO;
	}

	public static PriceInfoDTO toPriceInfoDTO(long categoryId, long sellerId, PriceInfoVO priceInfo) {
		if (categoryId <= 0 || sellerId <= 0 || priceInfo == null) {
			return null;
		}
		PriceInfoDTO target = new PriceInfoDTO();
		// SKU分离
		List<ItemSkuDO> itemSkuVOs = toItemSkuDOList(categoryId, sellerId, priceInfo.getTcs());
		Map<Long, ItemSkuDO> skuVOMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuVOs) {
			if (itemSkuDO.getId() > 0) {
				skuVOMap.put(itemSkuDO.getId(), itemSkuDO);
			}
		}
		List<ItemSkuDO> addSkuList = new ArrayList<ItemSkuDO>();
		List<ItemSkuPubUpdateDTO> updateSkuList = new ArrayList<ItemSkuPubUpdateDTO>();
		List<Long> deleteSkuList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(itemSkuVOs)) {
			// 新增
			for (ItemSkuDO itemSkuDO : itemSkuVOs) {
				if (itemSkuDO.getId() <= 0) {
					addSkuList.add(itemSkuDO);
				}
			}
		}
		// 删除
		Set<Long> deletedSKUSet = priceInfo.getDeletedSKU();
		if (CollectionUtils.isNotEmpty(deletedSKUSet)) {
			deleteSkuList.addAll(deletedSKUSet);
		}
		// 更新
		Set<Long> updatedSKUSet = priceInfo.getUpdatedSKU();
		if (CollectionUtils.isNotEmpty(updatedSKUSet)) {
			updatedSKUSet.removeAll(deletedSKUSet);
			for (long skuId : updatedSKUSet) {
				if (skuId > 0 && skuVOMap.containsKey(skuId)) {
					updateSkuList.add(toItemSkuPubUpdateDTO(skuVOMap.get(skuId)));
				}
			}
		}
		target.setAddItemSkuList(addSkuList);
		target.setUpdItemSkuList(updateSkuList);
		target.setDelItemSkuList(deleteSkuList);
		return target;
	}

	public static ItemSkuPubUpdateDTO toItemSkuPubUpdateDTO(ItemSkuDO itemSkuVO) {
		if (itemSkuVO == null) {
			return null;
		}
		ItemSkuPubUpdateDTO itemSkuPubUpdateDTO = new ItemSkuPubUpdateDTO();
		BeanUtils.copyProperties(itemSkuVO, itemSkuPubUpdateDTO);
		return itemSkuPubUpdateDTO;
	}

	public static RouteTrafficInfo toRouteTrafficInfo(RouteTrafficVO routeTrafficVO) {
		if (routeTrafficVO == null) {
			return null;
		}
		RouteTrafficInfo routeTrafficInfo = new RouteTrafficInfo();
		routeTrafficInfo.setType(routeTrafficVO.getType());
		routeTrafficInfo.setDescription(routeTrafficVO.getDescription());
		return routeTrafficInfo;
	}

	public static RouteTrafficVO toRouteTrafficVO(RouteTrafficInfo routeTrafficInfo) {
		if (routeTrafficInfo == null) {
			return null;
		}
		RouteTrafficVO routeTrafficVO = new RouteTrafficVO();
		routeTrafficVO.setType(routeTrafficInfo.getType());
		routeTrafficVO.setDescription(routeTrafficInfo.getDescription());
		return routeTrafficVO;
	}

	public static LinePubAddDTO toLinePublishDTOForSave(long sellerId, LineVO line) {
		if (sellerId <= 0 || line == null) {
			return null;
		}
		LinePubAddDTO dto = new LinePubAddDTO();
		LineDO lineDO = toLineDO(line.getBaseInfo(), line.getNeedKnow());
		dto.setLine(lineDO);
		RoutePlanVo routePlan = line.getRoutePlan();
		if (routePlan != null) {
			RouteDO routeDTO = new RouteDO();
			routeDTO.setRoutePlan(toRoutePlan(routePlan));
			dto.setRoute(routeDTO);
		}
		RouteInfoVO routeInfo = line.getRouteInfo();
		if (routeInfo != null) {
			List<RouteDayVO> routeDays = routeInfo.getRouteDays();
			List<RouteItemDO> routeItemDOList = toRouteItemDOs(routeDays);
			dto.setRouteItemList(routeItemDOList);
		}
		
		BaseInfoVO baseInfo = line.getBaseInfo();
		PriceInfoVO priceInfo = line.getPriceInfo();
		long categoryId = baseInfo.getCategoryId();
		dto.setItem(toItemDO(categoryId, sellerId, baseInfo, priceInfo));
		dto.setItemSkuList(toItemSkuDOList(categoryId, sellerId, priceInfo.getTcs()));
		return dto;
	}
	
	public static List<DestinationNodeVO> toDestinationNodeVO(List<DestinationNode> destinationNodes) {
		if (CollectionUtils.isEmpty(destinationNodes)) {
			return new ArrayList<DestinationNodeVO>(0);
		}
		List<DestinationNodeVO> departs = new ArrayList<DestinationNodeVO>();
		for (DestinationNode node : destinationNodes) {
			DestinationNodeVO destinationNodeVO = new DestinationNodeVO();
			if (node.isHasChild()) {
				DestinationDO destinationDO = node.getDestinationDO();
				DestinationVO destinationVO = new DestinationVO(destinationDO.getId(), destinationDO.getName(),
						destinationDO.getCode(), destinationDO.getSimpleCode());
				destinationNodeVO.setChild(toDestinationNodeVO(node.getChildList()));
				destinationNodeVO.setDestinationVO(destinationVO);
			} else {
				DestinationDO destinationDO = node.getDestinationDO();
				DestinationVO destinationVO = new DestinationVO(destinationDO.getId(), destinationDO.getName(),
						destinationDO.getCode(), destinationDO.getSimpleCode());
				destinationNodeVO.setDestinationVO(destinationVO);
			}
			departs.add(destinationNodeVO);
		}
		return departs;
	}

	public static void fillDestinationesSelectedInfo(List<DestinationNodeVO> destinationNodeVOs,
			List<String> selectedIds) {
		if (CollectionUtils.isEmpty(destinationNodeVOs) || CollectionUtils.isEmpty(selectedIds)) {
			return ;
		}
		for (DestinationNodeVO destinationNodeVO : destinationNodeVOs) {
			List<DestinationNodeVO> children = destinationNodeVO.getChild() ;
			if (CollectionUtils.isNotEmpty( children )) {
				fillDestinationesSelectedInfo(children, selectedIds);
			}
			DestinationVO destinationVO = destinationNodeVO.getDestinationVO();
			
			//FIXME 非空判断
			Long id = destinationVO.getId() ;
			if (selectedIds.contains( id.toString() )) {
				destinationVO.setSelected(true);
			}
		}
	}
	
	public static List<ItemSkuDO> filterUnnecessaryItem(CategoryDO category, List<ItemSkuDO> itemSkuDOList) {
		if (category==null || CollectionUtils.isEmpty(itemSkuDOList)) {
			return null;
		}
		List<CategoryPropertyValueDO> sellCategoryPropertyDOs = category.getSellCategoryPropertyDOs();
		if (CollectionUtils.isEmpty(sellCategoryPropertyDOs) || CollectionUtils.isEmpty(itemSkuDOList) ) {
			return null;
		}
		/*
		 * 套餐
		 * 出发日期
		 * 人员类型   ---成人，儿童，单房差
		 * */
		//计算出类目下所有销售属性
		List<Long> usingVIds = new ArrayList<Long>() ;
		for (CategoryPropertyValueDO categoryPropertyValueDO : sellCategoryPropertyDOs) {
			List<CategoryValueDO> categoryValueDOs = categoryPropertyValueDO.getCategoryValueDOs() ;
			if( CollectionUtils.isNotEmpty(categoryValueDOs) ){
				for( CategoryValueDO value : categoryValueDOs ){
					//计算出所有销售属性
					usingVIds.add(value.getId()) ;
				}
			}
		}
		
		List<ItemSkuDO> skus = new ArrayList<ItemSkuDO>() ;
		
		for (ItemSkuDO sku : itemSkuDOList) {
			List<ItemSkuPVPair> itemSkuPVPairList = sku.getItemSkuPVPairList();
			for (ItemSkuPVPair itemSkuPVPair : itemSkuPVPairList) {
				if (itemSkuPVPair.getPType() == PropertyType.PERSON_TYPE.getType()) {
					long vId = itemSkuPVPair.getVId() ;
					if ( !usingVIds.contains(vId)) {
						skus.add(sku);
						break; 
					}
				}
			}
		}
		return skus ;
	}

	public static void filterItemSku(CategoryDO category, List<ItemSkuDO> itemSkuDOList) {
		if (category==null || CollectionUtils.isEmpty(itemSkuDOList)) {
			return;
		}
		List<CategoryPropertyValueDO> sellCategoryPropertyDOs = category.getSellCategoryPropertyDOs();
		if (CollectionUtils.isEmpty(sellCategoryPropertyDOs) || CollectionUtils.isEmpty(itemSkuDOList) ) {
			return;
		}
		/*
		 * 套餐
		 * 出发日期
		 * 人员类型   ---成人，儿童，单房差
		 * */
		//计算出类目下所有销售属性
		Map<Long, CategoryValueDO> map = new HashMap<Long, CategoryValueDO>();
		CategoryPropertyDO categoryPropertyValueDOForPerson=new CategoryPropertyDO();
		Long mapcpvd =null;
		for (CategoryPropertyValueDO categoryPropertyValueDO : sellCategoryPropertyDOs) {
			List<CategoryValueDO> categoryValueDOs = categoryPropertyValueDO.getCategoryValueDOs() ;
			if( CollectionUtils.isNotEmpty(categoryValueDOs) ){
				categoryPropertyValueDOForPerson=categoryPropertyValueDO.getCategoryPropertyDO();
//				mapcpvd.put(categoryPropertyValueDO.getPropertyId(), categoryPropertyValueDO) ;
				mapcpvd=categoryPropertyValueDO.getPropertyId();
				for( CategoryValueDO value : categoryValueDOs ){
					//计算出所有销售属性
					map.put(value.getId(), value) ;
				}
			}
		}
		Set<Long> skuPIds = new HashSet<Long>();//原来人员类型下的属性取值：1，2，3，4
		//取出所有的人员类型  下的属性值id
		for (ItemSkuDO sku : itemSkuDOList) {
			// Set<Long> pIds = mapcpvd.keySet();
			List<ItemSkuPVPair> itemSkuPVPairList = sku.getItemSkuPVPairList();
			for (ItemSkuPVPair itemSkuPVPair : itemSkuPVPairList) {
				long pid = itemSkuPVPair.getPId();
				if (mapcpvd != null && mapcpvd.equals(pid)) {
					skuPIds.add(itemSkuPVPair.getVId());
				}
			}
		}
		List<ItemSkuDO> itemSkuDOs=new ArrayList<ItemSkuDO>();
		Set<Long> vIds = map.keySet();// 1，4，145
		Set<Long> oVIds = new HashSet<Long>();
		oVIds.addAll(vIds);
		vIds.removeAll(skuPIds);
		if (CollectionUtils.isEmpty(oVIds)) {
			return  ;
		}
		for (ItemSkuDO sku : itemSkuDOList) {
//			for (Long vId : vIds) {
				List<ItemSkuPVPair> itemSkuPVPairList = sku.getItemSkuPVPairList();
				List<ItemSkuPVPair> newItemSkuPVPairList = new ArrayList<ItemSkuPVPair>();
				boolean containPropertyType = false;
				for (ItemSkuPVPair itemSkuPVPair : itemSkuPVPairList) {
					if (itemSkuPVPair.getPType() == PropertyType.PERSON_TYPE.getType()) {
						long vId = itemSkuPVPair.getVId() ;
						if (oVIds.contains(vId)) {
							containPropertyType = true; 
							newItemSkuPVPairList.add(itemSkuPVPair);
						}
					} else {
						newItemSkuPVPairList.add(itemSkuPVPair);
					}
				}
				if( !containPropertyType ){
					for( long vId : vIds ){
						ItemSkuPVPair pair = new ItemSkuPVPair();
						CategoryValueDO value = map.get(vId);
						pair.setPId(categoryPropertyValueDOForPerson.getId());
						pair.setPType(categoryPropertyValueDOForPerson.getType());
						pair.setPTxt(categoryPropertyValueDOForPerson.getText());
						pair.setVTxt(value.getText());
						pair.setVType(value.getType());
						pair.setVId(value.getId());
						newItemSkuPVPairList.add(pair);
						sku.setPrice(0);
						sku.setStockNum(0);
						sku.setId(0);
					}
					
				}
				
				sku.setItemSkuPVPairList(newItemSkuPVPairList);
				itemSkuDOs.add(sku);
//			}
		}
		
		
		itemSkuDOList=itemSkuDOs;
	}
	public static void filterItemSku2(CategoryDO category, List<ItemSkuDO> itemSkuDOList) {
		if (category==null || CollectionUtils.isEmpty(itemSkuDOList)) {
			return;
		}
		List<CategoryPropertyValueDO> sellCategoryPropertyDOs = category.getSellCategoryPropertyDOs();
		if (CollectionUtils.isEmpty(sellCategoryPropertyDOs) || CollectionUtils.isEmpty(itemSkuDOList) ) {
			return;
		}
		/*
		 * 套餐
		 * 出发日期
		 * 人员类型   ---成人，儿童，单房差
		 * */
		//计算出类目下所有销售属性
		Map<Long, CategoryValueDO> map = new HashMap<Long, CategoryValueDO>();
		CategoryPropertyDO categoryPropertyValueDOForPerson=new CategoryPropertyDO();
		Long mapcpvd =null;
		for (CategoryPropertyValueDO categoryPropertyValueDO : sellCategoryPropertyDOs) {
			List<CategoryValueDO> categoryValueDOs = categoryPropertyValueDO.getCategoryValueDOs() ;
			if( CollectionUtils.isNotEmpty(categoryValueDOs) ){
				categoryPropertyValueDOForPerson=categoryPropertyValueDO.getCategoryPropertyDO();
//				mapcpvd.put(categoryPropertyValueDO.getPropertyId(), categoryPropertyValueDO) ;
				mapcpvd=categoryPropertyValueDO.getPropertyId();
				for( CategoryValueDO value : categoryValueDOs ){
					//计算出所有销售属性
					map.put(value.getId(), value) ;
				}
			}
		}
		Set<Long> skuPIds = new HashSet<Long>();//原来人员类型下的属性取值：1，2，3，4
		//取出所有的人员类型  下的属性值id
		for (ItemSkuDO sku : itemSkuDOList) {
			// Set<Long> pIds = mapcpvd.keySet();
			List<ItemSkuPVPair> itemSkuPVPairList = sku.getItemSkuPVPairList();
			for (ItemSkuPVPair itemSkuPVPair : itemSkuPVPairList) {
				long pid = itemSkuPVPair.getPId();
				if (mapcpvd != null && mapcpvd.equals(pid)) {
					skuPIds.add(itemSkuPVPair.getVId());
				}
			}
		}
		List<ItemSkuDO> itemSkuDOs=new ArrayList<ItemSkuDO>();
		Set<Long> vIds = map.keySet();// 1，4，145
		Set<Long> oVIds = new HashSet<Long>();
		oVIds.addAll(vIds);
		vIds.removeAll(skuPIds);
		if (CollectionUtils.isNotEmpty(vIds)) {
			for (ItemSkuDO sku : itemSkuDOList) {
				for (Long vId : vIds) {
					List<ItemSkuPVPair> itemSkuPVPairList = sku.getItemSkuPVPairList();
					List<ItemSkuPVPair> newItemSkuPVPairList = new ArrayList<ItemSkuPVPair>();
					for (ItemSkuPVPair itemSkuPVPair : itemSkuPVPairList) {
						if (itemSkuPVPair.getPType() == PropertyType.PERSON_TYPE.getType()) {
							if (oVIds.contains(itemSkuPVPair.getVId())) {
								newItemSkuPVPairList.add(itemSkuPVPair);
							} else {
								ItemSkuPVPair pair = new ItemSkuPVPair();
								CategoryValueDO value = map.get(vId);
								pair.setPId(categoryPropertyValueDOForPerson.getId());
								pair.setPType(categoryPropertyValueDOForPerson.getType());
								pair.setPTxt(categoryPropertyValueDOForPerson.getText());
								pair.setVTxt(value.getText());
								pair.setVType(value.getType());
								pair.setVId(value.getId());
								newItemSkuPVPairList.add(pair);
								sku.setPrice(0);
								sku.setStockNum(0);
							}
						} else {
							newItemSkuPVPairList.add(itemSkuPVPair);
						}
					}
					sku.setItemSkuPVPairList(newItemSkuPVPairList);
					itemSkuDOs.add(sku);
				}
			}
		}
		itemSkuDOList=itemSkuDOs;
	}
}

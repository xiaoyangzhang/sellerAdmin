package com.yimayhd.sellerAdmin.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.domain.share_json.RouteTextItem;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 行程-天
 * 
 * @author yebin
 *
 */
public class TripDay {
	private long trafficRouteItemId;
	private TripTraffic traffic;// 交通方式
	private long descriptionRouteItemId;
	private String description;// 描述
	private long breakfastRouteItemId;
	private List<IdNamePair> restaurant1;// 早餐
	private long lunchRouteItemId;
	private List<IdNamePair> restaurant2;// 午餐
	private long dinnerRouteItemId;
	private List<IdNamePair> restaurant3;// 晚餐
	private long scenicsRouteItemId;
	private List<IdNamePair> scenics;// 景区
	private long hotelsRouteItemId;
	private List<IdNamePair> hotels;// 酒店
	private long restaurantDetailRouteItemId;
	private RouteItemDetail restaurantDetail;
	private long scenicDetailRouteItemId;
	private RouteItemDetail scenicDetail;
	private long hotelDetailRouteItemId;
	private RouteItemDetail hotelDetail;

	public TripDay() {
	}

	public TripDay(RouteItemDO trafficInfo, RouteItemDO description, RouteItemDO breakfast, RouteItemDO lunch,
			RouteItemDO dinner, RouteItemDO scenic, RouteItemDO hotel, RouteItemDO restaurantDetail,
			RouteItemDO scenicDetail, RouteItemDO hotelDetail) {
		if (trafficInfo != null && trafficInfo.getRouteTrafficInfo() != null) {
			this.trafficRouteItemId = trafficInfo.getId();
			this.traffic = new TripTraffic(trafficInfo.getRouteTrafficInfo());
		}
		if (description != null) {
			this.descriptionRouteItemId = description.getId();
			this.description = description.getDescription();
		}
		this.restaurant1 = new ArrayList<IdNamePair>();
		if (breakfast != null) {
			this.breakfastRouteItemId = breakfast.getId();
			List<RouteTextItem> breakfastTextItemList = breakfast.getRouteItemDesc().getTextItems();
			if (CollectionUtils.isNotEmpty(breakfastTextItemList)) {
				for (RouteTextItem routeTextItem : breakfastTextItemList) {
					this.restaurant1.add(new IdNamePair(routeTextItem));
				}
			}
		}
		this.restaurant2 = new ArrayList<IdNamePair>();
		if (lunch != null) {
			this.lunchRouteItemId = lunch.getId();
			List<RouteTextItem> lunchTextItemList = lunch.getRouteItemDesc().getTextItems();
			if (CollectionUtils.isNotEmpty(lunchTextItemList)) {
				for (RouteTextItem routeTextItem : lunchTextItemList) {
					this.restaurant2.add(new IdNamePair(routeTextItem));
				}
			}
		}
		this.restaurant3 = new ArrayList<IdNamePair>();
		if (dinner != null) {
			this.dinnerRouteItemId = dinner.getId();
			List<RouteTextItem> dinnerTextItemList = dinner.getRouteItemDesc().getTextItems();
			if (dinner != null && CollectionUtils.isNotEmpty(dinnerTextItemList)) {
				for (RouteTextItem routeTextItem : dinnerTextItemList) {
					this.restaurant3.add(new IdNamePair(routeTextItem));
				}
			}
		}
		this.scenics = new ArrayList<IdNamePair>();
		if (scenic != null) {
			this.scenicsRouteItemId = scenic.getId();
			List<RouteTextItem> scenicTextItemList = scenic.getRouteItemDesc().getTextItems();
			if (scenic != null && CollectionUtils.isNotEmpty(scenicTextItemList)) {
				for (RouteTextItem routeTextItem : scenicTextItemList) {
					this.scenics.add(new IdNamePair(routeTextItem));
				}
			}
		}
		this.hotels = new ArrayList<IdNamePair>();
		if (hotel != null) {
			this.hotelsRouteItemId = hotel.getId();
			List<RouteTextItem> hotelTextItemList = hotel.getRouteItemDesc().getTextItems();
			if (hotel != null && CollectionUtils.isNotEmpty(hotelTextItemList)) {
				for (RouteTextItem routeTextItem : hotelTextItemList) {
					this.hotels.add(new IdNamePair(routeTextItem));
				}
			}
		}
		if (restaurantDetail != null) {
			this.restaurantDetailRouteItemId = restaurantDetail.getId();
			this.restaurantDetail = restaurantDetail.getRouteItemDetail();
		}
		if (scenicDetail != null) {
			this.scenicDetailRouteItemId = scenicDetail.getId();
			this.scenicDetail = scenicDetail.getRouteItemDetail();
		}
		if (hotelDetail != null) {
			this.hotelDetailRouteItemId = hotelDetail.getId();
			this.hotelDetail = hotelDetail.getRouteItemDetail();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TripTraffic getTraffic() {
		return traffic;
	}

	public void setTraffic(TripTraffic traffic) {
		this.traffic = traffic;
	}

	public RouteItemDO getRouteItemTrafficInfo(int day) {
		RouteItemDO routeItemDO = null;
		if (this.traffic != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.trafficRouteItemId);
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_TRAFFIC_INFO.getType());
			routeItemDO.setRouteTrafficInfo(this.traffic.toRouteTrafficInfo());
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemDescription(int day) {
		RouteItemDO routeItemDO = null;
		if (StringUtils.isNotBlank(this.description)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.descriptionRouteItemId);
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.DESCRIPTION.getType());
			routeItemDO.setDescription(this.description);
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemBreakfast(int day) {
		RouteItemDO routeItemDO = null;
		if (CollectionUtils.isNotEmpty(this.restaurant1)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.breakfastRouteItemId);
			RouteItemDesc routeItemDesc = new RouteItemDesc();
			List<RouteTextItem> items = new ArrayList<RouteTextItem>();
			for (IdNamePair pair : this.restaurant1) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.BREAKFAST.name());
				items.add(item);
			}
			routeItemDesc.setTextItems(items);
			routeItemDesc.setType(RouteItemType.BREAKFAST.name());
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setRouteItemDesc(routeItemDesc);
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemLunch(int day) {
		RouteItemDO routeItemDO = null;
		if (CollectionUtils.isNotEmpty(this.restaurant2)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.lunchRouteItemId);
			RouteItemDesc routeItemDesc = new RouteItemDesc();
			List<RouteTextItem> items = new ArrayList<RouteTextItem>();
			for (IdNamePair pair : this.restaurant2) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.LUNCH.name());
				items.add(item);
			}
			routeItemDesc.setTextItems(items);
			routeItemDesc.setType(RouteItemType.LUNCH.name());
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setRouteItemDesc(routeItemDesc);
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemDinner(int day) {
		RouteItemDO routeItemDO = null;
		if (CollectionUtils.isNotEmpty(this.restaurant3)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.dinnerRouteItemId);
			RouteItemDesc routeItemDesc = new RouteItemDesc();
			List<RouteTextItem> items = new ArrayList<RouteTextItem>();
			for (IdNamePair pair : this.restaurant3) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.DINNER.name());
				items.add(item);
			}
			routeItemDesc.setTextItems(items);
			routeItemDesc.setType(RouteItemType.DINNER.name());
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setRouteItemDesc(routeItemDesc);
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemScenic(int day) {
		RouteItemDO routeItemDO = null;
		if (CollectionUtils.isNotEmpty(this.scenics)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.scenicsRouteItemId);
			RouteItemDesc routeItemDesc = new RouteItemDesc();
			List<RouteTextItem> items = new ArrayList<RouteTextItem>();
			for (IdNamePair pair : this.scenics) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.SCENIC.name());
				items.add(item);
			}
			routeItemDesc.setTextItems(items);
			routeItemDesc.setType(RouteItemType.SCENIC.name());
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setRouteItemDesc(routeItemDesc);
		}
		return routeItemDO;
	}

	public RouteItemDO getRouteItemHotel(int day) {
		RouteItemDO routeItemDO = null;
		if (CollectionUtils.isNotEmpty(this.hotels)) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.hotelsRouteItemId);
			RouteItemDesc routeItemDesc = new RouteItemDesc();
			List<RouteTextItem> items = new ArrayList<RouteTextItem>();
			for (IdNamePair pair : this.hotels) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.HOTEL.name());
				items.add(item);
			}
			routeItemDesc.setTextItems(items);
			routeItemDesc.setType(RouteItemType.HOTEL.name());
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setRouteItemDesc(routeItemDesc);
		}
		return routeItemDO;
	}

	public List<IdNamePair> getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(List<IdNamePair> restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public List<IdNamePair> getRestaurant2() {
		return restaurant2;
	}

	public void setRestaurant2(List<IdNamePair> restaurant2) {
		this.restaurant2 = restaurant2;
	}

	public List<IdNamePair> getRestaurant3() {
		return restaurant3;
	}

	public void setRestaurant3(List<IdNamePair> restaurant3) {
		this.restaurant3 = restaurant3;
	}

	public List<IdNamePair> getScenics() {
		return scenics;
	}

	public void setScenics(List<IdNamePair> scenics) {
		this.scenics = scenics;
	}

	public List<IdNamePair> getHotels() {
		return hotels;
	}

	public void setHotels(List<IdNamePair> hotels) {
		this.hotels = hotels;
	}

	public long getRestaurantDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(restaurant1)) {
			for (IdNamePair idNamePair : restaurant1) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		if (id <= 0 && CollectionUtils.isNotEmpty(restaurant2)) {
			for (IdNamePair idNamePair : restaurant2) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		if (id <= 0 && CollectionUtils.isNotEmpty(restaurant3)) {
			for (IdNamePair idNamePair : restaurant3) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public long getScenicDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(scenics)) {
			for (IdNamePair idNamePair : scenics) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public long getHotelDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(hotels)) {
			for (IdNamePair idNamePair : hotels) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public RouteItemDO getRouteItemRestaurantDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.restaurantDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DETAIL.getType());
			routeItemDO.setRouteItemDetail(this.restaurantDetail);
		}
		return routeItemDO;
	}

	public void setRestaurantDetail(RouteItemDetail restaurantDetail) {
		this.restaurantDetail = restaurantDetail;
	}

	public RouteItemDO getRouteItemScenicDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.scenicDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.scenicDetailRouteItemId);
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DETAIL.getType());
			routeItemDO.setRouteItemDetail(this.scenicDetail);
		}
		return routeItemDO;
	}

	public void setScenicDetail(RouteItemDetail scenicDetail) {
		this.scenicDetail = scenicDetail;
	}

	public RouteItemDO getRouteItemHotelDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.hotelDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setId(this.hotelDetailRouteItemId);
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DETAIL.getType());
			routeItemDO.setRouteItemDetail(this.hotelDetail);
		}
		return routeItemDO;
	}

	public void setHotelDetail(RouteItemDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}

	public long getTrafficRouteItemId() {
		return trafficRouteItemId;
	}

	public void setTrafficRouteItemId(long trafficRouteItemId) {
		this.trafficRouteItemId = trafficRouteItemId;
	}

	public long getDescriptionRouteItemId() {
		return descriptionRouteItemId;
	}

	public void setDescriptionRouteItemId(long descriptionRouteItemId) {
		this.descriptionRouteItemId = descriptionRouteItemId;
	}

	public long getBreakfastRouteItemId() {
		return breakfastRouteItemId;
	}

	public void setBreakfastRouteItemId(long breakfastRouteItemId) {
		this.breakfastRouteItemId = breakfastRouteItemId;
	}

	public long getLunchRouteItemId() {
		return lunchRouteItemId;
	}

	public void setLunchRouteItemId(long lunchRouteItemId) {
		this.lunchRouteItemId = lunchRouteItemId;
	}

	public long getDinnerRouteItemId() {
		return dinnerRouteItemId;
	}

	public void setDinnerRouteItemId(long dinnerRouteItemId) {
		this.dinnerRouteItemId = dinnerRouteItemId;
	}

	public long getScenicsRouteItemId() {
		return scenicsRouteItemId;
	}

	public void setScenicsRouteItemId(long scenicsRouteItemId) {
		this.scenicsRouteItemId = scenicsRouteItemId;
	}

	public long getHotelsRouteItemId() {
		return hotelsRouteItemId;
	}

	public void setHotelsRouteItemId(long hotelsRouteItemId) {
		this.hotelsRouteItemId = hotelsRouteItemId;
	}

	public long getRestaurantDetailRouteItemId() {
		return restaurantDetailRouteItemId;
	}

	public void setRestaurantDetailRouteItemId(long restaurantDetailRouteItemId) {
		this.restaurantDetailRouteItemId = restaurantDetailRouteItemId;
	}

	public long getScenicDetailRouteItemId() {
		return scenicDetailRouteItemId;
	}

	public void setScenicDetailRouteItemId(long scenicDetailRouteItemId) {
		this.scenicDetailRouteItemId = scenicDetailRouteItemId;
	}

	public long getHotelDetailRouteItemId() {
		return hotelDetailRouteItemId;
	}

	public void setHotelDetailRouteItemId(long hotelDetailRouteItemId) {
		this.hotelDetailRouteItemId = hotelDetailRouteItemId;
	}

	public RouteItemDetail getRestaurantDetail() {
		return restaurantDetail;
	}

	public RouteItemDetail getScenicDetail() {
		return scenicDetail;
	}

	public RouteItemDetail getHotelDetail() {
		return hotelDetail;
	}
}

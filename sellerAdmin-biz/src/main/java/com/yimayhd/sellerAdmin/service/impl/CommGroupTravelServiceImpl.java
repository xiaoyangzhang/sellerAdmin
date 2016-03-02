package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.model.travel.BaseTravel;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.GroupTravel;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.TripDay;
import com.yimayhd.sellerAdmin.repo.HotelRepo;
import com.yimayhd.sellerAdmin.repo.PictureRepo;
import com.yimayhd.sellerAdmin.repo.RestaurantRepo;
import com.yimayhd.sellerAdmin.repo.ScenicRepo;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class CommGroupTravelServiceImpl extends CommTravelServiceImpl {
	@Autowired
	private RestaurantRepo restaurantRepo;
	@Autowired
	private HotelRepo hotelRepo;
	@Autowired
	private ScenicRepo scenicRepo;
	@Autowired
	private PictureRepo pictureRepo;

	@Override
	public <T extends BaseTravel> void beforePublish(T travel) {
		GroupTravel gt = (GroupTravel) travel;
		// 详情
		for (TripDay tripDay : gt.getTripInfo()) {
			long rId = tripDay.getRestaurantDetailId();
			if (rId > 0) {
				RestaurantDO restaurant = restaurantRepo.getRestaurantById(rId);
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(restaurant.getId());
				detail.setType(RouteItemType.RESTAURANT.name());
				detail.setName(restaurant.getName());
				detail.setShortDesc(restaurant.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRepo.queryTopPictures(PictureOutType.RESTAURANT, rId,
						PICTURE_TOP_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setRestaurantDetail(detail);
			}
			long sId = tripDay.getScenicDetailId();
			if (sId > 0) {
				ScenicDO scenic = scenicRepo.getScenicById(sId);
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(scenic.getId());
				detail.setType(RouteItemType.SCENIC.name());
				detail.setName(scenic.getName());
				detail.setShortDesc(scenic.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRepo.queryTopPictures(PictureOutType.SCENIC, sId, PICTURE_TOP_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setScenicDetail(detail);
			}
			long hId = tripDay.getHotelDetailId();
			if (hId > 0) {
				HotelDO hotel = hotelRepo.getHotelById(hId);
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(hotel.getId());
				detail.setType(RouteItemType.HOTEL.name());
				detail.setName(hotel.getName());
				detail.setShortDesc(hotel.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRepo.queryTopPictures(PictureOutType.HOTEL, hId, PICTURE_TOP_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setHotelDetail(detail);
			}
		}
	}
}

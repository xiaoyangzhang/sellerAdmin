package com.yimayhd.sellerAdmin.model.travel.flightHotelTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.ic.client.model.domain.item.HotelShortItem;
import com.yimayhd.ic.client.model.domain.share_json.FlightDetail;
import com.yimayhd.ic.client.model.domain.share_json.FlightInfo;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.model.travel.BaseTravel;

/**
 * 自由行
 * 
 * @author yebin
 *
 */
public class FlightHotelTravel extends BaseTravel {

	private TripPackageInfo tripPackageInfo;// 套餐信息

	@Override
	protected void parseTripInfo(LineResult lineResult) {
		this.tripPackageInfo = new TripPackageInfo(lineResult);
	}

	public TripPackageInfo getTripPackageInfo() {
		return tripPackageInfo;
	}

	public void setTripPackageInfo(TripPackageInfo tripPackageInfo) {
		this.tripPackageInfo = tripPackageInfo;
	}

	@Override
	public void setRouteInfo(LinePublishDTO dto) {
		dto.getLineDO().setFlightDetail(null);
		dto.getLineDO().setFlights(null);
		if (this.tripPackageInfo.getHasFlight() != 1) {
			FlightInfo flightInfo = this.tripPackageInfo.getFlightInfo();
			if (flightInfo != null) {
				dto.getLineDO().setFlights(flightInfo);
			}
		} else {
			List<FlightDetail> flightDetails = this.tripPackageInfo.getFlightDetails();
			if (CollectionUtils.isNotEmpty(flightDetails)) {
				FlightDetail flightDetail = flightDetails.get(0);
				FlightInfo flightInfo = new FlightInfo();
				flightInfo.setForwardArriveDate(flightDetail.getForwardArriveDate());
				flightInfo.setForwardArriveCity(flightDetail.getForwardArriveCity());
				// flightInfo.setForwardArriveTime(flightDetail.getForwardArriveTime());
				flightInfo.setForwardDepartDate(flightDetail.getForwardDepartDate());
				flightInfo.setForwardDepartCity(flightDetail.getForwardDepartCity());
				// flightInfo.setForwardDepartTime(flightDetail.getForwardDepartTime());
				flightInfo.setReturnArriveDate(flightDetail.getReturnArriveDate());
				flightInfo.setReturnArriveCity(flightDetail.getReturnArriveCity());
				// flightInfo.setReturnArriveTime(flightDetail.getReturnArriveTime());
				flightInfo.setReturnDepartDate(flightDetail.getReturnDepartDate());
				flightInfo.setReturnDepartCity(flightDetail.getReturnDepartCity());
				// flightInfo.setReturnDepartTime(flightDetail.getReturnDepartTime());
				flightInfo.setMemo("");
				dto.getLineDO().setFlights(flightInfo);
			} else {
				flightDetails = new ArrayList<FlightDetail>();
			}
			dto.getLineDO().setFlightDetail(flightDetails);
		}
		List<HotelShortItem> hotels = this.tripPackageInfo.getHotels();
		if (CollectionUtils.isEmpty(hotels)) {
			hotels = new ArrayList<HotelShortItem>();
		}
		dto.getLineDO().setHotels(hotels);
	}

	@Override
	protected void modifyRouteInfo(LinePublishDTO dto, LineResult lineResult) {
		setRouteInfo(dto);
	}

	@Override
	protected int getItemType() {
		return ItemType.FLIGHT_HOTEL.getValue();
	}

}

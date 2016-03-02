package com.yimayhd.sellerAdmin.model.travel.flightHotelTravel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.HotelShortItem;
import com.yimayhd.ic.client.model.domain.share_json.FlightDetail;
import com.yimayhd.ic.client.model.domain.share_json.FlightInfo;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 行程套餐信息
 * 
 * @author yebin
 *
 */
public class TripPackageInfo {
	private int hasFlight;// 有无航班
	private FlightInfo flightInfo;// 航班信息
	private List<FlightDetail> flightDetails;// 航班列表
	private List<HotelShortItem> hotels;// 酒店

	public TripPackageInfo() {
	}

	public TripPackageInfo(LineResult lineResult) {
		LineDO line = lineResult.getLineDO();
		// 有无航班
		this.hasFlight = CollectionUtils.isNotEmpty(line.getFlightDetail()) ? 1 : 0;
		// 航班信息
		this.flightInfo = line.getFlights();
		// 航班列表
		this.flightDetails = line.getFlightDetail();
		// 酒店
		this.hotels = line.getHotels();
	}

	public int getHasFlight() {
		return hasFlight;
	}

	public void setHasFlight(int hasFlight) {
		this.hasFlight = hasFlight;
	}

	public List<HotelShortItem> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelShortItem> hotels) {
		this.hotels = hotels;
	}

	public FlightInfo getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(FlightInfo flightInfo) {
		this.flightInfo = flightInfo;
	}

	public List<FlightDetail> getFlightDetails() {
		return flightDetails;
	}

	public void setFlightDetails(List<FlightDetail> flightDetails) {
		this.flightDetails = flightDetails;
	}

}

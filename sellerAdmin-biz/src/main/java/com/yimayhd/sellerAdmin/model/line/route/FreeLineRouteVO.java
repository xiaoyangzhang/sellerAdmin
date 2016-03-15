package com.yimayhd.sellerAdmin.model.line.route;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自由行行程
 * 
 * @author yebin
 *
 */
public class FreeLineRouteVO implements Serializable {
	private static final long serialVersionUID = -8507331081900225417L;
	private TrafficVO trafficVO; //交通信息
	private List<HotelAndScenic> hotelAndScenicList; //景酒和景区信息

	public String getJsonStr(){
		return JSON.toJSONString(this);
	}

	public TrafficVO getTrafficVO() {
		return trafficVO;
	}

	public void setTrafficVO(TrafficVO trafficVO) {
		this.trafficVO = trafficVO;
	}

	public List<HotelAndScenic> getHotelAndScenicList() {
		return hotelAndScenicList;
	}

	public void setHotelAndScenicList(List<HotelAndScenic> hotelAndScenicList) {
		this.hotelAndScenicList = hotelAndScenicList;
	}

	public static void main(String[] args) {
		FreeLineRouteVO freeLineRouteVO = new FreeLineRouteVO();
		TrafficVO trafficVO = new TrafficVO();
		trafficVO.setGoType(1);
		trafficVO.setGoDescription("海南飞北京");
		trafficVO.setBackType(null);
		//trafficVO.setBackDescription("没有返程");
		freeLineRouteVO.setTrafficVO(trafficVO);
		List<HotelAndScenic> hotelAndScenicList = new ArrayList<HotelAndScenic>();
		HotelAndScenic hotel = new HotelAndScenic();
		hotel.setType(1);
		hotel.setDescription("入住五星级酒店");
		hotelAndScenicList.add(hotel);
		HotelAndScenic scenic = new HotelAndScenic();
		scenic.setType(2);
		scenic.setDescription("游览六星级景区");
		hotelAndScenicList.add(scenic);
		freeLineRouteVO.setHotelAndScenicList(hotelAndScenicList);

		String jsonStr = JSON.toJSONString(freeLineRouteVO);
		System.out.println(jsonStr);
		FreeLineRouteVO freeLineRouteVO1 = JSON.parseObject(jsonStr,FreeLineRouteVO.class);
		System.out.println("end");
	}
}

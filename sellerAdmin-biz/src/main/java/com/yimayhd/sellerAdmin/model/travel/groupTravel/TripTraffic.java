package com.yimayhd.sellerAdmin.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;
import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 交通方式
 * 
 * @author yebin
 *
 */
public class TripTraffic {
	private static final Map<Integer, String> WAYS = new TreeMap<Integer, String>();

	static {
		WAYS.put(RouteItemType.PLANE.getType(), RouteItemType.PLANE.getDesc());
		WAYS.put(RouteItemType.TRAIN.getType(), RouteItemType.TRAIN.getDesc());
		WAYS.put(RouteItemType.BUS.getType(), RouteItemType.BUS.getDesc());
		WAYS.put(RouteItemType.BOAT.getType(), RouteItemType.BOAT.getDesc());
	}

	private String from;
	private String to;
	private int way;

	public TripTraffic() {
	}

	public TripTraffic(RouteTrafficInfo trafficInfo) {
		this.from = trafficInfo.getStartCity();
		this.to = trafficInfo.getDestCity();
		this.way = RouteItemType.getByName(trafficInfo.getType()).getType();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	/**
	 * 获取交通方式的名称
	 * 
	 * @return
	 */
	public String wayName() {
		return WAYS.get(way);
	}

	/**
	 * 得到全部的交通方式
	 * 
	 * @return
	 */
	public static List<Entry<Integer, String>> ways() {
		return new ArrayList<Entry<Integer, String>>(WAYS.entrySet());
	}

	/**
	 * 生成 toRouteTrafficInfo
	 * 
	 * @return
	 */
	public RouteTrafficInfo toRouteTrafficInfo() {
		RouteTrafficInfo routeTrafficInfo = new RouteTrafficInfo();
		routeTrafficInfo.setStartCity(from);
		routeTrafficInfo.setDestCity(to);
		routeTrafficInfo.setType(RouteItemType.getByType(this.way).name());
		return routeTrafficInfo;
	}
}

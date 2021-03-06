package com.yimayhd.sellerAdmin.model.line.tour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
	private static final Map<String, String> WAYS = new TreeMap<String, String>();

	static {
		WAYS.put(RouteItemType.PLANE.name(), RouteItemType.PLANE.getDesc());
		WAYS.put(RouteItemType.TRAIN.name(), RouteItemType.TRAIN.getDesc());
		WAYS.put(RouteItemType.BUS.name(), RouteItemType.BUS.getDesc());
		WAYS.put(RouteItemType.BOAT.name(), RouteItemType.BOAT.getDesc());
		WAYS.put(RouteItemType.OTHERS.name(), RouteItemType.OTHERS.getDesc());
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
	public static List<Entry<String, String>> ways() {
		ArrayList<Entry<String, String>> arrayList = new ArrayList<Entry<String, String>>();
		Iterator<Entry<String, String>> iterator = WAYS.entrySet().iterator();
		Entry<String, String> temp=null;
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			if (!next.getKey().equals(RouteItemType.OTHERS.name())) {
				arrayList.add(next);
			}else {
				temp=next;
			}
		}
		arrayList.add(temp);
		return arrayList;
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

package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.tradecenter.client.model.enums.OrderBizType;

/**
 * 九休商品类型（订单查询）
 * 
 *
 */
public enum OrderSearchType {
	TOUR_LINE(OrderBizType.TOUR_LINE,"国内跟团游"),
	FREE_LINE(OrderBizType.FREE_LINE,"国内自由行"),
	TOUR_LINE_ABOARD(OrderBizType.TOUR_LINE_ABOARD),
	FREE_LINE_ABOARD(OrderBizType.FREE_LINE_ABOARD,"境外自由行"),
	CITY_ACTIVITY(OrderBizType.CITY_ACTIVITY,"国内当地玩乐"),
	SPOTS(OrderBizType.SPOTS,"国内景点门票"),
	HOTEL(OrderBizType.HOTEL,"酒店客栈"),
//	POINT_MALL(OrderBizType.POINT_MALL, "积分商品"),
	MUST_BUY(OrderBizType.NORMAL, "特产商品");
	private String		text;
	private OrderBizType	orderBizType;

	private OrderSearchType(OrderBizType orderBizType) {
		this.orderBizType = orderBizType;
		this.text = orderBizType.getDes();
	}

	private OrderSearchType(OrderBizType orderBizType, String text) {
		this.orderBizType = orderBizType;
		this.text = text;
	}

	public int getValue() {
		return orderBizType.getBizType();
	}

	public String getText() {
		return text;
	}

	public boolean isEqual(int value) {
		return getValue() == value;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static OrderSearchType get(int enumValue) {
		for (OrderSearchType em : OrderSearchType.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static OrderSearchType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (OrderSearchType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}

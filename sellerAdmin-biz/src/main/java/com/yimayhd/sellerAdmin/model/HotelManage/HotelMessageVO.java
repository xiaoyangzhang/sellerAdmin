package com.yimayhd.sellerAdmin.model.HotelManage;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 酒店信息
 * @author wangdi
 */
public class HotelMessageVO  implements Serializable {


	private static final long serialVersionUID = -8634777288365225852L;

	private long sellerId;// 商户Id
	private long hotelId;//酒店ID
	private String name;//酒店名称
	private String area;//酒店区域
	private String phone;//酒店电话

	private String locationText;//地址

	private long locationProvinceId;

	private String locationProvinceName;

	private long locationCityId;

	private String locationCityName;

	private long locationTownId;

	private String locationTownName;

	private Long startBookTimeLimit;// 提前预定天数

	private String title; // '标题',
	private String code; // '商品代码',
	private int payType  ; // COMMENT '可支持支付方式',

	private long itemId;// 商品Id

	private long outType;//商品类型:1.酒店.2客栈
	private String outTypeStr;//商品类型:1.酒店.2客栈
	private int cancelLimit;// 退订限制:1,可退,2不可退,3免费退
	private String cancelLimitStr;// 退订限制:1,可退,2不可退,3免费退
	private String description;// 退订规则
	private List<String> latestArriveTime;//最晚到店时间 中台
	private String storeLastTime;// 最晚到店时间

	private int breakfast;//早餐
	private String breakfastStr;
	//{"supplier_calendar":{ "seller_id":"2088102122524333","biz_list":[{"stock_num":10,"price":"8.8","vTxt":"2088101117955611"}]}}
	private String supplierCalendar;//价格日历json 信息,
	private long roomId;//房型ID

	private RoomMessageVO roomMessageVO;//房型
	private List<RoomMessageVO> listRoomMessageVO;//房型信息

	private String currentState;//当前操作状态,update,add,del

	private long categoryId;

	public Integer page =1;
	protected Integer pageSize =8 ;

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocationText() {
		return locationText;
	}

	public void setLocationText(String locationText) {
		this.locationText = locationText;
	}

	public long getLocationProvinceId() {
		return locationProvinceId;
	}

	public void setLocationProvinceId(long locationProvinceId) {
		this.locationProvinceId = locationProvinceId;
	}

	public String getLocationProvinceName() {
		return locationProvinceName;
	}

	public void setLocationProvinceName(String locationProvinceName) {
		this.locationProvinceName = locationProvinceName;
	}

	public long getLocationCityId() {
		return locationCityId;
	}

	public void setLocationCityId(long locationCityId) {
		this.locationCityId = locationCityId;
	}

	public String getLocationCityName() {
		return locationCityName;
	}

	public void setLocationCityName(String locationCityName) {
		this.locationCityName = locationCityName;
	}

	public long getLocationTownId() {
		return locationTownId;
	}

	public void setLocationTownId(long locationTownId) {
		this.locationTownId = locationTownId;
	}

	public String getLocationTownName() {
		return locationTownName;
	}

	public void setLocationTownName(String locationTownName) {
		this.locationTownName = locationTownName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}


	public long getOutType() {
		return outType;
	}

	public void setOutType(long outType) {
		this.outType = outType;
	}

	public String getOutTypeStr() {
		return outTypeStr;
	}

	public void setOutTypeStr(String outTypeStr) {
		this.outTypeStr = outTypeStr;
	}

	public int getCancelLimit() {
		return cancelLimit;
	}

	public void setCancelLimit(int cancelLimit) {
		this.cancelLimit = cancelLimit;
	}

	public String getCancelLimitStr() {
		return cancelLimitStr;
	}

	public void setCancelLimitStr(String cancelLimitStr) {
		this.cancelLimitStr = cancelLimitStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getStoreLastTime() {
		return storeLastTime;
	}


	public int getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}

	public String getSupplierCalendar() {
		return supplierCalendar;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public RoomMessageVO getRoomMessageVO() {
		return roomMessageVO;
	}

	public void setRoomMessageVO(RoomMessageVO roomMessageVO) {
		this.roomMessageVO = roomMessageVO;
	}

	public List<RoomMessageVO> getListRoomMessageVO() {
		return listRoomMessageVO;
	}

	public void setListRoomMessageVO(List<RoomMessageVO> listRoomMessageVO) {
		this.listRoomMessageVO = listRoomMessageVO;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setStoreLastTime(String storeLastTime) {
		this.storeLastTime = storeLastTime;
		if(!StringUtils.isBlank(storeLastTime)){
			List<String> latestCheckin = new ArrayList<String>();
			if(storeLastTime.indexOf(",")>0){
				/**最晚到店时间不为空**/
				String [] timeArr = storeLastTime.split(",");
				for(int i=0;i<timeArr.length;i++){
					latestCheckin.add(timeArr[i]+":00");
				}
			}else{
				latestCheckin.add(storeLastTime+":00");
			}
			this.setLatestArriveTime(latestCheckin);

		}

	}


	public void setSupplierCalendar(String supplierCalendar) {
		this.supplierCalendar = supplierCalendar;
		if(StringUtils.isNotBlank(supplierCalendar)){

		}
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getBreakfastStr() {
		return breakfastStr;
	}

	public void setBreakfastStr(String breakfastStr) {
		this.breakfastStr = breakfastStr;
	}

	public Long getStartBookTimeLimit() {
		return startBookTimeLimit;
	}

	public void setStartBookTimeLimit(Long startBookTimeLimit) {
		this.startBookTimeLimit = startBookTimeLimit;
	}

	public List<String> getLatestArriveTime() {
		return latestArriveTime;
	}

	public void setLatestArriveTime(List<String> latestArriveTime) {
		this.latestArriveTime = latestArriveTime;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

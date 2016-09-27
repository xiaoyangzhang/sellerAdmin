package com.yimayhd.sellerAdmin;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.enums.MerchantNameType;
import com.yimayhd.sellerAdmin.model.order.OrderSonModel;

import java.math.BigDecimal;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		OrderSonModel[] orderSonModel = new OrderSonModel[3];
		/*int i=0;
		for(int j=0;j<3;j++){
			OrderSonModel o = new OrderSonModel();
			o.setSonOrderId(Long.valueOf(j));
			orderSonModel[i++] = o;
		}*/
		//System.out.println(i);
		System.out.println(orderSonModel.length);
		System.out.println(JSON.toJSONString(orderSonModel));



		Map<Integer,String> merchantNameTypeMap = new TreeMap<Integer,String>(new Comparator<Integer>() {
			public int compare(Integer obj1, Integer obj2) {
				// 降序排序
				return obj2.compareTo(obj1);
			}
		});
		merchantNameTypeMap.put(MerchantNameType.ALL_USER.getType(),MerchantNameType.ALL_USER.getScheme());
		merchantNameTypeMap.put(MerchantNameType.TOUR_COR.getType(),MerchantNameType.TOUR_COR.getScheme());
		merchantNameTypeMap.put(MerchantNameType.HOTEL.getType(),MerchantNameType.HOTEL.getScheme());
		merchantNameTypeMap.put(MerchantNameType.SCENIC.getType(),MerchantNameType.SCENIC.getScheme());
		merchantNameTypeMap.put(MerchantNameType.CITY_COR.getType(),MerchantNameType.CITY_COR.getScheme());

		Set<Integer> keySet = merchantNameTypeMap.keySet();
		Iterator<Integer> iter = keySet.iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			System.out.println(key + ":" + merchantNameTypeMap.get(key));
		}

	}




}

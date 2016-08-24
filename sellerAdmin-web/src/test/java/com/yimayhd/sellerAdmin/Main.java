package com.yimayhd.sellerAdmin;

import com.alibaba.fastjson.JSON;
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
	}

}

package com.yimayhd.sellerAdmin.item;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.entity.PictureTextItem;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.entity.ServiceArea;
import org.yimayhd.sellerAdmin.query.ItemQueryParam;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.BaseTest;

public class PulishItemTest extends BaseTest {

	@Autowired
	private PublishItemApi publishItemApi;
	
	@Test
	public void testPublishItem() {
//		publishItem();
//		testWhiteList();
//		testGetPublishItem();
		testGetItemManagementList();
	}

	private void publishItem() {
		PublishServiceDO publishServiceDO = new PublishServiceDO();
		publishServiceDO.avater = "11111111111";
		publishServiceDO.bookingTip = "bookingTip";
		publishServiceDO.categoryType = 241;
		publishServiceDO.discountPrice = 1000;
		publishServiceDO.discountTime = 10;
		publishServiceDO.feeDesc = "feeDesc";
		publishServiceDO.oldPrice = 1200;
		publishServiceDO.oldTime = 10;
		publishServiceDO.refundRule = "refundRule";
		publishServiceDO.serviceState = 2;
		publishServiceDO.title = "title";
		List<PictureTextItem> pictureTextItems = new ArrayList<>();
		PictureTextItem pictureTextItem = new PictureTextItem();
		pictureTextItem.type =  "img";
		pictureTextItem.value = "jfosifjosdafjiosd";
		pictureTextItems.add(pictureTextItem);
		List<ServiceArea> serviceAreas = new ArrayList<>();
		ServiceArea serviceArea = new ServiceArea();
		serviceArea.areaCode = 1000410;
		serviceArea.areaName = "北京";
		serviceArea.domain = 1200;
		serviceArea.outId = 21220;
		serviceArea.outType = 12;
		serviceAreas.add(serviceArea);
		publishServiceDO.pictureTextItems = pictureTextItems;
		publishServiceDO.serviceAreas = serviceAreas;
		boolean publishService = publishItemApi.publishService(0, 1200, 0, 21220, 0, publishServiceDO);
		System.out.println(publishService);
	}
	
	private void testWhiteList() {
		boolean checkWhiteList = publishItemApi.checkWhiteList(0, 1200, 0, 21219, 0);
		System.out.println(checkWhiteList);
	}
	
	private void testGetPublishItem() {
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.id = 110801;
		itemQueryParam.categoryId = 241;
		PublishServiceDO publishItemInfo = publishItemApi.getPublishItemInfo(0, 1200, 0, 21220, 0, itemQueryParam);
		System.out.println("======================"+JSON.toJSONString(publishItemInfo));
	}
	
	private void testGetItemManagementList() {
//		ItemListPage itemListPage = new ItemListPage();
//		itemListPage.pageNo = 1;
//		itemListPage.pageSize = 10;
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.categoryId = 241;
		itemQueryParam.pageNo = 1;
		itemQueryParam.pageSize = 10;
		itemQueryParam.serviceState = 2;
		ItemApiResult goodsManagementInfo = publishItemApi.getItemList(0, 1200, 0, 21220, 0, itemQueryParam);
		System.out.println("------------------"+JSON.toJSONString(goodsManagementInfo));
		
	}
}

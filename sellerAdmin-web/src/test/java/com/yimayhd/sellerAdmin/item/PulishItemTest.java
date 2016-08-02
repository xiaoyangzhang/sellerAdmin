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
import com.yimayhd.commentcenter.client.enums.FeatureType;
import com.yimayhd.sellerAdmin.BaseTest;

public class PulishItemTest extends BaseTest {

	@Autowired
	private PublishItemApi publishItemApi;
	
	@Test
	public void testPublishItem() {
		publishItem();
//		testWhiteList();
//		testGetPublishItem();
//		testGetItemManagementList();
//		getItemDetail();
	}

	private void publishItem() {
		PublishServiceDO publishServiceDO = new PublishServiceDO();
		//publishServiceDO.id = 110801;
		publishServiceDO.avater = "2222222222222222222222";
		publishServiceDO.bookingTip = "bookingTip1";
		publishServiceDO.categoryType = 241;
		publishServiceDO.discountPrice = 1000;
		publishServiceDO.discountTime = 10;
		publishServiceDO.feeDesc = "feeDesc1";
		publishServiceDO.oldPrice = 1200;
		publishServiceDO.oldTime = 10;
		publishServiceDO.refundRule = "refundRule1";
		publishServiceDO.serviceState = 3;
		publishServiceDO.title = "2222222222222222222222";
		List<PictureTextItem> pictureTextItems = new ArrayList<>();
		PictureTextItem pictureTextItem = new PictureTextItem();
		pictureTextItem.type =  "IMAGE";
		pictureTextItem.value = "jfosifjosdafjiosd";
		PictureTextItem pictureTextItem2 = new PictureTextItem();
		pictureTextItem.type =  "COMENT";
		pictureTextItem.value = "111111111111111";
		pictureTextItems.add(pictureTextItem);
		pictureTextItems.add(pictureTextItem2);
		List<ServiceArea> serviceAreas = new ArrayList<>();
		ServiceArea serviceArea = new ServiceArea();
		serviceArea.areaCode = 532600;
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
	
	private void getItemDetail() {
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.id = 110801;
		itemQueryParam.categoryId = 241;
		 ItemApiResult itemDetailInfo = publishItemApi.getItemDetailInfo(0, 1200, 0, 21220, 0, itemQueryParam);
		System.out.println("======================"+JSON.toJSONString(itemDetailInfo));
	}
	
	@Test
	public void testPicText() {
		System.out.println(FeatureType.getByType(1).name());
	}
}

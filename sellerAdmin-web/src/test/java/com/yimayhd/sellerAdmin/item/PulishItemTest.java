package com.yimayhd.sellerAdmin.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bouncycastle.crypto.tls.HashAlgorithm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.api.PublishItemApi;
import org.yimayhd.sellerAdmin.entity.ConsultCategoryInfo;
import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.entity.ItemProperty;
import org.yimayhd.sellerAdmin.entity.PictureTextItem;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.entity.ServiceArea;
import org.yimayhd.sellerAdmin.query.ItemQueryParam;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.enums.FeatureType;
import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;

public class PulishItemTest extends BaseTest {

	@Autowired
	private PublishItemApi publishItemApi;
	
	@Test
	public void testPublishItem() {
//		publishItem();
//		testWhiteList();
//		testGetPublishItem();
//		testGetItemManagementList();
//		getItemDetail();
//		testUpdateState();
		testItemProperties();
	}

	private void publishItem() {
		PublishServiceDO publishServiceDO = new PublishServiceDO();
		//publishServiceDO.id = 110801;
		publishServiceDO.avater = "44444444444444444444";
		//publishServiceDO.bookingTip = "bookingTip1";
		publishServiceDO.categoryType = 241;
		publishServiceDO.discountPrice = 1000;
		publishServiceDO.discountTime = 999999999;
		//publishServiceDO.feeDesc = "feeDesc1";
		publishServiceDO.oldPrice = 1200;
		publishServiceDO.oldTime = 999999999;
		//publishServiceDO.refundRule = "refundRule1";
		publishServiceDO.serviceState = 2;
		publishServiceDO.title = "55555555555555555555555";
		List<ItemProperty> itemProperties = new ArrayList<ItemProperty>();
		ItemProperty itemProperty1 = new ItemProperty();
		itemProperty1.id = 71;
		itemProperty1.text = "费用描述";
		itemProperty1.type = "1";
		itemProperty1.value = "费用描述";
		itemProperties.add(itemProperty1);
		ItemProperty itemProperty2 = new ItemProperty();
		itemProperty2.id = 72;
		itemProperty2.text = "预定说明";
		itemProperty2.type = "1";
		itemProperty2.value = "预定说明";
		itemProperties.add(itemProperty2);
		ItemProperty itemProperty3 = new ItemProperty();
		itemProperty3.id = 73;
		itemProperty3.text = "退改规定";
		itemProperty3.type = "1";
		itemProperty3.value = "退改规定";
		itemProperties.add(itemProperty3);
		publishServiceDO.itemProperties = itemProperties;
		List<PictureTextItem> pictureTextItems = new ArrayList<>();
		PictureTextItem pictureTextItem = new PictureTextItem();
		pictureTextItem.type =  "IMAGE";
		pictureTextItem.value = "11111111";
		PictureTextItem pictureTextItem3 = new PictureTextItem();
		pictureTextItem3.type =  "IMAGE";
		pictureTextItem3.value = "3333333";
		PictureTextItem pictureTextItem4 = new PictureTextItem();
		pictureTextItem4.type =  "IMAGE";
		pictureTextItem4.value = "444444";
		PictureTextItem pictureTextItem5 = new PictureTextItem();
		pictureTextItem5.type =  "IMAGE";
		pictureTextItem5.value = "555555";
		PictureTextItem pictureTextItem2 = new PictureTextItem();
		pictureTextItem2.type =  "COMENT";
		pictureTextItem2.value = "2222222";
		pictureTextItems.add(pictureTextItem);
		pictureTextItems.add(pictureTextItem2);
		pictureTextItems.add(pictureTextItem3);
		pictureTextItems.add(pictureTextItem4);
		pictureTextItems.add(pictureTextItem5);
		List<ServiceArea> serviceAreas = new ArrayList<>();
		ServiceArea serviceArea = new ServiceArea();
		serviceArea.areaCode = 532500;
		serviceArea.domain = 1200;
//		serviceArea.outId = 1309500;
//		serviceArea.outType = 12;
		serviceAreas.add(serviceArea);
		publishServiceDO.serviceAreas = serviceAreas;
		publishServiceDO.pictureTextItems = pictureTextItems;
		//publishServiceDO.serviceAreas = serviceAreas;
		boolean publishService = publishItemApi.publishService(0, 1200, 0, 1309500, 0, publishServiceDO);
		System.out.println(publishService);
	}
	
	private void testWhiteList() {
		boolean checkWhiteList = publishItemApi.checkWhiteList(0, 1200, 0, 21219, 0);
		System.out.println(checkWhiteList);
	}
	
	private void testGetPublishItem() {
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.id = 112422;
		itemQueryParam.categoryId = 241;
		PublishServiceDO publishItemInfo = publishItemApi.getPublishItemInfo(0, 1200, 0, 1309500, 0, itemQueryParam);
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
		ItemApiResult goodsManagementInfo = publishItemApi.getItemList(0, 1200, 0, 1309500, 0, itemQueryParam);
		System.out.println("------------------"+JSON.toJSONString(goodsManagementInfo));
		
	}
	
	private void getItemDetail() {
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.id = 110801;
		itemQueryParam.categoryId = 241;
		 ItemApiResult itemDetailInfo = publishItemApi.getItemDetailInfo(0, 1200, 0, 1303209, 0, itemQueryParam);
		System.out.println("======================"+JSON.toJSONString(itemDetailInfo));
	}
	
	@Test
	public void testPicText() {
//		System.out.println(FeatureType.getByType(1).name());
		//testItemProperties();
		testConsultCategory();
	}
	
	private void testUpdateState() {
		ItemQueryParam itemQueryParam = new ItemQueryParam();
		itemQueryParam.id = 111540;
		itemQueryParam.categoryId = 241;
		itemQueryParam.state = 2;
		//itemQueryParam.
		boolean updateState = publishItemApi.updateState(0, 1200, 0, 1309500, 0, itemQueryParam);
	}
	
	private void testItemProperties() {
		ConsultCategoryInfo consultItemProperties = publishItemApi.getConsultItemProperties(0, 1200, 0, 0, 0);
		System.out.println("----------------------------"+JSON.toJSONString(consultItemProperties));
	}
	
	private void testConsultCategory() {
		System.out.println("--------------------------------"+WebResourceConfigUtil.getRootPath());
		System.out.println("--------------------------------"+WebResourceConfigUtil.getConsultCategory());
		System.out.println("--------------------------------"+Integer.parseInt(WebResourceConfigUtil.getConsultCategory()));
	}
}

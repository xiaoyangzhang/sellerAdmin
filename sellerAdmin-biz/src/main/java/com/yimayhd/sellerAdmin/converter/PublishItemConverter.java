package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import org.yimayhd.sellerAdmin.entity.PublishServiceDO;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ValueType;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;

/**
 * 
* @ClassName: PublishServiceConverter
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年7月20日 下午5:11:29
*
 */
public class PublishItemConverter {

	public static ConsultPublishAddDTO converterLocal2AddPublishConsult(PublishServiceDO publishServiceDO,long sellerId) {
		ConsultPublishAddDTO dto = new ConsultPublishAddDTO();
		ItemDO itemDO = new ItemDO();
		//服务咨询类
		itemDO.setCategoryId(Constant.CONSULT_SERVICE);
		itemDO.setTitle(publishServiceDO.title);
		if (publishServiceDO.serviceState == Constant.PUBLISHED) {
			
			itemDO.setStatus(ItemStatus.valid.getValue());
		}else if (publishServiceDO.serviceState == Constant.TO_BE_PUBLISHED) {
			itemDO.setStatus(ItemStatus.invalid.getValue());
			
		}
		itemDO.setSellerId(sellerId);
		itemDO.setDomain(Constant.DOMAIN_JIUXIU);
		itemDO.addPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS, publishServiceDO.avater);
		itemDO.setPrice(publishServiceDO.discountPrice);
		itemDO.setOriginalPrice(publishServiceDO.oldPrice);
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.CONSULT_TIME, (publishServiceDO.oldTime)*60);
		itemDO.setItemFeature(itemFeature);
		List<ItemSkuPVPair> itemSkuPVPairs = createItemSkuPVPair(publishServiceDO);
		itemDO.setItemPropertyList(itemSkuPVPairs);
		itemDO.setOrderNum(0);
		dto.setItemDO(itemDO);
		return dto;
	}
	public static ConsultPublishUpdateDTO converterLocal2UpdatePublishConsult(PublishServiceDO publishServiceDO,long sellerId) {
		ConsultPublishUpdateDTO dto = new ConsultPublishUpdateDTO();
		ItemPubUpdateDTO itemDTO = new ItemPubUpdateDTO();
		itemDTO.setId(publishServiceDO.id);
		//服务咨询类
		
		itemDTO.setTitle(publishServiceDO.title);
		itemDTO.addPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS, publishServiceDO.avater);
		itemDTO.setPrice((long)(publishServiceDO.discountPrice));
		itemDTO.setOriginalPrice((long)(publishServiceDO.oldPrice));
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.CONSULT_TIME, (publishServiceDO.oldTime)*60);
		itemDTO.setItemFeature(itemFeature);
		List<ItemSkuPVPair> itemSkuPVPairs = createItemSkuPVPair(publishServiceDO);
		itemDTO.setItemPropertyList(itemSkuPVPairs);
		dto.setItemDTO(itemDTO);
		
		return dto;
	}
	private static List<ItemSkuPVPair> createItemSkuPVPair(
			PublishServiceDO publishServiceDO) {
		List<ItemSkuPVPair> itemSkuPVPairs = new ArrayList<ItemSkuPVPair>(); 
		ItemSkuPVPair skuPVPair = new ItemSkuPVPair();
		skuPVPair.setPId(61);
		skuPVPair.setPTxt("费用包含");
		skuPVPair.setPType(1);
		skuPVPair.setVType(ValueType.DEFAULT.getType());
		skuPVPair.setVTxt(publishServiceDO.feeDesc);
		skuPVPair.setVId(-1);
		itemSkuPVPairs.add(skuPVPair);
		ItemSkuPVPair skuPVPair2 = new ItemSkuPVPair();
		skuPVPair2.setPId(57);
		skuPVPair2.setPTxt("预定时间");
		skuPVPair2.setPType(1);
		skuPVPair2.setVTxt(publishServiceDO.bookingTip);
		skuPVPair2.setVType(ValueType.DEFAULT.getType());
		skuPVPair2.setVId(-2);
		itemSkuPVPairs.add(skuPVPair2);
		ItemSkuPVPair skuPVPair3 = new ItemSkuPVPair();
		skuPVPair3.setPId(62);
		skuPVPair3.setPTxt("退票说明");
		skuPVPair3.setPType(1);
		skuPVPair3.setVType(ValueType.DEFAULT.getType());
		skuPVPair3.setVId(-3);
		skuPVPair3.setVTxt(publishServiceDO.refundRule);
		itemSkuPVPairs.add(skuPVPair3);
		return itemSkuPVPairs;
	}
	
	public static ItemPublishDTO converterLocal2ItemPublishDTO(ItemQueryDTO dto) {
		ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
		itemPublishDTO.setItemId(dto.getItemId());
		itemPublishDTO.setSellerId(dto.getSellerId());
		return itemPublishDTO;
	}
	
	public static ItemQryDTO converterLocal2ItemQueryDTO(ItemCategoryQuery query) {
		ItemQryDTO dto = new ItemQryDTO();
		dto.setPageNo(query.getItemQueryParam().pageNo);
		dto.setPageSize(query.getItemQueryParam().pageSize);
		dto.setSellerId(query.getSellerId());
		List<Integer> domains = new ArrayList<Integer>();
		domains.add(query.getDomainId());
		dto.setDomains(domains);
		List<Integer> statuses = new ArrayList<Integer>();
		statuses.add(query.getItemQueryParam().serviceState);
		dto.setStatus(statuses);
		dto.setCategoryId(query.getCategoryId());
		return dto;
	}
}

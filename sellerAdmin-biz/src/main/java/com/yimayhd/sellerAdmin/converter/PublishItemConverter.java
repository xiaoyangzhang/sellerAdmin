package com.yimayhd.sellerAdmin.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;

import com.sun.tools.classfile.Annotation.element_value;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;
import com.yimayhd.user.session.manager.SessionManager;

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
		itemDO.setCategoryId(241);
		itemDO.setTitle(publishServiceDO.title);
		if (publishServiceDO.serviceState == 1) {
			
			itemDO.setStatus(ItemStatus.valid.getValue());
		}else if (publishServiceDO.serviceState == 2) {
			itemDO.setStatus(ItemStatus.invalid.getValue());
			
		}
		itemDO.setSellerId(sellerId);
		itemDO.setDomain(Constant.DOMAIN_JIUXIU);
		itemDO.setPicUrlsString(publishServiceDO.avater);
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.CONSULT_TIME, publishServiceDO.oldTime);
		itemDO.setItemFeature(itemFeature);
		return dto;
	}
	public static ConsultPublishUpdateDTO converterLocal2UpdatePublishConsult(PublishServiceDO publishServiceDO,long sellerId) {
		ConsultPublishUpdateDTO dto = new ConsultPublishUpdateDTO();
		ItemDO itemDO = new ItemDO();
		itemDO.setId(publishServiceDO.id);
		//服务咨询类
		itemDO.setCategoryId(241);
		itemDO.setTitle(publishServiceDO.title);
		if (publishServiceDO.serviceState == 1) {
			
			itemDO.setStatus(ItemStatus.valid.getValue());
		}else if (publishServiceDO.serviceState == 2) {
			itemDO.setStatus(ItemStatus.invalid.getValue());
			
		}
		itemDO.setSellerId(sellerId);
		itemDO.setDomain(Constant.DOMAIN_JIUXIU);
		itemDO.setPicUrlsString(publishServiceDO.avater);
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.CONSULT_TIME, publishServiceDO.oldTime);
		itemDO.setItemFeature(itemFeature);
		return dto;
	}
	
	public static ItemPublishDTO converterLocal2ItemPublishDTO(ItemQueryDTO dto) {
		ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
		itemPublishDTO.setItemId(dto.getItemId());
		itemPublishDTO.setSellerId(dto.getSellerId());
		return itemPublishDTO;
	}
}

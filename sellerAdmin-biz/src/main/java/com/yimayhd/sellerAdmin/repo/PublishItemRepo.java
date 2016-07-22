package com.yimayhd.sellerAdmin.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * 
* @ClassName: PublishItemRepo
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年7月20日 下午2:48:55
*
 */
public class PublishItemRepo {

	private static Logger log  = LoggerFactory.getLogger("PublishItemRepo");
	@Autowired
	private ItemPublishService itemPublishService;
	@Autowired
	private ItemQueryService itemQueryService;
	
	public ItemPubResult addItem(ConsultPublishAddDTO dto) {
		log.info("param:ConsultPublishAddDTO={}",JSON.toJSONString(dto));
		ItemPubResult addPublishConsult = itemPublishService.addPublishConsult(dto);
		if (addPublishConsult != null) {
			
			log.info("result:ItemPubResult={}",JSON.toJSONString(addPublishConsult));
		}
		return addPublishConsult;
		
	}
	
	public ItemPubResult updateItem(ConsultPublishUpdateDTO dto) {
		log.info("param:ConsultPublishUpdateDTO={}",JSON.toJSONString(dto));
		ItemPubResult updatePublishConsult = itemPublishService.updatePublishConsult(dto);
		if (updatePublishConsult != null) {
		log.info("result:ItemPubResult={}",JSON.toJSONString(updatePublishConsult));
		}
		return updatePublishConsult;
		
	}
	
	public ItemPageResult getItem(ItemQryDTO dto) {
		log.info("param:ItemQryDTO={}",JSON.toJSONString(dto));
		ItemPageResult item = itemQueryService.getItem(dto);
		if (item != null) {
		log.info("result:ItemPageResult={}",JSON.toJSONString(item));
		}
		return item;
	}
	
	public ItemPubResult publishItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemPubResult publishResult = itemPublishService.publish(dto);
		if (publishResult != null) {
		log.info("param:ItemPubResult={}",JSON.toJSONString(publishResult));
		}
		return publishResult;
	}
	public ItemCloseResult closeItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemCloseResult closeResult = itemPublishService.close(dto);
		if (closeResult != null) {
			log.info("param:ItemCloseResult={}",JSON.toJSONString(closeResult));
		}
		return closeResult;
	}
	public ItemDeleteResult deleteItem(ItemPublishDTO dto) {
		log.info("param:ItemPublishDTO={}",JSON.toJSONString(dto));
		
		ItemDeleteResult deleteResult = itemPublishService.delete(dto);
		if (deleteResult != null) {
			log.info("param:ItemDeleteResult={}",JSON.toJSONString(deleteResult));
		}
		return deleteResult;
	}
}

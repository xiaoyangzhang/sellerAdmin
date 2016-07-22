package com.yimayhd.sellerAdmin.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.converter.PublishServiceConverter;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;
import com.yimayhd.sellerAdmin.repo.PublishItemRepo;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
* @ClassName: PublishItemBiz
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年7月20日 下午3:05:45
*
 */
public class PublishItemBiz {

	private static Logger log = LoggerFactory.getLogger("PublishItemBiz");
	
	@Autowired
	private PublishItemRepo publishItemRepo;
	@Autowired
	private SessionManager sessionManager;
	
	public WebResult<Boolean> addItem(PublishServiceDO publishServiceDO) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			ConsultPublishAddDTO dto = PublishServiceConverter.converterLocal2AddPublishConsult(publishServiceDO, sessionManager.getUserId());
			ItemPubResult addItemResult = publishItemRepo.addItem(dto);
			if (addItemResult == null) {
				log.error("result:ItemPubResult={}",addItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!addItemResult.isSuccess() || addItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
			}
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	public WebResult<Boolean> updateItem(PublishServiceDO publishServiceDO) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			ConsultPublishUpdateDTO dto = PublishServiceConverter.converterLocal2UpdatePublishConsult(publishServiceDO, sessionManager.getUserId());
			ItemPubResult updateItemResult = publishItemRepo.updateItem(dto);
			if (updateItemResult == null) {
				log.error("result:ItemPubResult={}",updateItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!updateItemResult.isSuccess() || updateItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
			}
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public WebResult<ItemPageResult> getItemList() {
		//log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<ItemPageResult> result = new WebResult<ItemPageResult>();
		
		return result;
	}
	
//	public WebResult<Boolean> updateItemState(ItemQueryDTO dto) {
//		WebResult<Boolean> result = new WebResult<Boolean>();
//		if (condition) {
//			
//		}
//		return result;
//	}
}

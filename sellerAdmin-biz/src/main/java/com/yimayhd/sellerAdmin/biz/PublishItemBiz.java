package com.yimayhd.sellerAdmin.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.PictureTextItem;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;
import org.yimayhd.sellerAdmin.entity.ServiceArea;
import org.yimayhd.sellerAdmin.entity.TalentInfo;
import org.yimayhd.sellerAdmin.enums.ServiceState;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.domain.PicTextDO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoByOutIdDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.FeatureType;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ConsultPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ConsultPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.resourcecenter.model.enums.DestinationUseType;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.PictureTextConverter;
import com.yimayhd.sellerAdmin.converter.PublishItemConverter;
import com.yimayhd.sellerAdmin.model.enums.PictureTextItemType;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.model.query.ItemCategoryQuery;
import com.yimayhd.sellerAdmin.model.query.ItemQueryDTO;
import com.yimayhd.sellerAdmin.repo.PictureTextRepo;
import com.yimayhd.sellerAdmin.repo.PublishItemRepo;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserTalentDO;
import com.yimayhd.user.client.dto.TalentDTO;
import com.yimayhd.user.client.enums.UserOptions;
import com.yimayhd.user.client.service.TalentService;
import com.yimayhd.user.client.service.UserService;

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
	private ExamineDealService examineDealService;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private PictureTextRepo pictureTextRepo;
	@Autowired
	private ComCenterService comCenterServiceRef;
	@Autowired
	private DestinationService destinationServiceRef;
	@Autowired
	private TalentService talentServiceRef;
	@Autowired
	private ComTagCenterService comTagCenterServiceRef;
	public WebResult<Boolean> addItem(PublishServiceDO publishServiceDO,long sellerId) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			UserDO userDO = userServiceRef.getUserDOById(sellerId);
			long option = userDO.getOptions();
			boolean isTalentA = UserOptions.CERTIFICATED.has(option);
			boolean isTalentB = UserOptions.USER_TALENT.has(option);
			com.yimayhd.user.client.result.BaseResult<TalentDTO> queryTalentInfoResult = talentServiceRef.queryTalentInfo(sellerId);
			if ((!isTalentB && !isTalentA) || queryTalentInfoResult == null || queryTalentInfoResult.getValue() == null ) {
				UserTalentDO talentDO = new UserTalentDO();
				talentDO.setUserId(sellerId);
				com.yimayhd.user.client.result.BaseResult<Boolean> insertTalent = talentServiceRef.insertTalent(talentDO);
				if (insertTalent == null || !insertTalent.isSuccess()) {
					log.error("talentServiceRef.insertTalent,param:talentDO={},result:{}",JSON.toJSONString(talentDO),JSON.toJSONString(insertTalent));
					result.setWebReturnCode(WebReturnCode.ADD_TALENT_ERROR);
					return result;
				}
			}
			
			ConsultPublishAddDTO dto = PublishItemConverter.converterLocal2AddPublishConsult(publishServiceDO, sellerId);
			ItemPubResult addItemResult = publishItemRepo.addItem(dto);
			if (addItemResult == null) {
				log.error("result:ItemPubResult={}",addItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!addItemResult.isSuccess() || addItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
			}
			publishServiceDO.id = addItemResult.itemId;
			//保存图文详情
			boolean savePicTextResult = savePicText(publishServiceDO, sellerId);
			//保存服务区域
			BaseResult<Boolean> saveServiceAreaResult = saveServiceArea(publishServiceDO, sellerId);
			//商品上架
			if (publishServiceDO.serviceState == ServiceState.ON_SALE.getState()) {
				ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
				itemPublishDTO.setItemId(addItemResult.itemId);
				itemPublishDTO.setSellerId(sellerId);
				ItemPubResult publishItemResult = publishItemRepo.publishItem(itemPublishDTO);
			}
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(addItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public WebResult<Boolean> updateItem(PublishServiceDO publishServiceDO,long sellerId) {
		log.info("param:PublishServiceDO={}",JSON.toJSONString(publishServiceDO));
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (publishServiceDO == null || publishServiceDO.serviceState <= 0) {
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			ConsultPublishUpdateDTO dto = PublishItemConverter.converterLocal2UpdatePublishConsult(publishServiceDO, sellerId);
			//保存图文
			savePicText(publishServiceDO, sellerId);
			//保存服务区域
			BaseResult<Boolean> saveServiceAreaResult = saveServiceArea(publishServiceDO, sellerId);
			//更新服务
			ItemPubResult updateItemResult = publishItemRepo.updateItem(dto);
			if (updateItemResult == null) {
				log.error("result:ItemPubResult={}",updateItemResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!updateItemResult.isSuccess() || updateItemResult.getItemId() <= 0) {
				log.error("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
				result.setWebReturnCode(WebReturnCode.UPDATE_ERROR);
				return result;
			}
			//判断是否更新商品状态
			ItemCategoryQuery query = new ItemCategoryQuery();
			query.setItemId(publishServiceDO.id);
			query.setSellerId(sellerId);
			SingleItemQueryResult queryResult = publishItemRepo.queryPublishItem(query);
			if (queryResult != null && queryResult.getItemDO() != null) {
				ItemPublishDTO	itemPublishDTO = new ItemPublishDTO();
				itemPublishDTO.setItemId(publishServiceDO.id);
				itemPublishDTO.setSellerId(sellerId);
				if (queryResult.getItemDO().getStatus() != publishServiceDO.serviceState && publishServiceDO.serviceState == Constant.PUBLISHED) {
					ItemPubResult itemPubResult = publishItemRepo.publishItem(itemPublishDTO);
					
				}else if (queryResult.getItemDO().getStatus() != publishServiceDO.serviceState && publishServiceDO.serviceState == Constant.TO_BE_PUBLISHED) {
					ItemCloseResult itemCloseResult = publishItemRepo.closeItem(itemPublishDTO);
					 
				}
			}
			
			result.setValue(Boolean.TRUE);
			log.info("result:ItemPubResult={}",JSON.toJSONString(updateItemResult));
		} catch (Exception e) {
			log.error("param:PublishServiceDO={} , error:{}",JSON.toJSONString(publishServiceDO),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public ItemApiResult getItemList(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
		
		try {
			ItemApiResult apiResult = new ItemApiResult();
			ItemQryDTO itemQryDTO = PublishItemConverter.converterLocal2ItemQueryDTO(query);
			ItemPageResult itemPageResult = publishItemRepo.getItemList(itemQryDTO);
			if (itemPageResult == null || !itemPageResult.isSuccess() ) {
				log.error("param:ItemCategoryQuery={},result:itemPageResult={}",JSON.toJSONString(query),JSON.toJSONString(itemPageResult));
				return null;
			}
			if (CollectionUtils.isEmpty(itemPageResult.getItemDOList())) {
				return apiResult;
			}
			List<ItemManagement> itemManagements = new ArrayList<ItemManagement>();
			List<Long> idList = new ArrayList<Long>(); 
			for (ItemDO item : itemPageResult.getItemDOList()) {
				idList.add(item.getId());
			}
			Map<Long, List<ComTagDO>> comTagMaps = getComTagMapsByIdList(idList);
			//			DestinationQueryDTO  dto = new DestinationQueryDTO();
//			dto.setOutType(DestinationOutType.SERVICE.getCode());
//			dto.setCodeList(codeList);
//			dto.setDomain(Constant.DOMAIN_JIUXIU);
//			dto.setUseType(DestinationUseType.APP_SHOW.getCode());
			RcResult<List<DestinationDO>> destinationListResult = null;
			if (!CollectionUtils.isEmpty(comTagMaps)) {
				Set<Integer> codeSet = new HashSet<Integer>();
				for (Map.Entry<Long, List<ComTagDO>> map : comTagMaps
						.entrySet()) {
					for (ComTagDO comTag : map.getValue()) {
						codeSet.add(Integer.parseInt(comTag.getName()));
					}
				}
				List<Integer> codeList = new ArrayList<Integer>();
				codeList.addAll(codeSet);
				destinationListResult = destinationServiceRef
						.queryDestinationsByCodeList(Constant.DOMAIN_JIUXIU,
								DestinationUseType.APP_SHOW.getCode(),
								DestinationOutType.SERVICE.getCode(), codeList);
			}
			for (ItemDO item : itemPageResult.getItemDOList()) {
				ItemManagement itemManagement = new ItemManagement();
				PublishServiceDO publishService = new PublishServiceDO();
				publishService.avater = item.getPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS);
				publishService.title = item.getTitle();
				publishService.discountPrice = item.getPrice();
				publishService.discountTime = (item.getItemFeature().getConsultTime())/60;
				if (destinationListResult != null) {
					
					publishService.serviceAreas = getServiceAreas(comTagMaps,item.getId(),destinationListResult.getT());
				}
				publishService.id = item.getId();
				publishService.categoryType = Constant.CONSULT_SERVICE;
				itemManagement.publishServiceDO = publishService;
				itemManagement.itemId = item.getId();
				itemManagement.saleVolume = item.getSales();
				itemManagements.add(itemManagement);
			}
			apiResult.itemManagements = itemManagements;
			return apiResult;
		} catch (Exception e) {
			log.error("param:ItemCategoryQuery={} , error:{}",JSON.toJSONString(query),e);
		}
		return null;
	}

	
	private List<ServiceArea> getServiceAreas(
			Map<Long, List<ComTagDO>> comTagMaps,long itemId,List<DestinationDO> destinationList) {
		if (CollectionUtils.isEmpty(comTagMaps) || itemId <= 0 || CollectionUtils.isEmpty(destinationList)) {
			log.error("param:comTagMaps={},itemId={},destinationList={}",JSON.toJSONString(comTagMaps),itemId,JSON.toJSONString(destinationList));
			return null;
		}
		List<ServiceArea> serviceAreas = new ArrayList<ServiceArea>();
		serviceAreas.clear();
		for (Map.Entry<Long, List<ComTagDO>> map : comTagMaps.entrySet()) {
			if (itemId == map.getKey()) {
				for (ComTagDO comTag : map.getValue()) {
					ServiceArea serviceArea = new ServiceArea();
					for (DestinationDO dest : destinationList) {
						if (Integer.parseInt(comTag.getName()) == dest.getCode() ) {
							serviceArea.areaCode = dest.getCode();
							serviceArea.areaName = dest.getName();
							serviceAreas.add(serviceArea);
						}
					}
					
				}
				
				break;
			}
		}
		return serviceAreas;
	}

	/**
	 * 
	* created by zhangxiaoyang
	* @date 2016年8月5日
	* @Title: getServiceAreasByIdList 
	* @Description: 根据商品id集合批量查询服务区域
	* @param @param idList
	* @param @return    设定文件 
	* @return List<ServiceArea>    返回类型 
	* @throws
	 */
	private Map<Long, List<ComTagDO>> getComTagMapsByIdList(List<Long> idList) {
		TagInfoByOutIdDTO tagInfoByOutIdDTO = new TagInfoByOutIdDTO();
		tagInfoByOutIdDTO.setDomain(Constant.DOMAIN_JIUXIU);
		tagInfoByOutIdDTO.setIdList(idList);
		tagInfoByOutIdDTO.setOutType(TagType.DESTPLACE.name());
		BaseResult<Map<Long, List<ComTagDO>>> comTagResult = comTagCenterServiceRef.getComTag(tagInfoByOutIdDTO);
		if (comTagResult == null || CollectionUtils.isEmpty(comTagResult.getValue()) ) {
			log.error("comTagCenterServiceRef.getComTag param:tagInfoByOutIdDTO={},result:{}",JSON.toJSONString(tagInfoByOutIdDTO),JSON.toJSONString(comTagResult));
			return null;
		}
		return comTagResult.getValue();
	}

	public WebResult<Boolean> updateItemState(ItemQueryDTO dto) {
		WebResult<Boolean> result = new WebResult<Boolean>();
		if (dto == null || dto.getDomainId() <= 0 || dto.getItemId() <= 0 ) {
			log.error("params:ItemQueryDTO={}",JSON.toJSONString(dto));
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		ItemPublishDTO itemPublishDTO = PublishItemConverter.converterLocal2ItemPublishDTO(dto);
		
		try {
			if (dto.getState() == ItemStatus.valid.getValue()) {
				ItemPubResult pubResult = publishItemRepo.publishItem(itemPublishDTO);
				if (pubResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),pubResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!pubResult.isSuccess()) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(pubResult));
					result.setWebReturnCode(WebReturnCode.ON_SALE_ERROR);
					return result;
				}
				return result;
			}else if (dto.getState() == ItemStatus.invalid.getValue()) {
				ItemCloseResult closeResult = publishItemRepo.closeItem(itemPublishDTO);
				if (closeResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),closeResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!closeResult.isSuccess() ) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(closeResult));
					result.setWebReturnCode(WebReturnCode.OFF_SALE_ERROR);
					return result;
				}
				return result;
			}else if (dto.getState() == ItemStatus.deleted.getValue()) {
				ItemDeleteResult deleteResult = publishItemRepo.deleteItem(itemPublishDTO);
				if (deleteResult == null) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),deleteResult);
					result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
					return result;
				}else if (!deleteResult.isSuccess() ) {
					log.error("params:ItemQueryDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(deleteResult));
					result.setWebReturnCode(WebReturnCode.DELETE_ERROR);
					return result;
				}
				return result;
			}
		} catch (Exception e) {
			log.error("param:ItemQueryDTO={},error:{}",JSON.toJSONString(dto),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	public WebResult<Boolean> checkWhiteList(ItemCategoryQuery query) {
		WebResult<Boolean> result = new WebResult<Boolean>();
		try {
			MemResult<List<MerchantItemCategoryDO>> itemCaetgoryListResult = publishItemRepo.getMerchantItemCaetgoryList(query);
			if (itemCaetgoryListResult == null) {
				log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),itemCaetgoryListResult);
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
				return result;
			}else if (!itemCaetgoryListResult.isSuccess() || CollectionUtils.isEmpty(itemCaetgoryListResult.getValue())) {
				log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),JSON.toJSONString(itemCaetgoryListResult));
				result.setWebReturnCode(WebReturnCode.QUERY_FAILURE);
				return result;
			}
			int count = 0;
			for (MerchantItemCategoryDO item : itemCaetgoryListResult.getValue()) {
				if (item.getItemCategoryId() == Constant.CONSULT_SERVICE) {
					count ++ ;
				}
			}
			if (count > 0) {
				return result;
			}else {
				result.setWebReturnCode(WebReturnCode.NO_AUTHORITY);
				return result;
			}
		} catch (Exception e) {
			log.error("param:ItemCategoryQuery={},error:{}",JSON.toJSONString(query),e);
			result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
		}
		return result;
	}
	
	public ItemApiResult getItemDetail(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
		ItemApiResult apiResult = new ItemApiResult();
		SingleItemQueryResult queryResult = publishItemRepo.queryPublishItem(query);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getItemDO() == null) {
			log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),JSON.toJSONString(queryResult));
			return null;
		}
		try {
			List<ServiceArea> serviceAreas = getServiceAreas(query);
			ItemDO itemDO = queryResult.getItemDO();
			PublishServiceDO publishService = new PublishServiceDO();
			publishService.avater = itemDO.getPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS);
			publishService.title = itemDO.getTitle();
			publishService.discountPrice = itemDO.getPrice();
			publishService.discountTime = (itemDO.getItemFeature().getConsultTime())/60;
			publishService.serviceAreas = serviceAreas;
			publishService.id = itemDO.getId();
			publishService.categoryType = Constant.CONSULT_SERVICE;
			List<ItemSkuPVPair> itemPropertyList = itemDO.getItemPropertyList();
			for (ItemSkuPVPair itemSkuPVPair : itemPropertyList) {
				if (itemSkuPVPair.getPId() == Constant.FEE_DESC) {
					publishService.feeDesc = itemSkuPVPair.getVTxt();
				}else if (itemSkuPVPair.getPId() == Constant.BOOKING_TIP) {
					publishService.bookingTip = itemSkuPVPair.getVTxt();
				}else if (itemSkuPVPair.getPId() == Constant.REFUND_RULE) {
					publishService.refundRule = itemSkuPVPair.getVTxt();
				}
			}
			ItemManagement itemManagement = new ItemManagement();
			itemManagement.saleVolume = itemDO.getSales();
			itemManagement.publishServiceDO = publishService;
			itemManagement.itemId = itemDO.getId();
			ItemDetail itemDetail = new ItemDetail();
			apiResult.itemDetail = itemDetail;
			apiResult.itemDetail.itemManagement = itemManagement;
			com.yimayhd.user.client.result.BaseResult<TalentDTO> queryTalentInfoResult = talentServiceRef. queryTalentInfo(query.getSellerId());
			if (queryTalentInfoResult != null && queryTalentInfoResult.getValue() != null) {
				
				TalentInfo talentInfo = new TalentInfo();
				talentInfo.avater = queryTalentInfoResult.getValue().getUserDTO().getAvatar();
				talentInfo.nickName = queryTalentInfoResult.getValue().getUserDTO().getNickname();
				InfoQueryDTO examineQueryDTO = new InfoQueryDTO();
				examineQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
				examineQueryDTO.setType(MerchantType.TALENT.getType());
				examineQueryDTO.setSellerId(query.getSellerId());
				
				log.info("param:InfoQueryDTO  ={}",JSON.toJSONString(examineQueryDTO));
				MemResult<ExamineInfoDTO> talentInfoResult = examineDealService.queryMerchantExamineInfoBySellerId(examineQueryDTO);
				log.info("result:MemResult<ExamineInfoDTO> ={}",JSON.toJSONString(talentInfoResult));
				if (talentInfoResult != null && talentInfoResult.isSuccess() && talentInfoResult.getValue() != null) {
				if (StringUtils.isNotBlank(talentInfoResult.getValue().getPrincipleCardDown()) && StringUtils.isNotBlank(talentInfoResult.getValue().getPrincipleCardUp()) && talentInfoResult.getValue().getExaminStatus() == ExamineStatus.EXAMIN_OK.getStatus()) {
					talentInfo.certificateSate = 1;
				}
				}
				apiResult.talentInfo = talentInfo;
			}
				
			return apiResult;
		} catch (Exception e) {
			log.error("param:ItemQueryDTO={},error:{}",JSON.toJSONString(query),e);
		}
		return null;
	}
	public ItemApiResult getPublishItemById(ItemCategoryQuery query) {
		log.info("param:ItemCategoryQuery={}",JSON.toJSONString(query));
		ItemApiResult apiResult = new ItemApiResult();
		SingleItemQueryResult queryResult = publishItemRepo.queryPublishItem(query);
		if (queryResult == null || !queryResult.isSuccess() || queryResult.getItemDO() == null) {
			log.error("param:ItemCategoryQuery={},result:{}",JSON.toJSONString(query),JSON.toJSONString(queryResult));
			return null;
		}
		try {
			List<ServiceArea> serviceAreas = getServiceAreas(query);
			ItemDO itemDO = queryResult.getItemDO();
			PublishServiceDO publishService = new PublishServiceDO();
			publishService.avater = itemDO.getPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS);
			publishService.title = itemDO.getTitle();
			publishService.discountPrice = itemDO.getPrice();
			publishService.discountTime = ( itemDO.getItemFeature().getConsultTime())/60;
			publishService.serviceAreas = serviceAreas;
			publishService.id = itemDO.getId();
			publishService.categoryType = Constant.CONSULT_SERVICE;
			publishService.oldPrice = itemDO.getOriginalPrice();
			publishService.oldTime = (itemDO.getItemFeature().getConsultTime())/60;
			List<ItemSkuPVPair> itemPropertyList = itemDO.getItemPropertyList();
			for (ItemSkuPVPair itemSkuPVPair : itemPropertyList) {
				if (itemSkuPVPair.getPId() == Constant.FEE_DESC) {
					publishService.feeDesc = itemSkuPVPair.getVTxt();
				}else if (itemSkuPVPair.getPId() == Constant.BOOKING_TIP) {
					publishService.bookingTip = itemSkuPVPair.getVTxt();
				}else if (itemSkuPVPair.getPId() == Constant.REFUND_RULE) {
					publishService.refundRule = itemSkuPVPair.getVTxt();
				}
			}
			PicTextResult pictureTextResult = pictureTextRepo.getPictureText(query.getItemId(), PictureText.EXPERTPUBLISH);
			if (pictureTextResult != null && !CollectionUtils.isEmpty(pictureTextResult.getList())) {
				List<PictureTextItem> pictureTextItems = new ArrayList<PictureTextItem>();
				for (PicTextDO picText : pictureTextResult.getList()) {
					PictureTextItem pictureTextItem = new PictureTextItem();
					if (picText.getType() == FeatureType.COMENT.getType()) {
						
						pictureTextItem.type = FeatureType.COMENT.name();
					}else if (picText.getType() == FeatureType.IMAGE.getType()) {
						pictureTextItem.type = FeatureType.IMAGE.name();
						
					}
					pictureTextItem.value = picText.getValue();
					pictureTextItems.add(pictureTextItem);
				}
				publishService.pictureTextItems = pictureTextItems;
			}
			ItemManagement itemManagement = new ItemManagement();
			itemManagement.saleVolume = itemDO.getSales();
			itemManagement.publishServiceDO = publishService;
			itemManagement.itemId = itemDO.getId();
			ItemDetail itemDetail = new ItemDetail();
			apiResult.itemDetail = itemDetail;
			apiResult.itemDetail.itemManagement = itemManagement;
			
			return apiResult;
		} catch (Exception e) {
			log.error("param:ItemQueryDTO={},error:{}",JSON.toJSONString(query),e);
		}
		return null;
	}
	
	private BaseResult<Boolean> saveServiceArea(PublishServiceDO publishServiceDO,
			long sellerId) {
		try {
			TagRelationInfoDTO tagRelationInfo = new TagRelationInfoDTO();
			List<Long> codeList = new ArrayList<Long>();
			for (ServiceArea sa : publishServiceDO.serviceAreas) {
				codeList.add(sa.areaCode);
			}
			tagRelationInfo.setDomain(Constant.DOMAIN_JIUXIU);
			tagRelationInfo.setList(codeList);
			tagRelationInfo.setOutId(publishServiceDO.id);
			tagRelationInfo.setOutType(TagType.DESTPLACE.getType());
			tagRelationInfo.setOrderTime(new Date());
			BaseResult<Boolean> addResult = comCenterServiceRef.addLineTagRelationInfo(tagRelationInfo);
			return addResult;
		} catch (Exception e) {
			log.error("param:PublishServiceDO={},error:{}",JSON.toJSONString(publishServiceDO),e);
		}
		return null;
	}
	private boolean savePicText(PublishServiceDO publishServiceDO, long sellerId) {
		try {
			PictureTextVO pictureTextVO = new PictureTextVO();
			List<PictureTextItemVo> pictureTextItemVos = new ArrayList<PictureTextItemVo>();
			for (PictureTextItem pictureTextItem : publishServiceDO.pictureTextItems) {
				PictureTextItemVo vo = new PictureTextItemVo();
				if (FeatureType.COMENT.name() .equalsIgnoreCase(pictureTextItem.type)) {
					
					vo.setType(PictureTextItemType.TEXT.name());
				}else if (FeatureType.IMAGE.name() .equalsIgnoreCase(pictureTextItem.type)) {
					vo.setType(PictureTextItemType.IMG.name());
					
				}
				vo.setValue(pictureTextItem.value);
				pictureTextItemVos.add(vo);
			}
			pictureTextVO.setPictureTextItems(pictureTextItemVos);
			ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(publishServiceDO.id, PictureText.EXPERTPUBLISH, pictureTextVO);
			
			pictureTextRepo.editPictureText(comentEditDTO);
			return true;
		} catch (Exception e) {
			log.error("param:PublishServiceDO={},userId={},error:{}",JSON.toJSONString(publishServiceDO),sellerId,e);
			return false;
		}
	}
	private List<ServiceArea> getServiceAreas(ItemCategoryQuery query) {
		List<ServiceArea> serviceAreas = new ArrayList<ServiceArea>();
		BaseResult<List<ComTagDO>> tagInfoResult = comCenterServiceRef.getTagInfoByOutIdAndType(query.getItemId(),TagType.DESTPLACE.name());
		log.info("comCenterServiceRef.getTagInfoByOutIdAndType ,param:{},result:{}",query.getItemId(),TagType.DESTPLACE.name(),JSON.toJSONString(tagInfoResult));
		if (tagInfoResult == null || CollectionUtils.isEmpty(tagInfoResult.getValue())) {
			log.error("comCenterServiceRef.getTagInfoByOutIdAndType,param:itemId={},outType:{},result:{}",query.getItemId(),TagType.DESTPLACE.name(),JSON.toJSONString(tagInfoResult));
			return null;
		}
		List<Integer> codeList = new ArrayList<Integer>();
		List<ComTagDO> comTagDOs = tagInfoResult.getValue();
		for (ComTagDO tag : comTagDOs) {
			codeList.add(Integer.parseInt(tag.getName()));
		}
//		DestinationQueryDTO  dto = new DestinationQueryDTO();
//		dto.setOutType(DestinationOutType.SERVICE.getCode());
//		dto.setCodeList(codeList);
//		dto.setDomain(Constant.DOMAIN_JIUXIU);
//		dto.setUseType(DestinationUseType.APP_SHOW.getCode());
		RcResult<List<DestinationDO>> destinationListResult = destinationServiceRef.queryDestinationsByCodeList(Constant.DOMAIN_JIUXIU,
				DestinationUseType.APP_SHOW.getCode(),DestinationOutType.SERVICE.getCode(),codeList);
		for (DestinationDO des : destinationListResult.getT()) {
			ServiceArea serviceArea = new ServiceArea();
			serviceArea.areaCode = des.getCode();
			serviceArea.areaName = des.getName();
			serviceAreas.add(serviceArea);
		}
		return serviceAreas;
	}
}

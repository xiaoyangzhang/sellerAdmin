package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.domain.ComTagRelationDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.ActivityVO;
import com.yimayhd.sellerAdmin.model.query.ActivityListQuery;
import com.yimayhd.sellerAdmin.repo.ActivityRepo;
import com.yimayhd.sellerAdmin.service.ActivityService;
import com.yimayhd.sellerAdmin.service.TfsService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.snscenter.client.domain.ActivityJsonDO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.enums.ActivityPicUrlsKey;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ActivityServiceImpl implements ActivityService {

	private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	private SnsCenterService snsCenterService;
	@Autowired
	private TfsService tfsService;
	@Autowired
	private ComCenterService comCenterService;
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ActivityRepo activityRepo;

	@Override
	public PageVO<SnsActivityDO> pageQueryActivities(ActivityListQuery query) {
		ActivityQueryDTO activityQueryDTO = new ActivityQueryDTO();
		if(query.getPageNo()!=null){
			int pageNumber =query.getPageNo();
			int pageSize = query.getPageSize();
			activityQueryDTO.setPageNo(pageNumber);
			activityQueryDTO.setPageSize(pageSize);
		}
		//创建开始时间
		if (StringUtils.isNotBlank(query.getStartTime())) {
			Date startTime = DateUtil.parseDate(query.getStartTime());
			activityQueryDTO.setStartTime(startTime);
		}
		//创建结束时间
		if (StringUtils.isNotBlank(query.getEndTime())) {
			Date endTime = DateUtil.parseDate(query.getEndTime());
			activityQueryDTO.setEndTime(endTime);
		}
		//活动开始时间
		if (StringUtils.isNotBlank(query.getActivityStartTime())) {
			Date activityStartTime = DateUtil.parseDate(query.getActivityStartTime());
			activityQueryDTO.setActivityStartTime(activityStartTime);
		}
		//活动结束时间
		if (StringUtils.isNotBlank(query.getActivityEndTime())) {
			Date activityEndTime = DateUtil.parseDate(query.getActivityEndTime());
			activityQueryDTO.setActivityEndTime(activityEndTime);
		}
		//名称
		activityQueryDTO.setTitle(query.getTitle());
		//状态
		if (query.getState() != 0) {
			activityQueryDTO.setState(query.getState());
		}
		//俱乐部
		if (query.getClubId() != 0) {
			activityQueryDTO.setClubId(query.getClubId());
		}

		//标签
		if (query.getTagId() != 0) {
			com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagRelationDO>> result
					= comCenterService.getTagRelationByTagIdAndType(query.getTagId(), TagType.ACTIVETYTAG.name());
			if (null == result) {
				log.error(
						"ActivityServiceImpl.getById-comCenterService.getTagInfoByOutIdAndType result is null and parame:tagId= "
								+ query.getTagId() + "|outType=" + TagType.ACTIVETYTAG.name());
				throw new BaseException("查询结果为空,查询失败 ");
			} else if (!result.isSuccess()) {
				log.error("ActivityServiceImpl.getById--comCenterService.getTagInfoByOutIdAndType  error:"
						+ JSON.toJSONString(result) + "and parame:tagId=  " + query.getTagId() + "|outType=" + TagType.ACTIVETYTAG.name());
				throw new BaseException(result.getResultMsg());
			}
			List<ComTagRelationDO> tagList = result.getValue();
			List<Long> activityIdList = query.getActivityIdList();
			for (ComTagRelationDO tag : tagList) {
				activityIdList.add(tag.getOutId());

			}
			activityQueryDTO.setActivityIdList(activityIdList);
		}

		return activityRepo.pageQueryActivities(activityQueryDTO);
	}

	@Override
	public SnsActivityDO getById(long id) {
		return activityRepo.getActivityById(id);
	}

	@Override
	public com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> getTagInfoByOutIdAndType(long outId,
																									   String outType) {
		com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> talList = comCenterService
				.getTagInfoByOutIdAndType(outId, outType);
		if (null == talList) {
			log.error(
					"ActivityServiceImpl.getById-comCenterService.getTagInfoByOutIdAndType result is null and parame:outId= "
							+ outId + "|outType=" + outType);
			throw new BaseException("查询结果为空,修改失败 ");
		} else if (!talList.isSuccess()) {
			log.error("ActivityServiceImpl.getById--comCenterService.getTagInfoByOutIdAndType  error:"
					+ JSON.toJSONString(talList) + "and parame:outId=  " + +outId + "|outType=" + outType);
			throw new BaseException(talList.getResultMsg());
		}
		return talList;
	}

	@Override
	public BaseResult<SnsActivityDO> save(ActivityVO activityVO)throws Exception{
		BaseResult<SnsActivityDO> result = null;

		if (activityVO.getId() != 0) {
			BaseResult<SnsActivityDO> activityInfo = snsCenterService.getActivityInfoByActivityId(activityVO.getId());
			if (null == activityInfo) {
				log.error(
						"ActivityServiceImpl.save-snsCenterService.getActivityInfoByActivityId result is null and parame: "
								+ activityVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			} else if (!activityInfo.isSuccess()) {
				log.error("ActivityServiceImpl.save--snsCenterService.getActivityInfoByActivityId  error:"
						+ JSON.toJSONString(activityInfo) + "and parame: " + activityVO.getId());
				throw new BaseException(activityInfo.getResultMsg());
			}
			SnsActivityDO snsActivityDO = activityInfo.getValue();
			//TODO snsActivityDO之后都没用到，这样是覆盖，需要改一下
			if (snsActivityDO == null) {
				log.error(
						"ActivityServiceImpl.save-snsCenterService.getActivityInfoByActivityId result.getValue is null and parame: "
								+ activityVO.getId());
			}
			ActivityInfoDTO dto = ActivityVO.getActivityInfoDTO(activityVO);
			dto.setOriginalPrice(dto.getOriginalPrice());
			dto.setPreferentialPrice(dto.getPreferentialPrice());
			if (org.apache.commons.lang.StringUtils.isNotBlank(dto.getContent())) {
				dto.setContent(tfsService.publishHtml5(dto.getContent()));
			}
			
			// 设置库存
			ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
			// 全部设置成true
			itemOptionDTO.setCreditFade(true);
			itemOptionDTO.setNeedCategory(true);
			itemOptionDTO.setNeedSku(true);
			ItemResult itemResult = itemQueryServiceRef.getItem(activityVO.getProductId(), itemOptionDTO);
			if (null == itemResult) {
				log.error("ActivityServiceImpl.save--itemQueryService.getItem return value is null and parame: "
						+ JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
				throw new BaseException("返回结果错误");
			} else if (!itemResult.isSuccess()) {
				log.error("ActivityServiceImpl.save--itemQueryService.getItem return value error ! returnValue : "
						+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO)
						+ " and id is : " + activityVO.getProductId());
				throw new NoticeException(itemResult.getResultMsg());
			}
			dto.setMemberCount(itemResult.getItem().getStockNum());
			List<ActivityJsonDO> jsonDOs = activityVO.getActivityJsonArray();
			List<ActivityJsonDO> newjsonDOs = new ArrayList<ActivityJsonDO>();
			if (jsonDOs != null && !jsonDOs.isEmpty()) {
				for (int i = 0; i < jsonDOs.size(); i++) {
					if (StringUtils.isNotBlank(jsonDOs.get(i).getActivityTitle())
							|| StringUtils.isNotBlank(jsonDOs.get(i).getActivityDec())) {
						newjsonDOs.add(jsonDOs.get(i));
					}
				}
			}
			dto.setActivityJsonArray(newjsonDOs);
			Map<String,String> picUrls = new HashMap<String, String>();
			//String imagePC = snsActivityDO.getPicUrls(ActivityPicUrlsKey.BIG_LIST_PIC);
			if(StringUtils.isNotBlank(activityVO.getImagePC())){
				picUrls.put(ActivityPicUrlsKey.BIG_LIST_PIC.getCode(),activityVO.getImagePC());
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(activityVO.getContentPC())){
				picUrls.put(ActivityPicUrlsKey.BIG_H5_PIC.getCode(), tfsService.publishHtml5(activityVO.getContentPC()));	
			}
			dto.setPicUrlsMap(picUrls);
			result = snsCenterService.updateActivityInfo(dto);
			if (!result.isSuccess()) {
				log.error("Activity|snsCenterService.save return value is null !returnValue :"
						+ JSON.toJSONString(result));
				throw new NoticeException(result.getResultMsg());
			} else {
				TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
				tagRelationInfoDTO.setList(Arrays.asList(activityVO.getTagList()));
				tagRelationInfoDTO.setOutId(result.getValue().getId());
				tagRelationInfoDTO.setTagType(TagType.ACTIVETYTAG.getType());
				tagRelationInfoDTO.setOrderTime(result.getValue().getActivityDate());
				com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagRelationInfoRurult = comCenterService
						.addTagRelationInfo(tagRelationInfoDTO);
				if (!addTagRelationInfoRurult.isSuccess()) {
					log.error("保存活动主题失败：" + addTagRelationInfoRurult.getResultMsg());
					throw new BaseException("保存活动主题失败");
				}

			}
		} else {
			ActivityInfoDTO dto = ActivityVO.getActivityInfoDTO(activityVO);
			dto.setOriginalPrice(dto.getOriginalPrice());
			dto.setPreferentialPrice(dto.getPreferentialPrice());
			if (org.apache.commons.lang.StringUtils.isNotBlank(dto.getContent())) {
				dto.setContent(tfsService.publishHtml5(dto.getContent()));
			}
			
			if(org.apache.commons.lang.StringUtils.isNotBlank(activityVO.getContentPC())){
				Map<String, String> picUrlsMap = dto.getPicUrls();
				picUrlsMap.put(ActivityPicUrlsKey.BIG_H5_PIC.getCode(), tfsService.publishHtml5(activityVO.getContentPC()));
			}
			// 设置库存
			ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
			// 全部设置成true
			itemOptionDTO.setCreditFade(true);
			itemOptionDTO.setNeedCategory(true);
			itemOptionDTO.setNeedSku(true);
			ItemResult itemResult = itemQueryServiceRef.getItem(activityVO.getProductId(), itemOptionDTO);
			if (null == itemResult) {
				log.error("ActivityServiceImpl.save--itemQueryService.getItem return value is null and parame: "
						+ JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
				throw new BaseException("返回结果错误");
			} else if (!itemResult.isSuccess()) {
				log.error("ActivityServiceImpl.save--itemQueryService.getItem return value error ! returnValue : "
						+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO)
						+ " and id is : " + activityVO.getProductId());
				throw new NoticeException(itemResult.getResultMsg());
			}
			dto.setMemberCount(itemResult.getItem().getStockNum());
			List<ActivityJsonDO> jsonDOs = activityVO.getActivityJsonArray();
			List<ActivityJsonDO> newjsonDOs = new ArrayList<ActivityJsonDO>();
			if (jsonDOs != null && !jsonDOs.isEmpty()) {
				for (int i = 0; i < jsonDOs.size(); i++) {
					if (StringUtils.isNotBlank(jsonDOs.get(i).getActivityTitle())
							|| StringUtils.isNotBlank(jsonDOs.get(i).getActivityDec())) {
						newjsonDOs.add(jsonDOs.get(i));
					}
				}
			}
			dto.setActivityJsonArray(newjsonDOs);
			result = snsCenterService.addActivityInfo(dto);
			if (!result.isSuccess()) {
				log.error("Activity|snsCenterService.save return value is null !returnValue :"
						+ JSON.toJSONString(result));
				throw new NoticeException(result.getResultMsg());
			} else {
				TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
				tagRelationInfoDTO.setList(Arrays.asList(activityVO.getTagList()));
				tagRelationInfoDTO.setOutId(result.getValue().getId());
				tagRelationInfoDTO.setTagType(TagType.ACTIVETYTAG.getType());
				tagRelationInfoDTO.setOrderTime(result.getValue().getActivityDate());
				com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagRelationInfoRurult = comCenterService
						.addTagRelationInfo(tagRelationInfoDTO);
				if (!addTagRelationInfoRurult.isSuccess()) {
					log.error("保存活动主题失败：" + addTagRelationInfoRurult.getResultMsg());
					throw new BaseException("保存活动主题失败");
				}

			}
		}
		return result;

	}

	@Override
	public boolean updateActivityStateByIList(List<Long> ids, int state) {
		return activityRepo.updateActivityStateByIList(ids, state);
	}

}

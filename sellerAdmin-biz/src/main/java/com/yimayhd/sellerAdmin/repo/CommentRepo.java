package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.dto.TagOutIdTypeDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.query.TagPageQuery;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 标签Repo
 * 
 * @author yebin
 *
 */
public class CommentRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	public static final int STATUS_DISABLE = 2;
	public static final int STATUS_ENABLE = 1;
	@Autowired
	private ComCenterService comCenterServiceRef;
	@Autowired
	private ComTagCenterService comTagCenterServiceRef;

	/**
	 * 保存标签
	 * 
	 * @param outId
	 * @param tagType
	 * @param tagDTOs
	 */
	@Deprecated
	public void saveTags(long outId, TagType tagType, List<? extends TagDTO> tagDTOs) {
		if (outId <= 0 || tagType == null || CollectionUtils.isEmpty(tagDTOs)) {
			log.warn("save tags params error");
			throw new BaseException("参数异常");
		}
		List<Long> tagIds = new ArrayList<Long>();
		for (TagDTO tagDTO : tagDTOs) {
			if (tagDTO.getId() > 0) {
				tagIds.add(tagDTO.getId());
			} else {
				TagInfoAddDTO tagInfoAddDTO = new TagInfoAddDTO();
				tagInfoAddDTO.setDomain(Constant.DOMAIN_JIUXIU);
				tagInfoAddDTO.setTagId(tagDTO.getId());
				tagInfoAddDTO.setName(tagDTO.getName());
				tagInfoAddDTO.setOutType(tagType.getType());
				ComTagDO comTagDO = saveTag(tagInfoAddDTO);
				tagIds.add(comTagDO.getId());
			}
		}
		saveTagRelation(outId, tagType, tagIds);
	}

	/**
	 * 保存标签
	 * 
	 * @param tagInfoAddDTO
	 * @return
	 */
	public ComTagDO saveTag(TagInfoAddDTO tagInfoAddDTO) {
		if (tagInfoAddDTO == null || tagInfoAddDTO == null) {
			return null;
		}
		RepoUtils.requestLog(log, "comCenterServiceRef.saveTagInfo", tagInfoAddDTO);
		BaseResult<ComTagDO> result = comTagCenterServiceRef.saveTagInfo(tagInfoAddDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.saveTagInfo", result);
		return result.getValue();
	}

	/**
	 * 保存关联关系
	 * 
	 * @param outId
	 * @param tagType
	 * @param tagIds
	 */
	public void saveTagRelation(long outId, TagType tagType, List<Long> tagIds) {
		if (outId <= 0 || tagType == null || CollectionUtils.isEmpty(tagIds)) {
			log.warn("save tag relation params error");
			throw new BaseException("参数异常");
		}
		TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
		tagRelationInfoDTO.setTagType(tagType.getType());
		tagRelationInfoDTO.setOutId(outId);
		tagRelationInfoDTO.setOrderTime(new Date());
		tagRelationInfoDTO.setList(tagIds);
		RepoUtils.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
		BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
	}

	/**
	 * 查询标签
	 * 
	 * @param outId
	 * @param tagType
	 * @return
	 */
	public List<ComTagDO> getTags(Long outId, TagType tagType) {
		TagOutIdTypeDTO tagOutIdTypeDTO = new TagOutIdTypeDTO();
		tagOutIdTypeDTO.setDomain(Constant.DOMAIN_JIUXIU);
		if (outId != null) {
			tagOutIdTypeDTO.setOutId(outId);
		}
		tagOutIdTypeDTO.setOutType(tagType.name());
		RepoUtils.requestLog(log, "comTagCenterServiceRef.getTagInfo", tagOutIdTypeDTO);
		BaseResult<List<ComTagDO>> result = comTagCenterServiceRef.getTagInfo(tagOutIdTypeDTO);
		RepoUtils.resultLog(log, "comTagCenterServiceRef.getTagInfo", result);
		return result.getValue();
	}

	public PageVO<ComTagDO> pageQueryTag(TagInfoDTO tagInfoDTO) {
		RepoUtils.requestLog(log, "comCenterServiceRef.selectTagInfoPage", tagInfoDTO);
		BasePageResult<ComTagDO> result = comCenterServiceRef.selectTagInfoPage(tagInfoDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.selectTagInfoPage", result);
		int totalCount = result.getTotalCount();
		List<ComTagDO> itemList = result.getList();
		if (itemList == null) {
			itemList = new ArrayList<ComTagDO>();
		}
		return new PageVO<ComTagDO>(tagInfoDTO.getPageNo(), tagInfoDTO.getPageSize(), totalCount, itemList);
	}

	/**
	 * 查询标签信息
	 * 
	 * @param id
	 * @return
	 */
	public ComTagDO getTagById(long id) {
		RepoUtils.requestLog(log, "comCenterServiceRef.selectByPrimaryKey", id);
		BaseResult<ComTagDO> result = comCenterServiceRef.selectByPrimaryKey(id);
		RepoUtils.resultLog(log, "comCenterServiceRef.selectByPrimaryKey", result);
		return result.getValue();
	}

	/**
	 * 更新标签
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public ComTagDO updateTagStateById(long id, int state) {
		TagPageQuery query = new TagPageQuery();
		query.setList(Arrays.asList(id));
		query.setState(state);
		RepoUtils.requestLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", query);
		BaseResult<ComTagDO> result = comCenterServiceRef.updateTagInfoStateByIdList(query);
		RepoUtils.resultLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", result);
		return result.getValue();
	}

	/**
	 * 批量修改标签状态
	 * 
	 * @param ids
	 * @param state
	 * @return
	 */
	public ComTagDO batchUpdateTagsState(List<Long> ids, int state) {
		TagPageQuery query = new TagPageQuery();
		query.setList(ids);
		query.setState(state);
		RepoUtils.requestLog(log, "comTagCenterServiceRef.updateTagInfoByIdList", query);
		BaseResult<ComTagDO> result = comTagCenterServiceRef.updateTagInfoByIdList(query);
		RepoUtils.resultLog(log, "comTagCenterServiceRef.updateTagInfoByIdList", result);
		return result.getValue();
	}

	/**
	 * 更新标签
	 * 
	 * @param tagType
	 * @param tagDTO
	 * @return
	 */
	public ComTagDO updateTag(TagInfoAddDTO tagInfoAddDTO) {
		if (tagInfoAddDTO == null) {
			return null;
		}
		RepoUtils.requestLog(log, "comTagCenterServiceRef.updateTagInfo", tagInfoAddDTO);
		BaseResult<ComTagDO> result = comTagCenterServiceRef.updateTagInfo(tagInfoAddDTO);
		RepoUtils.resultLog(log, "comTagCenterServiceRef.updateTagInfo", result);
		return result.getValue();
	}

	/**
	 * 根据TagType获取标签列表
	 * 
	 * @param tagType
	 *            标签类型
	 * @return 标签列表
	 */
	public List<ComTagDO> getTagsByTagType(TagType tagType) {
		if (tagType == null) {
			return new ArrayList<ComTagDO>(0);
		}
		RepoUtils.requestLog(log, "comCenterServiceRef.selectTagListByTagType", tagType.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(tagType.name());
		RepoUtils.resultLog(log, "comCenterServiceRef.selectTagListByTagType", tagResult);
		return tagResult.getValue();
	}
}

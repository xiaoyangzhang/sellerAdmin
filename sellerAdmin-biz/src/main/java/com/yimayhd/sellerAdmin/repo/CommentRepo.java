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
import com.yimayhd.commentcenter.client.domain.ComTagRelationDO;
import com.yimayhd.commentcenter.client.domain.ComentDO;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.dto.TagRelaByTagIdAndTypeDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.query.TagPageQuery;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.TagRelationResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.constant.Constant;
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

	public boolean addTagRelation(long outId, TagType tagType, List<Long> tagIdList, Date date) {
		if (outId <= 0 || tagType == null || CollectionUtils.isEmpty(tagIdList) || date == null) {
			return false;
		}
		TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
		tagRelationInfoDTO.setTagType(tagType.getType());
		tagRelationInfoDTO.setOutId(outId);
		tagRelationInfoDTO.setOrderTime(date);
		tagRelationInfoDTO.setList(tagIdList);
		RepoUtils.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
		BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
		return addTagRelationInfo.getValue();
	}

	public List<Long> findAllTag(long outId, TagType tagType) {
		TagRelaByTagIdAndTypeDTO tagrelabytagidandtypedto = new TagRelaByTagIdAndTypeDTO();
		tagrelabytagidandtypedto.setOutId(outId);
		tagrelabytagidandtypedto.setDomain(Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "comTagCenterServiceRef.getTagRelationByOutIdAndType", tagrelabytagidandtypedto);
		BaseResult<TagRelationResult> tagRelationByOutIdAndType = comTagCenterServiceRef
				.getTagRelationByOutIdAndType(tagrelabytagidandtypedto);
		RepoUtils.resultLog(log, "comTagCenterServiceRef.getTagRelationByOutIdAndType", tagRelationByOutIdAndType);
		TagRelationResult tagRelationResult = tagRelationByOutIdAndType.getValue();
		List<Long> result = new ArrayList<Long>();
		List<ComTagRelationDO> expertList = tagRelationResult.getExpertList();
		if (CollectionUtils.isNotEmpty(expertList)) {
			for (ComTagRelationDO comTagRelationDO : expertList) {
				if (comTagRelationDO.getOutType() == tagType.getType()) {
					result.add(comTagRelationDO.getTagId());
				}
			}
		}
		return result;
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

	public ComTagDO getTagById(long id) {
		RepoUtils.requestLog(log, "comCenterServiceRef.selectByPrimaryKey", id);
		BaseResult<ComTagDO> result = comCenterServiceRef.selectByPrimaryKey(id);
		RepoUtils.resultLog(log, "comCenterServiceRef.selectByPrimaryKey", result);
		return result.getValue();
	}

	public ComTagDO updateTagStateById(long id, int state) {
		TagPageQuery query = new TagPageQuery();
		query.setList(Arrays.asList(id));
		query.setState(state);
		RepoUtils.requestLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", query);
		BaseResult<ComTagDO> result = comCenterServiceRef.updateTagInfoStateByIdList(query);
		RepoUtils.resultLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", result);
		return result.getValue();
	}

	public void updateTagStateByIdList(List<Long> ids, int state) {
		TagPageQuery query = new TagPageQuery();
		query.setList(ids);
		query.setState(state);
		RepoUtils.requestLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", query);
		BaseResult<ComTagDO> result = comCenterServiceRef.updateTagInfoStateByIdList(query);
		RepoUtils.resultLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", result);
	}

	public ComTagDO saveTag(TagInfoAddDTO tag) {
		RepoUtils.requestLog(log, "comCenterServiceRef.addComTagInfo", tag);
		BaseResult<ComTagDO> result = comCenterServiceRef.addComTagInfo(tag);
		RepoUtils.resultLog(log, "comCenterServiceRef.addComTagInfo", result);
		return result.getValue();
	}

	public ComTagDO updateTag(TagInfoAddDTO tag) {
		RepoUtils.requestLog(log, "comCenterServiceRef.updateComTagInfo", tag);
		BaseResult<ComTagDO> result = comCenterServiceRef.updateComTagInfo(tag);
		RepoUtils.resultLog(log, "comCenterServiceRef.updateComTagInfo", result);
		return result.getValue();
	}

	/**
	 * 根据TagType获取标签列表
	 * 
	 * @param tagType
	 *            标签类型
	 * @return 标签列表
	 */
	public List<ComTagDO> getTagListByTagType(TagType tagType) {
		RepoUtils.requestLog(log, "comCenterServiceRef.selectTagListByTagType", tagType.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(tagType.name());
		RepoUtils.resultLog(log, "comCenterServiceRef.selectTagListByTagType", tagResult);
		return tagResult.getValue();
	}

	/**
	 * 获取全部线路主题
	 * 
	 * @return
	 */
	public List<ComTagDO> getAllLineThemes() {
		return getTagListByTagType(TagType.LINETAG);
	}

	public ComentDO savePictureText(ComentDTO comentDTO) {
		RepoUtils.requestLog(log, "comTagCenterServiceRef.savePictureText", comentDTO);
		BaseResult<ComentDO> savePictureText = comTagCenterServiceRef.savePictureText(comentDTO);
		RepoUtils.resultLog(log, "comTagCenterServiceRef.savePictureText", savePictureText);
		return savePictureText.getValue();
	}

}

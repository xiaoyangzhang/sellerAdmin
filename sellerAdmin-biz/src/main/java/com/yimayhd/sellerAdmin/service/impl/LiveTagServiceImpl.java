package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.checker.TagChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.LiveTagVO;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.service.LiveTagService;

public class LiveTagServiceImpl implements LiveTagService {
	@Autowired
	private CommentRepo tagRepo;

	@Override
	public PageVO<ComTagDO> pageQueryLiveTag(TagInfoDTO tagInfoDTO) {
		tagInfoDTO.setOutType(TagType.LIVESUPTAG.getType());
		return tagRepo.pageQueryTag(tagInfoDTO);
	}

	@Override
	public ComTagDO getLveTagById(long id) {
		return tagRepo.getTagById(id);
	}

	@Override
	public void disableLiveTagById(long id) {
		tagRepo.updateTagStateById(id, CommentRepo.STATUS_DISABLE);
	}

	@Override
	public void enableLiveTagById(long id) {
		tagRepo.updateTagStateById(id, CommentRepo.STATUS_ENABLE);
	}

	@Override
	public void disableLiveTagByIdList(List<Long> idList) {
		tagRepo.batchUpdateTagsState(idList, CommentRepo.STATUS_DISABLE);
	}

	@Override
	public void enableLiveTagByIdList(List<Long> idList) {
		tagRepo.batchUpdateTagsState(idList, CommentRepo.STATUS_ENABLE);
	}

	@Override
	public void save(LiveTagVO tag) {
		if (tag.getId() > 0) {
			WebCheckResult checkResult = TagChecker.checkLiveTagVOForUpdate(tag);
			if (checkResult.isSuccess()) {
				tagRepo.updateTag(tag.toTagInfo());
			} else {
				throw new BaseException(checkResult.getResultMsg());
			}
		} else {
			WebCheckResult checkResult = TagChecker.checkLiveTagVOForSave(tag);
			if (checkResult.isSuccess()) {
				tagRepo.saveTag(tag.toTagInfo());
			} else {
				throw new BaseException(checkResult.getResultMsg());
			}
		}
	}
}

package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.LiveTagVO;

/**
 * 直播标签
 * 
 * @author yebin
 *
 */
public interface LiveTagService {
	/**
	 * 分页查询直播标签
	 * 
	 * @param tagInfoDTO
	 * @return
	 */
	PageVO<ComTagDO> pageQueryLiveTag(TagInfoDTO tagInfoDTO);

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	ComTagDO getLveTagById(long id);

	/**
	 * 禁用
	 * 
	 * @param id
	 */
	void disableLiveTagById(long id);

	/**
	 * 启用
	 * 
	 * @param id
	 */
	void enableLiveTagById(long id);

	/**
	 * 批量禁用
	 * 
	 * @param idList
	 */
	void disableLiveTagByIdList(List<Long> idList);

	/**
	 * 批量启用
	 * 
	 * @param idList
	 */
	void enableLiveTagByIdList(List<Long> idList);

	/**
	 * 保存
	 * 
	 * @param liveTagVO
	 */
	void save(LiveTagVO liveTagVO);
}

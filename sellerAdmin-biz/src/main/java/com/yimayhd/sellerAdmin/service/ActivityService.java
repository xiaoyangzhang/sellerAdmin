package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ActivityVO;
import com.yimayhd.sellerAdmin.model.query.ActivityListQuery;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.result.BaseResult;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ActivityService {
	/**
	 * 获取活动列表(可带查询条件)
	 * 
	 * @return 活动列表
	 */
	PageVO<SnsActivityDO> pageQueryActivities(ActivityListQuery query);

	/**
	 * 获取活动详情
	 * 
	 * @return 活动详情
	 */
	SnsActivityDO getById(long id) throws Exception;

	/**
	 * 保存
	 * 
	 * @param activityVO
	 * @return
	 */
	BaseResult<SnsActivityDO> save(ActivityVO activityVO) throws Exception;

	public com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> getTagInfoByOutIdAndType(long outId,
			String outType);

	/**
	 * 上下架
	 * 
	 * @param ids
	 * @param state
	 * @return
	 */
	boolean updateActivityStateByIList(List<Long> ids, int state);

}

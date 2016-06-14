package com.yimayhd.sellerAdmin.service.draft;

import com.yimayhd.membercenter.client.query.DraftListQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.draft.DraftDetailVO;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;

/**
 * 草稿箱共同逻辑
 * 
 * @author liuxp
 *
 */
public interface DraftService {

	/**
	 * 存储草稿
	 * @param JsonObject
	 * @param draftVO
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	WebOperateResult saveDraft(Object JsonObject, DraftVO draftVO);
	
	/**
	 * 覆盖草稿
	 * @param JsonObject
	 * @param draftVO
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	WebOperateResult coverDraft(Object JsonObject, DraftVO draftVO);
	
	/**
	 * 删除草稿
	 * @param id
	 * @param accountIds
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	WebOperateResult deleteDraft(Long id, Long accountIds);
	
	/**
	 * 获得草稿列表
	 * @param sellerId
	 * @param draftListQuery
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	WebResult<PageVO<DraftVO>> getDraftList(long sellerId, DraftListQuery draftListQuery);

	/**
	 * 通过id获得草稿详细信息
	 * @param id
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月8日
	 */
	WebResult<DraftDetailVO> getDetailById(Long id);
}

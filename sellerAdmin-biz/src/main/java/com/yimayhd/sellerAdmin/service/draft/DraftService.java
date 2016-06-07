package com.yimayhd.sellerAdmin.service.draft;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.model.query.DraftListQuery;

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
	 * @param draftVo
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	WebOperateResult saveDraft(Object JsonObject, DraftVO draftVo);
	
	/**
	 * 覆盖草稿
	 * @param JsonObject
	 * @param draftVo
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	WebOperateResult coverDraft(Object JsonObject, DraftVO draftVo);
	
	/**
	 * 删除草稿
	 * @param id
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	WebOperateResult deleteDraft(Long id);
	
	/**
	 * 获得草稿列表
	 * @param sellerId
	 * @param draftListQuery
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	WebResult<PageVO<DraftVO>> getDraftList(long sellerId, DraftListQuery draftListQuery);
	
	//TODO 跳转
}

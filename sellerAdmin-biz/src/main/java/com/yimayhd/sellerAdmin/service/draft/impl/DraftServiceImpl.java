package com.yimayhd.sellerAdmin.service.draft.impl;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.model.query.DraftListQuery;
import com.yimayhd.sellerAdmin.service.draft.DraftService;

/**
 * 草稿箱共同逻辑
 * 
 * @author liuxp
 *
 */
public class DraftServiceImpl implements DraftService {

	/**
	 * 存储草稿
	 * @param JsonObject
	 * @param draftVo
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult saveDraft(Object JsonObject, DraftVO draftVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 覆盖草稿
	 * @param JsonObject
	 * @param draftVo
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult coverDraft(Object JsonObject, DraftVO draftVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 删除草稿
	 * @param id
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult deleteDraft(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得草稿列表
	 * @param sellerId
	 * @param draftListQuery
	 * @return
	 * @author yimay
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebResult<PageVO<DraftVO>> getDraftList(long sellerId, DraftListQuery draftListQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}

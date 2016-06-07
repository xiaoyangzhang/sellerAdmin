package com.yimayhd.sellerAdmin.service.draft.impl;

import com.yimayhd.membercenter.client.domain.draft.DraftDO;
import com.yimayhd.membercenter.client.dto.DraftDTO;
import com.yimayhd.membercenter.client.query.DraftListQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.DraftChecker;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.draft.DraftDetailVO;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.repo.DraftRepo;
import com.yimayhd.sellerAdmin.service.draft.DraftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 草稿箱共同逻辑
 * 
 * @author liuxp
 *
 */
public class DraftServiceImpl implements DraftService {

	@Autowired
	private DraftRepo draftRepo;

    private Logger log	= LoggerFactory.getLogger(DraftServiceImpl.class);

    /**
	 * 存储草稿
	 * @param JsonObject 草稿信息
	 * @param draftVO 配置信息
	 * @return 返回结果
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult saveDraft(Object JsonObject, DraftVO draftVO) {
        DraftDO draftDO = DraftChecker.checkSaveDraft(JsonObject, draftVO);
        if(null==draftDO) {
            log.error("DraftServiceImpl.saveDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
		try {
			MemResult<Boolean> result = draftRepo.saveDraft(draftDO);
			if(result.isSuccess()) {
				return WebOperateResult.success();
			} else {
                log.error("DraftServiceImpl.saveDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
                return 	WebOperateResult.failure(WebReturnCode.REMOTE_CALL_FAILED);
			}
		}catch (Exception e) {
            log.error("DraftServiceImpl.saveDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
            log.error("DraftServiceImpl.saveDraft error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 覆盖草稿
	 * @param JsonObject
	 * @param draftVO
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult coverDraft(Object JsonObject, DraftVO draftVO) {
        DraftDO draftDO = DraftChecker.checkCoverDraft(JsonObject, draftVO);
        if(null==draftDO) {
            log.error("DraftServiceImpl.coverDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
        try {
            MemResult<Boolean> result = draftRepo.coverDraft(draftDO);
            if(result.isSuccess()) {
                return WebOperateResult.success();
            } else {
                log.error("DraftServiceImpl.coverDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
                return 	WebOperateResult.failure(WebReturnCode.REMOTE_CALL_FAILED);
            }
        }catch (Exception e) {
            log.error("DraftServiceImpl.coverDraft: JsonObject=" + JsonObject + ", draftVO="+ draftVO);
            log.error("DraftServiceImpl.coverDraft error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
	}

	/**
	 * 删除草稿
	 * @param id
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebOperateResult deleteDraft(Long id) {
        if(null==id) {
            log.error("DraftServiceImpl.deleteDraft: id=" + id);
            log.error("DraftServiceImpl.deleteDraft error: no draftId!");
        }
        try {
            MemResult<Boolean> result = draftRepo.deleteDraft(id);
            if(result.isSuccess()) {
                return WebOperateResult.success();
            } else {
                log.error("DraftServiceImpl.deleteDraft: id=" + id);
                return 	WebOperateResult.failure(WebReturnCode.REMOTE_CALL_FAILED);
            }
        }catch (Exception e) {
            log.error("DraftServiceImpl.deleteDraft: id=" + id);
            log.error("DraftServiceImpl.deleteDraft error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
	}

	/**
	 * 获得草稿列表
	 * @param sellerId
	 * @param draftListQuery
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月2日
	 */
	@Override
	public WebResult<PageVO<DraftVO>> getDraftList(long sellerId, DraftListQuery draftListQuery) {
        draftListQuery.setAccountId(sellerId);
        draftListQuery.setDomainId(Constant.DOMAIN_JIUXIU);

        if(DraftChecker.checkDraftList(draftListQuery)) {
            log.error("DraftServiceImpl.getDraftList: sellerId=" + sellerId + ", draftListQuery="+draftListQuery);
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        try {
            MemPageResult<DraftDTO> result = draftRepo.getDraftList(draftListQuery);
            if(result.isSuccess()) {
                if(result.getList().size()>0) {
                    List<DraftVO> draftVOList = new ArrayList<DraftVO>();
                    for (DraftDTO draftDTO : result.getList()) {
                        DraftVO draftVO = new DraftVO();
                        draftVO.setId(draftDTO.getId());
                        draftVO.setDraftName(draftDTO.getDraftName());
                        draftVO.setSubTypeName(draftDTO.getSubTypeName());
                        draftVO.setGmtCreated(draftDTO.getGmtCreated());
                        draftVOList.add(draftVO);
                    }
                    PageVO<DraftVO> pages = new PageVO<DraftVO>(result.getPageNo(), result.getPageSize(), result.getTotalCount(), draftVOList);
                    return WebResult.success(pages);
                } else {
                    return WebResult.success(new PageVO<DraftVO>(result.getPageNo(), result.getPageSize(), result.getTotalCount()));
                }
            } else {
                log.error("DraftServiceImpl.getDraftList: sellerId=" + sellerId + ", draftListQuery="+draftListQuery);
                return 	WebResult.failure(WebReturnCode.REMOTE_CALL_FAILED);
            }
        }catch (Exception e) {
            log.error("DraftServiceImpl.getDraftList: sellerId=" + sellerId + ", draftListQuery="+draftListQuery);
            log.error("DraftServiceImpl.deleteDraft error", e);
            return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
	}

    @Override
    public WebResult<DraftDetailVO> getDetailByType(Long sellerId, int mainType, int subType) {


        return null;
    }
}

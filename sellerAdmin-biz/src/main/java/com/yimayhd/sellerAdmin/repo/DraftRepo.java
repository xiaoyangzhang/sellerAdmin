package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.draft.DraftDO;
import com.yimayhd.membercenter.client.dto.DraftDTO;
import com.yimayhd.membercenter.client.dto.DraftDetailDTO;
import com.yimayhd.membercenter.client.query.DraftListQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.DraftManagerService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * Created by liuxp on 2016/6/7.
 */
public class DraftRepo {

    private Logger log	= LoggerFactory.getLogger(DraftRepo.class);

    @Autowired
    private DraftManagerService draftManagerServiceRef;


    public MemResult<Boolean> saveDraft(DraftDO draftDO) {

        if(null==draftDO) {
            log.error("DraftRepo.saveDraft(draftDO) error: draftDO is null");
            throw new BaseException("参数为null ");
        }
        RepoUtils.requestLog(log, "draftManagerServiceRef.saveDraft", draftDO);
        MemResult<Boolean> result = draftManagerServiceRef.saveDraft(draftDO);
        RepoUtils.resultLog(log, "draftManagerServiceRef.saveDraft", result);
        return result;
    }

    public MemResult<Boolean> coverDraft(DraftDO draftDO) {

        if(null==draftDO) {
            log.error("DraftRepo.coverDraft(draftDO) error: draftDO is null");
            throw new BaseException("参数为null ");
        }
        RepoUtils.requestLog(log, "draftManagerServiceRef.coverDraft", draftDO);
        MemResult<Boolean> result = draftManagerServiceRef.coverDraft(draftDO);
        RepoUtils.resultLog(log, "draftManagerServiceRef.coverDraft", result);
        return result;
    }

    public MemResult<Boolean> deleteDraft(Long id, Long accountId) {
        if(null==id||null==accountId) {
            log.error("DraftRepo.deleteDraft(id, accountId) error: id or accountId is null");
            throw new BaseException("参数为null ");
        }
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        RepoUtils.requestLog(log, "draftManagerServiceRef.deleteDraft", ids, accountId);
        MemResult<Boolean> result = draftManagerServiceRef.deleteDrafts(ids, accountId);
        RepoUtils.resultLog(log, "draftManagerServiceRef.deleteDraft", result);
        return result;
    }

    public MemPageResult<DraftDTO> getDraftList(DraftListQuery draftListQuery) {
        if(null==draftListQuery) {
            log.error("DraftRepo.getDraftList(draftListQuery) error: draftListQuery is null");
            throw new BaseException("参数为null ");
        }
        RepoUtils.requestLog(log, "draftManagerServiceRef.getDraftList", draftListQuery);
        MemPageResult<DraftDTO> result = draftManagerServiceRef.getDraftList(draftListQuery);
        RepoUtils.resultLog(log, "draftManagerServiceRef.getDraftList", result);
        return result;
    }
    
    public MemResult<DraftDetailDTO> getDraftDetailById(Long id) {
    	
    	if(null==id) {
    		log.error("DraftRepo.getDraftDetailById(id) error: id is null");
            throw new BaseException("参数为null ");
    	}
    	RepoUtils.requestLog(log, "draftManagerServiceRef.getDraftDetailById", id);
    	MemResult<DraftDetailDTO> result = draftManagerServiceRef.getDraftDetail(id);
        RepoUtils.resultLog(log, "draftManagerServiceRef.getDraftDetailById", result);
    	return result;
    }
}

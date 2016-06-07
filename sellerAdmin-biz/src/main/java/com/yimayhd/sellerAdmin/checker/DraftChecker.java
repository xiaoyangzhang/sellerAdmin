package com.yimayhd.sellerAdmin.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.membercenter.client.domain.draft.DraftDO;
import com.yimayhd.membercenter.client.query.DraftListQuery;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.service.draft.impl.DraftServiceImpl;

/**
 * 草稿箱checker
 * @author liuxp
 *
 */
public class DraftChecker {
	
	/**
	 * 日志
	 */
    private static Logger log	= LoggerFactory.getLogger(DraftServiceImpl.class);
	
    /**
     * 验证并转换保存草稿消息
     * @param JsonObject
     * @param draftVO
     * @return
     * @author liuxp
     * @createTime 2016年6月7日
     */
	public static DraftDO checkSaveDraft(Object JsonObject, DraftVO draftVO) {
		
		DraftDO draftDO = new DraftDO();
		try {
			String jsonStr = JSONObject.toJSONString(JsonObject).toString();
			draftDO.setJSONStr(jsonStr);
		} catch (Exception e) {
			log.error("DraftChecker.checkSaveDraft error!");
			return null;
		}
		if(null==draftVO.getAccountId()||StringUtils.isEmpty(draftVO.getDraftName())||draftVO.getMainType()<=0||draftVO.getSubType()<=0||StringUtils.isEmpty(draftDO.getJSONStr())) {
			log.error("DraftChecker.checkSaveDraft error!");
			return null;
		}
		draftDO.setAccountId(draftVO.getAccountId());
		draftDO.setDraftName(draftVO.getDraftName());
		draftDO.setMainType(draftVO.getMainType());
		draftDO.setSubType(draftVO.getSubType());
		draftDO.setDomainId(Constant.DOMAIN_JIUXIU);
		return draftDO;
	}
	
	/**
	 * 验证并转换覆盖草稿信息
	 * @param JsonObject
	 * @param draftVO
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月7日
	 */
	public static DraftDO checkCoverDraft(Object JsonObject, DraftVO draftVO) {
		
		DraftDO draftDO = new DraftDO();
		try {
			String jsonStr = JSONObject.toJSONString(JsonObject).toString();
			draftDO.setJSONStr(jsonStr);
		} catch (Exception e) {
			log.error("DraftChecker.checkSaveDraft error!");
			return null;
		}
		if(null==draftVO.getId()) {
			log.error("DraftChecker.checkSaveDraft error!");
			return null;
		}
		draftDO.setId(draftVO.getId());
		return draftDO;

	}
	
	/**
	 * 验证草稿箱列表
	 * @param draftListQuery
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月7日
	 */
	public static Boolean checkDraftList(DraftListQuery draftListQuery) {
		
		if(null==draftListQuery.getAccountId()||draftListQuery.getDomainId()<=0||draftListQuery.getMainType()<=0||draftListQuery.getSubType()<=0) {
			log.error("DraftChecker.checkSaveDraft error!");
			return true;
		}
		return false;
	}
	
	
}

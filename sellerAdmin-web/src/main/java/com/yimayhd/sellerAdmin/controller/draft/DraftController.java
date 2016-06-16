package com.yimayhd.sellerAdmin.controller.draft;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.membercenter.client.query.DraftListQuery;
import com.yimayhd.membercenter.enums.DraftEnum;
import com.yimayhd.sellerAdmin.base.BaseDraftController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.DraftConverter;
import com.yimayhd.sellerAdmin.enums.BizDraftSubType;
import com.yimayhd.sellerAdmin.model.draft.DraftDetailVO;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.service.draft.DraftService;

/**
 * 草稿箱
 *
 * @author xiemingna
 *
 */
@Controller
@RequestMapping("/draft")
public class DraftController extends BaseDraftController {

	@Autowired
	private DraftService draftService;

	/**
	 * 草稿箱列表获取接口
	 * @param query 查询条件
	 * @return 草稿箱列表
	 * @author liuxp
	 * @createTime 2016年6月15日
	 */
	@RequestMapping(value = "/list")
	public String list(DraftListQuery query) {
		try {
			long sellerId = getCurrentUserId();
			if (sellerId <= 0) {
				log.warn("未登录");
				throw new BaseException("请登陆后重试");
			}
			query.setDomainId(Constant.DOMAIN_JIUXIU);
			query.setAccountId(sellerId);
			query.setMainType(query.getMainType());
			query.setSubType(query.getSubType());
			WebResult<PageVO<DraftVO>> result = draftService.getDraftList(sellerId, query);
			if (result.isSuccess()) {
				put("pageVo", result.getValue());
			}
			BizDraftSubType[] draftSubTypes = BizDraftSubType.values();
			put("query", query);
			put("draftTypeList", draftSubTypes);
			return "/system/draft/list";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BaseException("系统错误");
		}
	}

	/**
	 * 保存/覆盖草稿
	 * 
	 * @param json 草稿json数据
	 * @param uuid 多次提交唯一标识id
	 * @param draftVO 草稿具体属性信息
	 * @return 保存结果
	 * @author liuxp
	 * @createTime 2016年6月15日
	 */
	@RequestMapping(value = "/saveLineDraft")
	public @ResponseBody WebResultSupport saveLineDraft(String json, String uuid, DraftVO draftVO) {
		try {
			long sellerId = getCurrentUserId();
			if (sellerId <= 0) {
				log.warn("未登录");
				return WebOperateResult.failure(WebReturnCode.USER_NOT_FOUND);
			}
			if (StringUtils.isBlank(json)) {
				log.warn("json is null");
				return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
			}
			json = json.replaceAll("\\s*\\\"\\s*", "\\\"");
			LineVO gt = (LineVO) JSONObject.parseObject(json, LineVO.class);
			
			draftVO.setDraftName(draftVO.getDraftName());
			draftVO.setJsonObject(json);
			draftVO.setId(draftVO.getId());
			draftVO.setAccountId(sellerId);
			draftVO.setDomainId(Constant.DOMAIN_JIUXIU);
			draftVO.setMainType(DraftEnum.ITEM.getValue());
			BizDraftSubType bizDraftSubType = BizDraftSubType.get(draftVO.getSubType());
			draftVO.setSubTypeName(bizDraftSubType.getText());
			if (null == draftVO.getId()) {
				WebResult<Long> resultSave = draftService.saveDraft(json, draftVO);
				if (resultSave.isSuccess()) {
					return WebOperateResult.success(resultSave.getValue().toString());
				} else if (resultSave.getErrorCode() == WebReturnCode.DRAFTNAME_REPEAT_ERROR.getErrorCode()) {
					return WebOperateResult.success(WebReturnCode.DRAFTNAME_REPEAT_ERROR.getErrorMsg());
				}
			} else {
				WebOperateResult resultCover = draftService.coverDraft(json, draftVO);
				if (resultCover.isSuccess()) {
					return WebOperateResult.success("保存草稿成功");
				} else if (resultCover.getErrorCode() == WebReturnCode.DRAFTNAME_REPEAT_ERROR.getErrorCode()) {
					return WebOperateResult.success(WebReturnCode.DRAFTNAME_REPEAT_ERROR.getErrorMsg());
				}
			}

			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, e.getMessage());
		}
		return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
	}

	/**
	 * 草稿箱删除功能
	 * 
	 * @param id
	 * @return
	 * @author liuxp
	 * @createTime 2016年6月15日
	 */
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody WebOperateResult delete(@PathVariable("id") long id) {
		long sellerId = getCurrentUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
		}
		if (id > 0) {
			WebOperateResult deleteDraft = draftService.deleteDraft(id,sellerId);
			if (deleteDraft.isSuccess()) {
				return WebOperateResult.success("删除草稿成功");
			} else {
				return deleteDraft;
			}
		} else {
			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
		}
	}

	/**
	 * 草稿箱编辑跳转
	 * @param id
	 * @param mainType
	 * @param subType
	 * @return
	 * @author liuxp
	 * @throws Exception 
	 * @createTime 2016年6月14日
	 */
	@RequestMapping(value = "/edit/{id}/{mainType}/{subType}")
	public String edit(@PathVariable("id") long id, @PathVariable("mainType") int mainType, @PathVariable("subType") int subType) throws Exception {
		if (id>0 && mainType>0 && subType>0) {
		    
		    if(mainType == DraftEnum.ITEM.getValue()) {
		    	return draftRedirectToItem(id, mainType, subType);
		    } else {
				throw new BaseException("unsupport DraftType " + mainType);
		    }
		} else {
			throw new BaseException("参数错误");
		}
	}
	
	/**
	 * 重定向到商品
	 * @param id
	 * @param mainType
	 * @param subType
	 * @return
	 * @author liuxp
	 * @throws Exception 
	 * @createTime 2016年6月14日
	 */
	private String draftRedirectToItem(Long id, int mainType, int subType) throws Exception {
		ItemType itemType = BizDraftSubType.get(subType).getValue();
		if (ItemType.FREE_LINE.getValue() == itemType.getValue()
				|| ItemType.TOUR_LINE.getValue() == itemType.getValue()
				|| ItemType.TOUR_LINE_ABOARD.getValue() == itemType.getValue()
				|| ItemType.FREE_LINE_ABOARD.getValue() == itemType.getValue()) {
			return editLineDraft(id);
		} else if (ItemType.CITY_ACTIVITY.getValue() == itemType.getValue()) {
			return redirect("/draft/cityactivity/edit/" + id);
		} else if (ItemType.NORMAL.getValue() == itemType.getValue()) {
			return redirect("/draft/barterItem/common/edit/" + id);
		} else {
			throw new BaseException("unsupport ItemType " + itemType);
		}
	}

	/**
	 * 重定向到线路商品
	 * @param id
	 * @return
	 * @author liuxp
	 * @throws Exception 
	 * @createTime 2016年6月14日
	 */
	public String editLineDraft(Long id) throws Exception {
		if (id > 0) {
			long sellerId = sessionManager.getUserId();
			Preconditions.checkState(sellerId > 0, "请登录后访问");
			initBaseInfo();
			if (id > 0) {
				WebResult<DraftDetailVO> draftDetailVOResult = draftService.getDetailById(id);
				LineVO gt = DraftConverter.toLineVOWithDraftDetailVO(draftDetailVOResult);
				BaseInfoVO baseInfo = gt.getBaseInfo();
				if (baseInfo != null) {
					initLinePropertyTypes(baseInfo.getCategoryId());
				}
				initBaseInfo();
				
				// 线路商品信息
				put("product", gt);
				
				// 草稿id信息
				put("draftId", id);
				return "/system/comm/line/detail";
			} else {
				throw new BaseException("参数错误");
			}
		} else {
			throw new BaseException("参数错误");
		}
	}

//	@RequestMapping(value = "/cityactivity/edit/{id}")
	public String editCityactivityDraft(DraftVO draftVO, @PathVariable("id") long id) {
		if (null != draftVO && id > 0) {
			// int mainType = draftVO.getMainType();
			int subType = draftVO.getSubType();
			ItemType itemType = BizDraftSubType.get(subType).getValue();
		}
		return redirect("/draft/list");
	}

//	@RequestMapping(value = "/barterItem/common/edit/{id}")
	public String editBarterItemDraft(DraftVO draftVO, @PathVariable("id") long id) {
		if (null != draftVO && id > 0) {
			// int mainType = draftVO.getMainType();
			int subType = draftVO.getSubType();
			ItemType itemType = BizDraftSubType.get(subType).getValue();
		}
		return redirect("/draft/list");
	}
}

package com.yimayhd.sellerAdmin.controller.draft;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.LineChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.draft.DraftVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.query.DraftListQuery;
import com.yimayhd.sellerAdmin.service.draft.DraftService;

/**
 * 草稿箱
 *
 * @author xiemingna
 *
 */
@Controller
@RequestMapping("/draft")
public class DraftController extends BaseController {

	@Autowired
	private DraftService draftService;

	@RequestMapping(value = "/list")
	public String list(DraftListQuery query) throws Exception {
		// long sellerId = getCurrentUserId();
		// if (sellerId <= 0) {
		// log.warn("未登录");
		// throw new BaseException("请登陆后重试");
		// }
		// WebResult<PageVO<ItemListItemVO>> result =
		// itemService.getItemList(sellerId, query);
		// if (!result.isSuccess()) {
		// throw new BaseException(result.getResultMsg());
		// }
		// put("pageVo", result.getValue());
		// put("itemTypeList", BizItemType.values());
		// put("itemStatusList", BizItemStatus.values());
		// put("query", query);
		return "/system/draft/list";
	}

	@RequestMapping(value = "/saveLineDraft")
	public @ResponseBody WebResultSupport saveLineDraft(
			String json,
			String uuid,
			@RequestParam(value = "draftId", required = false) Long draftId,
			@RequestParam(value = "draftName") String draftName) {
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
			WebCheckResult checkLine = LineChecker.checkLine(gt);
			DraftVO draftVO = new DraftVO();
			draftVO.setDraftName(draftName);
			draftVO.setJsonObject(json);
			draftVO.setId(draftId);
//			draftVO.setMainType(DraftEnum.MainType);
			WebOperateResult result = draftService.saveDraft(json, draftVO);
			if (!result.isSuccess()) {
				return checkLine;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR,
					e.getMessage());
		}
		return null;
	}
}

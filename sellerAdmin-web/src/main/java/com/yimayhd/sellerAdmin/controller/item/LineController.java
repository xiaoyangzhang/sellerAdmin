package com.yimayhd.sellerAdmin.controller.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yimayhd.sellerAdmin.service.draft.DraftService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.BaseLineController;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.checker.LineChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.service.item.LineService;

/**
 * 线路商品
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/line")
public class LineController extends BaseLineController {
	@Autowired
	private LineService commLineService;
	@Autowired
	private CacheManager cacheManager ;
	@Autowired
	private ItemQueryService itemQueryService;
    @Autowired
    private MerchantItemCategoryService merchantItemCategoryService;

	@Autowired
	private DraftService draftService;
	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") long id) throws Exception {
		long sellerId = sessionManager.getUserId();
		Preconditions.checkState(sellerId > 0, "请登录后访问");
		initBaseInfo();
		if (id > 0) {
			WebResult<LineVO> result = commLineService.getByItemId(sellerId, id);
			if (result.isSuccess()) {
				LineVO gt = result.getValue();
				BaseInfoVO baseInfo = gt.getBaseInfo();
				if (baseInfo != null) {
					initLinePropertyTypes(baseInfo.getCategoryId());
				}
				put("product", gt);
				put("isReadonly", false);
				return "/system/comm/line/detail";
			} else {
				throw new BaseException(result.getResultMsg());
			}
		} else {
			throw new BaseException("参数错误");
		}
	}

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view/{id}/", method = RequestMethod.GET)
	public String view(@PathVariable(value = "id") long id) throws Exception {
		long sellerId = sessionManager.getUserId();
		Preconditions.checkState(sellerId > 0, "请登录后访问");
		initBaseInfo();
		if (id > 0) {
			WebResult<LineVO> result = commLineService.getByItemId(sellerId, id);
			if (result.isSuccess()) {
				LineVO gt = result.getValue();
				BaseInfoVO baseInfo = gt.getBaseInfo();
				if (baseInfo != null) {
					initLinePropertyTypes(baseInfo.getCategoryId());
				}
				put("product", gt);
				put("isReadonly", true);
				return "/system/comm/line/detail";
			} else {
				throw new BaseException(result.getResultMsg());
			}
		} else {
			throw new BaseException("参数错误");
		}
	}

	/**
	 * 创建跟团游
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category/{categoryId}/create/", method = RequestMethod.GET)
	public String create(Model model,@PathVariable(value = "categoryId") long categoryId) throws Exception {
		long sellerId = getCurrentUserId();
		 /**categoryId 权限验证**/
        MemResultSupport memResultSupport =merchantItemCategoryService.checkCategoryPrivilege(Constant.DOMAIN_JIUXIU, categoryId, sellerId);
        if(!memResultSupport.isSuccess()){
        	 return "/system/error/lackPermission";
        }
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		put("lineType", LineType.TOUR_LINE);
		model.addAttribute("UUID",UUID.randomUUID().toString());
		put("isDraft", true);
		return "/system/comm/line/detail";
	}

	/**
	 * 批量录入
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert(long categoryId) throws Exception {
		initLinePropertyTypes(categoryId);
		return "/system/comm/line/batchInsert";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody WebResultSupport save(String json,String uuid, String draftId) {
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
			if (!checkLine.isSuccess()) {
				log.error(checkLine.getResultMsg());
				return checkLine;
			}
			long itemId = gt.getBaseInfo().getItemId();
			if (itemId > 0) {
				List<Long> itemIds = new ArrayList<Long>();
				itemIds.add(itemId);
				ICResult<List<ItemDO>> itemQueryResult = itemQueryService.getItemByIds(itemIds);
				if(itemQueryResult.getModule() != null && itemQueryResult.getModule().size() > 0 && itemQueryResult.getModule().get(0).getSellerId() != sellerId) {
					
					log.warn("不支持的操作");
					return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
				}
				return  commLineService.update(sellerId, gt);
				
				
			} else {
				String key = Constant.APP+"_repeat_"+sessionManager.getUserId()+uuid;
				boolean rs = cacheManager.addToTair(key, true , 2, 24*60*60);
				if (!rs) {
					log.warn("重复添加");
					return WebOperateResult.failure(WebReturnCode.REPEAT_ERROR);
				}
				WebResult<Long> result = commLineService.save(sellerId, gt);
				if(result.isSuccess()&&!StringUtils.isEmpty(draftId)) {
					WebOperateResult deleteDraft = draftService.deleteDraft(Long.parseLong(draftId), sellerId);
				}
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.parseInt("100011111111"));
	}
}

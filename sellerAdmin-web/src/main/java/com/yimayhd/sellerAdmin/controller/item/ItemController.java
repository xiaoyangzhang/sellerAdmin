package com.yimayhd.sellerAdmin.controller.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;
import com.yimayhd.sellerAdmin.enums.BizItemType;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.item.ItemService;
import com.yimayhd.sellerAdmin.vo.menu.CategoryVO;
import com.yimayhd.stone.enums.DomainType;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.enums.UserOptions;

/**
 * 商品管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
	@Autowired
	private ItemService		itemService;
	@Autowired
	private CategoryService	categoryService;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(ItemListQuery query) throws Exception {
		long sellerId = getCurrentUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			throw new BaseException("请登陆后重试");
		}
		WebResult<PageVO<ItemListItemVO>> result = itemService.getItemList(sellerId, query);
		if (!result.isSuccess()) {
			throw new BaseException(result.getResultMsg());
		}
		put("pageVo", result.getValue());
		put("itemTypeList", BizItemType.values());
		put("itemStatusList", BizItemStatus.values());
		put("query", query);
		return "/system/comm/itemList";
	}

	@RequestMapping(value = "/cateList")
	public String cateList() {
		return "/system/comm/category";
	}

	@ResponseBody
	@RequestMapping(value = "/getcate")
	public List<CategoryVO> getcate(HttpServletRequest request) {
		// 判断是否是达人
		UserDO user = sessionManager.getUser(request);
		long option = user.getOptions();
		boolean isTalent = UserOptions.USER_TALENT.has(option);

		String cateId = get("categoryId");
		List<CategoryVO> list = null;
		if (StringUtils.isBlank(cateId)) {
			WebResult<CategoryDO> webResult = categoryService.getCategoryByDomainId(DomainType.DOMAIN_JX.getType());
			if (null != webResult && webResult.getValue() != null) {
				list = categoryDoTOVo(webResult.getValue().getChildren(), isTalent);
			}
		} else {
			// 查询某节点下的子节点
			WebResult<CategoryDO> webResult = categoryService.getCategoryById(Integer.parseInt(cateId));
			if (null != webResult && webResult.getValue() != null) {
				list = categoryDoTOVo(webResult.getValue().getChildren(),false);
			}
		}
		return list;
	}

	private List<CategoryVO> categoryDoTOVo(List<CategoryDO> childrenList, boolean isTalent) {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		if (CollectionUtils.isEmpty(childrenList)) {
			return list;
		}
		for (CategoryDO categoryDO : childrenList) {
			if (isTalent ){
				if(categoryDO.getCategoryFeature().getItemType() != ItemType.NORMAL.getValue()){
					CategoryVO vo = new CategoryVO();
					vo.setCategoryId(categoryDO.getId());
					vo.setIsLeaf(categoryDO.getLeaf());
					vo.setLevel(categoryDO.getLevel());
					vo.setCategoryName(categoryDO.getName());
					list.add(vo);
				}
			}else {
				CategoryVO vo = new CategoryVO();
				vo.setCategoryId(categoryDO.getId());
				vo.setIsLeaf(categoryDO.getLeaf());
				vo.setLevel(categoryDO.getLevel());
				vo.setCategoryName(categoryDO.getName());
				list.add(vo);
			} 
		}
		return list;
	}

	@RequestMapping(value = "/category/{categoryId}/create")
	public String createItem(@PathVariable(value = "categoryId") long categoryId) throws Exception {
		CategoryDO categoryDO = categoryService.getCategoryDOById(categoryId);
		if (categoryId <= 0 || categoryDO == null) {
			log.warn("无效categoryId");
			throw new BaseException("无效categoryId");
		}
		ItemType itemType = ItemType.get(categoryDO.getCategoryFeature().getItemType());
		if (itemType == null) {
			log.error("category {} ItemType is null", categoryId);
			throw new BaseException("ItemType is null");
		}
		// TODO YEBIN 待开发
		if (ItemType.FREE_LINE.equals(itemType) || ItemType.TOUR_LINE.equals(itemType)) {
			return redirect("/line/category/" + categoryId + "/create/");
		} else if (ItemType.CITY_ACTIVITY.equals(itemType)) {
			return redirect("/cityactivity/toAdd?categoryId=" + categoryId);
		} else if (ItemType.NORMAL.equals(itemType)) {
			return redirect("/barterItem/common/toAdd?categoryId=" + categoryId);
		} else {
			throw new BaseException("unsupport ItemType " + itemType.name());
		}
	}

	@RequestMapping(value = "/{id}/operate")
	public @ResponseBody WebOperateResult opeate(@PathVariable("id") long id, @RequestParam("operate") String operate) {
		long sellerId = getCurrentUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
		}
		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
			return itemService.shelve(sellerId, id);
		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
			return itemService.unshelve(sellerId, id);
		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
			return itemService.delete(sellerId, id);
		} else {
			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
		}
	}

	@RequestMapping(value = "/batchOperate")
	public @ResponseBody WebOperateResult batchPerate(@RequestParam("itemIds[]") Long[] itemIds,
			@RequestParam("operate") String operate) {
		long sellerId = getCurrentUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
		}
		if (ArrayUtils.isEmpty(itemIds)) {
			log.warn("itemIds is null");
			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
		}
		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
			return itemService.batchShelve(sellerId, Arrays.asList(itemIds));
		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
			return itemService.batchUnshelve(sellerId, Arrays.asList(itemIds));
		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
			return itemService.batchDelete(sellerId, Arrays.asList(itemIds));
		} else {
			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
		}
	}

	@RequestMapping(value = "/{id}/type/{type}/edit")
	public String detail(@PathVariable(value = "id") long itemId, @PathVariable(value = "type") int itemType) {
		// TODO YEBIN 待开发
		if (ItemType.FREE_LINE.getValue() == itemType || ItemType.TOUR_LINE.getValue() == itemType) {
			return redirect("/line/edit/" + itemId + "/");
		} else if (ItemType.CITY_ACTIVITY.getValue() == itemType) {
			return redirect("/cityactivity/edit/" + itemId);
		} else if (ItemType.NORMAL.getValue() == itemType) {
			return redirect("/barterItem/common/edit/" + itemId);
		} else {
			throw new BaseException("unsupport ItemType " + itemType);
		}
	}

	@RequestMapping(value = "/{id}/type/{type}/view")
	public String view(@PathVariable(value = "id") long itemId, @PathVariable(value = "type") int itemType) {
		// TODO YEBIN 待开发
		if (ItemType.FREE_LINE.getValue() == itemType || ItemType.TOUR_LINE.getValue() == itemType) {
			return redirect("/line/view/" + itemId + "/");
		} else if (ItemType.CITY_ACTIVITY.getValue() == itemType) {
			return redirect("/cityactivity/view/" + itemId);
		} else if (ItemType.NORMAL.getValue() == itemType) {
			return redirect("/barterItem/common/view/" + itemId);
		} else {
			throw new BaseException("unsupport ItemType " + itemType);
		}
	}
}
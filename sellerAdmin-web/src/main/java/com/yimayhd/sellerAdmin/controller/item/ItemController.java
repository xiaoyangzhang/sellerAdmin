package com.yimayhd.sellerAdmin.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.repo.CategoryRepo;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.item.ItemService;
import com.yimayhd.sellerAdmin.vo.menu.CategoryVO;
import com.yimayhd.stone.enums.DomainType;

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
	private ItemService itemService;
	@Autowired
	private CategoryService categoryServiceRef;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ItemListQuery query) throws Exception {
		/*
		 * long sellerId = getCurrentUserId(); if (sellerId <= 0) {
		 * log.warn("未登录"); throw new BaseException("请登陆后重试"); }
		 */
		long sellerId = 12800;
		WebResult<PageVO<ItemListItemVO>> result = itemService.getItemList(sellerId, query);
		if (!result.isSuccess()) {
			throw new BaseException(result.getResultMsg());
		}
		put("pageVo", result.getValue());
		put("itemTypes", ItemType.values());
		put("query", query);
		return "/system/comm/itemList";
	}

	@RequestMapping(value = "/cateList", method = RequestMethod.GET)
	public String cateList() {
		return "/system/comm/category";
	}

	@ResponseBody
	@RequestMapping(value = "/getcate", method = RequestMethod.GET)
	public List<CategoryVO> getcate() {
		String cateId = get("categoryId");
		List<CategoryVO> list = null;
		if(StringUtils.isBlank(cateId)){
			WebResult<CategoryDO> webResult = categoryServiceRef.getCategoryByDomainId(DomainType.DOMAIN_JX.getType());
			if( null != webResult && webResult.getValue() != null){
				list = categoryDoTOVo(webResult.getValue().getChildren());
			}
		}else {
			//查询某节点下的子节点
			WebResult<CategoryDO> webResult = categoryServiceRef.getCategoryById(Integer.parseInt(cateId));
			if( null != webResult && webResult.getValue() != null ){
				list = categoryDoTOVo(webResult.getValue().getChildren());
			}
		}
		return list;
	}

	private List<CategoryVO> categoryDoTOVo(List<CategoryDO> children) {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		if (CollectionUtils.isEmpty(children)) {
			return list;
		}
		for (CategoryDO categoryDO : children) {
			CategoryVO vo = new CategoryVO();
			vo.setCategoryId(categoryDO.getId());
			vo.setIsLeaf(categoryDO.getLeaf());
			vo.setLevel(categoryDO.getLevel());
			vo.setCategoryName(categoryDO.getName());
			list.add(vo);
		}
		return list;
	}

	@RequestMapping(value = "/category/{categoryId}/create", method = RequestMethod.GET)
	public String createItem(@PathVariable(value = "categoryId") long categoryId) throws Exception {
		CategoryDO categoryDO = categoryServiceRef.getCategoryDOById(categoryId);
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
		} else {
			throw new BaseException("unsupport ItemType " + itemType.name());
		}
	}

}

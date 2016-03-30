package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.CategoryFeature;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.JsonResultUtil;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * Created by czf on 2015/11/24.
 */
@Controller
@RequestMapping("/B2C/commodityManage")
public class CommodityManageController extends BaseController {

	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 新增商品
	 * 
	 * @return 新增商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model, int categoryId) throws Exception {
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
		CategoryFeature categoryFeature = categoryVO.getCategoryFeature();
		int itemType = categoryFeature.getItemType();// 不可能有空值，就不判断空了

		String redirectUrl = "";
		if (itemType == ItemType.HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/hotelManage/toAdd?categoryId=" + categoryId;
		} else if (itemType == ItemType.SPOTS.getValue()) {
			redirectUrl = "/B2C/comm/scenicManage/toAdd?categoryId=" + categoryId;
		} else if (itemType == ItemType.LINE.getValue()) {
			redirectUrl = "/B2C/comm/groupTravel/create?categoryId=" + categoryId;
		} else if (itemType == ItemType.FLIGHT_HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/selfServiceTravel/create?categoryId=" + categoryId;
		} else if (itemType == ItemType.SPOTS_HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/selfServiceTravel/create?categoryId=" + categoryId;
		} else if (itemType == ItemType.ACTIVITY.getValue()) {
			redirectUrl = "/B2C/comm/activityManage/toAdd?categoryId=" + categoryId;
		} else if (itemType == ItemType.GF.getValue()) {
			redirectUrl = "/GF/commodityManage/toAdd?categoryId=" + categoryId;
		} else {
			// 普通商品，伴手礼应该也走普通商品
			// 库存选项
			List<ReduceType> reduceTypeList = Arrays.asList(ReduceType.values());
			model.addAttribute("reduceTypeList", reduceTypeList);
			model.addAttribute("category", categoryVO);
			model.addAttribute("itemType", ItemType.NORMAL.getValue());
			return "/system/comm/common/edit";
		}
		return "redirect:" + redirectUrl;
	}

	/**
	 * 编辑商品
	 * 
	 * @param model
	 * @param itemId
	 *            商品ID
	 * @param itemType
	 *            商品类型
	 * @return 商品详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{itemId}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "itemId") long itemId, int itemType, int categoryId,
			int outId) throws Exception {
		String redirectUrl = "";
		if (itemType == ItemType.HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/hotelManage/edit/" + itemId;
		} else if (itemType == ItemType.SPOTS.getValue()) {
			redirectUrl = "/B2C/comm/scenicManage/edit/" + itemId;
		} else if (itemType == ItemType.LINE.getValue()) {
			redirectUrl = "/B2C/comm/groupTravel/detail/" + outId + "?categoryId=" + categoryId;
		} else if (itemType == ItemType.FLIGHT_HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/selfServiceTravel/detail/" + outId + "?categoryId=" + categoryId;
		} else if (itemType == ItemType.SPOTS_HOTEL.getValue()) {
			redirectUrl = "/B2C/comm/selfServiceTravel/detail/" + outId + "?categoryId=" + categoryId;
		} else if (itemType == ItemType.ACTIVITY.getValue()) {
			redirectUrl = "/B2C/comm/activityManage/edit/" + itemId;
		} else {
			// 普通商品，伴手礼应该也走普通商品
			ItemResultVO itemResultVO = commodityService.getCommodityById(itemId);
			ItemType.NORMAL.getValue();
			// 库存选项
			List<ReduceType> reduceTypeList = Arrays.asList(ReduceType.values());
			model.addAttribute("reduceTypeList", reduceTypeList);
			model.addAttribute("itemResult", itemResultVO);
			model.addAttribute("commodity", itemResultVO.getItemVO());
			model.addAttribute("category", itemResultVO.getCategoryVO());
			model.addAttribute("itemType", ItemType.NORMAL.getValue());
			return "/system/comm/common/edit";
		}
		return "redirect:" + redirectUrl;
	}

	/**
	 * 新增普通商品
	 * 
	 * @return 新增普通商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/toAdd", method = RequestMethod.GET)
	public String toAddCommon(Model model, long categoryId) throws Exception {
		CategoryDO categoryDO = categoryService.getCategoryVOById(categoryId);
		model.addAttribute("category", categoryDO);
		return "";
	}

	/**
	 * 新增普通商品
	 * 
	 * @return 新增普通商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/add", method = RequestMethod.POST)
	public String addCommon(ItemVO itemVO) throws Exception {
		long sellerId = sessionManager.getUserId();
		sellerId = Constant.YIMAY_OFFICIAL_ID;
		itemVO.setSellerId(sellerId);
		commodityService.addCommonItem(itemVO);
		return "/success";
	}

	/**
	 * 编辑普通商品
	 * 
	 * @param model
	 * @param itemId
	 *            商品ID
	 * @param itemType
	 *            商品类型
	 * @return 普通商品详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/edit/{itemId}", method = RequestMethod.GET)
	public String toEditCommon(Model model, @PathVariable(value = "id") long itemId, int itemType) throws Exception {
		ItemResult itemResult = commodityService.getCommodityById(itemId);
		model.addAttribute("category", itemResult.getCategory());
		model.addAttribute("commodity", itemResult.getItem());
		model.addAttribute("itemSkuList", itemResult.getItemSkuDOList());
		return "/success";
	}

	/**
	 * 修改普通商品
	 * 
	 * @param itemVO
	 * @param itemId
	 *            商品ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/edit/{itemId}", method = RequestMethod.POST)
	public String editCommon(ItemVO itemVO, @PathVariable(value = "itemId") long itemId) throws Exception {
		itemVO.setId(itemId);
		long sellerId = sessionManager.getUserId();
		// TODO
		sellerId = Constant.YIMAY_OFFICIAL_ID;
		itemVO.setSellerId(sellerId);
		commodityService.modifyCommonItem(itemVO);
		return "/success";
	}

	/**
	 * 商品上架
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo publish(@PathVariable("id") long id) throws Exception {
		// long sellerId = sessionManager.getUserId();
		// sellerId = Constant.SELLERID;
		long sellerId = Constant.YIMAY_OFFICIAL_ID;
		commodityService.publish(sellerId, id);
		return new ResponseVo();
	}

	/**
	 * 商品下架
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo close(@PathVariable("id") long id) throws Exception {
		// long sellerId = sessionManager.getUserId();
		long sellerId = Constant.YIMAY_OFFICIAL_ID;
		commodityService.close(sellerId, id);
		return new ResponseVo();
	}

	/**
	 * 商品上架(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchPublish", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchPublish(@RequestParam("commIdList[]") ArrayList<Long> commIdList) throws Exception {
		// long sellerId = sessionManager.getUserId();
		// sellerId = Constant.YIMAY_OFFICIAL_ID;

		long sellerId = Constant.YIMAY_OFFICIAL_ID;
		commodityService.batchPublish(sellerId, commIdList);
		return new ResponseVo();
	}

	/**
	 * 商品下架(批量)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchClose", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchClose(@RequestParam("commIdList[]") ArrayList<Long> commIdList) throws Exception {
		// long sellerId = sessionManager.getUserId();
		long sellerId = Constant.YIMAY_OFFICIAL_ID;
		commodityService.batchClose(sellerId, commIdList);
		return new ResponseVo();
	}

	/**
	 * 商品分类页面
	 * 
	 * @return 商品分类
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCategory", method = RequestMethod.GET)
	public String toCategory(Model model) throws Exception {
		List<CategoryDO> categoryDOList = categoryService.getCategoryDOList();
		model.addAttribute("categoryList", categoryDOList);
		return "/system/comm/category";
	}

	/**
	 * 商品分类子类
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/subCategory/{parentId}", method = RequestMethod.GET)
	@ResponseBody
	public void getCategoryByParentId(HttpServletResponse response, @PathVariable("parentId") long parentId)
			throws Exception {
		List<CategoryDO> categoryDOList = categoryService.getCategoryDOList(parentId);

		for (CategoryDO aCategoryDOList : categoryDOList) {
			aCategoryDOList.setParent(null);
			aCategoryDOList.setChildren(null);
		}
		JsonResultUtil.jsonResult(response, categoryDOList);
	}
}

package com.yimayhd.sellerAdmin;

import com.yimayhd.gf.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.B2CConstant;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemSkuVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.user.session.manager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by czf on 2015/11/24.
 */
@Controller
@RequestMapping("/GF/commodityManage")
public class GFCommodityManageController extends BaseController {

	@Autowired
	private CommodityService gfCommodityService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private com.yimayhd.sellerAdmin.service.CommodityService commodityService;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, CommodityListQuery commodityListQuery) throws Exception {
		PageVO<ItemVO> pageVO = gfCommodityService.getList(commodityListQuery);
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("itemTypeList", itemTypeList);
		model.addAttribute("commodityList", pageVO.getItemList());
		model.addAttribute("commodityListQuery", commodityListQuery);
		return "/system/comm/gf/list";
	}
	/**
	 * 商品列表
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	public ResponseVo listAjax(CommodityListQuery commodityListQuery) throws Exception {
		PageVO<ItemVO> pageVO = gfCommodityService.getList(commodityListQuery);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageVo", pageVO);
		map.put("query", commodityListQuery);
		return new ResponseVo(map);
	}
	/**
	 * 商品sku列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/skuList/{itemId}", method = RequestMethod.GET)
	public String getSkuListAjax(Model model,@PathVariable("itemId") long itemId) throws Exception {
		List<ItemSkuVO> itemSkuVOList = gfCommodityService.getSkuListByItemId(itemId);
		model.addAttribute("skuList",itemSkuVOList);
		return "/system/comm/gf/commSkuList";
	}

	/**
	 * 新增商品(GF)
	 * 
	 * @return 新增商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model,int categoryId) throws Exception {
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
		List<ReduceType> reduceTypeList = Arrays.asList(ReduceType.values());
		model.addAttribute("reduceTypeList", reduceTypeList);
		model.addAttribute("category", categoryVO);
		return "/system/comm/gf/edit";

	}

	/**
	 * 编辑商品
	 * 
	 * @param model
	 * @param itemId 商品ID
	 * @return 商品详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{itemId}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "itemId") long itemId) throws Exception {

		//TODO
		ItemResultVO itemResultVO = gfCommodityService.getCommodityById(itemId);
		//库存选项
		List<ReduceType> reduceTypeList= Arrays.asList(ReduceType.values());
		model.addAttribute("reduceTypeList", reduceTypeList);
		model.addAttribute("itemResult", itemResultVO);
		model.addAttribute("commodity", itemResultVO.getItemVO());
		model.addAttribute("category", itemResultVO.getCategoryVO());
		return "/system/comm/gf/edit";
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
		return "/system/comm/gf/edit";
	}

	/**
	 * 新增普通商品
	 * 
	 * @return 新增普通商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/add", method = RequestMethod.POST)
	public String addCommon(ItemVO itemVO) throws Exception {
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
		itemVO.setSellerId(sellerId);
		gfCommodityService.addCommonItem(itemVO);
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
		ItemResult itemResult = gfCommodityService.getCommodityById(itemId);
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
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
		itemVO.setSellerId(sellerId);
		gfCommodityService.modifyCommonItem(itemVO);
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
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
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
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
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
	public ResponseVo batchPublish(@RequestParam("commIdList[]") ArrayList<Long> commIdList)
			throws Exception {
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
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
	public ResponseVo batchClose(@RequestParam("commIdList[]") ArrayList<Long> commIdList)
			throws Exception {
		long sellerId = B2CConstant.GF_OFFICIAL_ID;
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
		//TODO
		long parentId = 43;
		List<CategoryDO> categoryDOList = categoryService.getCategoryDOList(parentId);
		model.addAttribute("categoryList", categoryDOList);
		return "/system/comm/category";
	}


}

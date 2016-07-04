package com.yimayhd.sellerAdmin.controller.item;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Preconditions;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.checker.BarterItemChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.helper.NumberFormatHelper;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.CommodityService;

/**
 * Created by czf on 2016/4/7.
 */
@Controller
@RequestMapping("/barterItem")
public class BarterItemController extends BaseController {
	@Autowired
	private CommodityService	commodityService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private CacheManager cacheManager ;
    @Autowired
    private MerchantItemCategoryService merchantItemCategoryService;

	/**
	 * 新增普通商品
	 *
	 * @return 新增普通商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/toAdd", method = RequestMethod.GET)
	public String toAddCommon(Model model, long categoryId) throws Exception {
		long sellerId = sessionManager.getUserId();
		 /**categoryId 权限验证**/
        MemResultSupport memResultSupport =merchantItemCategoryService.checkCategoryPrivilege(Constant.DOMAIN_JIUXIU, categoryId, sellerId);
        if(!memResultSupport.isSuccess()){
        	 return "/system/error/lackPermission";
        }
        
        //是否有发积分商品权限
//        MemResult<List<MerchantItemCategoryDO>> result = categoryService.getMerchantItemCategory(Constant.DOMAIN_JIUXIU, categoryId, sellerId);
//        if(null!=result && result.isSuccess() && null!=result.getValue()){
//        	model.addAttribute("integralType", );
//        }
        
		CategoryDO categoryDO = categoryService.getCategoryVOById(categoryId);
		ItemType itemType = ItemType.get(categoryDO.getCategoryFeature().getItemType());
		Preconditions.checkArgument(ItemType.NORMAL.equals(itemType), "错误的商品类型");
		model.addAttribute("category", categoryDO);
		model.addAttribute("UUID",UUID.randomUUID().toString());
		
		return "/system/comm/common/edit";
	}

	/**
	 * 新增普通商品
	 *
	 * @return 新增普通商品页
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/add", method = RequestMethod.POST)
	public String addCommon(Model model, ItemVO itemVO,String uuid) throws Exception {
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId()+uuid;
		boolean rs = cacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			WebCheckResult check = BarterItemChecker.BarterItemCheck(itemVO);
			if (!check.isSuccess()) {
				throw new NoticeException(check.getResultMsg());
			}
			long sellerId = sessionManager.getUserId();
			itemVO.setSellerId(sellerId);
			commodityService.addCommonItem(itemVO);
			model.addAttribute("href", "/item/list");
		}
		return "/success";
	}

	/**
	 * 编辑普通商品
	 *
	 * @param model
	 * @param itemId
	 *            商品ID 商品类型
	 * @return 普通商品详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/edit/{itemId}", method = RequestMethod.GET)
	public String toEditCommon(Model model, @PathVariable(value = "itemId") long itemId) throws Exception {
		long sellerId = sessionManager.getUserId();
		Preconditions.checkState(sellerId > 0, "请登录后访问");
		ItemResultVO itemResultVO = commodityService.getCommodityById(sellerId, itemId);
		Preconditions.checkState(itemResultVO != null, "商品未找到");
		int itemType = itemResultVO.getItemVO().getItemType();
		Preconditions.checkArgument(ItemType.NORMAL.getValue() == itemType, "错误的商品类型");
		model.addAttribute("category", itemResultVO.getCategoryVO());
		model.addAttribute("commodity", itemResultVO.getItemVO());
		model.addAttribute("itemSkuList", itemResultVO.getItemSkuVOList());
		model.addAttribute("pictureText", itemResultVO.getItemVO().getPictureTextVO());
		model.addAttribute("isReadonly", itemResultVO.getItemVO().getStatus() == ItemStatus.valid.getValue());
		model.addAttribute("numberHelper",new NumberFormatHelper() );
		return "/system/comm/common/edit";
	}

	/**
	 * 查看普通商品
	 *
	 * @param model
	 * @param itemId
	 *            商品ID 商品类型
	 * @return 普通商品详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/view/{itemId}", method = RequestMethod.GET)
	public String toViewCommon(Model model, @PathVariable(value = "itemId") long itemId) throws Exception {
		long sellerId = sessionManager.getUserId();
		Preconditions.checkState(sellerId > 0, "请登录后访问");
		ItemResultVO itemResultVO = commodityService.getCommodityById(sellerId, itemId);
		Preconditions.checkState(itemResultVO != null, "商品未找到");
		model.addAttribute("category", itemResultVO.getCategoryVO());
		model.addAttribute("commodity", itemResultVO.getItemVO());
		model.addAttribute("itemSkuList", itemResultVO.getItemSkuVOList());
		model.addAttribute("pictureText", itemResultVO.getItemVO().getPictureTextVO());
		model.addAttribute("isReadonly", true);
		model.addAttribute("numberHelper",new NumberFormatHelper() );
		return "/system/comm/common/edit";
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
	public String editCommon(Model model, ItemVO itemVO, @PathVariable(value = "itemId") long itemId) throws Exception {

		WebCheckResult check = BarterItemChecker.BarterItemCheck(itemVO);
		if (!check.isSuccess()) {
			throw new NoticeException(check.getResultMsg());
		}
		itemVO.setId(itemId);
		long sellerId = sessionManager.getUserId();
		// TODO
		sellerId = Constant.YIMAY_OFFICIAL_ID;
		itemVO.setSellerId(sellerId);
		commodityService.modifyCommonItem(itemVO);
		model.addAttribute("href", "/item/list");
		return "/success";
	}
}

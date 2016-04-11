package com.yimayhd.sellerAdmin.controller.item;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.checker.BarterItemChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Created by czf on 2016/4/7.
 */
@Controller
@RequestMapping("/barterItem")
public class BarterItemController extends BaseController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CategoryService categoryService;

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
        return "/system/comm/common/edit";
    }

    /**
     * 新增普通商品
     *
     * @return 新增普通商品页
     * @throws Exception
     */
    @RequestMapping(value = "/common/add", method = RequestMethod.POST)
    public String addCommon(ItemVO itemVO) throws Exception {

        WebCheckResult check = BarterItemChecker.BarterItemCheck(itemVO);
        if(!check.isSuccess()){
            throw new NoticeException(check.getResultMsg());
        }
        long sellerId = sessionManager.getUserId();
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
     *            商品类型
     * @return 普通商品详情
     * @throws Exception
     */
    @RequestMapping(value = "/common/edit/{itemId}", method = RequestMethod.GET)
    public String toEditCommon(Model model, @PathVariable(value = "itemId") long itemId) throws Exception {
        ItemResultVO itemResultVO = commodityService.getCommodityById(itemId);
        model.addAttribute("category", itemResultVO.getCategoryVO());
        model.addAttribute("commodity", itemResultVO.getItemVO());
        model.addAttribute("itemSkuList", itemResultVO.getItemSkuVOList());
        model.addAttribute("pictureText", itemResultVO.getItemVO().getPictureTextVO());
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
    public String editCommon(ItemVO itemVO, @PathVariable(value = "itemId") long itemId) throws Exception {

        WebCheckResult check = BarterItemChecker.BarterItemCheck(itemVO);
        if(!check.isSuccess()){
            throw new NoticeException(check.getResultMsg());
        }
        itemVO.setId(itemId);
        long sellerId = sessionManager.getUserId();
        // TODO
        sellerId = Constant.YIMAY_OFFICIAL_ID;
        itemVO.setSellerId(sellerId);
        commodityService.modifyCommonItem(itemVO);
        return "/success";
    }
}

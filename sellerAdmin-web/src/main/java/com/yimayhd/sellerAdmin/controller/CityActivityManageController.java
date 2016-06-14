package com.yimayhd.sellerAdmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.CategoryFeature;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.checker.CityActivityChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 活动商品
 * 
 * @author xuzj
 */
@Controller
@RequestMapping("/cityactivity")
public class CityActivityManageController extends BaseController {
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private CityActivityService cityActivityService;
    @Autowired
    private TagService tagService;
    @Autowired
	private CacheManager cacheManager ;
    @Autowired
    private MerchantItemCategoryService merchantItemCategoryService;
	
	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model,int categoryId) throws Exception {
		long sellerId = sessionManager.getUserId();
		 /**categoryId 权限验证**/
        MemResultSupport memResultSupport =merchantItemCategoryService.checkCategoryPrivilege(Constant.DOMAIN_JIUXIU, categoryId, sellerId);
        if(!memResultSupport.isSuccess()){
        	 return "/system/error/lackPermission";
        }
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
        if(categoryVO == null) {
            log.warn("错误的类目");
            throw new BaseException("商品类目错误");
        }
        CategoryFeature categoryFeature = categoryVO.getCategoryFeature();
        if(categoryFeature == null || categoryFeature.getItemType() != ItemType.CITY_ACTIVITY.getValue()) {
            log.warn("错误的类目");
            throw new BaseException("商品类目错误");
        }
        WebResult<List<TagDTO>> allThemes = tagService.getAllThemes(TagType.CITYACTIVITY);
        if (allThemes.isSuccess()) {
            put("themes", allThemes.getValue());
        }
        WebResult<List<CityVO>> allDests = tagService.getAllDests();
        if (allDests.isSuccess()) {
            put("dests", allDests.getValue());
        }
		model.addAttribute("category", categoryVO);
		model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
		model.addAttribute("UUID",UUID.randomUUID().toString());
		return "/system/cityactivity/edit";
	}

    /**
     * 编辑活动（商品）
     * @return 活动（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            throw new BaseException("未登录");
        }
		CityActivityItemVO itemVO = cityActivityService.getCityActivityById(id);
        if(itemVO == null || itemVO.getItemVO() == null || itemVO.getItemVO().getSellerId() != sellerId) {
            throw new BaseException("系统未知错误");
        }
        WebResult<List<TagDTO>> allThemes = tagService.getAllThemes(TagType.CITYACTIVITY);
        if (allThemes.isSuccess()) {
            put("themes", allThemes.getValue());
        }
        WebResult<List<CityVO>> allDests = tagService.getAllDests();
        if (allDests.isSuccess()) {
            put("dests", allDests.getValue());
        }
        model.addAttribute("category", itemVO.getCategoryVO());
    	model.addAttribute("item", itemVO.getItemVO());
        model.addAttribute("cityActivity", itemVO.getCityActivityVO());
        model.addAttribute("itemThemes", itemVO.getThemes());
        model.addAttribute("itemDest", itemVO.getDest());
    	model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
        model.addAttribute("pictureText", itemVO.getPictureTextVO());
        model.addAttribute("needKnow", itemVO.getNeedKnowVO());

        return "/system/cityactivity/edit";
    }

    /**
     * chakan活动（商品）
     * @return 活动（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public
    String toView(Model model,@PathVariable(value = "id") long id) throws Exception {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            throw new BaseException("未登录");
        }
        CityActivityItemVO itemVO = cityActivityService.getCityActivityById(id);
        if(itemVO == null || itemVO.getItemVO() == null || itemVO.getItemVO().getSellerId() != sellerId) {
            throw new BaseException("系统未知错误");
        }
        WebResult<List<TagDTO>> allThemes = tagService.getAllThemes(TagType.CITYACTIVITY);
        if (allThemes.isSuccess()) {
            put("themes", allThemes.getValue());
        }
        WebResult<List<CityVO>> allDests = tagService.getAllDests();
        if (allDests.isSuccess()) {
            put("dests", allDests.getValue());
        }
        model.addAttribute("category", itemVO.getCategoryVO());
        model.addAttribute("item", itemVO.getItemVO());
        model.addAttribute("cityActivity", itemVO.getCityActivityVO());
        model.addAttribute("itemThemes", itemVO.getThemes());
        model.addAttribute("itemDest", itemVO.getDest());
        model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
        model.addAttribute("pictureText", itemVO.getPictureTextVO());
        model.addAttribute("needKnow", itemVO.getNeedKnowVO());
        model.addAttribute("isReadonly", true);
        return "/system/cityactivity/edit";
    }

    /**
     * 编辑活动（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody WebResultSupport edit(String json,String uuid) throws Exception {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
        }
        try {
            CityActivityItemVO itemVO = (CityActivityItemVO) JSONObject.parseObject(json, CityActivityItemVO.class);
            WebCheckResult result = CityActivityChecker.checkCityActivity(itemVO);
            if(!result.isSuccess()) {
                log.warn(result.getResultMsg());
                return result;
            }
            itemVO.getItemVO().setSellerId(sellerId);
            if(itemVO.getItemVO().getId() > 0) {
                return cityActivityService.modifyCityActivityItem(itemVO);
            }
            else {
            	String key = Constant.APP+"_repeat_"+sessionManager.getUserId()+uuid;
        		boolean rs = cacheManager.addToTair(key, true , 2, 24*60*60);
        		if (!rs) {
                    return WebOperateResult.failure(WebReturnCode.REPEAT_ERROR);
                }
                return cityActivityService.addCityActivityItem(itemVO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, "");
        }
    }
	
	
}

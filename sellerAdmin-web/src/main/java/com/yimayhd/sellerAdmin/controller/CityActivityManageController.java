package com.yimayhd.sellerAdmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.CityActivityChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
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
	
	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model,int categoryId) throws Exception {
		
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
        //TODO: add cityActivityTagType
        WebResult<List<TagDTO>> allThemes = tagService.getAllThemes(TagType.LINETAG);
        if (allThemes.isSuccess()) {
            put("themes", allThemes.getValue());
        }
        WebResult<List<CityVO>> allDests = tagService.getAllDests();
        if (allDests.isSuccess()) {
            put("dests", allDests.getValue());
        }
		model.addAttribute("category", categoryVO);
		model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
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

		CityActivityItemVO itemVO = cityActivityService.getCityActivityById(id);
        if(itemVO == null) {
            //TODO: exception
            return "/system/comm/itemList";
        }
        WebResult<List<TagDTO>> allThemes = tagService.getAllThemes(TagType.LINETAG);
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
        model.addAttribute("pictureTextVO", itemVO.getPictureTextVO());
        model.addAttribute("needKnow", itemVO.getNeedKnowVO());

        return "/system/cityactivity/edit";
    }

    /**
     * 编辑活动（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody WebResultSupport edit(String json) throws Exception {
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
                return cityActivityService.addCityActivityItem(itemVO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, "");
        }
    }
	
	
}

package com.yimayhd.sellerAdmin.controller;

import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

/**
 * 活动商品
 * 
 * @author xuzj
 */
@Controller
@RequestMapping("/cityActivity")
public class CityActivityManageController extends BaseController {
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private CityActivityService cityActivityService;
	
	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model,int categoryId) throws Exception {
		
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
		model.addAttribute("category", categoryVO);
		model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
		return "/system/cityactivity/edit";
	}

	 /**
     * 新增活动商品	
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(CityActivityItemVO itemVO) throws Exception {
    	
		//long sellerId = sessionManager.getUserId();
		//itemVO.setSellerId(sellerId)
        cityActivityService.addCityActivityItem(itemVO);
        return "/success";
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
    	model.addAttribute("itemResult", itemVO);
    	model.addAttribute("commodity", itemVO.getItemVO());
    	model.addAttribute("category", itemVO.getCategoryVO());
    	model.addAttribute("itemType",ItemType.CITY_ACTIVITY.getValue());
       
        return "/system/cityactivity/edit";
    }

    /**
     * 编辑活动（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public
    String edit(CityActivityItemVO itemVO,@PathVariable("id") long id) throws Exception {
        cityActivityService.modifyCityActivityItem(itemVO);
        return "/success";
    }
	
	
}

package com.yimayhd.sellerAdmin.controller;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.CommScenicVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ScenicVO;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.CommScenicService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import com.yimayhd.sellerAdmin.service.ScenicService;

/**
 * 发布景区（商品）
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/comm/scenicManage")
public class CommScenicManageController extends BaseController {
	@Autowired
	private CommScenicService commScenicService;
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	private CategoryService categoryService;


	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ScenicService scenicSpotService;
    /**
     * 新增景区
     * @return 景区
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model,long categoryId) throws Exception {
    	CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
    	//主题
    	BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.VIEWTAG.name());
    	if(tagResult!=null){
    		model.addAttribute("tagResult",tagResult.getValue());
    	}
    	model.addAttribute("itemType",ItemType.SPOTS.getValue());
    	model.addAttribute("category", categoryVO);
    	return "/system/comm/scenic/edit";
    }
    
    
    /**
     * 编辑景区（商品）
     * @return
     * @throws Exception,
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo save(CommScenicVO scenicVO) throws Exception {
    	try {
    	
    	ItemPubResult result = commScenicService.save(scenicVO);
    	return  ResponseVo.success(result.getResultMsg());
    	} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}

    }

    /**
     * 编辑景区（商品）
     * @return 景区（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {

        ItemResultVO itemResultVO = commodityService.getCommodityById(id);
        //主题
    	BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.VIEWTAG.name());
    	BaseResult<List<ComTagDO>> tagResultCheck =comCenterServiceRef.getTagInfoByOutIdAndType(itemResultVO.getItemVO().getOutId(),TagType.VIEWTAG.name() );
    	if(tagResult!=null){
    		model.addAttribute("tagResult",tagResult.getValue());
    	}
    	if(tagResult!=null){
    		model.addAttribute("tagResultCheck",tagResultCheck.getValue());
    	}
    	ScenicVO scenicDO = scenicSpotService.getById(itemResultVO.getItemVO().getOutId());
    	if(null != scenicDO){
			model.addAttribute("scenicName", scenicDO.getName());
			model.addAttribute("orderNum", scenicDO.getOrderNum());
		}
        model.addAttribute("itemResult", itemResultVO);
        model.addAttribute("commScenic", itemResultVO.getItemVO());
        model.addAttribute("category", itemResultVO.getCategoryVO());
        model.addAttribute("itemType",ItemType.SPOTS.getValue());

        return "/system/comm/scenic/edit";
    }
  
    
    
}

package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by wangdi on 16/5/17.
 */
@Controller
@RequestMapping("/scenic")
public class ScenicManageEnhanceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ScenicManageEnhanceController.class);
    @Autowired
    private ScenicManageService scenicManageService;
    /**
     * 查询景区资源列表
     * @param model
     * @param scenicManageVO
     * @return
     */
    @RequestMapping(value = "/queryScenicManageVOListByData", method = RequestMethod.GET)
    public  String queryScenicManageVOListByData(Model model, ScenicManageVO scenicManageVO){
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        scenicManageVO.setPageSize(8);
        //hotelMessageVO.setPageNo(pageNo);//
        WebResult<PageVO<ScenicManageVO>> result= scenicManageService.queryScenicManageVOListByData(scenicManageVO);
        if(!result.isSuccess()){
            logger.error("查询列表失败");
            return "/error";
        }
        PageVO<ScenicManageVO> pageResult = result.getValue();
        List<ScenicManageVO> scenicManageVOList = pageResult.getResultList();
        int totalPage = 0;
        if (pageResult.getTotalCount()%pageResult.getPageSize() > 0) {
            totalPage += pageResult.getTotalCount()/pageResult.getPageSize()+1;
        }else {
            totalPage += pageResult.getTotalCount()/pageResult.getPageSize();
        }

        model.addAttribute("pageVo", pageResult);
        model.addAttribute("scenicManageVOList", scenicManageVOList);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageNo", pageResult.getPaginator().getPage());
        model.addAttribute("pageSize",8);
        model.addAttribute("totalCount", pageResult.getPaginator().getTotalItems());
        return "/system/comm/hotelManage/searchscenic";
    }


    /**
     * 添加景区商品View页
     * @param model
     * @return
     */
    @RequestMapping(value = "/addScenicManageView")
    public String addScenicManageView(Model model){
        System.out.println("123123132");
        ScenicManageVO scenicManageVO = new  ScenicManageVO();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        System.out.println(userId);
        // 初始化属性列表
        //List<MultiChoice> multiChoiceList = initMultiChoiceList(null);
        model.addAttribute("scenicManageVO", scenicManageVO);
        //model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
        return "/system/comm/hotelManage/addticket";
    }

    /**
     * 添加景区商品
     * @param model
     * @return
     */
    @RequestMapping(value = "/addScenicManageVOByDdata", method = RequestMethod.GET)
    public WebResult<String> addScenicManageVOByDdata(Model model, ScenicManageVO scenicManageVO ){
        WebResult<String> message = new WebResult<String>();
        if(scenicManageVO==null||scenicManageVO.getScenicId()==0){
            message.initFailure(WebReturnCode.PARAM_ERROR,"景区资源信息错误,无法添加商品");
            return message;
        }
        /**必要参数验证**/
        String checkMsg = checkaddScenicManageVOByDdataParam(scenicManageVO);
        if(StringUtils.isNotBlank(checkMsg)){
            message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
            return message;
        }

        WebResult<ScenicManageVO> result = scenicManageService.addScenicManageVOByDdata(scenicManageVO);
        if(!result.isSuccess()){
            message.initFailure(WebReturnCode.PARAM_ERROR,"添加商品错误");
            return message;
        }
        /**属性列表 添加 **/
        model.addAttribute("hotelMessageVO", result.getValue());
        return message;

    }

    /**
     * 编辑景区view
     * @param model
     * @return
     */
    @RequestMapping(value = "/editScenicManageView", method = RequestMethod.GET)
    public String editScenicManageView(Model model){

        System.out.println(123);
        ScenicManageVO scenicManageVO = new ScenicManageVO();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        scenicManageVO.setCategoryId(6);
        scenicManageVO.setItemId(586);
        if(scenicManageVO==null){
            // "编辑商品信息错误";
            return "/error";
        }
        if(scenicManageVO.getItemId()==0){
            // "编辑商品ID错误";
            return "/error";
        }
        if(scenicManageVO.getCategoryId()==0){
            // "商品类目ID错误";
            return "/error";
        }

        WebResult<ScenicManageVO> webResult = scenicManageService.queryScenicManageVOByData(scenicManageVO);
        if(!webResult.isSuccess()){
            // "商品类目ID错误";
            return "/error";
        }
        model.addAttribute("scenicManageVO", scenicManageVO);
        /**动态属性列表***/
        return "/system/comm/hotelManage/addticket";
    }

    /**
     * 编辑景区商品
     * @param model
     * @param scenicManageVO
     * @return
     */
    public WebResult<String> editScenicManageVOByDdata(Model model, ScenicManageVO scenicManageVO){
        WebResult<String> message = new WebResult<String>();

        if(scenicManageVO==null||scenicManageVO.getScenicId()==0){
            message.initFailure(WebReturnCode.PARAM_ERROR,"景区资源信息错误,无法编辑商品");
            return message;
        }
        /**必要参数验证**/
        String checkMsg = checkaddScenicManageVOByDdataParam(scenicManageVO);
        if(StringUtils.isNotBlank(checkMsg)){
            message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
            return message;
        }

        WebResult<ScenicManageVO> result = scenicManageService.editScenicManageVOByDdata(scenicManageVO);
        if(!result.isSuccess()){
            message.initFailure(WebReturnCode.PARAM_ERROR,"添加商品错误");
            return message;
        }
        /**最晚到店时间**/
        model.addAttribute("itemId", scenicManageVO.getItemId());
        model.addAttribute("scenicManageVO",result.getValue());
        return message;
    }

    /**
     * 景区商品验证
     * @param scenicManageVO
     * @return
     */
    public String checkaddScenicManageVOByDdataParam(ScenicManageVO scenicManageVO){
        if(scenicManageVO.getScenicId()==0){
            return "景区ID不能为空";
        }
        if(StringUtils.isBlank(scenicManageVO.getTitle())){
            return "商品标题为空";
        }
        if(scenicManageVO.getTicketId()==0){
            return "付款方式不能为空";
        }
        if(scenicManageVO.getStartBookTimeLimit()==0){
            return "提前预定天数不能为空";
        }

        return  null;
    }

    public ScenicManageService getScenicManageService() {
        return scenicManageService;
    }

    public void setScenicManageService(ScenicManageService scenicManageService) {
        this.scenicManageService = scenicManageService;
    }
}

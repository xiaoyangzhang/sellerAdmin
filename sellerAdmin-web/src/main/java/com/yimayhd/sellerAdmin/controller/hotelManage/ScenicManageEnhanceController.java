package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.TicketResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.ic.client.service.item.ScenicPublishService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.HotelManage.BizCategoryInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.MultiChoice;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    @Autowired
    private CategoryService categoryServiceRef;
    @Resource
    private ScenicPublishService scenicPublishService;

    @Value("${sellerAdmin.rootPath}")
    private String rootPath;

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
        WebResult<PageVO<ScenicManageVO>> result= scenicManageService.queryScenicManageVOListByData(scenicManageVO);
        if(!result.isSuccess()){
            logger.error("查询列表失败");
            return "/system/comm/hotelManage/searchscenic";
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
        ScenicManageVO scenicManageVO = new  ScenicManageVO();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        scenicManageVO.setCategoryId(233);
        CategoryResult categoryResult = categoryServiceRef.getCategory(scenicManageVO.getCategoryId());
        if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
            log.error("类目信息错误");
            return "/error";
        }
         CategoryDO categoryDO = categoryResult.getCategroyDO();
        if(CollectionUtils.isEmpty(categoryDO.getKeyCategoryPropertyDOs())){
            log.error("类目必要属性信息错误");
            return "/error";
        }
        if(CollectionUtils.isEmpty(categoryDO.getNonKeyCategoryPropertyDOs())){
            log.error("类目非必要属性信息错误");
            return "/error";
        }
        List<CategoryPropertyValueDO> keyPro = categoryDO.getKeyCategoryPropertyDOs();
        List<CategoryPropertyValueDO> nonPro = categoryDO.getNonKeyCategoryPropertyDOs();
        keyPro.addAll(nonPro);
        List<BizCategoryInfo> bizCategoryInfoList = new ArrayList<BizCategoryInfo>(keyPro.size());
        for(CategoryPropertyValueDO prov:keyPro){
            BizCategoryInfo bizCategoryInfo = categoryDOToBizCategoryInfo(prov);
            bizCategoryInfoList.add(bizCategoryInfo);
        }
       // CategoryPropertyValueDO sellDO = categoryDO.getKeyCategoryPropertyDOs().get(0);
        // 初始化属性列表
        model.addAttribute("scenicManageVO", scenicManageVO);
        //model.addAttribute("categoryDO",categoryDO);// 最晚到店时间列表
        String json  = CommonJsonUtil.objectToJson(bizCategoryInfoList,List.class);
        System.out.println("dynamicEntry:"+json);
        model.addAttribute("bizCategoryInfoList",bizCategoryInfoList);// 最晚到店时间列表
        return "/system/comm/hotelManage/addticket";
    }

    /**
     * 添加景区商品
     * @param model
     * @return
     */
    @RequestMapping(value = "/addScenicManageVOByDdata", method = RequestMethod.POST)
    @ResponseBody
    public WebResult<String> addScenicManageVOByDdata(Model model, ScenicManageVO scenicManageVO ){
        WebResult<String> message = new WebResult<String>();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        scenicManageVO.setCategoryId(233);
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
        String url = UrlHelper.getUrl(rootPath, "/item/list") ;
        message.setValue(url);
        return message;

    }

    /**
     * 编辑景区view
     * @param model
     * @return
     */
    @RequestMapping(value = "/editScenicManageView")
    public String editScenicManageView(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{

        System.out.println(123);
        String operationFlag = request.getParameter("operationFlag");
        operationFlag="update";
        ScenicManageVO scenicManageVO = new ScenicManageVO();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
       // scenicManageVO.setCategoryId(233);
        scenicManageVO.setItemId(108276);
        if(scenicManageVO==null){
            // "编辑商品信息错误";
            log.warn("编辑商品信息错误");
            throw new BaseException("编辑商品信息错误");
        }
        if(scenicManageVO.getItemId()==0){
            // "编辑商品ID错误";
            log.warn("编辑商品ID错误");
            throw new BaseException("编辑商品ID错误");
        }
        /*if(scenicManageVO.getCategoryId()==0){
            // "商品类目ID错误";
            log.warn("商品类目ID错误");
            throw new BaseException("商品类目ID错误");
        }*/

        WebResult<ScenicManageVO> webResult = scenicManageService.queryScenicManageVOByData(scenicManageVO);
        if(!webResult.isSuccess()){
            // "查询详情错误";
            log.warn("查询详情错误");
            throw new BaseException("查询详情错误");
        }
        scenicManageVO = webResult.getValue();
        model.addAttribute("bizCategoryInfoList",scenicManageVO.getBizCategoryInfoList());// 最晚到店时间列表
        model.addAttribute("scenicManageVO", scenicManageVO);
        model.addAttribute("operationFlag", operationFlag);
        /**动态属性列表***/
        return "/system/comm/hotelManage/addticket";
    }

    /**
     * 编辑景区商品
     * @param model
     * @param scenicManageVO
     * @return
     */
    @RequestMapping(value = "/editScenicManageVOByDdata", method = RequestMethod.POST)
    @ResponseBody
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
        String url = UrlHelper.getUrl(rootPath, "/item/list") ;
        message.setValue(url);
        return message;
    }

    /**
     * 查询票型信息
     * @param model
     * @param scenicId
     * @return
     */
   @RequestMapping(value = "/queryTicketListByScenicId",method = RequestMethod.POST)
   @ResponseBody
    public  WebResult<String> queryTicketListByScenicId (Model model,long scenicId){
       WebResult<String> result = new WebResult<String>();
       TicketResult ticketResult =scenicPublishService.getTicketListByScenicId(scenicId);
       if(!ticketResult.isSuccess()){
           result.initFailure(WebReturnCode.PARAM_ERROR,"门票类型");
           return result;
       }
       List<TicketDO> ticketDOList = ticketResult.getTicketDOList();
       if(CollectionUtils.isEmpty(ticketDOList)){
           result.initFailure(WebReturnCode.PARAM_ERROR,"没有门票类型");
       }
       List<MultiChoice> multiList = new ArrayList<MultiChoice>();
       for(TicketDO ticketDO:ticketDOList){
           MultiChoice multi = new MultiChoice();
           multi.setId(ticketDO.getId());
           multi.setTitle(ticketDO.getTitle());
           multi.setChoiceNo(false);
           multiList.add(multi);
       }
       result.setValue(CommonJsonUtil.objectToJson(multiList,List.class));
       return result;
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


    /**
     * 页面转换 类目参数
     * @param categoryPro
     * @return
     */
    public BizCategoryInfo categoryDOToBizCategoryInfo(CategoryPropertyValueDO categoryPro){
        BizCategoryInfo bizCategoryInfo = new  BizCategoryInfo();
        if(categoryPro==null){
            return null;
        }
        CategoryPropertyDO proDo = categoryPro.getCategoryPropertyDO();
        bizCategoryInfo.setPId( categoryPro.getPropertyId());//propertyID
        bizCategoryInfo.setPTxt(proDo.getText());
        bizCategoryInfo.setVTxt("");
        bizCategoryInfo.setPType(categoryPro.getType());
        bizCategoryInfo.setCategoryId(categoryPro.getCategoryId());
        return bizCategoryInfo;
    }




    public void setScenicManageService(ScenicManageService scenicManageService) {
        this.scenicManageService = scenicManageService;
    }

    public CategoryService getCategoryServiceRef() {
        return categoryServiceRef;
    }

    public void setCategoryServiceRef(CategoryService categoryServiceRef) {
        this.categoryServiceRef = categoryServiceRef;
    }


    public ScenicManageService getScenicManageService() {
        return scenicManageService;
    }

    public ScenicPublishService getScenicPublishService() {
        return scenicPublishService;
    }

    public void setScenicPublishService(ScenicPublishService scenicPublishService) {
        this.scenicPublishService = scenicPublishService;
    }
    public static void main(String[] args) {
        List<MultiChoice> multiList = new ArrayList<MultiChoice>();
        for (int i=0;i<3;i++) {
            MultiChoice multi = new MultiChoice();
            multi.setId(i);
            multi.setTitle("票型说明");
            multi.setChoiceNo(false);
            multiList.add(multi);
        }
        System.out.println(CommonJsonUtil.objectToJson(multiList, List.class));
    }
}

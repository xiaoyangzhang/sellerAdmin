package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.google.gson.reflect.TypeToken;
import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.query.TicketQuery;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.TicketResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.ic.client.service.item.ScenicPublishService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.enums.ItemCodeEnum;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.HotelManage.BizCategoryInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.MultiChoice;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdi on 16/5/17.
 */
@Controller
@RequestMapping("/scenic")
public class ScenicManageEnhanceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger("scenicManage-business.log");
    @Autowired
    private ScenicManageService scenicManageService;
    @Autowired
    private CategoryService categoryServiceRef;
    @Resource
    private ScenicPublishService scenicPublishService;
    @Autowired
    private CacheManager cacheManager ;

    @Value("${sellerAdmin.rootPath}")
    private String rootPath;

    private static final String UPDATE="update";

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
        //model.addAttribute("scenicManageVO", scenicManageVO);
        model.addAttribute("pageVo", pageResult);
        model.addAttribute("scenicManageVOList", scenicManageVOList);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", pageResult.getPaginator().getTotalItems());

        return "/system/comm/hotelManage/searchscenic";
    }


    /**
     * 添加景区商品View页
     * @param model
     * @return
     */
    @RequestMapping(value = "/addScenicManageView")
    public String addScenicManageView(Model model,@RequestParam(required = true) long categoryId){
        ScenicManageVO scenicManageVO = new  ScenicManageVO();
        long userId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(userId);
        if(categoryId==0){
            return "/system/error/404";
        }
        scenicManageVO.setCategoryId(categoryId);
        /**初始化动态列表**/
        List<BizCategoryInfo> bizCategoryInfoList=  getBizCategoryInfoListByCategoryBiz(scenicManageVO.getCategoryId());
        if(CollectionUtils.isEmpty(bizCategoryInfoList)){
            return "/system/error/500";
        }
        // 初始化属性列表
        model.addAttribute("scenicManageVO", scenicManageVO);
        //model.addAttribute("categoryDO",categoryDO);// 最晚到店时间列表
       // String json  = CommonJsonUtil.objectToJson(bizCategoryInfoList,List.class);
       // System.out.println("dynamicEntry:"+json);
        model.addAttribute("bizCategoryInfoList",bizCategoryInfoList);// 最晚到店时间列表
        model.addAttribute("categoryId",categoryId);//
        model.addAttribute("itemId", 0);
        model.addAttribute("UUID", UUID.randomUUID().toString());
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
        if(scenicManageVO==null||scenicManageVO.getScenicId()==0){
            message.initFailure(WebReturnCode.PARAM_ERROR,"景区资源信息错误,无法添加商品");
            log.error("addScenicManageVOByDdata-error 景区资源信息错误,无法添加商品");
            return message;
        }
        /**必要参数验证**/
        String checkMsg = checkaddScenicManageVOByDdataParam(scenicManageVO);
        if(StringUtils.isNotBlank(checkMsg)){
            message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
            log.error("addScenicManageVOByDdata-error"+checkMsg);
            return message;
        }

        boolean rs = cacheManager.addToTair(Constant.UUIDKEY+scenicManageVO.getUUID(), true , 2, 10*60*60);
        if(!rs){
            message.initFailure(WebReturnCode.SYSTEM_ERROR,"请不要重复提交");
            return message;
        }

        WebResult<ScenicManageVO> result = scenicManageService.addScenicManageVOByDdata(scenicManageVO);
        if(!result.isSuccess()){
            log.error("addScenicManageVOByDdata-error"+"添加商品错误");
            message.initFailure(WebReturnCode.PARAM_ERROR,"添加商品错误");
            return message;
        }

        List<BizCategoryInfo> bizCategoryInfoList=  getBizCategoryInfoListByCategoryBiz(scenicManageVO.getCategoryId());
        if(!CollectionUtils.isEmpty(bizCategoryInfoList)){
            String bizJson  = CommonJsonUtil.objectToJson(bizCategoryInfoList,List.class);
            String cacheKey = scenicManageVO.getSellerId()+"_"+scenicManageVO.getCategoryId();
            cacheManager.addToTair(Constant.SELLER_CATEGORY_CHECK+cacheKey, bizJson , 0, 0);//忽略版本,永久保存
            log.info("cacheKey:"+Constant.SELLER_CATEGORY_CHECK+cacheKey);
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
    public String editScenicManageView(Model model, @RequestParam(required = true) long categoryId,
                                                    @RequestParam(required = true) long itemId,
                                                    @RequestParam(required = true) String operationFlag) throws Exception{
        String systemLog = ItemCodeEnum.SYS_START_LOG.getDesc();
        ScenicManageVO scenicManageVO = new ScenicManageVO();
        long currentUserId = sessionManager.getUserId() ;
        scenicManageVO.setSellerId(currentUserId);
        scenicManageVO.setCategoryId(categoryId);
        scenicManageVO.setItemId(itemId);

        if(scenicManageVO==null){
            // "编辑商品信息错误";
            systemLog="编辑商品信息错误";
        }
        if(scenicManageVO.getItemId()==0){
            // "编辑商品ID错误";
            systemLog="编辑商品ID错误";
             new BaseException("编辑商品ID错误");
        }
        //logger.info("editScenicManageView: 入参:scenicManageVO="+CommonJsonUtil.objectToJson(scenicManageVO,ScenicManageVO.class));
        WebResult<ScenicManageVO> webResult = scenicManageService.queryScenicManageVOByData(scenicManageVO);
        if(!webResult.isSuccess()){
            // "查询详情错误";
            systemLog="查询详情错误";
            //throw new BaseException("查询详情错误");
        }

        List<MultiChoice> multiList= queryTicketListByData(scenicManageVO.getScenicId(),scenicManageVO.getTicketId());
        scenicManageVO = webResult.getValue();
        if(currentUserId>0&&currentUserId!=scenicManageVO.getSellerId()){
            return "/system/error/lackPermission";
        }
       // logger.info("editScenicManageView: 回参:webResult="+webResult.isSuccess()+",\n scenicManageVO="+CommonJsonUtil.objectToJson(scenicManageVO,ScenicManageVO.class));
        /***编辑查看时,在类目中匹配验证信息*/
        String cacheKey = scenicManageVO.getSellerId()+"_"+scenicManageVO.getCategoryId();
        log.info("cacheKey:"+Constant.SELLER_CATEGORY_CHECK+cacheKey);
        Object object= cacheManager.getFormTair(Constant.SELLER_CATEGORY_CHECK+cacheKey);
        List<BizCategoryInfo> bizCategoryInfoList = scenicManageVO.getBizCategoryInfoList();
        if(object!=null){
            String bizJson = (String)object;
            List<BizCategoryInfo> bizList = (List<BizCategoryInfo>) CommonJsonUtil.jsonToListObject(bizJson,new TypeToken<List<BizCategoryInfo>>(){}.getType());
            if(!CollectionUtils.isEmpty(bizList)){
                    for(BizCategoryInfo resultCate:bizCategoryInfoList){
                        for(BizCategoryInfo categoryInfo :bizList){
                        if (resultCate.getPId() == categoryInfo.getPId()){
                            resultCate.setMaxLength(categoryInfo.getMaxLength());
                            resultCate.setHint(categoryInfo.getHint());
                        }
                    }
                }
            }
        }
        model.addAttribute("bizCategoryInfoList",bizCategoryInfoList);//
        model.addAttribute("scenicManageVO", scenicManageVO);
        model.addAttribute("operationFlag", operationFlag);
        model.addAttribute("systemLog", systemLog);
        model.addAttribute("categoryId",categoryId);//
        model.addAttribute("itemId", itemId);
        model.addAttribute("multiList",multiList);
        model.addAttribute("UUID",UUID.randomUUID().toString());
        /**动态属性列表***/
        if(operationFlag.equals(UPDATE)){
            return "/system/comm/hotelManage/addticket";
        }else{
            return "/system/comm/hotelManage/ticketdetails";
        }

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
        /**必要参数验证**/
        String checkMsg = checkaddScenicManageVOByDdataParam(scenicManageVO);
        if(StringUtils.isNotBlank(checkMsg)){
            message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
            return message;
        }
        if(scenicManageVO==null||scenicManageVO.getScenicId()==0){
            message.initFailure(WebReturnCode.PARAM_ERROR,"景区资源信息错误,无法编辑商品");
            return message;
        }
        boolean rs = cacheManager.addToTair(Constant.UUIDKEY+scenicManageVO.getUUID(), true , 2, 10*60*60);
        if(!rs){
            message.initFailure(WebReturnCode.SYSTEM_ERROR,"请不要重复提交");
            return message;
        }
        log.info("editScenicManageVOByDdata start :scenicManageVO="+CommonJsonUtil.objectToJson(scenicManageVO,ScenicManageVO.class));
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

       List<MultiChoice> multiList= queryTicketListByData(scenicId,0);

       result.setValue(CommonJsonUtil.objectToJson(multiList,List.class));
       return result;
    }

    /**
     * 查询票型列表
     * @param scenicId
     * @param ticketId
     * @return
     */
    public List<MultiChoice> queryTicketListByData(long scenicId,long ticketId){
        TicketQuery ticketQuery = new TicketQuery();
        ticketQuery.setScenicId(scenicId);
        ticketQuery.setStatus(BaseStatus.AVAILABLE.getType());
        TicketResult ticketResult =scenicPublishService.getTicketListByScenicId(ticketQuery);
        if(!ticketResult.isSuccess()){
            log.error("没有门票类型");
            return null;
        }
        List<TicketDO> ticketDOList = ticketResult.getTicketDOList();
        if(CollectionUtils.isEmpty(ticketDOList)){
            log.error("没有门票类型");
            return null;
        }
        List<MultiChoice> multiList = new ArrayList<MultiChoice>();
        for(TicketDO ticketDO:ticketDOList){
            MultiChoice multi = new MultiChoice();
            multi.setChoiceNo(false);
            multi.setId(ticketDO.getId());
            multi.setTitle(ticketDO.getTitle());
            if (ticketDO.getId()==ticketId){
                multi.setChoiceNo(true);
            }
            multiList.add(multi);
        }

        return multiList;
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
            return "门票类型不能为空";
        }
        /*if(scenicManageVO.getStartBookTimeLimit()==0){
            return "提前预定天数不能为空";
        }*/
        if (StringUtils.isBlank(scenicManageVO.getUUID())){
            return "UUID不能为空";
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
        bizCategoryInfo.setHint(categoryPro.getHint());
        bizCategoryInfo.setMaxLength(categoryPro.getMaxLength());
        return bizCategoryInfo;
    }

    /**
     * 获取获取类目配置信息
     * @return
     */
    public  List<BizCategoryInfo> getBizCategoryInfoListByCategoryBiz(long categoryId){
        /**新增商户类目属性信息*/
        CategoryResult categoryResult = categoryServiceRef.getCategory(categoryId);
        if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
            log.error("类目信息错误");
            return  null;
        }
        CategoryDO categoryDO = categoryResult.getCategroyDO();
        if(CollectionUtils.isEmpty(categoryDO.getKeyCategoryPropertyDOs())){
            log.error("类目必要属性信息错误");
            return  null;
        }
        if(CollectionUtils.isEmpty(categoryDO.getNonKeyCategoryPropertyDOs())){
            log.error("类目非必要属性信息错误");
            return  null;
        }
        List<CategoryPropertyValueDO> keyPro = categoryDO.getKeyCategoryPropertyDOs();
        List<CategoryPropertyValueDO> nonPro = categoryDO.getNonKeyCategoryPropertyDOs();
        keyPro.addAll(nonPro);
        List<BizCategoryInfo> bizCategoryInfoList = new ArrayList<BizCategoryInfo>(keyPro.size());
        for(CategoryPropertyValueDO prov:keyPro){
            BizCategoryInfo bizCategoryInfo = categoryDOToBizCategoryInfo(prov);
            bizCategoryInfoList.add(bizCategoryInfo);
        }
        //String json  = CommonJsonUtil.objectToJson(bizCategoryInfoList,List.class);

        return bizCategoryInfoList;
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

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
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

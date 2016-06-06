package com.yimayhd.sellerAdmin.checker;

import com.google.gson.reflect.TypeToken;
import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.StarLevelType;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishUpdateDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.HotelManage.BizCategoryInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.BizSkuInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.SupplierCalendarTemplate;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import com.yimayhd.sellerAdmin.util.DateCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangdi on 16/5/18.
 */
public class ScenicManageDomainChecker {
    private  ScenicManageVO scenicManageVO;
    private  ItemDO itemDO;
    private  ScenicDO scenicDO;
    private List<ItemSkuDO> itemSkuDOList;

    private CategoryDO category;
    private  CategoryPropertyValueDO categoryPropertyValueDO;
    private  WebResult<PageVO<ScenicManageVO>> webPageResult;

    private  WebResult<ScenicManageVO> webResult;
    private final String UPDATE = "update";
    private final String ADD = "add";
    private final String DEL = "delete";
    private int dayTime=24*60*60;

    public ScenicManageDomainChecker(ScenicManageVO scenicManageVO){
        this.scenicManageVO = scenicManageVO;
    }

    /**
     * 景区资源列表查询 参数验证
     * @return
     */
    public WebResult checkQueryScenicManageVOListByData(){

        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (scenicManageVO.getPage() == null || scenicManageVO.getPageSize() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "分页数据错误");
        }

        return WebResult.success(null);
    }

    /**
     * 获取查询景区资源参数
     * @return
     */
    public ScenicPageQuery getBizQueryModel(){
        ScenicPageQuery scenicPageQuery = new ScenicPageQuery();
        if(scenicManageVO==null){
            return scenicPageQuery;
        }
        if(StringUtils.isNotBlank(scenicManageVO.getName())){
            scenicPageQuery.setName(scenicManageVO.getName());
        }
        if(scenicManageVO.getLocationCityId()>0){
            scenicPageQuery.setLocationCityId(Long.valueOf(scenicManageVO.getLocationCityId()));
        }
        if(scenicManageVO.getLocationProvinceId()>0){
            scenicPageQuery.setLocationProvinceId(Long.valueOf(scenicManageVO.getLocationProvinceId()));
        }
        if(scenicManageVO.getLocationTownId()>0){
            scenicPageQuery.setLocationTownId(Long.valueOf(scenicManageVO.getLocationTownId()));
        }
        scenicPageQuery.setPageNo(scenicManageVO.getPage());
        scenicPageQuery.setPageSize(scenicManageVO.getPageSize());
        return  scenicPageQuery;
    }

    /**
     * 添加/编辑景区商品信息参数验证
     * @return
     */
    public WebResult checkAddScenicManageVOByDdata(){
        if(scenicManageVO.getSellerId()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "商户id不能为空");
        }
        if (scenicManageVO.getScenicId()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "景区资源ID不能为空");
        }
        return WebResult.success(null);
    }
    /**
     * do 2 modle
     * @return
     */
    public ScenicManageVO doToModel(ScenicDO _do){
        ScenicManageVO scenicManageVO = new ScenicManageVO();
        if(_do==null){
            return null;
        }
        scenicManageVO.setScenicId(_do.getId());
        scenicManageVO.setName(_do.getName());
        scenicManageVO.setLevel(_do.getLevel());
        scenicManageVO.setLevelStr(StarLevelType.getByLevelWithDefault(_do.getLevel()).getDesc());
        scenicManageVO.setArea(_do.getLocationProvinceName()+"/"+_do.getLocationCityName()+"/"+_do.getLocationTownName());
        List<String> subIds = _do.getScenicFeature().getSubjectIds();
        List<String> subNames = _do.getScenicFeature().getSubjectNames();
        if(CollectionUtils.isNotEmpty(subNames)){
            StringBuilder sb = new StringBuilder();
            for(String sbname:subNames){
                sb.append(sbname).append(" ");

            }
            scenicManageVO.setSubjectName(sb.toString());
        }
        scenicManageVO.setSubjectId(_do.getSubjectId());
        scenicManageVO.setLocationCityId(_do.getLocationCityId());
        scenicManageVO.setLocationProvinceId(_do.getLocationProvinceId());
        scenicManageVO.setLocationTownId(_do.getLocationTownId());
        scenicManageVO.setLocationText(_do.getLocationText());

        return scenicManageVO;

    }

    /**
     * 拼装景区商品信息
     * @return
     */
    public ScenicManageVO getBizScenicManageVO(){
        scenicManageVO.setScenicId(itemDO.getOutId());
        scenicManageVO.setLocationText(scenicDO.getLocationText());
        scenicManageVO.setTicketId(itemDO.getItemFeature().getTicketId());//门票ID
        scenicManageVO.setTicketTitle(itemDO.getItemFeature().getTicketTitle());//门票名称
        scenicManageVO.setItemId(itemDO.getId());
        scenicManageVO.setName(scenicDO.getName());
        scenicManageVO.setTitle(itemDO.getTitle());
        scenicManageVO.setCategoryId(itemDO.getCategoryId());//类目ID
        //scenicManageVO.setPrice(new BigDecimal(itemDO.getPrice()).divide(new BigDecimal(100)));
        scenicManageVO.setOriginalPrice(new BigDecimal(itemDO.getOriginalPrice()).divide(new BigDecimal(100)));
        scenicManageVO.setStartBookTimeLimit(itemDO.getItemFeature().getStartBookTimeLimit()/dayTime);//提前预定天数
        itemDO.getItemFeature().getTicketId();//门票ID
       // itemDO.getItemFeature().getTicketTitle();//门票名称
        // 根据listsku拼装json信息  价格日历
        scenicManageVO.setSupplierCalendar(getSupplierCalendarJson());
        //动态添加 属性
        List<BizCategoryInfo> bizList = new ArrayList<BizCategoryInfo>();
        for (ItemSkuPVPair skuPVPair: itemDO.getItemPropertyList()){
            BizCategoryInfo bizCategory = new BizCategoryInfo();
                System.out.println("销售属性:"+skuPVPair.getVTxt());
                bizCategory.setPId(skuPVPair.getPId());
                bizCategory.setVTxt(skuPVPair.getVTxt());
                bizCategory.setPTxt(skuPVPair.getPTxt());
                bizCategory.setPType(skuPVPair.getPType());
                bizCategory.setCategoryId(scenicManageVO.getCategoryId());
             bizList.add(bizCategory);
        }

        String bizCategoryJson =CommonJsonUtil.objectToJson(bizList,List.class);
        System.out.println("编辑Josn:"+bizCategoryJson);
        scenicManageVO.setDynamicEntry(bizCategoryJson);//属性json穿
        scenicManageVO.setBizCategoryInfoList(bizList);// json列表
        System.out.println("bizCategoryJson");
        return scenicManageVO;
    }
    public ItemDO  getBizScenicPublishAddDTO() {
        ItemDO itemDO = new ItemDO();
        itemDO.setCategoryId(scenicManageVO.getCategoryId());
        itemDO.setOutId(scenicManageVO.getScenicId());//酒店ID
        itemDO.setSellerId(scenicManageVO.getSellerId());//商家ID
        itemDO.setTitle(scenicManageVO.getTitle());
        //itemDO.setPrice(scenicManageVO.getPrice().multiply(new BigDecimal(100)).longValue());//价格
        //FIXME 缺少非空判断
        itemDO.setOriginalPrice(scenicManageVO.getOriginalPrice().multiply(new BigDecimal(100)).longValue());//门市价
        itemDO.setDomain(Constant.DOMAIN_JIUXIU);
        //FIXME 使用枚举或者静态变量，不要写死
        itemDO.setOptions(1);
        itemDO.setItemType(category.getCategoryFeature().getItemType());
        itemDO.addPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS,scenicDO.getLogoUrl());//图片

        //itemDO.setOutType();
        ItemFeature itemFeature = new ItemFeature(null);
        /**票型资源信息**/
        itemFeature.put(ItemFeatureKey.TICKET_ID,scenicManageVO.getTicketId());
        itemFeature.put(ItemFeatureKey.TICKET_TITLE,scenicManageVO.getTicketTitle());
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,scenicManageVO.getStartBookTimeLimit()*dayTime);//提前预定天数
        itemDO.setItemFeature(itemFeature);
        itemDO.setItemPropertyList(getItemSkuPVPairList(scenicManageVO.getDynamicEntry()));// 添加动态属性信息
        return itemDO;
    }


    /**
     * 更新ItemPubUpdateDTO参数
     * @return
     */
    public ScenicPublishUpdateDTO getBizScenicPublishUpdateDTO(){
        ScenicPublishUpdateDTO scenicPublishUpdateDTO = new ScenicPublishUpdateDTO();
        ItemPubUpdateDTO itemDO = new ItemPubUpdateDTO();
        /***基本信息**/
        itemDO.setId(scenicManageVO.getItemId());
        //itemDO.setCategoryId(scenicManageVO.getCategoryId());
        itemDO.setOutId(scenicManageVO.getScenicId());//酒店ID
        //itemDO.setSellerId(scenicManageVO.getSellerId());//商家ID
        itemDO.setTitle(scenicManageVO.getTitle());
        //itemDO.setPrice(scenicManageVO.getPrice().multiply(new BigDecimal(100)).longValue());//价格
        itemDO.setOriginalPrice(scenicManageVO.getOriginalPrice().multiply(new BigDecimal(100)).longValue());//门市价

        //itemDO.setOutType();
        //ItemFeature itemFeature = new ItemFeature(null);
        /**票型资源信息**/
       // itemFeature.put(ItemFeatureKey.TICKET_ID,scenicManageVO.getTicketId());
        itemDO.setTicketId(scenicManageVO.getTicketId());
        //itemFeature.put(ItemFeatureKey.TICKET_TITLE,scenicManageVO.getTicketTitle());
        itemDO.setTicketTitle(scenicManageVO.getTicketTitle());
        //itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,scenicManageVO.getStartBookTimeLimit().intValue()*dayTime);//提前预定天数
        itemDO.setStartBookTimeLimit(scenicManageVO.getStartBookTimeLimit().intValue()*dayTime);
        //itemDO.setItemFeature(itemFeature);
        scenicManageVO.getDynamicEntry();//动态json列表
        List<ItemSkuPVPair> skuPVPairList =  getItemSkuPVPairList(scenicManageVO.getDynamicEntry());
        itemDO.setItemPropertyList(skuPVPairList);// 动态属性列表
        scenicPublishUpdateDTO.setItemDTO(itemDO);
        /***编辑操作添加价格日历sku**/
        Map<String,Object> paramUp =  getUpdateItem(scenicManageVO.getSupplierCalendar());
        scenicPublishUpdateDTO.setAddItemSkuList( paramUp.get(ADD)==null?null:(List<ItemSkuDO>)paramUp.get(ADD));
        scenicPublishUpdateDTO.setUpdItemSkuList( paramUp.get(UPDATE)==null?null:(List<ItemSkuPubUpdateDTO>)paramUp.get(UPDATE));
        scenicPublishUpdateDTO.setDelItemSkuList(paramUp.get(DEL)==null?null:(List<Long>)paramUp.get(DEL));
        return scenicPublishUpdateDTO;
    }

    /**
     * 拼装skuList
     * @return
     */
    public  List<ItemSkuDO> getAddBizItemSkuDOList(){
        List<ItemSkuDO> skuList = addItemSkuDOList(scenicManageVO.getSupplierCalendar());
       return skuList;
    }

    public  List<ItemSkuDO> getUpdateBizItemSkuDOList(){
        if (StringUtils.isBlank(scenicManageVO.getSupplierCalendar())){
            return null;
        }
        List<ItemSkuDO> itemSkuList = new ArrayList<ItemSkuDO>();
        return  null;
    }

    /**
     * 拼装价格日历sku
     * @param supplierCalendar
     * @return
     */
    public  List<ItemSkuDO> addItemSkuDOList(String supplierCalendar){
        if (StringUtils.isBlank(supplierCalendar)){
            return null;
        }
        if(categoryPropertyValueDO==null){
            return null;
        }
        SupplierCalendarTemplate template = (SupplierCalendarTemplate) CommonJsonUtil.jsonToObject(supplierCalendar, SupplierCalendarTemplate.class);

        BizSkuInfo[] bizSkuInfos = template.getBizSkuInfo();
        List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>(bizSkuInfos.length);
        for (BizSkuInfo biz :bizSkuInfos){
            addItemSkuDOList.add(getItemSkuDOByBiz(template,biz));
        }

        return addItemSkuDOList;
    }

    /**
     * 动态属性信息
     * @return
     */
    public List<ItemSkuPVPair>  getItemSkuPVPairList(String dynamicEntry) {
        if(StringUtils.isBlank(dynamicEntry)){
            return null;
        }
        List<BizCategoryInfo> bizCategoryInfoList = (List<BizCategoryInfo>) CommonJsonUtil.jsonToListObject(dynamicEntry, new TypeToken<List<BizCategoryInfo>>(){}.getType());
        List<ItemSkuPVPair> itemSkuPVPairList = new ArrayList<ItemSkuPVPair>(bizCategoryInfoList.size());
        for(BizCategoryInfo bizCategoryInfo :bizCategoryInfoList){
            ItemSkuPVPair itemSkuPVPair = new ItemSkuPVPair();
            itemSkuPVPair.setPId(bizCategoryInfo.getPId());//properid
            itemSkuPVPair.setPTxt(bizCategoryInfo.getPTxt());//文本
            System.out.println("biz ptxt:"+bizCategoryInfo.getPTxt());
            System.out.println(itemSkuPVPair.getPTxt());
            itemSkuPVPair.setPType(bizCategoryInfo.getPType());//类型
            itemSkuPVPair.setVTxt(bizCategoryInfo.getVTxt());//value值
            itemSkuPVPairList.add(itemSkuPVPair);
        }
        String skuPvpJson = CommonJsonUtil.objectToJson(itemSkuPVPairList,List.class);
        System.out.println("属性json:"+skuPvpJson);
        return itemSkuPVPairList;
    }


    /**
     * 根据价格日历信息,拼装itemsku
     * @param biz
     * @return
     */
    public ItemSkuDO getItemSkuDOByBiz( SupplierCalendarTemplate template,BizSkuInfo biz){
        ItemSkuDO sku = new ItemSkuDO();
        sku.setSellerId(template.getSeller_id());//商家ID
        sku.setCategoryId(scenicManageVO.getCategoryId());//类目ID
        BigDecimal prize = biz.getPrice();
        long portionPrize = prize.multiply(new BigDecimal(100)).longValue();
        sku.setPrice(portionPrize);//价格
        sku.setStockNum(biz.getStock_num());//库存
        /**销售属性**/
        List<ItemSkuPVPair> itemSkuPVPairList = new ArrayList<ItemSkuPVPair>();
        ItemSkuPVPair pvPair =new ItemSkuPVPair();
        pvPair.setPId(categoryPropertyValueDO.getPropertyId());//销售属性ID
        String vTxt = biz.getvTxt();
        long time = Long.parseLong(vTxt);
        System.out.println(time);
        pvPair.setPTxt(DateCommon.timestampLongDate(time));//日期格式化
        pvPair.setVTxt(vTxt);//价格日期
        pvPair.setPType(categoryPropertyValueDO.getType());
        pvPair.setVId(-time);
        itemSkuPVPairList.add(pvPair);
        sku.setItemSkuPVPairList(itemSkuPVPairList);
        return sku;
    }

    /**
     * 价格日历更新dto
     * @param template
     * @param biz
     * @return
     */
    public ItemSkuPubUpdateDTO getBizItemSkuPubUpdateDTO(SupplierCalendarTemplate template, BizSkuInfo biz){
        ItemSkuPubUpdateDTO sku = new ItemSkuPubUpdateDTO();
        //sku.setSellerId(template.getSeller_id());//商家ID
        //sku.setCategoryId(scenicManageVO.getCategoryId());//类目ID
        BigDecimal prize = biz.getPrice();
        long portionPrize = prize.multiply(new BigDecimal(100)).longValue();
        sku.setPrice(portionPrize);//价格
        sku.setStockNum(biz.getStock_num());//库存
        /**销售属性**/
        List<ItemSkuPVPair> itemSkuPVPairList = new ArrayList<ItemSkuPVPair>();
        ItemSkuPVPair pvPair =new ItemSkuPVPair();
        pvPair.setPId(categoryPropertyValueDO.getPropertyId());//销售属性ID
        String vTxt = biz.getvTxt();
        long time = Long.parseLong(vTxt);
        //System.out.println(time);
        pvPair.setPTxt(DateCommon.timestampLongDate(time));//日期格式化
        pvPair.setVTxt(vTxt);//价格日期
        pvPair.setPType(categoryPropertyValueDO.getType());
        pvPair.setVId(-time);
        itemSkuPVPairList.add(pvPair);
        sku.setItemSkuPVPairList(itemSkuPVPairList);
        return sku;
    }

    /**
     * 更新商品信息添加价格日历信息
     * @param supplierCalendar
     * @return
     */
    public Map<String,Object> getUpdateItem(String supplierCalendar){
        List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
        List<Long> delItemSkuDOList = new ArrayList<Long>();
        List<ItemSkuPubUpdateDTO> itemSkuPubUpdateDTOList = new ArrayList<ItemSkuPubUpdateDTO>();

        if (org.apache.commons.lang3.StringUtils.isBlank(supplierCalendar)){
            return null;
        }
        if(categoryPropertyValueDO==null){
            return null;
        }
        SupplierCalendarTemplate template = (SupplierCalendarTemplate) CommonJsonUtil.jsonToObject(supplierCalendar, SupplierCalendarTemplate.class);
        BizSkuInfo[] bizSkuInfos = template.getBizSkuInfo();
        for (BizSkuInfo biz :bizSkuInfos){
            switch (biz.getState()) {
                case UPDATE:
                    ItemSkuPubUpdateDTO upSkuDo  = getBizItemSkuPubUpdateDTO(template,biz);
                    /**更新sku需要回填对应的skuid*/
                    upSkuDo.setId((Long)biz.getSku_id());
                    itemSkuPubUpdateDTOList.add(upSkuDo);
                    break;

                case DEL:
                    delItemSkuDOList.add((Long)biz.getSku_id());
                    break;

                case ADD:
                    addItemSkuDOList.add(getItemSkuDOByBiz(template,biz));
                    break;
                default:
                    System.out.println("default");
            }


        }
        Map<String,Object>  sukparam = new HashMap<String,Object>();
        sukparam.put(ADD,addItemSkuDOList);
        sukparam.put(UPDATE,itemSkuPubUpdateDTOList);
        sukparam.put(DEL,delItemSkuDOList);
        return  sukparam;
    }
    /**
     * 根据itemsku 拼装json信息
     * @return
     */
    public String getSupplierCalendarJson(){
        String json ="";
        if(CollectionUtils.isEmpty(itemSkuDOList)){
            return null;
        }
        SupplierCalendarTemplate temp = new SupplierCalendarTemplate();
        temp.setSeller_id(itemDO.getSellerId());//商家ID
        temp.setHotel_id(itemDO.getOutId());
        BizSkuInfo[] bizArr = new  BizSkuInfo[itemSkuDOList.size()];
        for(int i = 0;i<itemSkuDOList.size();i++){
            ItemSkuDO sku =  itemSkuDOList.get(i);
            BizSkuInfo  bizSkuInfo =new BizSkuInfo();
            bizSkuInfo.setSku_id(sku.getId());
            bizSkuInfo.setState("update");
            bizSkuInfo.setPrice(new BigDecimal(sku.getPrice()).divide(new BigDecimal(100)));;//价格
            bizSkuInfo.setStock_num(sku.getStockNum());//库存
            ItemSkuPVPair pvp = sku.getItemSkuPVPairList().get(0);
            bizSkuInfo.setvTxt(pvp.getVTxt());//日期
            bizArr[i]=bizSkuInfo;
        }
        temp.setBizSkuInfo(bizArr);
        json = CommonJsonUtil.objectToJson(temp,SupplierCalendarTemplate.class);
       // json= json.replaceAll("\"","\\\\\"");
        System.out.println(json);
        return json;

    }

    /**
     * 景区详情参数校验
     * @return
     */
    public WebResult checkQueryScenicManageVOByData(){
        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        return WebResult.success(null);
    }

    public ScenicManageVO getScenicManageVO() {
        return scenicManageVO;
    }

    public void setScenicManageVO(ScenicManageVO scenicManageVO) {
        this.scenicManageVO = scenicManageVO;
    }

    public WebResult<PageVO<ScenicManageVO>> getWebPageResult() {
        return webPageResult;
    }

    public void setWebPageResult(WebResult<PageVO<ScenicManageVO>> webPageResult) {
        this.webPageResult = webPageResult;
    }

    public WebResult<ScenicManageVO> getWebResult() {
        return webResult;
    }

    public void setWebResult(WebResult<ScenicManageVO> webResult) {
        this.webResult = webResult;
    }

    public ItemDO getItemDO() {
        return itemDO;
    }

    public void setItemDO(ItemDO itemDO) {
        this.itemDO = itemDO;
    }

    public ScenicDO getScenicDO() {
        return scenicDO;
    }

    public void setScenicDO(ScenicDO scenicDO) {
        this.scenicDO = scenicDO;
    }

    public List<ItemSkuDO> getItemSkuDOList() {
        return itemSkuDOList;
    }

    public void setItemSkuDOList(List<ItemSkuDO> itemSkuDOList) {
        this.itemSkuDOList = itemSkuDOList;
    }

    public CategoryDO getCategory() {
        return category;
    }

    public void setCategory(CategoryDO category) {
        this.category = category;
    }

    public CategoryPropertyValueDO getCategoryPropertyValueDO() {
        return categoryPropertyValueDO;
    }

    public void setCategoryPropertyValueDO(CategoryPropertyValueDO categoryPropertyValueDO) {
        this.categoryPropertyValueDO = categoryPropertyValueDO;
    }
}

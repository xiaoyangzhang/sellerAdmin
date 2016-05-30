package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.BizCategoryInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.BizSkuInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.SupplierCalendarTemplate;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


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
        if (scenicManageVO.getPageNo() == null || scenicManageVO.getPageSize() == null) {
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
        if(scenicManageVO.getScenicId()!=0){

        }
        if(StringUtils.isNotBlank(scenicManageVO.getName())){
            scenicPageQuery.setName(scenicManageVO.getName());
        }

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
        scenicManageVO.setItemId(itemDO.getId());
        scenicManageVO.setName(scenicDO.getName());
        scenicManageVO.setTitle(itemDO.getTitle());
        scenicManageVO.setCategoryId(category.getId());//类目ID
        scenicManageVO.setStartBookTimeLimit(itemDO.getItemFeature().getStartBookTimeLimit());//提前预定天数
        // 根据listsku拼装json信息
        scenicManageVO.setSupplierCalendar("");
        itemDO.getItemFeature().getTicketId();//门票ID
        itemDO.getItemFeature().getTicketTitle();//门票名称
        //动态添加 属性
        List<CategoryPropertyValueDO> keyCateList = category.getKeyCategoryPropertyDOs();
        List<CategoryPropertyValueDO> nonCateList =  category.getNonKeyCategoryPropertyDOs();
        for (CategoryPropertyValueDO category :keyCateList){
            BizCategoryInfo bizCategory = new BizCategoryInfo();
            CategoryPropertyDO propertyDO =  category.getCategoryPropertyDO();
            bizCategory.setCategoryId(category.getId());
            bizCategory.setpId(propertyDO.getId());
            bizCategory.setpTxt(propertyDO.getText());
        }

        return scenicManageVO;
    }
    public ItemDO  getBizScenicPublishAddDTO() {
        ItemDO itemDO = new ItemDO();
        itemDO.setCategoryId(scenicManageVO.getCategoryId());
        itemDO.setOutId(scenicManageVO.getScenicId());//酒店ID
        itemDO.setSellerId(scenicManageVO.getSellerId());//商家ID
        itemDO.setTitle(scenicManageVO.getTitle());
        itemDO.setPrice(scenicManageVO.getPrice());//价格
        itemDO.setOriginalPrice(scenicManageVO.getOriginalPrice());//门市价

        //itemDO.setOutType();
        ItemFeature itemFeature = new ItemFeature(null);
        /**票型资源信息**/
        itemFeature.put(ItemFeatureKey.TICKET_ID,scenicManageVO.getTicketId());
        itemFeature.put(ItemFeatureKey.TICKET_TITLE,scenicManageVO.getTicketTitle());
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,scenicManageVO.getStartBookTimeLimit());//提前预定天数
        itemDO.setItemFeature(itemFeature);
        return itemDO;
    }

    /**
     * 更新ItemPubUpdateDTO参数
     * @return
     */
    public ItemPubUpdateDTO getBizScenicPublishUpdateDTO(){
        ItemPubUpdateDTO itemDO = new ItemPubUpdateDTO();
        //itemDO.setCategoryId(scenicManageVO.getCategoryId());
        itemDO.setOutId(scenicManageVO.getScenicId());//酒店ID
        //itemDO.setSellerId(scenicManageVO.getSellerId());//商家ID
        itemDO.setTitle(scenicManageVO.getTitle());
        itemDO.setPrice(scenicManageVO.getPrice());//价格
        itemDO.setOriginalPrice(scenicManageVO.getOriginalPrice());//门市价

        //itemDO.setOutType();
        ItemFeature itemFeature = new ItemFeature(null);
        /**票型资源信息**/
        itemFeature.put(ItemFeatureKey.TICKET_ID,scenicManageVO.getTicketId());
        itemFeature.put(ItemFeatureKey.TICKET_TITLE,scenicManageVO.getTicketTitle());
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,scenicManageVO.getStartBookTimeLimit());//提前预定天数
        itemDO.setItemFeature(itemFeature);
        scenicManageVO.getDynamicEntry();//动态json列表
         List<ItemSkuDO> addItemSkuList;
         List<Long> delItemSkuList;
         List<ItemSkuDO> updItemSkuList;
        return itemDO;
    }

    /**
     * 拼装skuList
     * @return
     */
    public  List<ItemSkuDO> getAddBizItemSkuDOList(){
        List<ItemSkuDO> skuList = addItemSkuDOList(scenicManageVO.getSupplierCalendar());
        String dyJson = scenicManageVO.getDynamicEntry();//动态json列表
        List<CategoryPropertyValueDO> keyProList = category.getKeyCategoryPropertyDOs();
        List<CategoryPropertyValueDO> nonProList=category.getNonKeyCategoryPropertyDOs();

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
        category.getSellCategoryPropertyDOs();
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
     * 根据价格日历信息,拼装itemsku
     * @param biz
     * @return
     */
    public ItemSkuDO getItemSkuDOByBiz( SupplierCalendarTemplate template,BizSkuInfo biz){
        ItemSkuDO sku = new ItemSkuDO();
        sku.setSellerId(template.getSeller_id());//商家ID
        sku.setCategoryId(scenicManageVO.getCategoryId());//类目ID
        sku.setPrice(biz.getvPrize());//价格
        sku.setStockNum(biz.getStock_num());//库存
        /**销售属性**/
        List<ItemSkuPVPair> itemSkuPVPairList = new ArrayList<ItemSkuPVPair>();
        ItemSkuPVPair pvPair =new ItemSkuPVPair();
        pvPair.setPId(categoryPropertyValueDO.getId());//销售属性ID
        pvPair.setPTxt("2016-1-1");//日期格式化
        pvPair.setVTxt(biz.getvTxt());//价格日期
        pvPair.setPType(categoryPropertyValueDO.getType());
        pvPair.setVId(-Integer.valueOf(biz.getvTxt()).intValue());
        itemSkuPVPairList.add(pvPair);
        sku.setItemSkuPVPairList(itemSkuPVPairList);
        return sku;
    }
    /**
     * 景区详情参数校验
     * @return
     */
    public WebResult checkQueryScenicManageVOByData(){
        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (scenicManageVO.getScenicId()==0) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "景区ID不能为空");
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

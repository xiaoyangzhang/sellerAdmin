package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.item.RoomFeature;
import com.yimayhd.ic.client.model.enums.CancelLimit;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ResourceType;
import com.yimayhd.ic.client.model.enums.RoomNetwork;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.BizSkuInfo;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.SupplierCalendarTemplate;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import com.yimayhd.sellerAdmin.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 九牛-酒店信息check
 * Created by wangdi on 16/5/13.
 */
public class HotelManageDomainChecker {
    private HotelMessageVO hotelMessageVO;
    private WebResult <PageVO<HotelMessageVO>> pageResult ;
    private WebResult <HotelMessageVO> webResult;
    private WebResult<Long> longWebResult;
    private WebResult<List<RoomMessageVO>> listRoomMessageVOResult;

    /***拼装商品信息参数start**/
    private ItemDO itemDO;
    private List<ItemSkuDO> itemSkuDOList;
    private HotelDO hotelDO;
    private List<RoomDO> listRoomDO;
    private CategoryPropertyValueDO categoryPropertyValueDO;
    private final String UPDATE = "update";
    private final String ADD = "add";
    private final String DEL = "delete";


    /***拼装商品信息参数end**/



    public HotelManageDomainChecker(HotelMessageVO hotelMessageVO) {
        this.hotelMessageVO = hotelMessageVO;
    }

    /**
     * queryHotelMessageVOListByData 参数校验
     *
     * @return
     */
    public WebResult checkHotelMessageVO() {

        if (hotelMessageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (hotelMessageVO.getPageNo() == null || hotelMessageVO.getPageSize() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "分页数据错误");
        }

        return WebResult.success(null);

    }

    /**
     * 拼装中台业务参数
     * @return
     */
    public HotelPageQuery getBizQueryModel() {
        HotelPageQuery hotelPageQuery = new HotelPageQuery();
        hotelPageQuery.setNeedCount(true);
        if(hotelMessageVO.getHotelId()!=0){

        }
        if(StringUtils.isNotBlank(hotelMessageVO.getName())){

        }
        hotelPageQuery.setPageNo(hotelMessageVO.getPageNo());
        hotelPageQuery.setPageSize(hotelMessageVO.getPageSize());
        return hotelPageQuery;

    }

    /**
     * 酒店信息查询校验
     * @return
     */
    public WebResult checkQueryHotelMessageInfo(){
        if(hotelMessageVO.getHotelId()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "酒店ID为空");
        }

       return  WebResult.success(null);
    }

    /**
     *
     * @return
     */
    public WebResult checkQueryHotelMessageVOyData(){
        if(hotelMessageVO.getItemId()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "商品ID为空");
        }
        return  WebResult.success(null);
    }

    /**
     * 添加酒店商品有效验证
     * @return
     */
    public WebResult checkAddHotelMessageVOByData() {
        //商品标题
        if (StringUtils.isBlank(hotelMessageVO.getTitle())) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "商品标题不能为空");
        }
        if (hotelMessageVO.getPayType() == 0) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "支付方式不能为空");
        }
        if (hotelMessageVO.getCancelLimit() == 0) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "退订限制不能为空");
        }
        if (StringUtils.isBlank(hotelMessageVO.getDescription())) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "退订规则不能为空");
        }
        if (StringUtils.isBlank(hotelMessageVO.getStoreLastTime())) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "最晚到点时间不能为空");
        }
        if (hotelMessageVO.getStartBookTimeLimit() == 0) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "提前预定天数不能为空");
        }
        if (hotelMessageVO.getBreakfast() == 0) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "早餐信息不能为空");
        }

        return WebResult.success(null);
    }

    /**
     * do to model
     * @param _do
     * @return
     */
    public HotelMessageVO doToModel(HotelDO _do){
        HotelMessageVO model = new HotelMessageVO();
        model.setHotelId(_do.getId());
        model.setName(_do.getName());
        model.setLocationText(_do.getLocationText());
        model.setArea(_do.getLocationProvinceName()+"/"+_do.getLocationCityName()+"/"+_do.getLocationTownName());
        List<String> phoneList = _do.getPhoneNum();
        if(CollectionUtils.isNotEmpty(phoneList)){
            model.setPhone(phoneList.get(0));
        }
        model.setLocationProvinceId(_do.getLocationProvinceId());
        model.setLocationCityId(_do.getLocationCityId());
        model.setLocationTownId(_do.getLocationTownId());
        return  model;
    }

    /**
     * 添加酒店信息Model
     * @return
     */
    public CommonItemPublishDTO getBizCommonItemPublishDTO(){
        if(hotelMessageVO==null){
            return null;
        }
        CommonItemPublishDTO dto =new CommonItemPublishDTO();
        /*****添加商品信息***/
        ItemDO itemDO = new  ItemDO();//
        itemDO.setSellerId(hotelMessageVO.getSellerId());//商家ID
        itemDO.setOutId(hotelMessageVO.getHotelId());//酒店ID
        itemDO.setCategoryId(hotelMessageVO.getCategoryId());//商品类目ID
        itemDO.setOutType(ResourceType.HOTEL.getType());// 酒店类型:酒店景区 都outType
        itemDO.setTitle(hotelMessageVO.getTitle());//商品标题
        itemDO.setCode(hotelMessageVO.getCode());//商品代码
        itemDO.setPayType(hotelMessageVO.getPayType());//付款方式
        itemDO.setDescription(hotelMessageVO.getDescription());//退订规则描述
        /***feature**/
        ItemFeature itemFeature = new ItemFeature(null);
        //itemFeature.put(ItemFeatureKey.CANCEL_LIMIT, CancelLimit.Ok.getType());
        /**房型ID**/
        itemFeature.put(ItemFeatureKey.ROOM_ID,hotelMessageVO.getRoomId());//房型ID

        itemFeature.put(ItemFeatureKey.CANCEL_LIMIT, hotelMessageVO.getCancelLimit());//退订规则
        itemFeature.put(ItemFeatureKey.LATEST_CHECKIN,hotelMessageVO.getLatestCheckin());//前端String转list
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,hotelMessageVO.getStartBookTimeLimit());//提前预定天数
        itemFeature.put(ItemFeatureKey.BREAKFAST,hotelMessageVO.getBreakfast());//早餐
        itemDO.setItemFeature(itemFeature);
        /****sku价格日历***/
        // 价格日历 json解析
       // CategoryPropertyValueDO + 日期 存到 ItemSkuPVPair 中,每个sku 只有 一个 pv 属性
        if(hotelMessageVO.getCurrentState()==UPDATE){
           Map<String,Object> paramUp =  getUpdateItem(hotelMessageVO.getSupplierCalendar());
            dto.setAddItemSkuDOList( paramUp.get(ADD)==null?null:(List<ItemSkuDO>)paramUp.get(ADD));
            dto.setUpdItemSkuDOList( paramUp.get(UPDATE)==null?null:(List<ItemSkuDO>)paramUp.get(UPDATE));
            dto.setDelItemSkuDOList(paramUp.get(DEL)==null?null:(List<Long>)paramUp.get(DEL));
        }else{
            List<ItemSkuDO>  itemSkuDOList = addItemSkuDOList(hotelMessageVO.getSupplierCalendar());
            if(CollectionUtils.isEmpty(itemSkuDOList)){
                return null;
            }
            dto.setItemSkuDOList(itemSkuDOList);//价格日历信息
        }
        dto.setItemDO(itemDO);//商品信息

        return dto;
    }

    /**
     * 添加商品信息配置价格日历sku
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
     * 更新商品信息添加价格日历信息
     * @param supplierCalendar
     * @return
     */
    public Map<String,Object> getUpdateItem(String supplierCalendar){
         List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
         List<Long> delItemSkuDOList = new ArrayList<Long>();
         List<ItemSkuDO> updItemSkuDOList = new ArrayList<ItemSkuDO>();

        if (StringUtils.isBlank(supplierCalendar)){
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
                    ItemSkuDO upSkuDo  = getItemSkuDOByBiz(template,biz);
                    /**更新sku需要回填对应的skuid*/
                    upSkuDo.setId((Long)biz.getSku_id());
                    updItemSkuDOList.add(upSkuDo);
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
        sukparam.put(UPDATE,updItemSkuDOList);
        sukparam.put(DEL,delItemSkuDOList);
        return  sukparam;
    }

    /**
     * 根据价格日历信息,拼装itemsku
     * @param biz
     * @return
     */
    public ItemSkuDO getItemSkuDOByBiz( SupplierCalendarTemplate template,BizSkuInfo biz){
        ItemSkuDO sku = new ItemSkuDO();
        sku.setSellerId(template.getSeller_id());//商家ID
        sku.setCategoryId(hotelMessageVO.getCategoryId());//类目ID
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
     * 拼装商品信息返回数据
     * @return
     */
    public HotelMessageVO getBizQueryHotelMessageVOyData(){

        ItemFeature itemFeature = itemDO.getItemFeature();
        hotelMessageVO.setHotelId(itemDO.getOutId());//酒店ID
        hotelMessageVO.setOutType(itemDO.getOutType());//酒店类型
        hotelMessageVO.setOutTypeStr(ResourceType.getByType(itemDO.getOutType()).getDesc());//酒店类型name
        hotelMessageVO.setTitle(itemDO.getTitle());
        hotelMessageVO.setCode(itemDO.getCode());
        hotelMessageVO.setPayType(itemDO.getPayType());
        hotelMessageVO.setDescription(itemDO.getDescription());
        hotelMessageVO.setRoomId(itemFeature.getRoomId());// 房型ID
        hotelMessageVO.setCancelLimit(itemFeature.getCancelLimit());//退订规则
        hotelMessageVO.setCancelLimitStr(CancelLimit.getByType(itemFeature.getCancelLimit()).getDesc());//退订规则 string
        hotelMessageVO.setLatestCheckin(itemFeature.getLatestCheckin());//最后到点时间
        hotelMessageVO.setStartBookTimeLimit(Long.valueOf(itemFeature.getStartBookTimeLimit()));// 提前预定天数
        hotelMessageVO.setBreakfast(itemFeature.getBreakfast());//早餐
        /**价格日历**/
        hotelMessageVO.setSupplierCalendar(getSupplierCalendarJson());
        /***房型信息**/
        hotelMessageVO.setListRoomMessageVO(getRoomMessageVOList());
        return hotelMessageVO;
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
        List<BizSkuInfo> bizArr = new ArrayList<>(itemSkuDOList.size());
        for(ItemSkuDO sku: itemSkuDOList){
            BizSkuInfo bizSkuInfo = new BizSkuInfo();
            bizSkuInfo.setSku_id(sku.getId());
            bizSkuInfo.setPrice(new BigDecimal(sku.getPrice()));;//价格
            bizSkuInfo.setStock_num(sku.getStockNum());//库存
            ItemSkuPVPair pvp = sku.getItemSkuPVPairList().get(0);
            bizSkuInfo.setvTxt(pvp.getVTxt());//日期
            bizArr.add(bizSkuInfo);
        }
        temp.setBizSkuInfo((BizSkuInfo[])bizArr.toArray());
        json = CommonJsonUtil.objectToJson(temp,SupplierCalendarTemplate.class);
        return json;

    }

    /**
     * 获取房型信息
     * @return
     */
    public  List<RoomMessageVO> getRoomMessageVOList(){
        if(CollectionUtils.isEmpty(listRoomDO)){
            return  null;
        }
        List<RoomMessageVO> roomList = new ArrayList<RoomMessageVO>(listRoomDO.size());
        for(RoomDO room :listRoomDO){
            RoomMessageVO vo = new  RoomMessageVO();
            vo.setRoomId(room.getId());
            vo.setHotelId(room.getHotelId());
            vo.setName(room.getName());
            vo.setPics(room.getPics());
            RoomFeature roomFeature = room.getFeature();
            vo.setArea(roomFeature.getArea());
            vo.setBed(roomFeature.getBed());
            vo.setWindow(roomFeature.getWindow());
            List<Integer> netList = roomFeature.getNetwork();
            StringBuffer sb = new StringBuffer();
            if(CollectionUtils.isNotEmpty(netList)){
                for(Integer network:netList){
                    sb.append(RoomNetwork.getByType(network.intValue()).getDesc());
                    sb.append(" ");
                }
                vo.setNetworkStr(sb.toString());
            }
            vo.setNetwork(roomFeature.getNetwork());
            vo.setPeople(roomFeature.getPeople());
            roomList.add(vo);
        }
        return roomList;

    }

    /**
     * 房型model转换
     * @param roomDO
     * @return
     */
    public RoomMessageVO RoomDOToRoomMessageVO(RoomDO roomDO){
        RoomMessageVO vo = new RoomMessageVO();
        if(roomDO==null){
            return null;
        }
       // vo.setRoomId(roomDO.getId());
        //vo.setName(roomDO.getName());
        //vo.setArea(roomDO.getFeature().getArea());
        //vo.setPeopleNum(roomDO.getFeature().getPeople());
        return vo;
    }


    public List<ItemSkuDO> getItemSkuDOList() {
        return itemSkuDOList;
    }

    public void setItemSkuDOList(List<ItemSkuDO> itemSkuDOList) {
        this.itemSkuDOList = itemSkuDOList;
    }

    public HotelDO getHotelDO() {
        return hotelDO;
    }

    public void setHotelDO(HotelDO hotelDO) {
        this.hotelDO = hotelDO;
    }

    public List<RoomDO> getListRoomDO() {
        return listRoomDO;
    }

    public void setListRoomDO(List<RoomDO> listRoomDO) {
        this.listRoomDO = listRoomDO;
    }

    public HotelMessageVO getHotelMessageVO() {
        return hotelMessageVO;
    }

    public void setHotelMessageVO(HotelMessageVO hotelMessageVO) {
        this.hotelMessageVO = hotelMessageVO;
    }

    public WebResult<PageVO<HotelMessageVO>> getPageResult() {
        return pageResult;
    }

    public void setPageResult(WebResult<PageVO<HotelMessageVO>> pageResult) {
        this.pageResult = pageResult;
    }

    public WebResult<HotelMessageVO> getWebResult() {
        return webResult;
    }

    public void setWebResult(WebResult<HotelMessageVO> webResult) {
        this.webResult = webResult;
    }

    public WebResult<Long> getLongWebResult() {
        return longWebResult;
    }

    public void setLongWebResult(WebResult<Long> longWebResult) {
        this.longWebResult = longWebResult;
    }

    public ItemDO getItemDO() {
        return itemDO;
    }

    public void setItemDO(ItemDO itemDO) {
        this.itemDO = itemDO;
    }

    public CategoryPropertyValueDO getCategoryPropertyValueDO() {
        return categoryPropertyValueDO;
    }

    public void setCategoryPropertyValueDO(CategoryPropertyValueDO categoryPropertyValueDO) {
        this.categoryPropertyValueDO = categoryPropertyValueDO;
    }

    public WebResult<List<RoomMessageVO>> getListRoomMessageVOResult() {
        return listRoomMessageVOResult;
    }

    public void setListRoomMessageVOResult(WebResult<List<RoomMessageVO>> listRoomMessageVOResult) {
        this.listRoomMessageVOResult = listRoomMessageVOResult;
    }

    public static void main(String[] args) {

        System.out.println(ItemFeatureKey.CANCEL_LIMIT);
        String aa = "{\\\"supplier_calendar\\\":{\\\"seller_id\\\":\\\"2088102122524333\\\",\\\"biz_list\\\":[{\\\"stock_num\\\":10,\\\"price\\\":\\\"8.8\\\",\\\"vTxt\\\":\\\"2088101117955611\\\"}]}}";
    }



}

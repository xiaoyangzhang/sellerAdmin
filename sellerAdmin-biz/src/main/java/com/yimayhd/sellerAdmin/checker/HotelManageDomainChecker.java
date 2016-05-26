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
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        if(hotelMessageVO.getHotelId()!=null){

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
        if(hotelMessageVO.getHotelId()==null){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "酒店ID为空");
        }

       return  WebResult.success(null);
    }

    /**
     *
     * @return
     */
    public WebResult checkQueryHotelMessageVOyData(){
        if(hotelMessageVO.getHotelId()==null){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "酒店ID为空");
        }
        if(hotelMessageVO.getItemId()==null){
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
        if (hotelMessageVO.getPayType() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "支付方式不能为空");
        }
        if (hotelMessageVO.getCancelLimit() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "退订限制不能为空");
        }
        if (StringUtils.isBlank(hotelMessageVO.getDescription())) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "退订规则不能为空");
        }
        if (StringUtils.isBlank(hotelMessageVO.getStoreLastTime())) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "最晚到点时间不能为空");
        }
        if (hotelMessageVO.getStartBookTimeLimit() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "提前预定天数不能为空");
        }
        if (hotelMessageVO.getBreakfast() == null) {
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
        model.setAddress(_do.getLocationText());
        model.setArea(_do.getLocationProvinceName()+"/"+_do.getLocationCityName()+"/"+_do.getLocationCityName());
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
        ItemDO itemDO = new  ItemDO();// 添加商品信息
        itemDO.setSellerId(hotelMessageVO.getSellerId());//商家ID
        itemDO.setOutId(hotelMessageVO.getHotelId());//酒店ID
        itemDO.setOutType(ResourceType.HOTEL.getType());// 酒店类型:酒店景区 都outType
        itemDO.setTitle(hotelMessageVO.getTitle());//商品标题
        itemDO.setCode(hotelMessageVO.getCode());//商品代码
        itemDO.setPayType(hotelMessageVO.getPayType());//付款方式
        itemDO.setDescription(hotelMessageVO.getDescription());//退订规则描述
        /***feature**/
        ItemFeature itemFeature = new ItemFeature(null);
        //itemFeature.put(ItemFeatureKey.CANCEL_LIMIT, CancelLimit.Ok.getType());
        /**房型ID**/
        itemFeature.put(ItemFeatureKey.ROOM_ID,hotelMessageVO.getRoomId().longValue());

        itemFeature.put(ItemFeatureKey.CANCEL_LIMIT, hotelMessageVO.getCancelLimit().intValue());//退订规则
        itemFeature.put(ItemFeatureKey.LATEST_CHECKIN,hotelMessageVO.getLatestCheckin());//前端String转list
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,hotelMessageVO.getStartBookTimeLimit());//提前预定天数
        itemFeature.put(ItemFeatureKey.BREAKFAST,hotelMessageVO.getBreakfast());//早餐
        itemDO.setItemFeature(itemFeature);
        /****sku价格日历***/
        // 价格日历 json解析
        List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
       // CategoryPropertyValueDO + 日期 存到 ItemSkuPVPair 中,每个sku 只有 一个 pv 属性
        dto.setItemDO(itemDO);

        return dto;
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
        hotelMessageVO.setStartBookTimeLimit(itemFeature.getStartBookTimeLimit());// 提前预定天数
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
        for(ItemSkuDO sku: itemSkuDOList){
            sku.getId();
            sku.getSellerId();//商家ID
            sku.getPrice();//价格
            sku.getStockNum();//库存
            ItemSkuPVPair pvp = sku.getItemSkuPVPairList().get(0);
            pvp.getVTxt();//日期
        }

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
           // vo.getNetwork(roomFeature.getNetwork());
            vo.setPeople(roomFeature.getPeople());

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

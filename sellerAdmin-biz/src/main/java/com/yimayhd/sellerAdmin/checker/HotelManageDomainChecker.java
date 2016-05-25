package com.yimayhd.sellerAdmin.checker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IterableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
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

    /***拼装商品信息参数start**/
    private ItemDO itemDO;
    private List<ItemSkuDO> itemSkuDOList;
    private HotelDO hotelDO;
    private List<RoomDO> listRoomDO;

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
        if(hotelMessageVO.getProvinceId()!=null){

        }
        if(hotelMessageVO.getCityId()!=null){

        }
        if(hotelMessageVO.getTownId()!=null){

        }
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
        model.setProvinceId(_do.getLocationProvinceId());
        model.setCityId(_do.getLocationCityId());
        model.setTownId(_do.getLocationTownId());
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
        List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();

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
        hotelMessageVO.setOutTypeStr(ResourceType.getByType(this.itemDO.getOutType()).getDesc());//酒店类型name
        hotelMessageVO.setTitle(itemDO.getTitle());
        hotelMessageVO.setCode(itemDO.getCode());
        hotelMessageVO.setPayType(itemDO.getPayType());
        hotelMessageVO.setDescription(itemDO.getDescription());
        hotelMessageVO.setRoomId(itemFeature.getRoomId());
        hotelMessageVO.setCancelLimit(itemFeature.getCancelLimit());//退订规则
        hotelMessageVO.setCancelLimitStr(CancelLimit.getByType(itemFeature.getCancelLimit()).getDesc());//退订规则 string
        hotelMessageVO.setLatestCheckin(itemFeature.getLatestCheckin());//最后到点时间
        hotelMessageVO.setStartBookTimeLimit(itemFeature.getStartBookTimeLimit());// 提前预定天数
        hotelMessageVO.setBreakfast(itemFeature.getBreakfast());//早餐

        /***房型信息**/
        return null;
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

    public static void main(String[] args) {

        System.out.println(ItemFeatureKey.CANCEL_LIMIT);
        String aa = "{\\\"supplier_calendar\\\":{\\\"seller_id\\\":\\\"2088102122524333\\\",\\\"biz_list\\\":[{\\\"stock_num\\\":10,\\\"price\\\":\\\"8.8\\\",\\\"vTxt\\\":\\\"2088101117955611\\\"}]}}";
    }


}

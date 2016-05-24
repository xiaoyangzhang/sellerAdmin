package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 九牛-酒店信息check
 * Created by wangdi on 16/5/13.
 */
public class HotelManageDomainChecker {
    private HotelMessageVO hotelMessageVO;
    private WebResult <PageVO<HotelMessageVO>> pageResult ;



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
    public WebResult checkAddHotelMessageVOByData(){
        //商品标题
        if(StringUtils.isBlank(hotelMessageVO.getTitle())){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "商品标题不能为空");
        }
        return null;
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
}

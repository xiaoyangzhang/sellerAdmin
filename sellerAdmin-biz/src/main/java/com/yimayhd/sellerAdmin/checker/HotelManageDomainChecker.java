package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import org.apache.commons.lang3.StringUtils;

/**
 * 九牛-酒店信息check
 * Created by wangdi on 16/5/13.
 */
public class HotelManageDomainChecker {
    private HotelMessageVO hotelMessageVO;

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
    public WebResult checkQueryHotelMessageVOInfo(){
        if(hotelMessageVO.getHotelId()==null){
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "商品ID为空");
        }
       return  WebResult.success(null);
    }


}

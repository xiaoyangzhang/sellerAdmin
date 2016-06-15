package com.yimayhd.sellerAdmin.domain;

import com.yimayhd.sellerAdmin.enums.SellerBaseCodeEnum;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 九牛-
 * Created by wangdi on 16/5/13.
 */
public class SellerHotelManageDomain {
    private static final Logger log = LoggerFactory.getLogger("hotelManage-business.log");
    private HotelMessageVO hotelMessageVO;




    public SellerHotelManageDomain(HotelMessageVO hotelMessageVO) {
        this.hotelMessageVO = hotelMessageVO;
    }

    /**
     * queryHotelMessageVOListByData 参数校验
     *
     * @return
     */
    public SellerBaseCodeEnum checkHotelMessageVO() {

        if (hotelMessageVO == null) {
            return SellerBaseCodeEnum.NULL_ERROR;
        }
        if (hotelMessageVO.getPage() == null || hotelMessageVO.getPageSize() == null) {
            return SellerBaseCodeEnum.PAGE_PARAM_INVALID;
        }

        return SellerBaseCodeEnum.SUCCESS;

    }





}

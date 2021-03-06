package com.yimayhd.sellerAdmin.service.hotelManage;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.CallResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;

import java.util.List;

/**
 * Created by wangdi on 16/5/13.
 */
public interface HotelManageService {
    /**
     * 查询酒店列表
     * @return
     */
    public WebResult<PageVO<HotelMessageVO>> queryHotelMessageVOListByData(HotelMessageVO hotelMessageVO);

    /**
     * 酒店商品信息查询接口
     * @param hotelMessageVO
     * @return
     */
    public WebResult<HotelMessageVO> queryHotelMessageVOyData( HotelMessageVO hotelMessageVO);

    /**
     * 查询酒店房型列表
     * @param hotelMessageVO
     * @return
     */
    public WebResult<List<RoomMessageVO>> queryRoomTypeListByData(HotelMessageVO hotelMessageVO);

    /**
     * 商品信息填加
     * @param hotelMessageVO
     * @return
     */
    public WebResult<HotelMessageVO> addHotelMessageVOByData( HotelMessageVO hotelMessageVO);
    /**
     * 编辑商品信息
     * @param hotelMessageVO
     * @return
     */
    public  WebResult<Long> editHotelMessageVOByData( HotelMessageVO hotelMessageVO);



}

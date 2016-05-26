package com.yimayhd.sellerAdmin.service.hotelManage;


import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;

/**
 * Created by wangdi on 16/5/16.
 */
public interface ScenicManageService {
    /**
     * 景区资源列表
     * @param scenicManageVO
     * @return
     */
    public WebResult<PageVO<ScenicManageVO>> queryScenicManageVOListByData(ScenicManageVO scenicManageVO);

    /**
     * 景区商品信息
     * @param scenicManageVO
     * @return
     */
    public WebResult<ScenicManageVO> queryScenicManageVOByData(ScenicManageVO scenicManageVO);

    /**
     * 添加景区商品信息
     * @param scenicManageVO
     * @return
     */
    public WebResult<ScenicManageVO> addScenicManageVOByDdata(ScenicManageVO scenicManageVO);

    /**
     * 修改景区商品信息
     * @param scenicManageVO
     * @return
     */
    public WebResult<Boolean> editScenicManageVOByDdata(ScenicManageVO scenicManageVO);



}

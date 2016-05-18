package com.yimayhd.sellerAdmin.service.hotelManage.impl;


import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageServiceImpl  implements ScenicManageService {
    /**
     * 查询景区列表
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<PageVO<ScenicManageVO>> queryScenicManageVOListByData(ScenicManageVO scenicManageVO) {


        return null;
    }

    /**
     * 查询景区详情
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<ScenicManageVO> queryScenicManageVOByData(ScenicManageVO scenicManageVO) {
        return null;
    }

    /**
     * 添加景区商品信息
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<ScenicManageVO> addScenicManageVOByDdata(ScenicManageVO scenicManageVO) {
        return null;
    }

    /**
     * 修改景区商品信息
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<Boolean> editScenicManageVOByDdata(ScenicManageVO scenicManageVO) {
        return null;
    }
}

package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.ItemVO;

import java.util.List;

/**
 * Created by weiwenliang on 20160406.
 */
public interface CityActivityService {

    /**
     * 根据id获取同城活动商品信息
     * @param itemId
     * @return
     * @throws Exception
     */
    CityActivityItemVO getCityActivityById(long itemId)throws Exception;

    /**
     * 新增同城活动商品
     * @param cityActivityItemVO
     * @return
     * @throws Exception
     */
    WebResultSupport addCityActivityItem(CityActivityItemVO cityActivityItemVO)throws Exception;


    /**
     * 修改普通商品
     * @param itemVO 普通商品表单对象
     * @throws Exception
     */
    WebResultSupport modifyCityActivityItem(CityActivityItemVO itemVO)throws Exception;

}

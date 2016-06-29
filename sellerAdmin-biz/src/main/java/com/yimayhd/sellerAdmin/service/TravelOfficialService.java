package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.TravelOfficial;
import com.yimayhd.sellerAdmin.model.query.TravelOfficialListQuery;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;

/**
 * Created by Administrator on 2015/11/10.
 */
public interface TravelOfficialService {
        /**
         * 获取官方游记列表(可带查询条件)
         * @return 官方游记列表
         */
        PageVO<SnsTravelSpecialtyDO> getList(TravelOfficialListQuery travelOfficialListQuery)throws Exception;
        /**
         * 获取官方游记详情
         * @return 官方游记详情
         */
        TravelOfficial getById(long id)throws Exception;

        /**
         * 添加官方游记
         * @param travelOfficial
         * @return
         * @throws Exception
         */
        TravelOfficial add(TravelOfficial travelOfficial)throws Exception;

        /**
         * 修改官方游记
         * @param travelOfficial
         * @throws Exception
         */
        boolean modify(TravelOfficial travelOfficial)throws Exception;
        
        /**
        * @Title: upOrDownStatus 
        * @Description:(批量上下架) 
        * @author create by yushengwei @ 2015年12月25日 下午6:52:09 
        * @param @param ids
        * @param @param status
        * @param @return 
        * @return boolean 返回类型 
        * @throws
         */
        boolean batchUpOrDownStatus(List<Long> ids,int status) throws Exception;
}

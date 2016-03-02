package com.yimayhd.sellerAdmin.service;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/21.
 */
public interface FacilityIconService {
    List<FacilityIconDO> getListByType(int type)throws Exception;
    Map<Integer, FacilityIconDO> getMapByType(int type)throws Exception;
}

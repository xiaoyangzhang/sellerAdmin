package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.sellerAdmin.service.FacilityIconService;

/**
 * Created by Administrator on 2015/11/21.
 */
public class FacilityIconServiceImpl implements FacilityIconService {

    @Override
    public List<FacilityIconDO> getListByType(int type) throws Exception {
        List<FacilityIconDO> facilityIconDOList = new ArrayList<FacilityIconDO>();
        if(type == 1){
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("房价设施" + i);
                facilityIconDOList.add(facilityIconDO);
            }

        }else if (type == 2){
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("房价服务" + i);
                facilityIconDOList.add(facilityIconDO);
            }
        } else{
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("酒店设施" + i);
                facilityIconDOList.add(facilityIconDO);
            }
        }
        return facilityIconDOList;
    }

    @Override
    public Map<Integer, FacilityIconDO> getMapByType(int type) throws Exception {
        Map<Integer, FacilityIconDO> map = new HashMap<Integer, FacilityIconDO>();
        if(type == 1){
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("房价设施" + i);
                map.put(i,facilityIconDO);
            }

        }else if (type == 2){
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("房价服务" + i);
                map.put(i, facilityIconDO);
            }
        } else{
            for (int i = 0; i < 10; i++) {
                FacilityIconDO facilityIconDO = new FacilityIconDO();
                facilityIconDO.setId(1);
                facilityIconDO.setName("酒店设施" + i);
                map.put(i,facilityIconDO);
            }
        }
        return map;
    }
}

package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;

/**
 * Created by wangdi on 16/5/18.
 */
public class ScenicManageDomainChecker {
    private ScenicManageVO scenicManageVO;


    public ScenicManageDomainChecker(ScenicManageVO scenicManageVO){
        this.scenicManageVO = scenicManageVO;
    }


    public ScenicManageVO getScenicManageVO() {
        return scenicManageVO;
    }

    public void setScenicManageVO(ScenicManageVO scenicManageVO) {
        this.scenicManageVO = scenicManageVO;
    }
}

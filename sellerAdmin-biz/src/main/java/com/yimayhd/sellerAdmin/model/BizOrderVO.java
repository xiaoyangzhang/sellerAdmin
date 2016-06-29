package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;
//import org.apache.commons.beanutils.BeanUtils;

import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallInfo;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;


public class BizOrderVO extends BizOrderDO {
    private static final long serialVersionUID = 8182585991216318684L;
    private UserDO userDO;
    private IMallInfo mallInfo;

    private long usePoint;

    private long givePoint;

    private int payChannel;

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public static BizOrderVO getBizOrderVO(BizOrderDO bizOrderDO) throws Exception{
        BizOrderVO bizOrderVO = new BizOrderVO();
        //BeanUtils.copyProperties(bizOrderVO, bizOrderDO);
        BeanUtils.copyProperties(bizOrderDO, bizOrderVO);
        //获取部门工号，中短号等信息
        bizOrderVO.setMallInfo(BizOrderUtil.getIMallInfo(bizOrderDO));
        //电话去+86
        if(null != bizOrderVO.getMallInfo()){
            bizOrderVO.getMallInfo().setPn(PhoneUtil.mask(PhoneUtil.phoneFormat(bizOrderVO.getMallInfo().getPn())));
        }
        bizOrderVO.setUsePoint(BizOrderUtil.getUsePointNum(bizOrderDO));
        bizOrderVO.setGivePoint(BizOrderUtil.getLong(bizOrderDO, BizOrderFeatureKey.GIVE_POINT));
        bizOrderVO.payChannel = BizOrderUtil.getInt(bizOrderDO, BizOrderFeatureKey.PAY_CHANNEL);
        return bizOrderVO;
    }
    public static BizOrderDO getBizOrderDO(BizOrderVO bizOrderVO){
        return bizOrderVO;
    }

    public IMallInfo getMallInfo() {
        return mallInfo;
    }

    public void setMallInfo(IMallInfo mallInfo) {
        this.mallInfo = mallInfo;
    }

    public long getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(long usePoint) {
        this.usePoint = usePoint;
    }

    public long getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(long givePoint) {
        this.givePoint = givePoint;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }
}

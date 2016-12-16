package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class SettlementDetailVO extends SettlementDetailDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 实际支付金额 - 元
     */
    private double actualAmountYuan;

    public static SettlementDetailVO getSettlementDetailVO(SettlementDetailDTO dto) {

        SettlementDetailVO vo = new SettlementDetailVO();
        BeanUtils.copyProperties(dto, vo);
        //分转元
        vo.setActualAmountYuan(NumUtil.moneyTransformDouble(vo.getActualAmount()));
        return vo;
    }

    public double getActualAmountYuan() {
        return actualAmountYuan;
    }

    public void setActualAmountYuan(double actualAmountYuan) {
        this.actualAmountYuan = actualAmountYuan;
    }
}

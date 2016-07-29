package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class SettlementDetailVO extends SettlementDetailDTO{

	private static final long serialVersionUID = 1L;
	
	/** 交易金额 - 元*/
    private double tradeAmountYuan;

	public static SettlementDetailVO getSettlementDetailVO(SettlementDetailDTO dto){
		
		SettlementDetailVO vo = new SettlementDetailVO();
        BeanUtils.copyProperties(dto, vo);
        //分转元
        vo.setTradeAmountYuan(NumUtil.moneyTransformDouble(vo.getTradeAmount()));
        return vo;
    }
	
	public double getTradeAmountYuan() {
		return tradeAmountYuan;
	}

	public void setTradeAmountYuan(double tradeAmountYuan) {
		this.tradeAmountYuan = tradeAmountYuan;
	}
}

package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class SettlementVO extends SettlementDTO{

	private static final long serialVersionUID = 1L;
	
	/** 交易金额 - 元*/
    private double tradeAmountYuan;

    /** 服务费 （交易金额*1%） - 元 */
    private double serviceFeeYuan;
    
    /** 结算金额 (交易金额-服务费)  - 元*/
    private double settlementAmountYuan;

	public static SettlementVO getSettlementVO(SettlementDTO dto){
		
		SettlementVO vo = new SettlementVO();
        BeanUtils.copyProperties(dto, vo);
        //分转元
        vo.setTradeAmountYuan(NumUtil.moneyTransformDouble(vo.getTradeAmount()));
        vo.setServiceFeeYuan(NumUtil.moneyTransformDouble(vo.getServiceFee()));
        vo.setSettlementAmountYuan(NumUtil.moneyTransformDouble(vo.getSettlementAmount()));
        return vo;
    }
	
	public double getTradeAmountYuan() {
		return tradeAmountYuan;
	}
	public void setTradeAmountYuan(double tradeAmountYuan) {
		this.tradeAmountYuan = tradeAmountYuan;
	}
	public double getSettlementAmountYuan() {
		return settlementAmountYuan;
	}
	public void setSettlementAmountYuan(double settlementAmountYuan) {
		this.settlementAmountYuan = settlementAmountYuan;
	}
	public double getServiceFeeYuan() {
		return serviceFeeYuan;
	}
	public void setServiceFeeYuan(double serviceFeeYuan) {
		this.serviceFeeYuan = serviceFeeYuan;
	}
}

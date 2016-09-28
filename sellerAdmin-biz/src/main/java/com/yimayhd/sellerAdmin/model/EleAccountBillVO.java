package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class EleAccountBillVO extends EleAccountBillDTO{
	
	private static final long serialVersionUID = 1L;

	/**收支金额(元)*/
    private double transAmountYuan;

    /**账户余额（元）*/
    private double accountBalanceYuan;
	
	public static EleAccountBillVO getWithdrawalDTO(EleAccountBillDTO dto){
		EleAccountBillVO vo = new EleAccountBillVO();
        BeanUtils.copyProperties(dto, vo);
        vo.setTransAmountYuan(NumUtil.moneyTransformDouble(vo.getTransAmount()));
        vo.setAccountBalanceYuan(NumUtil.moneyTransformDouble(vo.getAccountBalance()));
        
        return vo;
    }
	
	public double getTransAmountYuan() {
		return transAmountYuan;
	}

	public void setTransAmountYuan(double transAmountYuan) {
		this.transAmountYuan = transAmountYuan;
	}

	public double getAccountBalanceYuan() {
		return accountBalanceYuan;
	}

	public void setAccountBalanceYuan(double accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
}

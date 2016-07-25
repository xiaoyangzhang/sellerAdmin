package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.eleaccount.WithdrawalDTO;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class WithdrawalVO extends WithdrawalDTO{
	
	
	public static WithdrawalDTO getWithdrawalDTO(WithdrawalVO vo){
		
		WithdrawalDTO dto = new WithdrawalDTO();
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }
}

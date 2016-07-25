package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class EleAccountBillVO extends EleAccountBillDTO{
	
	private static final long serialVersionUID = 1L;

	public static EleAccountBillVO getWithdrawalDTO(EleAccountBillDTO dto){
		EleAccountBillVO vo = new EleAccountBillVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }
}

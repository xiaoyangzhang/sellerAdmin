package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDetailDTO;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class SettlementDetailVO extends SettlementDetailDTO{

	private static final long serialVersionUID = 1L;

	public static SettlementDetailVO getSettlementDetailVO(SettlementDetailDTO dto){
		
		SettlementDetailVO vo = new SettlementDetailVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }
}

package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class SettlementVO extends SettlementDTO{

	private static final long serialVersionUID = 1L;

	public static SettlementVO getSettlementVO(SettlementDTO dto){
		
		SettlementVO vo = new SettlementVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }
}

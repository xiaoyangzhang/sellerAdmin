package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.eleaccount.EleAccountInfoResult;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class EleAccountInfoVO extends EleAccountInfoResult{

	private static final long serialVersionUID = 1L;
	
	public static EleAccountInfoVO getEleAccountInfoVO(EleAccountInfoResult result){
		if(result == null){
			return null;
		}
		
		EleAccountInfoVO vo = new EleAccountInfoVO();
		BeanUtils.copyProperties(vo, result);
		return vo;
	}
}

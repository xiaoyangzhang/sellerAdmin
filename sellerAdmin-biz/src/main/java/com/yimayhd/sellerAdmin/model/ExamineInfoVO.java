package com.yimayhd.sellerAdmin.model;

import java.util.Date;


import org.apache.commons.beanutils.BeanUtils;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;

/***
 * 
 * @author zhangxy
 * 
 *
 */
public class ExamineInfoVO extends ExamineInfoDTO  {
	
	private static final long serialVersionUID = 8113499074191458166L;
	
	public  ExamineInfoDTO getExamineInfoDTO(ExamineInfoVO vo) throws Exception {
		ExamineInfoDTO dto=new ExamineInfoDTO();
		BeanUtils.copyProperties(dto, vo);
		dto.setDomainId(1200);
		//dto.setSellerId(new SessionManager().getUserId());
		dto.setSellerId(25);
		dto.setCreateDate(new Date());
		dto.setType(1);
		dto.setAccountBankProvinceCode(vo.getAccountBankProvince());
		dto.setAccountBankCityCode(vo.getAccountBankCityCode());
		return dto;
		
	}

}

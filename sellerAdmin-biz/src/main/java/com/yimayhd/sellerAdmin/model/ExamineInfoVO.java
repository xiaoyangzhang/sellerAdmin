package com.yimayhd.sellerAdmin.model;

import java.util.Date;



import org.apache.commons.beanutils.BeanUtils;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.enums.ExamineType;

/***
 * 
 * @author zhangxy
 * 
 *
 */
public class ExamineInfoVO extends ExamineInfoDTO  {
	
	private static final long serialVersionUID = 8113499074191458166L;
	private String province;
	private String city;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public  ExamineInfoDTO getExamineInfoDTO(ExamineInfoVO vo) throws Exception {
		ExamineInfoDTO dto=new ExamineInfoDTO();
		BeanUtils.copyProperties(dto, vo);
		dto.setDomainId(1200);
		//dto.setSellerId(new SessionManager().getUserId());
		dto.setSellerId(25);
		dto.setCreateDate(new Date());
		dto.setType(ExamineType.TALENT.getId());
		dto.setAccountBankProvinceCode(vo.getProvince());
		dto.setAccountBankCityCode(vo.getCity());
		return dto;
		
	}

}

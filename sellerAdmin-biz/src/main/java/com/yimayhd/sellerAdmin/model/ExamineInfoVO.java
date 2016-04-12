package com.yimayhd.sellerAdmin.model;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;

/***
 * 
 * @author zhangxy
 * 
 *
 */
public class ExamineInfoVO extends ExamineInfoDTO  {
	
	private static final long serialVersionUID = 8113499074191458166L;
	protected Logger log = LoggerFactory.getLogger(getClass());
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
	public  ExamineInfoDTO getExamineInfoDTO(ExamineInfoVO vo,long userId) throws Exception {
		if (vo == null || userId <= 0 ) {
			log.error("get examineSubmitDTO params error :vo="+vo+"userId="+userId);
			throw new BaseException("参数错误");
		}
		ExamineInfoDTO dto=new ExamineInfoDTO();
		BeanUtils.copyProperties(dto, vo);
		if (vo.getDomainId() <= 0) {
			
			dto.setCreateDate(new Date());
		}
		dto.setDomainId(Constant.DOMAIN_JIUXIU);
		dto.setSellerId(userId);
		dto.setType(ExamineType.TALENT.getType());
		dto.setAccountBankProvinceCode(vo.getProvince());
		dto.setAccountBankCityCode(vo.getCity());
		return dto;
		
	}

	public InfoQueryDTO getInfoQueryDTO (long userId) {
		if (userId <= 0  ) {
			log.error("get infoQueryDTO error and params:userId="+userId);
			throw new BaseException("参数错误");
		}
		InfoQueryDTO infoQueryDTO = new InfoQueryDTO();
		infoQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		infoQueryDTO.setSellerId(userId);
		infoQueryDTO.setType(ExamineType.TALENT.getType());
		return infoQueryDTO;
		
	}
}

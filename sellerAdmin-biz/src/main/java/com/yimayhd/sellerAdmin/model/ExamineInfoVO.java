package com.yimayhd.sellerAdmin.model;

import java.lang.reflect.InvocationTargetException;

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
	private String a="aaa";
	private String b="bbb";
	
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public  ExamineInfoDTO getExamineInfoDTO(ExamineInfoVO vo) throws Exception {
		ExamineInfoDTO dto=new ExamineInfoDTO();
		BeanUtils.copyProperties(dto, vo);
		
		return dto;
		
	}

}

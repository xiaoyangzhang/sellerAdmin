package com.yimayhd.sellerAdmin.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.PicTextDO;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.domain.PictureTextDO;
import com.yimayhd.membercenter.client.domain.talent.TalentInfoDO;
import com.yimayhd.membercenter.client.dto.PictureTextDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.user.session.manager.SessionManager;

public class TalentInfoVO extends TalentInfoDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6724357327778286510L;

	private String pictureTextDOs;
	public String getPictureTextDOs() {
		return pictureTextDOs;
	}

	private String certificatess;
	public String getCertificatess() {
		return certificatess;
	}

	public void setCertificatess(String certificatess) {
		this.certificatess = certificatess;
	}

	public void setPictureTextDOs(String pictureTextDOs) {
		this.pictureTextDOs = pictureTextDOs;
	}

	private String filepath;//头像图片名称
	private String peopleName;//真实姓名
	private String tel;
	private String describe;
	private String imgpath;
	private String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public  TalentInfoDTO getTalentInfoDTO(TalentInfoVO vo) throws Exception {
		TalentInfoDTO dto=new TalentInfoDTO();
		dto.setDomainId(1200);
		dto.setTalentInfoDO(getTalentInfoDO(vo));
		PictureTextDTO pictureTextDTO=new PictureTextDTO();
		List<PictureTextDO> picTextDOs=new ArrayList<PictureTextDO>();
		
		List<PictureTextDO> pictureTextDOs = JSON.parseArray(vo.getPictureTextDOs(), PictureTextDO.class);
		for (PictureTextDO pictureTextDO : pictureTextDOs) {
			if (StringUtils.isNotBlank(pictureTextDO.getValue())) {
				picTextDOs.add(pictureTextDO);
			}
		}
		
		pictureTextDTO.setPicTexts(picTextDOs);
		dto.setPictureTextDTO(pictureTextDTO);
		//vo.getPicTextDOs();
		return dto;
		
	}
	
	public static TalentInfoDO getTalentInfoDO(TalentInfoVO vo) throws Exception {
		TalentInfoDO talentInfoDO=new TalentInfoDO();
		BeanUtils.copyProperties(talentInfoDO, vo);
		talentInfoDO.setCityCode(Integer.parseInt(vo.getCity()));
		talentInfoDO.setAvatar(vo.getFilepath());
		talentInfoDO.setReallyName(vo.getPeopleName());
		talentInfoDO.setServeDesc(vo.getDescribe());
		talentInfoDO.setTelNum(vo.getTel());
		talentInfoDO.setCertificates(JSON.parseArray(vo.getCertificatess(), CertificatesDO.class));
		talentInfoDO.setPictures(JSON.parseArray(vo.getImgpath(), String.class));
		//talentInfoDO.setId(new SessionManager().getUserId());
		talentInfoDO.setCity(vo.getProvince());
		talentInfoDO.setId(19000);
		return talentInfoDO;
		
	}


	
	

}

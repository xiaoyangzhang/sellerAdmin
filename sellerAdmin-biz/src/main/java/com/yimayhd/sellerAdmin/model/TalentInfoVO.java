package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.domain.PictureTextDO;
import com.yimayhd.membercenter.client.domain.talent.TalentInfoDO;
import com.yimayhd.membercenter.client.dto.PictureTextDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;

public class TalentInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6724357327778286510L;
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	private String pictureTextDOs;//图文详情
//	private int type;
//	
//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}

	public String getPictureTextDOs() {
		return pictureTextDOs;
	}

	private String certificatess;//服务类型
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

	private String describe;//服务描述
	private String imgpath;//店铺头图
	private String province;
	private String city;//cityCode
	private int gender;
	private Date birthday;
	private String nickName; // 昵称
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private long id;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getGender() {
		return gender;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}
	
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

	public  TalentInfoDTO getTalentInfoDTO(TalentInfoVO vo,long userId) throws Exception {
		if (vo == null || userId <= 0 ) {
			log.error("get talentInfoDTO params error :vo="+vo+"and userId="+userId);
			throw new BaseException("参数错误");
		}
		TalentInfoDTO dto=new TalentInfoDTO();
		dto.setDomainId(Constant.DOMAIN_JIUXIU);
		dto.setTalentInfoDO(getTalentInfoDO(vo,userId));
		PictureTextDTO pictureTextDTO=new PictureTextDTO();
		List<PictureTextDO> picTextDOs=new ArrayList<PictureTextDO>();
		
		List<PictureTextDO> pictureTextDOs = JSON.parseArray(vo.getPictureTextDOs(), PictureTextDO.class);
		if (pictureTextDOs != null && pictureTextDOs.size()>0) {
			
			for (PictureTextDO pictureTextDO : pictureTextDOs) {
				if (StringUtils.isNotBlank(pictureTextDO.getValue())) {
					picTextDOs.add(pictureTextDO);
				}
			}
			
			pictureTextDTO.setPicTexts(picTextDOs);
			dto.setPictureTextDTO(pictureTextDTO);
		}
		return dto;
		
	}
	
	public  TalentInfoDO getTalentInfoDO(TalentInfoVO vo,long userId) throws Exception {
		if (vo == null || userId <= 0 ) {
			log.error("get talentInfoDTO params error :vo="+vo+" and userId="+userId);
			throw new BaseException("参数错误");
		}
		TalentInfoDO talentInfoDO=new TalentInfoDO();
		//BeanUtils.copyProperties(talentInfoDO, vo);
		talentInfoDO.setBirthday(vo.getBirthday());
		talentInfoDO.setGender(vo.getGender());
		talentInfoDO.setNickName(vo.getNickName());
		talentInfoDO.setProvince(vo.getProvince());
		talentInfoDO.setCity(vo.getCity());
		talentInfoDO.setCityCode(Integer.parseInt(vo.getCity()));
		if (vo.getFilepath() != null) {
			
			talentInfoDO.setAvatar(vo.getFilepath());
		}
		talentInfoDO.setReallyName(vo.getPeopleName());
		talentInfoDO.setServeDesc(vo.getDescribe());
		talentInfoDO.setTelNum(vo.getTel());
		talentInfoDO.setServiceTypes(JSON.parseArray(vo.getCertificatess(), CertificatesDO.class));
		List<String> picList=new ArrayList<>();
		List<String> imgs = JSON.parseArray(vo.getImgpath(), String.class);
		if (imgs != null && imgs.size() > 0) {
			
			for (String str : imgs) {
				if (str.length()>0) {
					picList.add(str);
				}
			}
			talentInfoDO.setPictures(picList);
		}
		talentInfoDO.setProvinceCode(Integer.parseInt(vo.getProvince()));
		talentInfoDO.setId(userId);
		return talentInfoDO;
		
	}
	

	
	

}

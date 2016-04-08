package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yimayhd.commentcenter.client.domain.PicTextDO;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.FeatureType;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.sellerAdmin.model.enums.PictureTextItemType;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;

/**
 * 图文详情转换器
 * 
 * @author yebin
 *
 */
public class PictureTextConverter {
	public static PictureTextVO toPictureTextVO(PicTextResult picTextResult) {
		if (picTextResult == null) {
			return null;
		}
		PictureTextVO pictureTextVO = new PictureTextVO();
		List<PictureTextItemVo> pictureTextItemVos = new ArrayList<PictureTextItemVo>();
		List<PicTextDO> list = picTextResult.getList();
		if (CollectionUtils.isNotEmpty(list)) {
			for (PicTextDO picTextDO : list) {
				PictureTextItemVo pictureTextItemVo = new PictureTextItemVo();
				int type = picTextDO.getType();
				String pictureTextItemType = null;
				FeatureType byType = FeatureType.getByType(type);
				if (FeatureType.COMENT.equals(byType)) {
					pictureTextItemType = PictureTextItemType.TEXT.name();
				} else if (FeatureType.IMAGE.equals(byType)) {
					pictureTextItemType = PictureTextItemType.IMG.name();
				}
				pictureTextItemVo.setType(pictureTextItemType);
				pictureTextItemVo.setValue(picTextDO.getValue());
				pictureTextItemVos.add(pictureTextItemVo);
			}
		}
		pictureTextVO.setPictureTextItems(pictureTextItemVos);
		return pictureTextVO;
	}

	public static ComentEditDTO toComentEditDTO(PictureTextVO pictureTextVO) {
		if (pictureTextVO == null) {
			return null;
		}
		ComentEditDTO comentEditDTO = new ComentEditDTO();
		comentEditDTO.setOutId(pictureTextVO.getOutId());
		List<PicTextDO> picTextDOList = new ArrayList<PicTextDO>();
		List<PictureTextItemVo> pictureTextItems = pictureTextVO.getPictureTextItems();
		if (CollectionUtils.isNotEmpty(pictureTextItems)) {
			for (PictureTextItemVo pictureTextItemVo : pictureTextItems) {
				PicTextDO picTextDO = new PicTextDO();
				String type = pictureTextItemVo.getType();
				int picTextType = 0;
				if (StringUtils.isNotBlank(type)) {
					if (PictureTextItemType.TEXT.name().equalsIgnoreCase(type)) {
						picTextType = FeatureType.COMENT.getType();
					} else if (PictureTextItemType.IMG.name().equalsIgnoreCase(type)) {
						picTextType = FeatureType.IMAGE.getType();
					}
				}
				picTextDO.setType(picTextType);
				picTextDO.setValue(pictureTextItemVo.getValue());
				picTextDOList.add(picTextDO);
			}
		}
		comentEditDTO.setPicTextDOList(picTextDOList);
		return comentEditDTO;
	}

	public static ComentDTO toComentDTO(long itemId, PictureText pictureTextType, PictureTextVO pictureTextVO) {
		if (itemId <= 0 || pictureTextType == null || pictureTextVO == null) {
			return null;
		}
		ComentDTO comentDTO = new ComentDTO();
		comentDTO.setOutId(itemId);
		comentDTO.setOutType(pictureTextType.name());
		List<PicTextDO> picTextDOList = new ArrayList<PicTextDO>();
		List<PictureTextItemVo> pictureTextItems = pictureTextVO.getPictureTextItems();
		if (CollectionUtils.isNotEmpty(pictureTextItems)) {
			for (PictureTextItemVo pictureTextItemVo : pictureTextItems) {
				PicTextDO picTextDO = new PicTextDO();
				String type = pictureTextItemVo.getType();
				int picTextType = 0;
				if (StringUtils.isNotBlank(type)) {
					if (PictureTextItemType.TEXT.name().equalsIgnoreCase(type)) {
						picTextType = FeatureType.COMENT.getType();
					} else if (PictureTextItemType.IMG.name().equalsIgnoreCase(type)) {
						picTextType = FeatureType.IMAGE.getType();
					}
				}
				picTextDO.setType(picTextType);
				picTextDO.setValue(pictureTextItemVo.getValue());
				picTextDOList.add(picTextDO);
			}
		}
		comentDTO.setPicTextDOList(picTextDOList);
		return comentDTO;
	}
}

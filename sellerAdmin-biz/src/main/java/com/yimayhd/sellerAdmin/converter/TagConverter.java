package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;

/**
 * 标签转换器
 * 
 * @author yebin
 *
 */
public class TagConverter {
	public static List<TagDTO> toTagDTO(List<ComTagDO> comTagDOs) {
		if (CollectionUtils.isEmpty(comTagDOs)) {
			return new ArrayList<TagDTO>(0);
		}
		List<TagDTO> tagDTOs = new ArrayList<TagDTO>();
		for (ComTagDO comTagDO : comTagDOs) {
			tagDTOs.add(toTagDTO(comTagDO));
		}
		return tagDTOs;
	}

	public static TagDTO toTagDTO(ComTagDO comTagDO) {
		if (comTagDO == null) {
			return null;
		}
		return new TagDTO(comTagDO.getId(), comTagDO.getName());
	}
}

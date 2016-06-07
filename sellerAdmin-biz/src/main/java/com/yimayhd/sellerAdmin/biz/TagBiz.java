package com.yimayhd.sellerAdmin.biz;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.user.client.dto.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wzf
 *
 */
public class TagBiz {
	private static Logger LOGGER = LoggerFactory.getLogger(TagBiz.class);
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private CommentRepo commentRepo;

	public List<CityVO> getItemCityVOs(long id, TagType tagType) {
		List<ComTagDO> tags = getComTagDOList(id, tagType);
		if (CollectionUtils.isEmpty(tags)) {
			return new ArrayList<CityVO>(0);
		}
		List<String> codes = new ArrayList<String>();
		for (ComTagDO comTagDO : tags) {
			codes.add(comTagDO.getName());
		}
		Map<String, CityDTO> citiesByCodes = cityRepo.getCitiesByCodes(codes);
		List<CityVO> departs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : tags) {
			if (citiesByCodes.containsKey(comTagDO.getName())) {
				CityDTO cityDTO = citiesByCodes.get(comTagDO.getName());
				departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
			}
		}
		return departs;
	}

	public List<Long> getItemThemeIds(long id, TagType tagType) {
		List<ComTagDO> tags = getComTagDOList(id, tagType);
		if(CollectionUtils.isEmpty(tags)) {
			return null;
		}
		List<Long> themeIds = new ArrayList<>();
		if(!CollectionUtils.isEmpty(tags)) {
			for(ComTagDO comTagDO : tags) {
				if(comTagDO != null) {
					themeIds.add(comTagDO.getId());
				}
			}
		}
		return themeIds;
	}

	public List<ComTagDO> getComTagDOList(long id, TagType tagType) {
		return commentRepo.getTagsByOutId(id, tagType);
	}

}

package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.repo.*;
import com.yimayhd.sellerAdmin.service.TagService;
import com.yimayhd.user.client.dto.CityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagServiceImpl implements TagService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CityRepo cityRepo;


	@Override
	public WebResult<List<TagDTO>> getAllThemes(TagType tagType) {
		try {
			List<ComTagDO> tagsByTagType = commentRepo.getTagsByTagType(tagType);
			List<TagDTO> tagDTOList = toTagDTOs(tagsByTagType);
			return WebResult.success(tagDTOList);
		} catch (Exception e) {
			log.error("LineService.getAllLineThemes error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

	private List<TagDTO> toTagDTOs(List<ComTagDO> tagDOs) {
		if(CollectionUtils.isEmpty(tagDOs)) {
			return null;
		}
		List<TagDTO> tagDTOList = new ArrayList<>();
		for(ComTagDO comTagDO : tagDOs) {
			TagDTO tagDTO = new TagDTO();
			tagDTO.setId(comTagDO.getId());
			tagDTO.setName(comTagDO.getName());
			tagDTOList.add(tagDTO);
		}
		return tagDTOList;
	}

	@Override
	public WebResult<List<CityVO>> getAllDeparts() {
		try {
			List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DEPARTPLACE);
			return WebResult.success(toCityVO(comTagDOs));
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "系统错误 ");
		}
	}

	private List<CityVO> toCityVO(List<ComTagDO> tags) {
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

	@Override
	public WebResult<List<CityVO>> getAllDests() {
		try {
			List<ComTagDO> comTagDOs = commentRepo.getTagsByTagType(TagType.DESTPLACE);
			return WebResult.success(toCityVO(comTagDOs));
		} catch (Exception e) {
			log.error("LineService.getAllLineDeparts error", e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
	}

}

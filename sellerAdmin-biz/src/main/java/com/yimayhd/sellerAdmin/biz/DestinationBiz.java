package com.yimayhd.sellerAdmin.biz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.sellerAdmin.model.line.City;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.repo.DestinationRepo;

/**
 * Created by xushubing on 2016/6/7.
 */
public class DestinationBiz {
    private static Logger LOGGER = LoggerFactory.getLogger(DestinationBiz.class);
    @Autowired
    private DestinationRepo destinationRepo;

    public List<CityVO> toCityVOForDest(List<ComTagDO> destTags) {
        if (CollectionUtils.isEmpty(destTags)) {
            return new ArrayList<CityVO>(0);
        }
        List<Integer> codes = new ArrayList<Integer>();
        for (ComTagDO comTagDO : destTags) {
            int tagCode = 0;
            try {
                tagCode = Integer.parseInt(comTagDO.getName());
            } catch (NumberFormatException e) {
                //数据有问题
                LOGGER.error("comTag NumberFormatException,tagId={},tagName={}", comTagDO.getId(), comTagDO.getName());
            }
            if (tagCode > 0) {
                codes.add(tagCode);
            }
        }
        List<CityVO> dests = new ArrayList<CityVO>();
        if (codes.size() > 0) {
            List<DestinationDO> destinationDOs = destinationRepo.queryDestinationList(codes);
            for (DestinationDO destinationDO : destinationDOs) {
                City city = new City(String.valueOf(destinationDO.getCode()), destinationDO.getName(), destinationDO.getSimpleCode());
                CityVO cityVO = new CityVO(destinationDO.getId(), destinationDO.getName(), city);
                cityVO.setCode(destinationDO.getSimpleCode());
                dests.add(cityVO);
            }
        }
        return dests;
    }
    /**
     * 目的地回显编辑
     * @param destTags
     * @return
     * date:2016年6月28日
     * author:xmn
     */
	public List<CityVO> toCityVODestWithTags(List<ComTagDO> destTags) {
		if (CollectionUtils.isEmpty(destTags)) {
			return new ArrayList<CityVO>(0);
		}
		List<Integer> codes = new ArrayList<Integer>();
		for (ComTagDO comTagDO : destTags) {
			int tagCode = 0;
			try {
				tagCode = Integer.parseInt(comTagDO.getName());
			} catch (NumberFormatException e) {
				// 数据有问题
				LOGGER.error("comTag NumberFormatException,tagId={},tagName={}", comTagDO.getId(), comTagDO.getName());
			}
			if (tagCode > 0) {
				codes.add(tagCode);
			}
		}
		List<CityVO> dests = new ArrayList<CityVO>();
		if (codes.size() <= 0) {
			return new ArrayList<CityVO>(0);
		}
		HashSet<Integer> hashSet = new HashSet<Integer>();
		List<DestinationDO> destinationDOs = destinationRepo.queryDestinationList(codes);
		for (DestinationDO dest1 : destinationDOs) {
			
			for (DestinationDO dest2 : destinationDOs) {
				if (dest1.getCode() == dest2.getParentCode()) {
					City city = new City(String.valueOf(dest2.getCode()), dest2.getName(), dest2.getSimpleCode());
					CityVO cityVO = new CityVO(dest2.getId(), dest2.getName(), city);
					cityVO.setCode(dest2.getSimpleCode());
					cityVO.setPid(dest1.getId());
					cityVO.setPcode(dest2.getParentCode());
					cityVO.setPname(dest1.getName());
					dests.add(cityVO);
					hashSet.add(dest1.getCode());
					hashSet.add(dest2.getCode());
				}
			}
		}
		for (DestinationDO dest1 : destinationDOs) {
			if (!hashSet.contains(dest1.getCode())) {
				City city = new City(String.valueOf(dest1.getCode()), dest1.getName(), dest1.getSimpleCode());
				CityVO cityVO = new CityVO(dest1.getId(), dest1.getName(), city);
				cityVO.setCode(dest1.getSimpleCode());
				dests.add(cityVO);
			}
		}
		
		return dests;
	}
	public List<CityVO> toCityVODestWithCityVos(List<CityVO> dests) {
		if (CollectionUtils.isEmpty(dests)) {
			return new ArrayList<CityVO>(0);
		}
		List<Integer> codes = new ArrayList<Integer>();
		for (CityVO cityVO : dests) {
			int code = 0;
			try {
				code = Integer.parseInt(cityVO.getCode());
			} catch (NumberFormatException e) {
				// 数据有问题
				LOGGER.error("code NumberFormatException,cityVOId={},cityVOName={}", cityVO.getId(), cityVO.getName());
			}
			if (code > 0) {
				codes.add(code);
			}
		}
		List<CityVO> newdests = new ArrayList<CityVO>();
		if (codes.size() <= 0) {
			return new ArrayList<CityVO>(0);
		}
		HashSet<Integer> hashSet = new HashSet<Integer>();
		List<DestinationDO> destinationDOs = destinationRepo.queryDestinationList(codes);
		for (DestinationDO dest1 : destinationDOs) {
			
			for (DestinationDO dest2 : destinationDOs) {
				if (dest1.getCode() == dest2.getParentCode()) {
					City city = new City(String.valueOf(dest2.getCode()), dest2.getName(), dest2.getSimpleCode());
					CityVO cityVO = new CityVO(dest2.getId(), dest2.getName(), city);
					cityVO.setCode(String.valueOf(dest2.getCode()));
					cityVO.setPid(dest1.getId());
					cityVO.setPcode(dest2.getParentCode());
					cityVO.setPname(dest1.getName());
					newdests.add(cityVO);
					hashSet.add(dest1.getCode());
					hashSet.add(dest2.getCode());
				}
			}
		}
		for (DestinationDO dest1 : destinationDOs) {
			if (!hashSet.contains(dest1.getCode())) {
				City city = new City(String.valueOf(dest1.getCode()), dest1.getName(), dest1.getSimpleCode());
				CityVO cityVO = new CityVO(dest1.getId(), dest1.getName(), city);
				cityVO.setCode(dest1.getSimpleCode());
				newdests.add(cityVO);
			}
		}
		
		return newdests;
	}
}

package com.yimayhd.sellerAdmin.biz;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.sellerAdmin.model.line.City;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.repo.DestinationRepo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
}

package com.yimayhd.sellerAdmin.converter;

import org.springframework.beans.BeanUtils;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.sellerAdmin.model.ActActivityEditVO;

/**
 * Created by czf on 2016/2/18.
 */
public class ActPromotionEditDTOConverter {
    public static ActPromotionEditDTO getActPromotionEditDTO(ActActivityEditVO actActivityEditVO) throws Exception {
        ActPromotionEditDTO actPromotionEditDTO = new ActPromotionEditDTO();
        PromotionEditDTO promotionEditDTO = PromotionEditDTOConverter.getPromotionEditDTO(actActivityEditVO);

        actPromotionEditDTO.setAddPromotionDOList(promotionEditDTO.getAddPromotionDOList());
        actPromotionEditDTO.setDelPromotionIdList(promotionEditDTO.getDelPromotionIdList());
        actPromotionEditDTO.setUpdPromotionDOList(promotionEditDTO.getUpdPromotionDOList());

        ActActivityDO actActivityDO = new ActActivityDO();
        BeanUtils.copyProperties(actActivityEditVO.getActActivityVO(),actActivityDO);
        actActivityDO.setTitle(actActivityEditVO.getActActivityVO().getTitle());
        actActivityDO.setSummary(actActivityEditVO.getActActivityVO().getDescription());
        int lotteryType = EntityType.ITEM.getType();
        if(actActivityEditVO.getActActivityVO().getEntityType() == EntityType.SHOP.getType()){
            lotteryType = EntityType.SHOP.getType();
        }
        actActivityDO.setLotteryType(lotteryType);
        actActivityDO.setStartDate(actActivityEditVO.getActActivityVO().getStartDate());
        actActivityDO.setEndDate(actActivityEditVO.getActActivityVO().getEndDate());

        actPromotionEditDTO.setActActivityDO(actActivityDO);

        return actPromotionEditDTO;
    }
}

package com.yimayhd.sellerAdmin.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.domain.PromotionFeature;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import com.yimayhd.sellerAdmin.model.ActActivityEditVO;
import com.yimayhd.sellerAdmin.model.ActActivityVO;
import com.yimayhd.sellerAdmin.model.PromotionVO;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by czf on 2016/2/18.
 */
public class ActActivityEditVOConverter {
    public static ActActivityEditVO getActActivityEditVO(ActPromotionDTO actPromotionDTO){
        if(actPromotionDTO == null){
            return null;
        }
        ActActivityEditVO actActivityEditVO = new ActActivityEditVO();

        ActActivityVO actActivityVO = new ActActivityVO();
        ActActivityDO actActivityDO = actPromotionDTO.getActActivityDO();
        BeanUtils.copyProperties(actActivityDO, actActivityVO);
        actActivityEditVO.setActActivityVO(actActivityVO);

        List<PromotionVO> promotionVOList = new ArrayList<PromotionVO>();
        List<PromotionDO> promotionDOList = actPromotionDTO.getPromotionDOList();
        if(EntityType.SHOP.getType() == actActivityVO.getLotteryType()){
            if(CollectionUtils.isNotEmpty(promotionDOList)){
                //店铺优惠只有一跳记录
                PromotionDO promotionDO = promotionDOList.get(0);
                actActivityVO.setRequirementY(NumUtil.moneyTransformDouble(promotionDO.getRequirement()));
                actActivityVO.setValueY(NumUtil.moneyTransformDouble(promotionDO.getValue()));
                if(StringUtils.isNotBlank(promotionDO.getFeature())) {
                    PromotionFeature promotionFeature = new PromotionFeature(promotionDO.getFeature());
                    List<Long> itemIdList = promotionFeature.getAvailableItemIds();
                    List<Long> skuIdList = promotionFeature.getAvailableSkuIds();
                    if(CollectionUtils.isNotEmpty(itemIdList)) {
                        for (int i = 0; i < itemIdList.size(); i++) {
                            PromotionVO promotionVO = new PromotionVO();
                            BeanUtils.copyProperties(promotionDO, promotionVO);
                            promotionVO.setEntityId(Long.valueOf(String.valueOf(itemIdList.get(i))));
                            promotionVO.setEntityType(EntityType.ITEM.getType());
                            promotionVOList.add(promotionVO);
                        }
                    }
                    if(CollectionUtils.isNotEmpty(skuIdList)) {
                        for (int i = 0; i < skuIdList.size(); i++) {
                            PromotionVO promotionVO = new PromotionVO();
                            BeanUtils.copyProperties(promotionDO, promotionVO);
                            promotionVO.setEntityId(Long.valueOf(String.valueOf(skuIdList.get(i))));
                            promotionVO.setEntityType(EntityType.SKU.getType());
                            promotionVOList.add(promotionVO);
                        }
                    }
                }

            }
        }else{
            if(CollectionUtils.isNotEmpty(promotionDOList)){
                for(PromotionDO promotionDO : promotionDOList){
                    PromotionVO promotionVO = new PromotionVO();
                    BeanUtils.copyProperties(promotionDO, promotionVO);
                    promotionVO.setValueY(NumUtil.moneyTransformDouble(promotionDO.getValue()));
                    //非直降的情况下设置优惠条件和优惠额度
                    if(PromotionType.DIRECT_REDUCE.getType() != promotionDO.getPromotionType()) {
                        actActivityVO.setRequirementY(NumUtil.moneyTransformDouble(promotionDO.getRequirement()));
                        actActivityVO.setValueY(NumUtil.moneyTransformDouble(promotionDO.getValue()));
                    }
//                promotionVO.setItemId();
//                promotionVO.setItemSkuId();
//                promotionVO.setItemTitle();
//                promotionVO.setItemStatus();
                    promotionVOList.add(promotionVO);
                }
            }
        }


        actActivityEditVO.setPromotionVOList(promotionVOList);
        //TODO

        return actActivityEditVO;

    }
}

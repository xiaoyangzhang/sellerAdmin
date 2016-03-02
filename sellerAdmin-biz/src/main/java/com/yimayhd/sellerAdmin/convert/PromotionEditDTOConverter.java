package com.yimayhd.sellerAdmin.convert;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.constant.B2CConstant;
import com.yimayhd.sellerAdmin.model.ActActivityEditVO;
import com.yimayhd.sellerAdmin.model.ActActivityVO;
import com.yimayhd.sellerAdmin.model.PromotionVO;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.domain.PromotionFeature;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionFeatureKey;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2016/2/18.
 */
public class PromotionEditDTOConverter {
    public static PromotionEditDTO getPromotionEditDTO(ActActivityEditVO actActivityEditVO) throws Exception {
        ActActivityVO actActivityVO = actActivityEditVO.getActActivityVO();
        //活动能够时间格式转换
        actActivityVO.setStartDate(DateUtil.convertStringToDateUseringFormats(actActivityVO.getStartDateStr(), DateUtil.DAY_HORU_FORMAT));
        actActivityVO.setEndDate(DateUtil.convertStringToDateUseringFormats(actActivityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT));

        PromotionEditDTO promotionEditDTO = new PromotionEditDTO();
        List<PromotionVO> promotionVOList = null;
        if(StringUtils.isNotBlank(actActivityEditVO.getPromotionVOListStr())) {
            promotionVOList = JSON.parseArray(actActivityEditVO.getPromotionVOListStr(), PromotionVO.class);
        }
        List<PromotionDO> addPromotionDOList = new ArrayList<PromotionDO>();
        List<PromotionDO> updPromotionDOList = new ArrayList<PromotionDO>();
        List<Long> delPromotionIdList = new ArrayList<Long>();

        switch (EntityType.getByType(actActivityVO.getEntityType())){
            case SHOP:
                PromotionDO promotionDOShop = new PromotionDO();
                //设置ID
                if(0 != actActivityVO.getShopPromotionId()){
                    promotionDOShop.setId(actActivityVO.getShopPromotionId());
                }
                promotionDOShop.setTitle(actActivityVO.getTitle());
                promotionDOShop.setDescription(actActivityVO.getDescription());
                promotionDOShop.setEntityType(EntityType.SHOP.getType());
                promotionDOShop.setEntityId(actActivityVO.getEntityId());
                promotionDOShop.setPromotionType(actActivityVO.getPromotionType());
                promotionDOShop.setRequirement(Math.round(actActivityVO.getRequirementY() * 100));
                promotionDOShop.setValue(Math.round(actActivityVO.getValueY() * 100));
                promotionDOShop.setStartTime(actActivityVO.getStartDate());
                promotionDOShop.setEndTime(actActivityVO.getEndDate());
                promotionDOShop.setDomain(B2CConstant.GF_DOMAIN);
                //feature
                if(CollectionUtils.isNotEmpty(promotionVOList)){
                    List<Long> itemList = new ArrayList<Long>();
                    List<Long> skuList = new ArrayList<Long>();
                    for(PromotionVO promotionVO : promotionVOList){
                        if(!promotionVO.isDel()) {
                            if (EntityType.SKU.getType() == promotionVO.getEntityType()) {
                                skuList.add(promotionVO.getEntityId());
                            } else {
                                itemList.add(promotionVO.getEntityId());
                            }
                        }
                        //防止null，引用操作失败
                        if(promotionDOShop.getFeature() == null){
                            promotionDOShop.setFeature("");
                        }
                        PromotionFeature promotionFeature = new PromotionFeature(promotionDOShop.getFeature());
                        if(CollectionUtils.isNotEmpty( itemList)) {
                            promotionFeature.put(PromotionFeatureKey.AVAILABLE_ITEM_IDS, itemList);
                        }
                        if(CollectionUtils.isNotEmpty( skuList)) {
                            promotionFeature.put(PromotionFeatureKey.AVAILABLE_SKU_IDS, skuList);
                        }
                        promotionDOShop.setFeature(promotionFeature.getFeature());
                    }
                }
                if(0 == promotionDOShop.getId()){
                    addPromotionDOList.add(promotionDOShop);
                }else{
                    updPromotionDOList.add(promotionDOShop);
                }
                break;
            default:
                if(CollectionUtils.isNotEmpty(promotionVOList)){
                    //promotionVO.setEntityId(promotionVO.getEntityId());
                    for (PromotionVO promotionVO : promotionVOList){
                        PromotionDO promotionDO = new PromotionDO();
                        BeanUtils.copyProperties(promotionVO,promotionDO);
                        promotionDO.setTitle(actActivityVO.getTitle());
                        promotionDO.setDescription(actActivityVO.getDescription());

                        //promotionVO.setEntityType();


                        promotionDO.setPromotionType(actActivityVO.getPromotionType());
                        switch (PromotionType.getByType(actActivityVO.getPromotionType())) {
                            case SUM_REDUCE:
                                promotionDO.setRequirement(Math.round(actActivityVO.getRequirementY()));
                                promotionDO.setValue(Math.round(promotionVO.getValueY() * 100));
                                break;
                            case DIRECT_REDUCE:
                                promotionDO.setRequirement(0);
                                promotionDO.setValue(Math.round(promotionVO.getValueY() * 100));
                                break;
                            default:
                                break;
                        }
                        promotionDO.setStartTime(actActivityVO.getStartDate());
                        promotionDO.setEndTime(actActivityVO.getEndDate());
                        promotionDO.setDomain(B2CConstant.GF_DOMAIN);
                        //promotionVO.setStatus();
                        //promotionVO.setFeatureV();
                        //promotionVO.setUserTag();
                        if(promotionVO.getId() == 0){
                            addPromotionDOList.add(promotionDO);
                        }else if(promotionVO.isDel()){
                            delPromotionIdList.add(promotionDO.getId());
                        }else{
                            updPromotionDOList.add(promotionDO);
                        }
                    }
                }
                break;
        }


        promotionEditDTO.setAddPromotionDOList(addPromotionDOList);
        promotionEditDTO.setDelPromotionIdList(delPromotionIdList);
        promotionEditDTO.setUpdPromotionDOList(updPromotionDOList);
        return promotionEditDTO;
    }

}

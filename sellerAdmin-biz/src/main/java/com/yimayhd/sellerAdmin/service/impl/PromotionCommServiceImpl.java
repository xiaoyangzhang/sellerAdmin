package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.activitycenter.query.ActPromotionPageQuery;
import com.yimayhd.activitycenter.result.ActPageResult;
import com.yimayhd.activitycenter.result.ActResult;
import com.yimayhd.activitycenter.result.ActResultSupport;
import com.yimayhd.activitycenter.service.ActivityPromotionService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuMixDTO;
import com.yimayhd.ic.client.model.query.BatchRichSkuQuery;
import com.yimayhd.ic.client.model.query.Pair;
import com.yimayhd.ic.client.model.result.item.ItemSkuMixResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.convert.ActActivityEditVOConverter;
import com.yimayhd.sellerAdmin.convert.ActPromotionEditDTOConverter;
import com.yimayhd.sellerAdmin.convert.PromotionEditDTOConverter;
import com.yimayhd.sellerAdmin.model.ActActivityEditVO;
import com.yimayhd.sellerAdmin.model.PromotionVO;
import com.yimayhd.sellerAdmin.service.PromotionCommService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionCommServiceImpl implements PromotionCommService {

    @Autowired
    private ActivityPromotionService activityPromotionServiceRef;

    @Autowired
    private ItemQueryService itemQueryServiceRef;

    private static final Logger log = LoggerFactory.getLogger(PromotionCommServiceImpl.class);

    @Override
    public PageVO<ActActivityDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        PageVO<ActActivityDO> promotionDOPageVO = null;
        //构建查询条件

        if(StringUtils.isNotBlank(actPromotionPageQuery.getEndTime())) {
            Date endTime = DateUtil.formatMaxTimeForDate(actPromotionPageQuery.getEndTime());
            actPromotionPageQuery.setEndTime(DateUtil.date2String(endTime));
        }
        ActPageResult<ActActivityDO> basePageResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQuery);
        if(basePageResult == null){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException("返回结果错误");
        } else if(!basePageResult.isSuccess()){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException(basePageResult.getMsg());
        }
        promotionDOPageVO = new PageVO<ActActivityDO>(actPromotionPageQuery.getPageNo(),actPromotionPageQuery.getOldPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
    }

    public void modify(ActActivityEditVO actActivityEditVO) throws Exception {
        ActResult<ActPromotionDTO> result = activityPromotionServiceRef.getActPromotionById(actActivityEditVO.getActActivityVO().getId());

        if (result != null && result.getT()!= null && result.getT().getActActivityDO() != null && PromotionStatus.NOTBEING.getStatus() != result.getT().getActActivityDO().getStatus()){
            throw new BaseException("只有未开始活动允许修改");
        }

        ActPromotionEditDTO actPromotionEditDTO = ActPromotionEditDTOConverter.getActPromotionEditDTO(actActivityEditVO);

        ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.modify error: " + actPromotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.modify-promotionPublishService.updPromotion error:" + actPromotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
    }

    @Override
    public boolean add(ActActivityEditVO actActivityEditVO) throws Exception {
        PromotionEditDTO promotionEditDTO = PromotionEditDTOConverter.getPromotionEditDTO(actActivityEditVO);
        ActResultSupport baseResult = activityPromotionServiceRef.saveActivityPromotion(promotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.add error: " + promotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.add error:" + promotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
        return true;
    }

    @Override
    public ActActivityEditVO getById(long id) throws Exception {

        ActResult<ActPromotionDTO> actResult = activityPromotionServiceRef.getActPromotionById(id);
        if(actResult == null){
            log.error("activityPromotionServiceRef.getActPromotionById return null and param : " + id);
            throw new BaseException("返回结果为空");
        } else if(!actResult.isSuccess()){
            log.error("activityPromotionServiceRef.getActPromotionById error:" + actResult + "and param :" + id);
            throw new BaseException(actResult.getMsg());
        }
        ActActivityEditVO actActivityEditVO = ActActivityEditVOConverter.getActActivityEditVO(actResult.getT());
        //组合item和sku信息
        combineItem(actActivityEditVO);
        return actActivityEditVO;
    }
    public void combineItem(ActActivityEditVO actActivityEditVO){
        if(actActivityEditVO != null && CollectionUtils.isNotEmpty(actActivityEditVO.getPromotionVOList())){
            BatchRichSkuQuery batchRichSkuQuery = new BatchRichSkuQuery();
            List<Pair<Long,Long>> itemIdSkuIdPairList = new ArrayList<Pair<Long, Long>>();
            for(PromotionVO promotionVO : actActivityEditVO.getPromotionVOList()){
                if(EntityType.ITEM.getType() == promotionVO.getEntityType()){
                    itemIdSkuIdPairList.add(new Pair<Long, Long>(promotionVO.getEntityId(),null));
                }else if(EntityType.SKU.getType() == promotionVO.getEntityType()){
                    itemIdSkuIdPairList.add(new Pair<Long, Long>(null,promotionVO.getEntityId()));
                }
            }
            batchRichSkuQuery.setItemIdSkuIdPairs(itemIdSkuIdPairList);
            ItemSkuMixResult itemSkuMixResult = itemQueryServiceRef.batchQueryItemSku(batchRichSkuQuery);
            if(itemSkuMixResult == null){
                log.error("itemQueryServiceRef.batchQueryItemSku return null and param : " + JSON.toJSONString(batchRichSkuQuery));
                throw new BaseException("返回结果为空");
            } else if(!itemSkuMixResult.isSuccess()){
                log.error("itemQueryServiceRef.batchQueryItemSku error:" + itemSkuMixResult + "and param :" + JSON.toJSONString(batchRichSkuQuery));
                throw new BaseException(itemSkuMixResult.getResultMsg());
            }
            List<ItemSkuMixDTO> itemSkuMixDTOList = itemSkuMixResult.getItemSkuMixDTOList();
            if(CollectionUtils.isNotEmpty(itemSkuMixDTOList)){
                //转map
                Map<String,ItemSkuMixDTO> map = new HashMap<String, ItemSkuMixDTO>();
                for(ItemSkuMixDTO itemSkuMixDTO : itemSkuMixDTOList){
                    String key = "";
                    if (itemSkuMixDTO.getItemSkuDO() == null){
                        key = String.valueOf(itemSkuMixDTO.getItemDO().getId()) + "_";
                    }else{
                        key = "_" + String.valueOf(itemSkuMixDTO.getItemSkuDO().getId());
                    }
                    map.put(key,itemSkuMixDTO);
                }

                //组合值
                for(PromotionVO promotionVO : actActivityEditVO.getPromotionVOList()){
                    String key = "";
                    if(EntityType.ITEM.getType() == promotionVO.getEntityType()){
                        key = String.valueOf(promotionVO.getEntityId()) + "_";
                    }else if(EntityType.SKU.getType() == promotionVO.getEntityType()){
                        key =  "_" + String.valueOf(promotionVO.getEntityId());
                    }

                    ItemSkuMixDTO itemSkuMixDTO = map.get(key);
                    if(itemSkuMixDTO != null){
                        ItemDO itemDO = itemSkuMixDTO.getItemDO();
                        ItemSkuDO itemSkuDO = itemSkuMixDTO.getItemSkuDO();
                        promotionVO.setItemId(itemDO.getId());
                        promotionVO.setItemTitle(itemDO.getTitle());
                        promotionVO.setPriceY(NumUtil.moneyTransformDouble(itemDO.getPrice()));
                        promotionVO.setItemStatus(itemDO.getStatus());
                        if(itemSkuDO != null){
                            promotionVO.setItemSkuId(itemSkuDO.getId());
                            if (StringUtils.isNotEmpty(itemSkuDO.getTitle())){
                                promotionVO.setItemTitle(itemDO.getTitle() + "_" +itemSkuDO.getTitle());
                            }
                            promotionVO.setPriceY(NumUtil.moneyTransformDouble(itemSkuDO.getPrice()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean publish(long id) throws Exception {
        return activityPromotionServiceRef.openActPromotion(id).isSuccess();
    }

    @Override
    public boolean close(long id) throws Exception {
        boolean res = activityPromotionServiceRef.closeActPromotion(id).isSuccess();
        return res;
    }

    @Override
    public boolean checkActivityName(String name, int type) {
        ActActivityDO actActivityDO = new ActActivityDO();
        actActivityDO.setTitle(name);
        actActivityDO.setType(type);
        return activityPromotionServiceRef.checkDuplicationActivityName(actActivityDO);
    }
}

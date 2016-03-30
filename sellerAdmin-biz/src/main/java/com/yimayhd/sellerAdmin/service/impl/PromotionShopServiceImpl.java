package com.yimayhd.sellerAdmin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.PromotionVO;
import com.yimayhd.sellerAdmin.model.query.PromotionListQuery;
import com.yimayhd.sellerAdmin.service.PromotionShopService;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionShopServiceImpl implements PromotionShopService {
//    @Autowired
//    private PromotionPublishService promotionPublishServiceRef;

    private static final Logger log = LoggerFactory.getLogger(PromotionShopServiceImpl.class);

    @Override
    public PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception {
        PageVO<PromotionDO> promotionDOPageVO = null;
//        PromotionPageQuery promotionPageQuery = new PromotionPageQuery();
//        //构建查询条件
//        promotionPageQuery.setPageNo(promotionListQuery.getPageNumber());
//        promotionPageQuery.setPageSize(promotionListQuery.getPageSize());
//        promotionPageQuery.setNeedCount(true);
//        promotionPageQuery.setEntityType((long) EntityType.SHOP.getType());
//        if(StringUtils.isNotBlank(promotionListQuery.getBeginDate())) {
//            Date beginTime = DateUtil.formatMinTimeForDate(promotionListQuery.getBeginDate());
//            promotionPageQuery.setStartTime(beginTime);
//        }
//        if(StringUtils.isNotBlank(promotionListQuery.getEndDate())) {
//            Date endTime = DateUtil.formatMaxTimeForDate(promotionListQuery.getEndDate());
//            promotionPageQuery.setEndTime(endTime);
//        }
//        promotionPageQuery.setStatus(promotionListQuery.getStatus());
//        promotionPageQuery.setPromotionType(promotionListQuery.getPromotionType());
//        if(StringUtils.isNotBlank(promotionListQuery.getTitle())) {
//            promotionPageQuery.setTitle(promotionListQuery.getTitle());
//        }
//        BasePageResult<PromotionDO> basePageResult = promotionQueryServiceRef.queryPromotions(promotionPageQuery);
//        if(basePageResult == null){
//            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(promotionPageQuery) + "and promotionListQuery: " + promotionListQuery);
//            throw new BaseException("返回结果错误");
//        } else if(!basePageResult.isSuccess()){
//            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(promotionPageQuery) + "and promotionListQuery: " + promotionListQuery);
//            throw new BaseException(basePageResult.getResultMsg());
//        }
//        promotionDOPageVO = new PageVO<PromotionDO>(promotionListQuery.getPageNumber(),promotionListQuery.getPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
    }

    @Override
    public void modify(PromotionVO entity) throws Exception {
        //BaseResult<PromotionDO> baseResult = promotionPublishServiceRef.updPromotion();
    }

    @Override
    public boolean add(PromotionVO entity) throws Exception {
//        BaseResult<PromotionDO> baseResult = promotionPublishServiceRef.addPromotion(entity);
//        return baseResult.isSuccess();
        return true;
    }

    @Override
    public PromotionVO getById(long id) throws Exception {
//        BaseResult<PromotionDO> baseResult = promotionPublishServiceRef
        return null;
    }
}

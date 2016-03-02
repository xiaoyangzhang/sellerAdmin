package com.yimayhd.commission.repo;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.marketing.client.model.domain.ItemRebateDO;
import com.yimayhd.marketing.client.model.enums.DomainType;
import com.yimayhd.marketing.client.model.param.ItemRebateRateUpdateDTO;
import com.yimayhd.marketing.client.model.query.ItemRebateQuery;
import com.yimayhd.marketing.client.model.result.SpmPageResult;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.marketing.client.service.ItemRebateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;


/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/13
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class ItemRebateRepo {

    private static final Logger logger = LoggerFactory.getLogger(ItemRebateRepo.class);

    @Autowired
    private ItemRebateService itemRebateService;

    /**
     * 查询商品的设置比例
     * @param itemRebateQuery
     * @return
     */
    public SpmPageResult<ItemRebateDO> queryItemRebateResult(ItemRebateQuery itemRebateQuery){
    	
    	SpmPageResult<ItemRebateDO> spmPageResult = null;
        try{
            itemRebateQuery.setDomainId(DomainType.DOMAIN_MYTHIC_FLOW.getDomainId());
            itemRebateQuery.setNeedPage(true);
            spmPageResult =  itemRebateService.pageQuery(itemRebateQuery);
            
        }catch (Exception e){
            logger.error("ItemRebateRepo.queryItemRebateResult error!params:{},ex:{}",
            		JSONObject.toJSONString(itemRebateQuery),e);
        }
        return spmPageResult;
    }

    /**
     * 更新商品佣金比例
     * @param itemRebateRateUpdateDTO
     * @return
     */
    public SpmResult<ItemRebateDO> updateItemRebate(ItemRebateRateUpdateDTO itemRebateRateUpdateDTO){

        SpmResult<ItemRebateDO> spmResult = null;
        try{
            spmResult = itemRebateService.update(itemRebateRateUpdateDTO);
            if(spmResult.isSuccess() && spmResult.getT()!=null){
                return spmResult;
            }
        }catch (Exception e){
            logger.error("ItemRebateRepo.updateItemRebate exception!params:{}",JSONObject.toJSONString(itemRebateRateUpdateDTO));
        }
        return spmResult;
    }

}

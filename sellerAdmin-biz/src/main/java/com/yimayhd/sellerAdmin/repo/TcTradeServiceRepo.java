package com.yimayhd.sellerAdmin.repo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.tradecenter.client.model.param.order.OrderChangePriceDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.OrderChangePriceResult;
import com.yimayhd.tradecenter.client.model.result.order.metaq.OrderInfoTO;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangdi on 16/8/24.
 */
public class TcTradeServiceRepo {

    @Autowired
    private TcTradeService tcTradeServiceRef;

    @Autowired
    private TcQueryService tcQueryServiceRef;

    private Logger log = LoggerFactory.getLogger(TcTradeServiceRepo.class);

    public OrderChangePriceResult orderChangePrice(OrderChangePriceDTO dto){
        log.info("call tcTradeServiceRef dto={}", JSON.toJSONString(dto));
        OrderChangePriceResult result = null;
        if(dto==null){
            log.error("param is null");
            return result;
        }
        try{
            result = tcTradeServiceRef.orderChangePrice(dto);
            if(result==null||!result.isSuccess()){
                log.error("call orderChangePrice error, result={}",JSON.toJSONString(result));
                return result;
            }
        }catch (Exception e){
            log.error("orderChangePrice exception ",e);
        }

        return result;
    }

    public List<OrderInfoTO> getOrderInfo(List<Long> bizIds, OrderQueryOption orderQueryOption) {
        if(CollectionUtils.isEmpty(bizIds)||null==orderQueryOption) {
            log.error("getOrderInfo param is null");
            return null;
        }
        List<OrderInfoTO> result = new ArrayList<>();
        try {
            result = tcQueryServiceRef.batchQueryBizOrder(bizIds, orderQueryOption);
            return result;
        } catch (Exception e) {
            log.error("getOrderInfo has exception={}", e);
            return null;
        }
    }
}

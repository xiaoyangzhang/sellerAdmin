package com.yimayhd.sellerAdmin.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.tradecenter.client.model.param.order.OrderChangePriceDTO;
import com.yimayhd.tradecenter.client.model.result.order.OrderChangePriceResult;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/8/24.
 */
public class TcTradeServiceRepo {

    @Autowired
    private TcTradeService tcTradeServiceRef;

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
}

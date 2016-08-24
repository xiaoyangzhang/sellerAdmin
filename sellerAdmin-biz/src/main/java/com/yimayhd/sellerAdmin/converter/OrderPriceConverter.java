package com.yimayhd.sellerAdmin.converter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.sellerAdmin.model.order.OrderPriceJsonTemplate;
import com.yimayhd.sellerAdmin.model.order.OrderPriceQuery;
import com.yimayhd.sellerAdmin.model.order.OrderPrizeDTO;
import com.yimayhd.sellerAdmin.model.order.OrderSonModel;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import com.yimayhd.tradecenter.client.model.param.order.OrderChangePriceDTO;
import com.yimayhd.tradecenter.client.model.result.order.OrderChangePriceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdi on 16/8/24.
 */
public class OrderPriceConverter {
    private static final Logger logger = LoggerFactory.getLogger(OrderPriceConverter.class);

    private OrderChangePriceDTO orderChangePriceDTO;
    private OrderPriceJsonTemplate orderPriceJsonTemplate;


    /**
     * 产生改价订单dto
     * @param template
     * @return
     */
    public OrderChangePriceDTO getOrderChangePriceDTO(OrderPriceJsonTemplate template){
        OrderChangePriceDTO dto = new OrderChangePriceDTO();
        if(orderPriceJsonTemplate==null){
            return null;
        }
        dto.setBizOrderId(template.getMainOrderId());
        //dto.setOrderChangePriceMap();
        OrderSonModel[] orderArr = template.getOrderSonModel();
        Map<Long, Long> orderchangeMap = new HashMap<Long, Long>();
        /**** 子订单 添加**/
        for(int i=0;i<orderArr.length;i++){
            OrderSonModel order = orderArr[i];
            orderchangeMap.put(order.getSonOrderId(),order.getSonOrderPrize().multiply(new BigDecimal(100)).longValue());
        }
        dto.setOrderChangePriceMap(orderchangeMap);
        return dto;
    }

    /**
     *  前端json 转模板
     * @param orderPriceQuery
     * @return
     */
    public OrderPriceJsonTemplate getOrderPriceJsonTemplate(OrderPriceQuery orderPriceQuery){
        if(orderPriceQuery==null|| StringUtils.isBlank(orderPriceQuery.getOrderJson())){
            return null;
        }
        OrderPriceJsonTemplate tmplate  = (OrderPriceJsonTemplate)CommonJsonUtil.jsonToObject(orderPriceQuery.getOrderJson(),OrderPriceJsonTemplate.class);
        return tmplate;

    }

    /**
     * 返回值转json
     * @param result
     * @return
     */
    public OrderPrizeDTO getOrderPrizeDTO(OrderChangePriceResult result){
        if(result == null){
            return null;
        }
        OrderPrizeDTO dto = new OrderPrizeDTO();
        OrderPriceJsonTemplate template = new OrderPriceJsonTemplate();
        template.setMainOrderId(result.getBizOrderId());
        template.setMainOrderPrize(new BigDecimal(result.getAfterAdjustActualTotalFee()));
        Map<Long,Long> changeMap = result.getResultOrderChangePriceMap();
        OrderSonModel[] orderArr = new OrderSonModel[changeMap.size()];
        int i=0;
        for(Map.Entry<Long,Long> entry :changeMap.entrySet()){
            OrderSonModel orderSonModel = new OrderSonModel();
            orderSonModel.setSonOrderId(entry.getKey());
            orderSonModel.setSonOrderPrize(new BigDecimal(entry.getValue()==null?0:entry.getValue()));
            orderArr[i++] = orderSonModel;
        }
        template.setOrderSonModel(orderArr);
        dto.setOrderJson(CommonJsonUtil.objectToJson(template,OrderPriceJsonTemplate.class));
        return dto;

    }

    public OrderChangePriceDTO getOrderChangePriceDTO() {
        return orderChangePriceDTO;
    }

    public void setOrderChangePriceDTO(OrderChangePriceDTO orderChangePriceDTO) {
        this.orderChangePriceDTO = orderChangePriceDTO;
    }

    public OrderPriceJsonTemplate getOrderPriceJsonTemplate() {
        return orderPriceJsonTemplate;
    }

    public void setOrderPriceJsonTemplate(OrderPriceJsonTemplate orderPriceJsonTemplate) {
        this.orderPriceJsonTemplate = orderPriceJsonTemplate;
    }
}

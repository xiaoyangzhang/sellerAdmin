package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDO;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogConverter {
    private static final Logger logger = LoggerFactory.getLogger(OrderOperationLogConverter.class);
    private BeanCopier doCopier = BeanCopier.create(OrderOperationLogDO.class, OrderOperationLogDTO.class, false);
    private BeanCopier dtoCopier = BeanCopier.create(OrderOperationLogDTO.class, OrderOperationLogDO.class, false);

    private OrderOperationLogQuery orderOperationLogQuery;

    private OrderOperationLogDTO orderOperationLogDTO;

    public OrderOperationLogConverter(){}

    public  OrderOperationLogConverter (OrderOperationLogQuery query){
        this.orderOperationLogQuery=query;
    }

    public OrderOperationLogConverter(OrderOperationLogDTO orderOperationLogDTO){
        this.orderOperationLogDTO = orderOperationLogDTO;
    }

    public WebResult checkQueryParam(){
        if(orderOperationLogQuery==null){
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        return WebResult.success(null);
    }

    /**
     * 添加记录 参数验证
     * @return
     */
    public WebResult chekAddParam(){
        if(orderOperationLogDTO==null){
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if(orderOperationLogDTO.getBizNo()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR,"订单编号不能为空");
        }
        if(orderOperationLogDTO.getOperationId()==0){
            return WebResult.failure(WebReturnCode.PARAM_ERROR,"操作人ID不能为空");
        }
        return WebResult.success(null);
    }

    /**
     * do转dto
     * @param _do
     * @return
     */
    public  OrderOperationLogDTO doToDto(OrderOperationLogDO _do){
        if(_do==null){
            return null;
        }
        OrderOperationLogDTO dto = new OrderOperationLogDTO();
        try{
            doCopier.copy(_do,dto,null);
        }catch (Exception e){
            logger.error("doCopier bean 转换异常",e);
            return null;
        }

        return dto;
    }

    /**
     * dto转do
     * @param dto
     * @return
     */
    public  OrderOperationLogDO  doToDto(OrderOperationLogDTO dto){
        if(dto==null){
            return null;
        }
        OrderOperationLogDO _do = new OrderOperationLogDO();
        try{
            dtoCopier.copy(dto,_do,null);
        }catch (Exception e){
            logger.error("dtoCopier bean 转换异常",e);
            return null;
        }

        return _do;
    }


    public OrderOperationLogQuery getOrderOperationLogQuery() {
        return orderOperationLogQuery;
    }

    public void setOrderOperationLogQuery(OrderOperationLogQuery orderOperationLogQuery) {
        this.orderOperationLogQuery = orderOperationLogQuery;
    }

}

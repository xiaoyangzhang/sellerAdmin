package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.service.OrderOperationLogService;
import com.yimayhd.sellerAdmin.converter.OrderOperationLogConverter;
import com.yimayhd.sellerAdmin.manager.OrderOperationLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogServiceImpl implements OrderOperationLogService {
    private static final Logger logger = LoggerFactory.getLogger(OrderOperationLogServiceImpl.class);
    @Autowired
    private OrderOperationLogManager orderOperationLogManager;

    /**
     * 查询列表
     * @param query
     * @return
     */
    @Override
    public SellerResult<OrderOperationLogResult> queryOrderOperationLogDOList(OrderOperationLogQuery query) {
        logger.info("queryOrderOperationLogDOList start ,param={}", JSON.toJSONString(query));
        OrderOperationLogConverter converter = new OrderOperationLogConverter(query);
        OrderOperationLogResult result = new  OrderOperationLogResult();
        SellerResult chekResult = converter.checkQueryParam();
        if(!chekResult.isSuccess()){
            logger.error("参数错误,query is null");
            return chekResult;
        }
        try{
            int count =0;
            if(query.isNeedCount()){
                count= orderOperationLogManager.queryOrderOperationLogDOListCount(query);
            }
            List<OrderOperationLogDTO> logList = orderOperationLogManager.queryOrderOperationLogDOList(query);
            result.setTotalCount(count);
            result.setOrderOperationLogDTOList(logList);
        }catch (Exception e){
            logger.error("查询数据库失败",e);
            return SellerResult.failure(WebReturnCode.DB_ERROR.getErrorMsg());
        }
        return SellerResult.success(result);
    }

    /**
     * 查询总条数
     * @param query
     * @return
     */
    @Override
    public SellerResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query) {
        Integer count = 0;
        try{
            count = orderOperationLogManager.queryOrderOperationLogDOListCount(query);
        }catch (Exception e){
            logger.error("查询数据库失败",e);
            return SellerResult.failure(WebReturnCode.DB_ERROR.getErrorMsg());
        }
        return SellerResult.success(count);
    }

    /**
     * 添加记录
     * @param orderOperationLogDTO
     * @return
     */
    @Override
    public SellerResult<Boolean> insertOrderOperationLogDO(OrderOperationLogDTO orderOperationLogDTO) {
        OrderOperationLogConverter converter = new OrderOperationLogConverter(orderOperationLogDTO);
        SellerResult chekResult = converter.chekAddParam();
        if(!chekResult.isSuccess()){
            logger.error("参数错误 ,msg={}",chekResult.getMsg());
            return chekResult;
        }
        try{
            orderOperationLogManager.insertOrderOperationLogDO(converter.doToDto(orderOperationLogDTO));
        }catch (Exception e){
            logger.error("插入数据库异常",e);
            return SellerResult.failure(WebReturnCode.DB_ERROR.getErrorMsg());
        }
        return SellerResult.success(true);
    }
}

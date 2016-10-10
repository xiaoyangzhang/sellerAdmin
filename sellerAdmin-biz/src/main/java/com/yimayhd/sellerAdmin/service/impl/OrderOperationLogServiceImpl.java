package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.converter.OrderOperationLogConverter;
import com.yimayhd.sellerAdmin.manager.OrderOperationLogManager;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.service.OrderOperationLogService;
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
    public WebResult<OrderOperationLogResult> queryOrderOperationLogDOList(OrderOperationLogQuery query) {
        OrderOperationLogConverter converter = new OrderOperationLogConverter(query);
        OrderOperationLogResult result = new  OrderOperationLogResult();
        WebResult chekResult = converter.checkQueryParam();
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
            return WebResult.failure(WebReturnCode.DB_ERROR);
        }
        return WebResult.success(result);
    }

    /**
     * 查询总条数
     * @param query
     * @return
     */
    @Override
    public WebResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query) {
        Integer count = 0;
        try{
            count = orderOperationLogManager.queryOrderOperationLogDOListCount(query);
        }catch (Exception e){
            logger.error("查询数据库失败",e);
            return WebResult.failure(WebReturnCode.DB_ERROR);
        }
        return WebResult.success(count);
    }

    /**
     * 添加记录
     * @param orderOperationLogDTO
     * @return
     */
    @Override
    public WebResult<Boolean> insertOrderOperationLogDO(OrderOperationLogDTO orderOperationLogDTO) {
        OrderOperationLogConverter converter = new OrderOperationLogConverter(orderOperationLogDTO);
        WebResult chekResult = converter.chekAddParam();
        if(!chekResult.isSuccess()){
            logger.error("参数错误 ,msg={}",chekResult.getResultMsg());
            return chekResult;
        }
        try{
            orderOperationLogManager.insertOrderOperationLogDO(converter.doToDto(orderOperationLogDTO));
        }catch (Exception e){
            logger.error("插入数据库异常",e);
            return WebResult.failure(WebReturnCode.DB_ERROR);
        }
        return WebResult.success(true);
    }
}

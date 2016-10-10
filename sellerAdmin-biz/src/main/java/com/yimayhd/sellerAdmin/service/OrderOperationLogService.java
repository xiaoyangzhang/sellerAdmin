package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogResult;


/**
 * Created by wangdi on 16/10/10.
 */
public interface OrderOperationLogService {
    /**
     * 查询列表
     * @param query
     * @return
     */
    public WebResult<OrderOperationLogResult> queryOrderOperationLogDOList(OrderOperationLogQuery query);

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public WebResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query);

    /**
     * 插入
     * @param orderOperationLogDTO
     * @return
     */
    public  WebResult<Boolean> insertOrderOperationLogDO(OrderOperationLogDTO orderOperationLogDTO);

}

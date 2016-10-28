package com.yimayhd.sellerAdmin.client.service;


import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.client.result.SellerResult;

/**
 * Created by wangdi on 16/10/10.
 */
public interface OrderOperationLogService {
    /**
     * 查询列表
     * @param query
     * @return
     */
    public SellerResult<OrderOperationLogResult> queryOrderOperationLogDOList(OrderOperationLogQuery query);

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public SellerResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query);

    /**
     * 插入
     * @param orderOperationLogDTO
     * @return
     */
    public  SellerResult<Boolean> insertOrderOperationLogDO(OrderOperationLogDTO orderOperationLogDTO);

}

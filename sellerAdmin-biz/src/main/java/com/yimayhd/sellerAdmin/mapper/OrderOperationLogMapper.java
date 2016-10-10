package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.mapper.data.OrderOperationLogDO;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogQuery;

import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public interface OrderOperationLogMapper {
    /**
     * 查询列表
     * @param query
     * @return
     */
    public List<OrderOperationLogDO> queryOrderOperationLogDOList(OrderOperationLogQuery query);

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public Integer queryOrderOperationLogDOListCount(OrderOperationLogQuery query);

    /**
     * 插入
     * @param orderOperationLogDO
     * @return
     */
    public  int insertOrderOperationLogDO(OrderOperationLogDO orderOperationLogDO);
}

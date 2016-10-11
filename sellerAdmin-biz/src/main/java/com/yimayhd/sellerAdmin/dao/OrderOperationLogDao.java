package com.yimayhd.sellerAdmin.dao;

import com.yimayhd.sellerAdmin.mapper.OrderOperationLogMapper;
import  com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDO;;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogDao{
    private static final Logger logger = LoggerFactory.getLogger(OrderOperationLogDao.class);
    @Autowired
    private OrderOperationLogMapper mapper;

    /**
     * 查询列表
     * @param query
     * @return
     */
    public List<OrderOperationLogDO> queryOrderOperationLogDOList(OrderOperationLogQuery query){
        if (query==null){
            logger.error("查询参数错误,query is null");
            return null;
        }

        return mapper.queryOrderOperationLogDOList(query);
    }

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public Integer queryOrderOperationLogDOListCount(OrderOperationLogQuery query){
        if (query==null){
            logger.error("查询参数错误,query is null");
            return null;
        }
        Object obj =  mapper.queryOrderOperationLogDOListCount(query);
        if(obj==null){
            return 0;
        }
        return (Integer)obj;
    }

    /**
     * 插入
     * @param orderOperationLogDO
     * @return
     */
    public  int insertOrderOperationLogDO(OrderOperationLogDO orderOperationLogDO){
        if(orderOperationLogDO==null){
            logger.error("参数错误,orderOperationLogDO is null");
            return 0;
        }
        orderOperationLogDO.setGmtCreated(new Date());
        return mapper.insertOrderOperationLogDO(orderOperationLogDO);
    }
}

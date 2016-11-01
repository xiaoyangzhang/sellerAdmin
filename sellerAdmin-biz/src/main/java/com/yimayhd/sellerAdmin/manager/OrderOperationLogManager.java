package com.yimayhd.sellerAdmin.manager;

import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.converter.OrderOperationLogConverter;
import com.yimayhd.sellerAdmin.dao.OrderOperationLogDao;
import com.yimayhd.sellerAdmin.model.orderLog.OrderOperationLogDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderOperationLogManager {

    private static final Logger logger = LoggerFactory.getLogger(OrderOperationLogManager.class);
    @Autowired
    private OrderOperationLogDao orderOperationLogDao;


    /**
     * 查询列表
     * @param query
     * @return
     */
    public List<OrderOperationLogDTO> queryOrderOperationLogDOList(OrderOperationLogQuery query){
        if (query==null){
            logger.error("查询参数错误,query is null");
            return null;
        }

        List<OrderOperationLogDO> doList =   orderOperationLogDao.queryOrderOperationLogDOList(query);
        if(CollectionUtils.isEmpty(doList)){
            return null;
        }
        List<OrderOperationLogDTO> list = new ArrayList<OrderOperationLogDTO>(doList.size());
        OrderOperationLogConverter converter = new OrderOperationLogConverter();
        for(OrderOperationLogDO _do :doList){
            list.add(converter.doToDto(_do));
        }
        return list;
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
        return orderOperationLogDao.queryOrderOperationLogDOListCount(query);
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
        return orderOperationLogDao.insertOrderOperationLogDO(orderOperationLogDO);
    }

}

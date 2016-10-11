package com.yimayhd.sellerAdmin.order;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.BaseTest;
import com.yimayhd.sellerAdmin.ServiceTest;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.service.OrderOperationLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/10/11.
 */
public class OrderOperationLogServiceTest extends ServiceTest {

    @Autowired
    private OrderOperationLogService orderOperationLogService;

    @Test
    public void queryOrderOperationLogDOList(){
        OrderOperationLogQuery query = new OrderOperationLogQuery();
        SellerResult<OrderOperationLogResult> result =  orderOperationLogService.queryOrderOperationLogDOList(query);
        System.out.println("---start---");
        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void insertOrderOperationLogDO(){
        OrderOperationLogDTO dto =  new OrderOperationLogDTO();
        dto.setBizNo(110);
        dto.setContent("ddddd");
        dto.setDesc("adfafa");
        dto.setOperationId(88080);
        dto.setStatus(1);
        SellerResult<Boolean> result =  orderOperationLogService.insertOrderOperationLogDO(dto);
        System.out.println("---start---");
        System.out.println(JSON.toJSONString(result));

    }


}

package com.yimayhd.sellerAdmin.converter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yimayhd.sellerAdmin.model.enums.OrderActionStatus;
import com.yimayhd.sellerAdmin.model.enums.OrderShowStatus;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.SubOrder;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.SkuInfo;
import com.yimayhd.tradecenter.client.model.domain.order.SkuPropertyInfo;
import com.yimayhd.tradecenter.client.model.enums.LogisticsStatus;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.enums.PayStatus;
import com.yimayhd.tradecenter.client.model.enums.RefundStatus;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;

/**
 * Created by zhaozhaonan on 2015/12/18.
 *
 */
public class OrderConverter {
    private static final Logger LOG = LoggerFactory.getLogger(OrderConverter.class);

    public static OrderQueryDTO orderListQueryToOrderQueryDTO(OrderListQuery orderListQuery,long userId){
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        if (orderListQuery.getDomain() == null || orderListQuery.getDomain()==0){
            orderQueryDTO.setDomain(1000);
        }else{
            orderQueryDTO.setDomain(orderListQuery.getDomain());
        }
        orderQueryDTO.setPageNo(orderListQuery.getPageNo());
        orderQueryDTO.setPageSize(orderListQuery.getPageSize());
        //订单类型
        orderQueryDTO.setOrderBizTypes(orderListQuery.getOrderTypes());

        //订单编号
        if (StringUtils.isNotEmpty(orderListQuery.getOrderNO()) ){
            if (NumberUtils.isDigits(orderListQuery.getOrderNO())){
                List<Long> bizOrderIds = new ArrayList<Long>();
                bizOrderIds.add(NumberUtils.toLong(orderListQuery.getOrderNO()));
                orderQueryDTO.setBizOrderIds(bizOrderIds);
            }else{
                return null;
            }
        }
        //下单开始日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate()));
            } catch (ParseException e) {
                LOG.error("orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }else{
            //TODO 测试需要注释。测试完打开代码（默认取今天的所有数据）
//            String date = DateUtil.date2StringByDay(new Date());
//            try{
//                orderQueryDTO.setStartDate(DateUtil.convertStringToDate(date));
//            }catch (ParseException e){
//                LOG.error("orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate())); Exception:" + e);
//                e.printStackTrace();
//            }
        }
        //下单结束日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate()));
            } catch (ParseException e) {
                LOG.error("orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
        //订单状态
        String orderState = orderListQuery.getOrderStat();
        if (StringUtils.isNotEmpty(orderState)){
            if (orderState.equals(PayStatus.NOT_PAY.toString())){
                int [] payStatus = {PayStatus.NOT_PAY.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
            }else if (orderState.equals(LogisticsStatus.NO_LG_ORDER.toString())){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.NO_LG_ORDER.getStatus(),LogisticsStatus.UNCONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(LogisticsStatus.CONSIGNED.toString())){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.CONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.SUCCESS.toString())){
                int [] payStatus = {PayStatus.SUCCESS.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.DELIVERED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.NOT_PAY_CLOSE.toString())){
                int [] payStatus = {PayStatus.NOT_PAY_CLOSE.getStatus(),PayStatus.REFUNDED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
            }
        }
        //买家userId
        if (userId >0){
            orderQueryDTO.setBuyerId(userId);
        }
        //商品名称
        if (StringUtils.isNotEmpty(orderListQuery.getItemName())){
            orderQueryDTO.setItemName(orderListQuery.getItemName());
            orderQueryDTO.setIsMain(null);
            orderQueryDTO.setIsDetail(MainDetailStatus.YES.getType());
        }else{
            orderQueryDTO.setIsMain(MainDetailStatus.YES.getType());
            orderQueryDTO.setNeedDetailOrders(true);
        }
        return orderQueryDTO;
    }

    public static MainOrder mainOrderStatusConverter(MainOrder mainOrder,BizOrderDO bizOrderDO) {
        int payStatus = bizOrderDO.getPayStatus();
        int logisticsStatus = bizOrderDO.getLogisticsStatus();
        int refundStatus = bizOrderDO.getRefundStatus();

        if (payStatus == PayStatus.NOT_PAY.getStatus()){
            mainOrder.setOrderShowState(OrderShowStatus.NOTING.getStatus());//待付款
            if(bizOrderDO.getBizType() == OrderBizType.NORMAL.getBizType()){
                mainOrder.setOrderActionStates(OrderActionStatus.UPDATE_ADDRESS_CANCEL.getStatus());
            }else{
                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
            }
        }else if (RefundStatus.REFUND_SUCCESS.getStatus() == refundStatus || RefundStatus.REFUNDING.getStatus() == refundStatus){
            mainOrder.setOrderShowState(OrderShowStatus.REFUNDED.getStatus());//已退款
        }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.NO_LG_ORDER.getStatus() == logisticsStatus && RefundStatus.NOT_REFUND.getStatus() == refundStatus){
            mainOrder.setOrderShowState(OrderShowStatus.PAID.getStatus());//待发货|已付款
            mainOrder.setOrderActionStates(OrderActionStatus.AFFIRM_REFUND.getStatus());
        }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.CONSIGNED.getStatus() == logisticsStatus){
            mainOrder.setOrderShowState(OrderShowStatus.SHIPPED.getStatus());//待收货|已发货
            if(bizOrderDO.getBizType() == OrderBizType.NORMAL.getBizType()){
                mainOrder.setOrderActionStates(OrderActionStatus.OVERTIME.getStatus());
            }else{
                mainOrder.setOrderActionStates(OrderActionStatus.FINISH_REFUND.getStatus());
            }
        }else if (PayStatus.SUCCESS.getStatus() == payStatus && RefundStatus.NOT_REFUND.getStatus() == refundStatus){
            mainOrder.setOrderShowState(OrderShowStatus.FINISH.getStatus());//已完成
            mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
        }else if (PayStatus.REFUNDED.getStatus() == payStatus || PayStatus.NOT_PAY_CLOSE.getStatus() == payStatus ){
            mainOrder.setOrderShowState(OrderShowStatus.TRADE_CLOSE.getStatus());//关闭
        }
        return mainOrder;
    }



    public static MainOrder orderVOConverter(BizOrderDO bizOrderDO) {
        if(bizOrderDO!=null){
            if (BizOrderUtil.hasDetailOrder(bizOrderDO)) {
                List<SubOrder> subOrderList = new ArrayList<SubOrder>();
                if (!CollectionUtils.isEmpty(bizOrderDO.getDetailOrderList())){
                    for (BizOrderDO detailOrder : bizOrderDO.getDetailOrderList()) {
                        SubOrder subOrder =  new SubOrder();
                        subOrder.setBizOrderDO(detailOrder);
                        if (bizOrderDO.getBizType() == OrderBizType.LINE.getBizType() ||bizOrderDO.getBizType() == OrderBizType.FLIGHT_HOTEL.getBizType() ||bizOrderDO.getBizType() == OrderBizType.SPOTS_HOTEL.getBizType()){
                            long departDate = BizOrderUtil.getLineDepartDate(bizOrderDO);
                            subOrder.setExecuteTime(departDate);//出发时间
                            //sku
                            SkuInfo skuInfo = detailOrder.getSkuInfo();
                            if (skuInfo!=null){
                                List<SkuPropertyInfo> skuPropertyInfoList = skuInfo.getSkuPropertyInfoList();
                                for (SkuPropertyInfo skuPropertyInfo : skuPropertyInfoList) {
                                    //{"skuPropertyInfoList":[{"pId":1,"pTxt":"出行日期","pType":2,"vId":5,"vTxt":"1448625207921"},{"pId":2,"pTxt":"人员","pType":1,"vId":6,"vTxt":"儿童"}]}
                                    //为了获取购买类型（无枚举）
                                    if (skuPropertyInfo.getPId() == 2){
                                        subOrder.setvTxt(skuPropertyInfo.getVTxt());
                                    }
                                }
                            }
                        }else if (bizOrderDO.getBizType() == OrderBizType.SPOTS.getBizType()){
                            long spotStartDate = BizOrderUtil.getSpotStartDate(bizOrderDO);
                            subOrder.setExecuteTime(spotStartDate);//入院时间
                        }else if (bizOrderDO.getBizType() == OrderBizType.HOTEL.getBizType()){
                            long hotelStartDate = BizOrderUtil.getHotelStartDate(bizOrderDO);
                            long hotelEndDate = BizOrderUtil.getHotelEndDate(bizOrderDO);
                            subOrder.setStartTime(hotelStartDate);//入住日期
                            subOrder.setEndTime(hotelEndDate);//离店日期
                        }
                        subOrderList.add(subOrder);
                    }
                    return new MainOrder(bizOrderDO,subOrderList);
                }else{
                    subOrderList.add(new SubOrder(bizOrderDO));
                    return new MainOrder(bizOrderDO,subOrderList);
                }
            } else {
                List<SubOrder> subOrderList = new ArrayList<SubOrder>();
                subOrderList.add(new SubOrder(bizOrderDO));
                for (SubOrder subOrder : subOrderList) {
                    if (subOrder.getBizOrderDO().getBizType() == OrderBizType.SPOTS.getBizType()){
                        long spotStartDate = BizOrderUtil.getSpotStartDate(bizOrderDO);
                        subOrder.setExecuteTime(spotStartDate);//入院时间
                    }else if (subOrder.getBizOrderDO().getBizType() == OrderBizType.HOTEL.getBizType()){
                        long hotelStartDate = BizOrderUtil.getHotelStartDate(bizOrderDO);
                        long hotelEndDate = BizOrderUtil.getHotelEndDate(bizOrderDO);
                        subOrder.setStartTime(hotelStartDate);//入住日期
                        subOrder.setEndTime(hotelEndDate);//离店日期
                    }
                }
                return new MainOrder(bizOrderDO,subOrderList);
            }
        }
        return null;
    }


}

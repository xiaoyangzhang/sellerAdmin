package com.yimayhd.sellerAdmin.converter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.commentcenter.client.dto.RatePageListDTO;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.ic.client.model.enums.PropertyType;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.enums.ExpressCompanyEnum;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.JXComRateResult;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.SubOrder;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.MatcherUtil;
import com.yimayhd.tradecenter.client.model.domain.order.SkuInfo;
import com.yimayhd.tradecenter.client.model.enums.BizOrderStatus;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.enums.RateStatus;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.PackLgDetailResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.tradecenter.client.util.SkuUtils;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;

/**
 * Created by zhaozhaonan on 2015/12/18.
 *
 */
public class OrderConverter {
    private static final Logger LOG = LoggerFactory.getLogger(OrderConverter.class);

    public static OrderQueryDTO orderListQueryToOrderQueryDTO(OrderListQuery orderListQuery,long userId){
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        if (orderListQuery.getDomain() != null && orderListQuery.getDomain()!=0){
            orderQueryDTO.setDomain(orderListQuery.getDomain());
        }
        //是否需要买家备注
        orderQueryDTO.setNeedExtFeature(true);
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
        if (StringUtils.isNotEmpty(orderListQuery.getEndDate())){
            try {
                orderQueryDTO.setEndDate(DateUtil.add23Hours(DateUtil.convertStringToDate(orderListQuery.getEndDate())));
            } catch (ParseException e) {
                LOG.error("orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
        //订单状态
        String orderState = orderListQuery.getOrderStat();
        if (StringUtils.isNotEmpty(orderState)){
            if (orderState.equals(BizOrderStatus.WAITING_PAY.toString())){
                int payStatus = BizOrderStatus.WAITING_PAY.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }else if (orderState.equals(BizOrderStatus.WAITING_DELIVERY.toString())){
                int payStatus = BizOrderStatus.WAITING_DELIVERY.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }else if (orderState.equals(BizOrderStatus.SHIPPING.toString())){
                int payStatus = BizOrderStatus.SHIPPING.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }else if (orderState.equals(BizOrderStatus.CONFIRMED_CLOSE.toString())){
                int payStatus = BizOrderStatus.CONFIRMED_CLOSE.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }else if (orderState.equals(BizOrderStatus.FINISH.toString())){
                int payStatus = BizOrderStatus.FINISH.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }else if (orderState.equals(BizOrderStatus.CANCEL.toString())){
                int payStatus = BizOrderStatus.CANCEL.getCode();
                orderQueryDTO.setBizOrderStatus(payStatus);
            }
        }
        
        
//        if (StringUtils.isNotEmpty(orderState)){
//            if (orderState.equals(PayStatus.NOT_PAY.toString())){
//                int [] payStatus = {PayStatus.NOT_PAY.getStatus()};
//                orderQueryDTO.setPayStatuses(payStatus);
//            }else if (orderState.equals(LogisticsStatus.NO_LG_ORDER.toString())){
//                int [] payStatus = {PayStatus.PAID.getStatus()};
//                int [] logisticsStatuses = {LogisticsStatus.NO_LG_ORDER.getStatus(),LogisticsStatus.UNCONSIGNED.getStatus()};
//                orderQueryDTO.setPayStatuses(payStatus);
//                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
//            }else if (orderState.equals(LogisticsStatus.CONSIGNED.toString())){
//                int [] payStatus = {PayStatus.PAID.getStatus()};
//                int [] logisticsStatuses = {LogisticsStatus.CONSIGNED.getStatus()};
//                orderQueryDTO.setPayStatuses(payStatus);
//                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
//            }else if (orderState.equals(PayStatus.SUCCESS.toString())){
//                int [] payStatus = {PayStatus.SUCCESS.getStatus()};
//                int [] logisticsStatuses = {LogisticsStatus.DELIVERED.getStatus()};
//                orderQueryDTO.setPayStatuses(payStatus);
//                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
//            }else if (orderState.equals(PayStatus.NOT_PAY_CLOSE.toString())){
//                int [] payStatus = {PayStatus.NOT_PAY_CLOSE.getStatus(),PayStatus.REFUNDED.getStatus()};
//                orderQueryDTO.setPayStatuses(payStatus);
//            }
//        }
        //买家userId
        if (userId >0){
            orderQueryDTO.setBuyerId(userId);
        }
        //卖家sellerId
        if (orderListQuery.getSellerId() >0){
            orderQueryDTO.setSellerId(orderListQuery.getSellerId());
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
        //评价状态
        if (StringUtils.isNotEmpty(orderListQuery.getAssesmentType())){
        	if(orderListQuery.getAssesmentType().equals("1")){
        		int [] rateStatuses = {RateStatus.NOT_RATE.getStatus()};
            	orderQueryDTO.setRateStatuses(rateStatuses);
            	//如果没有选择订单状态，则给订单一个已完成状态，方便查询已完成的未评价订单
            	if(orderQueryDTO.getBizOrderStatus()==0){
            		int payStatus = BizOrderStatus.FINISH.getCode();
                    orderQueryDTO.setBizOrderStatus(payStatus);
            	}
        	}else if(orderListQuery.getAssesmentType().equals("2")){
        		int [] rateStatuses = {RateStatus.RATED.getStatus()};
            	orderQueryDTO.setRateStatuses(rateStatuses);
        	}
        }
        return orderQueryDTO;
    }

    public static MainOrder mainOrderPackageConverter(MainOrder mainOrder,List<PackLgDetailResult> packLgDetailList){
    	//用来设置物流栏是否展示
    	if( !CollectionUtils.isEmpty(packLgDetailList) ){
    	 	mainOrder.setHasExpress(BaseStatus.AVAILABLE.name());
    	}else {
    		mainOrder.setHasExpress(BaseStatus.DELETED.name() );
    	}
    	mainOrder.getTcMainOrder().setPackLgDetailList(packLgDetailList);
    	return mainOrder;
    }
    
    public static MainOrder mainOrderStatusConverter(MainOrder mainOrder,TcMainOrder tcMainOrder) {
        TcBizOrder tcBizOrder = tcMainOrder.getBizOrder();

        //订单状态
        mainOrder.setOrderStatus(tcBizOrder.getOrderStatus());
        
        for (BizOrderStatus bizOrderStatus : BizOrderStatus.values()) {
        	if(tcBizOrder.getOrderStatus() == bizOrderStatus.getCode()){
        		mainOrder.setOrderStatusStr(bizOrderStatus.name());
        	}
		}
        //订单类型
        mainOrder.setOrderType(tcBizOrder.getOrderType());
        long mainOrderTotalFee = BizOrderUtil.getMainOrderTotalFee(tcBizOrder.getBizOrderDO());//主订单金额
        mainOrder.setMainOrderTotalFee(mainOrderTotalFee);
        long mainOrderTotalChangeFee  = BizOrderUtil.getActualAmountPaid(tcBizOrder.getBizOrderDO());//订单实际支付金额
        mainOrder.setMainOrderTotalChangeFee(mainOrderTotalChangeFee);
        //LOG.info("实收order price:"+mainOrderTotalChangeFee);

        for (OrderBizType orderBizType : OrderBizType.values()) {
        	if(tcBizOrder.getOrderType() == orderBizType.getBizType()){
        		mainOrder.setOrderTypeStr(orderBizType.name());
        	}
		}
//        if (payStatus == PayStatus.NOT_PAY.getStatus()){
//            mainOrder.setOrderShowState(OrderShowStatus.NOTING.getStatus());//待付款
//            if(tcBizOrder.getOrderType() == OrderBizType.NORMAL.getBizType()){
//                mainOrder.setOrderActionStates(OrderActionStatus.UPDATE_ADDRESS_CANCEL.getStatus());
//            }else{
//                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
//            }
//        }else if (RefundStatus.REFUND_SUCCESS.getStatus() == refundStatus || RefundStatus.REFUNDING.getStatus() == refundStatus){
//            mainOrder.setOrderShowState(OrderShowStatus.REFUNDED.getStatus());//已退款
//        }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.NO_LG_ORDER.getStatus() == logisticsStatus && RefundStatus.NOT_REFUND.getStatus() == refundStatus){
//            mainOrder.setOrderShowState(OrderShowStatus.PAID.getStatus());//待发货|已付款
//            mainOrder.setOrderActionStates(OrderActionStatus.AFFIRM_REFUND.getStatus());
//        }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.CONSIGNED.getStatus() == logisticsStatus){
//            mainOrder.setOrderShowState(OrderShowStatus.SHIPPED.getStatus());//待收货|已发货
//            if(tcBizOrder.getOrderType() == OrderBizType.NORMAL.getBizType()){
//                mainOrder.setOrderActionStates(OrderActionStatus.OVERTIME.getStatus());
//            }else{
//                mainOrder.setOrderActionStates(OrderActionStatus.FINISH_REFUND.getStatus());
//            }
//        }else if (PayStatus.SUCCESS.getStatus() == payStatus && RefundStatus.NOT_REFUND.getStatus() == refundStatus){
//            mainOrder.setOrderShowState(OrderShowStatus.FINISH.getStatus());//已完成
//            mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
//        }else if (PayStatus.REFUNDED.getStatus() == payStatus || PayStatus.NOT_PAY_CLOSE.getStatus() == payStatus ){
//            mainOrder.setOrderShowState(OrderShowStatus.TRADE_CLOSE.getStatus());//关闭
//        }

        String expressCompany  = tcMainOrder.getLogisticsOrderDO()==null?"":tcMainOrder.getLogisticsOrderDO().getExpressCompany();
        if(StringUtils.isNotBlank(expressCompany)){
           boolean isOldInfo = MatcherUtil.isRegExpStr(Constant.EXPRESS_COMPANY_REG,expressCompany);
            if(isOldInfo){
                // 旧信息,EMS,中文名称
                LOG.info(" is old company "+expressCompany);
                mainOrder.setExpressCompanyName(tcMainOrder.getLogisticsOrderDO().getExpressCompany());
            }else{
                LOG.info(" is new company "+expressCompany);
                mainOrder.setExpressCompanyName(ExpressCompanyEnum.valueOfName(expressCompany).getDesc());//设置物流公司名称
            }


        }


        return mainOrder;
    }



    public static MainOrder orderVOConverter(TcMainOrder tcMainOrder) {
        if(tcMainOrder!=null){
            List<TcDetailOrder> tcDetailOrderList = tcMainOrder.getDetailOrders();
            List<SubOrder> subOrderList = new ArrayList<>();
            for (TcDetailOrder tcDetailOrder : tcDetailOrderList) {
                SubOrder subOrder =  new SubOrder();
                subOrder.setTcDetailOrder(tcDetailOrder);
                TcBizOrder tcBizOrder = tcDetailOrder.getBizOrder();
                //sku
                SkuInfo skuInfo = tcBizOrder.getSkuInfo();
                if (tcBizOrder.getOrderType() == OrderBizType.TOUR_LINE.getBizType() ||tcBizOrder.getOrderType() == OrderBizType.FREE_LINE.getBizType()
                		||tcBizOrder.getOrderType() == OrderBizType.TOUR_LINE_ABOARD.getBizType() ||tcBizOrder.getOrderType() == OrderBizType.FREE_LINE_ABOARD.getBizType()){
                    //获取出发时间
                    String startDate = SkuUtils.getPropertyValue(skuInfo, PropertyType.START_DATE.getType());
                    if (StringUtils.isNotEmpty(startDate)){
                        subOrder.setExecuteTime(Long.parseLong(startDate));
                    }
                    //人员类型
                    String personType = SkuUtils.getPropertyValue(skuInfo, PropertyType.PERSON_TYPE.getType());
                    if (StringUtils.isNotEmpty(personType)){
                        subOrder.setvTxt(personType);
                    }
                }else if (tcBizOrder.getOrderType() == OrderBizType.CITY_ACTIVITY.getBizType()){
                    String activityTime = SkuUtils.getPropertyValue(skuInfo, PropertyType.ACTIVITY_TIME.getType());
                    if (StringUtils.isNotEmpty(activityTime)){
                        subOrder.setActivityTime(activityTime);
                    }
                }else if (tcBizOrder.getOrderType() == OrderBizType.NORMAL.getBizType()){

                }
                //订单实付总额
                long total = BizOrderUtil.getSubOrderActualFee(tcDetailOrder.getBizOrder().getBizOrderDO());//子订单实付金额
                //获取子订单实付金额
                if(tcDetailOrder.getBizOrder().getBuyAmount() > 0){
                	long act = total/tcDetailOrder.getBizOrder().getBuyAmount();
                	subOrder.setItemPrice_(act);
                }
                //订单状态str
				for (BizOrderStatus bizOrderStatus : BizOrderStatus.values()) {
		        	if(tcDetailOrder.getBizOrder().getOrderStatus() == bizOrderStatus.getCode()){
		        		subOrder.setOrderStatusStr(bizOrderStatus.name());
		        		break;
		        	}
				}
		        //订单类型str
		        for (OrderBizType orderBizType : OrderBizType.values()) {
		        	if(tcDetailOrder.getBizOrder().getOrderType() == orderBizType.getBizType()){
		        		subOrder.setOrderTypeStr(orderBizType.name());
		        	}
				}
                BizOrderUtil.getSubOrderTotalFee(tcDetailOrder.getBizOrder().getBizOrderDO());
                
                long subOrderTotalFee =BizOrderUtil.getSubOrderTotalFee(tcDetailOrder.getBizOrder().getBizOrderDO());//子订单原价
                subOrder.setSubOrderTotalFee(subOrderTotalFee);
                long itemOriginalPrice =  BizOrderUtil.getItemOriginalPrice(tcDetailOrder.getBizOrder().getBizOrderDO());//划价金额
                subOrder.setItemOriginalPrice(itemOriginalPrice);
                subOrderList.add(subOrder);
            }
            return new MainOrder(tcMainOrder,subOrderList);
        }
        return null;
    }

    public static RatePageListDTO assessmentListQueryToRatePageListDTO(AssessmentListQuery assessmentListQuery ){
    	RatePageListDTO ratePageListDTO = new RatePageListDTO();
    	ratePageListDTO.setDomainId(assessmentListQuery.getDomain());
		ratePageListDTO.setSellerId(assessmentListQuery.getSellerId());
		ratePageListDTO.setPageNo(assessmentListQuery.getPageNo());
		ratePageListDTO.setPageSize(assessmentListQuery.getPageSize());
		//订单编号
        if (StringUtils.isNotEmpty(assessmentListQuery.getOrderNO()) ){
            if (NumberUtils.isDigits(assessmentListQuery.getOrderNO())){
            	ratePageListDTO.setOrderId(Long.parseLong(assessmentListQuery.getOrderNO()));
            }else{
            	 return null;
            }
        }
        if (StringUtils.isNotEmpty(assessmentListQuery.getItemNo()) ){
            if (NumberUtils.isDigits(assessmentListQuery.getItemNo())){
            	ratePageListDTO.setItemId(Long.parseLong(assessmentListQuery.getItemNo()));
            }else{
            	 return null;
            }
        }
		if(StringUtils.isNotEmpty(assessmentListQuery.getBeginDate())){
			  try {
				  ratePageListDTO.setStartTime(DateUtil.convertStringToDate(assessmentListQuery.getBeginDate()).getTime());
	            } catch (ParseException e) {
	                LOG.error("setStartTimeError",e);
	            }
		}
		if(StringUtils.isNotEmpty(assessmentListQuery.getEndDate())){
			  try {
				  Date endDateFormat = DateUtil.formatMaxTimeForDate(DateUtil.convertStringToDate(assessmentListQuery.getEndDate()));
				  ratePageListDTO.setEndTime(endDateFormat.getTime());
	            } catch (ParseException e) {
	            	LOG.error("setEndTimeError",e);
	                e.printStackTrace();
	            }
		}
		return ratePageListDTO;
    }
    
    public static void getIdByUserName(BaseResult<List<UserDO>> users,RatePageListDTO ratePageListDTO){
		if (null != users.getValue()) {
			List<Long> userList = new ArrayList<Long>();
			for (int i = 0; i < users.getValue().size(); i++) {
				UserDO userDO = users.getValue().get(i);
				userList.add(i, userDO.getId());
			}
			//如果根据name查询无此用户，那么放一个0的id，为了防止查询条件里的用户idList为[]，导致和没输入name时一样
			if(users.getValue().size()==0){
				userList.add(0, 0L);
			}
			ratePageListDTO.setUserId(userList);
		}
    }
    
    public static JXComRateResult changeComRateToJX(ComRateResult comRateResult){
    	JXComRateResult jxresult = new JXComRateResult();
        jxresult.setId(comRateResult.getId());
    	jxresult.setOrderId(comRateResult.getOrderId());
    	jxresult.setItemId(comRateResult.getItemId());
    	jxresult.setScore(comRateResult.getScore());
    	double score = (double)comRateResult.getScore()/100;
    	jxresult.setScore_(score);
    	jxresult.setDimensionInfoResultList(comRateResult.getDimensionInfoResultList());
    	jxresult.setPicUrls(comRateResult.getPicUrls());
    	jxresult.setGmtCreated(comRateResult.getGmtCreated());
    	jxresult.setUserId(comRateResult.getUserId());
        jxresult.setBackContent(comRateResult.getBackContent());
        jxresult.setBackTime(comRateResult.getBackTime());
    	return jxresult;
    }
}

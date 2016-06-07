package com.yimayhd.sellerAdmin.service.impl;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.converter.OrderConverter;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.sellerAdmin.service.OrderService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.CloseOrderReason;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.enums.TcPayChannel;
import com.yimayhd.tradecenter.client.model.param.order.*;
import com.yimayhd.tradecenter.client.model.param.refund.RefundTradeDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.*;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author zhaozhaonan
 *
 */
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private TcQueryService tcQueryServiceRef;
	@Autowired
	private TcTradeService tcTradeServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private TcBizQueryService tcBizQueryServiceRef;

	@Override
	public PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception {
		if (orderListQuery.getItemType() == 0){
			int [] orderBizTypes = new int[]{OrderBizType.TOUR_LINE.getBizType(),OrderBizType.FREE_LINE.getBizType(),
					OrderBizType.CITY_ACTIVITY.getBizType(),OrderBizType.NORMAL.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 3100){
			int [] orderBizTypes = new int[]{OrderBizType.TOUR_LINE.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 3200){
			int [] orderBizTypes = new int[]{OrderBizType.FREE_LINE.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 3300){
			int [] orderBizTypes = new int[]{OrderBizType.CITY_ACTIVITY.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 200){
			int [] orderBizTypes = new int[]{OrderBizType.NORMAL.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		}

		List<MainOrder> mainOrderList = new ArrayList<>();
		long userId = 0;
		if ( StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())){
			BaseResult<UserDO> result = userServiceRef.getUserByMobile(orderListQuery.getBuyerPhone());
			if (result.getValue()!=null){
				userId = result.getValue().getId();
			} else {
				return new PageVO<MainOrder>(orderListQuery.getPageNo(),orderListQuery.getPageSize(), 0,mainOrderList);
			}
		}
		if (userId == 0 && StringUtils.isNotEmpty(orderListQuery.getBuyerName())){
			BaseResult<List<UserDO>> result= userServiceRef.getUserByNickname(orderListQuery.getBuyerName());
			if (result.getValue() != null && !CollectionUtils.isEmpty(result.getValue())){
				List<UserDO> userDOList = result.getValue();
				UserDO userDO = userDOList.get(0);
				if (userDO!=null){
					userId = userDO.getId();
				}
			}
			if (userId == 0) {
				return new PageVO<MainOrder>(orderListQuery.getPageNo(),orderListQuery.getPageSize(), 0,mainOrderList);
			}
		}

		OrderQueryDTO orderQueryDTO = OrderConverter.orderListQueryToOrderQueryDTO(orderListQuery,userId);
		if (orderQueryDTO!=null){
			BatchBizQueryResult batchBizQueryResult = tcBizQueryServiceRef.queryOrderForSeller(orderQueryDTO);
			List<TcMainOrder> tcMainOrderList = batchBizQueryResult.getBizOrderDOList();
			if (!CollectionUtils.isEmpty(tcMainOrderList)){
				for (TcMainOrder tcMainOrder : tcMainOrderList) {
					MainOrder mo = OrderConverter.orderVOConverter(tcMainOrder);
					mo = OrderConverter.mainOrderStatusConverter(mo,tcMainOrder);
					UserDO user = userServiceRef.getUserDOById(tcMainOrder.getBizOrder().getBuyerId());
					mo.setUser(user);
					mainOrderList.add(mo);
				}
			}
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNo(),orderListQuery.getPageSize(),(int)batchBizQueryResult.getTotalCount(),mainOrderList);
			return orderPageVO;
		}else{
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNo(),orderListQuery.getPageSize(), 0,mainOrderList);
			return orderPageVO;
		}

	}

	@Override
	public OrderDetails getOrderById(long id) throws Exception {
		OrderQueryOption orderQueryOption = new OrderQueryOption();
		orderQueryOption.setAll();
		try {
			TcSingleQueryResult tcSingleQueryResult = tcBizQueryServiceRef.querySingle(id, orderQueryOption);
			if (tcSingleQueryResult.isSuccess()){
				OrderDetails orderDetails = new OrderDetails();
				MainOrder mainOrder = OrderConverter.orderVOConverter(tcSingleQueryResult.getTcMainOrder());
				mainOrder = OrderConverter.mainOrderStatusConverter(mainOrder,tcSingleQueryResult.getTcMainOrder());
				orderDetails.setMainOrder(mainOrder);
				TcMainOrder tcMainOrder = mainOrder.getTcMainOrder();
				TcBizOrder tcBizOrder = tcMainOrder.getBizOrder();

				if (tcBizOrder!=null){
					long buyerId = tcBizOrder.getBuyerId();
					UserDO buyer = userServiceRef.getUserDOById(buyerId);
					orderDetails.setSellerId(tcBizOrder.getSellerId());
					orderDetails.setBuyerName(buyer.getName());
					orderDetails.setBuyerNiceName(buyer.getNickname());
					orderDetails.setBuyerPhoneNum(buyer.getMobileNo());
					//付款方式
					if (tcMainOrder.getPayChannel()!=0){
						orderDetails.setPayChannel(TcPayChannel.getPayChannel(tcMainOrder.getPayChannel()).getDesc());
					}
				}
				//orderDetails.setTotalFee(tcMainOrder.getTotalFee());
				orderDetails.setActualTotalFee(tcMainOrder.getTotalFee());
				//联系人邮箱
				//String email = BizOrderUtil.getLineContactEmail(tcBizOrder);
				//orderDetails.setEmail(email);
				//参加人
				//orderDetails.setTourists(tcMainOrder.getTouristList());
				//联系人
				//orderDetails.setContacts(tcMainOrder.getContactInfo());
				//买家备忘录
				orderDetails.setBuyerMemo(tcMainOrder.getOtherInfo());
				//关闭原因
				orderDetails.setCloseReason(tcMainOrder.getCloseReason());
				//确认、发货时间
				orderDetails.setConsignTime(DateUtil.longToDate(tcMainOrder.getDeliveryTime()));
				return orderDetails;
			}
		}catch (Exception e){
			log.error("public OrderDetails getOrderById(long id);" + e);
			return null;
		}
		return null;
	}


	//完成
	@Override
	public boolean buyerConfirmGoods(long id) {
		BuyerConfirmGoodsDTO buyerConfirmGoodsDTO = new BuyerConfirmGoodsDTO();
		buyerConfirmGoodsDTO.setBizOrderId(id);
		try {
			BuyerConfirmGoodsResult buyerConfirmGoodsResult = tcTradeServiceRef.buyerConfirmGoods(buyerConfirmGoodsDTO);
			return buyerConfirmGoodsResult.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.buyerConfirmGoods(buyerConfirmGoodsDTO);" + e);
			return false;
		}
	}


	//发货--确认/
	@Override
	public boolean sellerSendGoods(long id) {
		SellerSendGoodsDTO sellerSendGoodsDTO = new SellerSendGoodsDTO();
		sellerSendGoodsDTO.setBizOrderId(id);
		try {
			SellerSendGoodsResult sellerSendGoodsResult = tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);
			return sellerSendGoodsResult.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);" + e);
			return false;
		}

	}

	//退款
	@Override
	public boolean refundOrder(long id) {
		RefundTradeDTO refundTradeDTO = new RefundTradeDTO();
		refundTradeDTO.setBizOrderId(id);
		try {
			ResultSupport resultSupport = tcTradeServiceRef.refundOrder(refundTradeDTO);
			return resultSupport.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.refundOrder(refundTradeDTO);" + e);
			return false;
		}
	}


	@Override
	public boolean closeOrder(long id) {
		try {
			CloseOrderDTO closeOrderDTO = new CloseOrderDTO();
			closeOrderDTO.setBizOrderId(id);
			closeOrderDTO.setCloseOrderReasonId(CloseOrderReason.CLOSE_BY_ADMIN.getType());
			ResultSupport resultSupport = tcTradeServiceRef.closeOrder(closeOrderDTO);
			return resultSupport.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.closeOrder(id);" + e);
			return false;
		}
	}
}

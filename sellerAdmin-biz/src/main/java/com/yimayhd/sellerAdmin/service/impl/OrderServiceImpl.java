package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.RatePageListDTO;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.commentcenter.client.service.ComRateService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.lgcenter.client.domain.ExpressCodeRelationDO;
import com.yimayhd.lgcenter.client.service.LgService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.OrderConverter;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.JXComRateResult;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.sellerAdmin.service.OrderService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.VoucherInfo;
import com.yimayhd.tradecenter.client.model.enums.BizOrderExtFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.CloseOrderReason;
import com.yimayhd.tradecenter.client.model.enums.FinishOrderSource;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.enums.TcPayChannel;
import com.yimayhd.tradecenter.client.model.param.order.BuyerConfirmGoodsDTO;
import com.yimayhd.tradecenter.client.model.param.order.CloseOrderDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.param.order.SellerConfirmCheckInDTO;
import com.yimayhd.tradecenter.client.model.param.order.SellerSendGoodsDTO;
import com.yimayhd.tradecenter.client.model.param.order.UpdateBizOrderExtFeatureDTO;
import com.yimayhd.tradecenter.client.model.param.refund.RefundTradeDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.BuyerConfirmGoodsResult;
import com.yimayhd.tradecenter.client.model.result.order.SellerConfirmCheckInResult;
import com.yimayhd.tradecenter.client.model.result.order.SellerSendGoodsResult;
import com.yimayhd.tradecenter.client.model.result.order.TcSingleQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;

/**
 * 订单管理实现
 * 
 * @author zhaozhaonan
 * 
 */
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory
			.getLogger(OrderServiceImpl.class);
	@Autowired
	private TcQueryService tcQueryServiceRef;
	@Autowired
	private TcTradeService tcTradeServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private TcBizQueryService tcBizQueryServiceRef;
	@Autowired
	private ComRateService comRateServiceRef;
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private LgService lgService;
	
	@Override
	public PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery)
			throws Exception {
		if (orderListQuery.getItemType() == 0) {
			int[] orderBizTypes = new int[] {
					OrderBizType.TOUR_LINE.getBizType(),
					OrderBizType.FREE_LINE.getBizType(),
					OrderBizType.CITY_ACTIVITY.getBizType(),
					OrderBizType.NORMAL.getBizType() ,
					OrderBizType.HOTEL.getBizType() ,
					OrderBizType.SPOTS.getBizType() ,
					OrderBizType.HOTEL_OFFLINE.getBizType() ,
					OrderBizType.TOUR_LINE_ABOARD.getBizType() ,
					OrderBizType.FREE_LINE_ABOARD.getBizType()};
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 3100) {
			int[] orderBizTypes = new int[] { OrderBizType.TOUR_LINE
					.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 3200) {
			int[] orderBizTypes = new int[] { OrderBizType.FREE_LINE
					.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 3300) {
			int[] orderBizTypes = new int[] { OrderBizType.CITY_ACTIVITY
					.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 200) {
			int[] orderBizTypes = new int[] { OrderBizType.NORMAL.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 1100) {
			int[] orderBizTypes = new int[] { OrderBizType.HOTEL.getBizType(),
					OrderBizType.HOTEL_OFFLINE.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		} else if (orderListQuery.getItemType() == 1200) {
			int[] orderBizTypes = new int[] { OrderBizType.SPOTS.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 3500) {
			int[] orderBizTypes = new int[] { OrderBizType.TOUR_LINE_ABOARD.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		}else if (orderListQuery.getItemType() == 3600) {
			int[] orderBizTypes = new int[] { OrderBizType.FREE_LINE_ABOARD.getBizType() };
			orderListQuery.setOrderTypes(orderBizTypes);
		}

		List<MainOrder> mainOrderList = new ArrayList<>();
		long userId = 0;
		if (StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())) {
			BaseResult<UserDO> result = userServiceRef
					.getUserByMobile(orderListQuery.getBuyerPhone());
			if (result.getValue() != null) {
				userId = result.getValue().getId();
			} else {
				return new PageVO<MainOrder>(orderListQuery.getPageNo(),
						orderListQuery.getPageSize(), 0, mainOrderList);
			}
		}
		if (userId == 0
				&& StringUtils.isNotEmpty(orderListQuery.getBuyerName())) {
			BaseResult<List<UserDO>> result = userServiceRef
					.getUserByNickname(orderListQuery.getBuyerName());
			if (result.getValue() != null
					&& !CollectionUtils.isEmpty(result.getValue())) {
				List<UserDO> userDOList = result.getValue();
				UserDO userDO = userDOList.get(0);
				if (userDO != null) {
					userId = userDO.getId();
				}
			}
			if (userId == 0) {
				return new PageVO<MainOrder>(orderListQuery.getPageNo(),
						orderListQuery.getPageSize(), 0, mainOrderList);
			}
		}

		OrderQueryDTO orderQueryDTO = OrderConverter
				.orderListQueryToOrderQueryDTO(orderListQuery, userId);
		if (orderQueryDTO != null) {
			BatchBizQueryResult batchBizQueryResult = tcBizQueryServiceRef
					.queryOrderForSeller(orderQueryDTO);
			List<TcMainOrder> tcMainOrderList = batchBizQueryResult
					.getBizOrderDOList();
			if (!CollectionUtils.isEmpty(tcMainOrderList)) {
				for (TcMainOrder tcMainOrder : tcMainOrderList) {
					MainOrder mo = OrderConverter.orderVOConverter(tcMainOrder);
					mo = OrderConverter.mainOrderStatusConverter(mo,
							tcMainOrder);
					UserDO user = userServiceRef.getUserDOById(tcMainOrder
							.getBizOrder().getBuyerId());
					mo.setUser(user);
					
					//获取优惠劵优惠金额
					VoucherInfo voucherInfo = BizOrderUtil.getVoucherInfo(tcMainOrder.getBizOrder().getBizOrderDO());
					if(null!=voucherInfo){
						mo.setRequirement(voucherInfo.getRequirement());
						mo.setValue(voucherInfo.getValue());
					}
					
					// 卖家备注
					// 获取卖家备注
					BizOrderDO bizOrderDO = new BizOrderDO();
					bizOrderDO.setDomain(Constant.DOMAIN_JIUXIU);
					bizOrderDO
							.setBizOrderId(tcMainOrder.getBizOrder() == null ? 0
									: tcMainOrder.getBizOrder().getBizOrderId());
					mo.setCustomerServiceNote(BizOrderUtil
							.getSellerMemo(bizOrderDO));
//					//添加主订单实付款
//					mo.setItemPrice_(tcMainOrder.getBizOrder().getActualTotalFee());
					mainOrderList.add(mo);
				}
			}
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(
					orderListQuery.getPageNo(), orderListQuery.getPageSize(),
					(int) batchBizQueryResult.getTotalCount(), mainOrderList);
			return orderPageVO;
		} else {
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(
					orderListQuery.getPageNo(), orderListQuery.getPageSize(),
					0, mainOrderList);
			return orderPageVO;
		}

	}

	@Override
	public OrderDetails getOrderById(long id) throws Exception {
		OrderQueryOption orderQueryOption = new OrderQueryOption();
		orderQueryOption.setAll();
		try {
			TcSingleQueryResult tcSingleQueryResult = tcBizQueryServiceRef
					.querySingle(id, orderQueryOption);
			if (tcSingleQueryResult.isSuccess()) {
				OrderDetails orderDetails = new OrderDetails();
				MainOrder mainOrder = OrderConverter
						.orderVOConverter(tcSingleQueryResult.getTcMainOrder());
				mainOrder = OrderConverter.mainOrderStatusConverter(mainOrder,
						tcSingleQueryResult.getTcMainOrder());
				//添加主订单原价
				mainOrder.setItemPrice_(BizOrderUtil.getMainOrderTotalFee(tcSingleQueryResult.getTcMainOrder().getBizOrder().getBizOrderDO()));
				//获取优惠劵优惠金额
				VoucherInfo voucherInfo = BizOrderUtil.getVoucherInfo(tcSingleQueryResult.getTcMainOrder().getBizOrder().getBizOrderDO());
				if(null!=voucherInfo){
					mainOrder.setRequirement(voucherInfo.getRequirement());
					mainOrder.setValue(voucherInfo.getValue());
				}
				//获取使用的积分
				mainOrder.setUserPointNum(BizOrderUtil.getUsePointNum(tcSingleQueryResult.getTcMainOrder().getBizOrder().getBizOrderDO()));
				orderDetails.setMainOrder(mainOrder);
				TcMainOrder tcMainOrder = mainOrder.getTcMainOrder();
				TcBizOrder tcBizOrder = tcMainOrder.getBizOrder();

				if (tcBizOrder != null) {
					long buyerId = tcBizOrder.getBuyerId();
					UserDO buyer = userServiceRef.getUserDOById(buyerId);
					orderDetails.setSellerId(tcBizOrder.getSellerId());
					orderDetails.setBuyerName(buyer.getName());
					orderDetails.setBuyerNiceName(buyer.getNickname());
					orderDetails.setBuyerPhoneNum(buyer.getMobileNo());
					// 付款方式
					if (tcMainOrder.getPayChannel() != 0) {
						orderDetails.setPayChannel(TcPayChannel.getPayChannel(
								tcMainOrder.getPayChannel()).getDesc());
					}
				}
				// orderDetails.setTotalFee(tcMainOrder.getTotalFee());
				orderDetails.setActualTotalFee(tcMainOrder.getTotalFee());
				// 联系人邮箱
				// String email = BizOrderUtil.getLineContactEmail(tcBizOrder);
				// orderDetails.setEmail(email);
				// 参加人
				// orderDetails.setTourists(tcMainOrder.getTouristList());
				// 联系人
				// orderDetails.setContacts(tcMainOrder.getContactInfo());
				// 买家备忘录
				orderDetails.setBuyerMemo(tcMainOrder.getOtherInfo());
				// 关闭原因
				orderDetails.setCloseReason(tcMainOrder.getCloseReason());
				// 确认、发货时间
				orderDetails.setConsignTime(DateUtil.longToDate(tcMainOrder
						.getDeliveryTime()));
				// 获取卖家备注
				BizOrderDO bizOrderDO = new BizOrderDO();
				bizOrderDO.setDomain(Constant.DOMAIN_JIUXIU);
				bizOrderDO.setBizOrderId(tcMainOrder.getBizOrder() == null ? 0
						: tcMainOrder.getBizOrder().getBizOrderId());
				orderDetails.setCustomerServiceNote(BizOrderUtil
						.getSellerMemo(bizOrderDO));
				return orderDetails;
			}
		} catch (Exception e) {
			log.error("public OrderDetails getOrderById(long id);" + e);
			return null;
		}
		return null;
	}

	// 完成
	@Override
	public boolean buyerConfirmGoods(long id) {
		BuyerConfirmGoodsDTO buyerConfirmGoodsDTO = new BuyerConfirmGoodsDTO();
		buyerConfirmGoodsDTO.setBizOrderId(id);
		try {
			BuyerConfirmGoodsResult buyerConfirmGoodsResult = tcTradeServiceRef
					.buyerConfirmGoods(buyerConfirmGoodsDTO);
			return buyerConfirmGoodsResult.isSuccess();
		} catch (Exception e) {
			log.error("tcTradeServiceRef.buyerConfirmGoods(buyerConfirmGoodsDTO);"
					+ e);
			return false;
		}
	}

	// 发货--确认/
	@Override
	public boolean sellerSendGoods(long id) {
		SellerSendGoodsDTO sellerSendGoodsDTO = new SellerSendGoodsDTO();
		sellerSendGoodsDTO.setBizOrderId(id);
		try {
			SellerSendGoodsResult sellerSendGoodsResult = tcTradeServiceRef
					.sellerSendGoods(sellerSendGoodsDTO);
			return sellerSendGoodsResult.isSuccess();
		} catch (Exception e) {
			log.error("tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);"
					+ e);
			return false;
		}

	}

	// 退款
	@Override
	public boolean refundOrder(long id) {
		RefundTradeDTO refundTradeDTO = new RefundTradeDTO();
		refundTradeDTO.setBizOrderId(id);
		try {
			ResultSupport resultSupport = tcTradeServiceRef
					.refundOrder(refundTradeDTO);
			return resultSupport.isSuccess();

//		} catch (Exception e) {
//			log.error("tcTradeServiceRef.refundOrder(refundTradeDTO);" + e);

		}catch (Exception e){
			log.error("id={}", id, e);

			return false;
		}
	}

	@Override
	public boolean closeOrder(long id) {
		try {
			CloseOrderDTO closeOrderDTO = new CloseOrderDTO();
			closeOrderDTO.setBizOrderId(id);
			closeOrderDTO.setCloseOrderReasonId(CloseOrderReason.CLOSE_BY_ADMIN
					.getType());
			ResultSupport resultSupport = tcTradeServiceRef
					.closeOrder(closeOrderDTO);
			return resultSupport.isSuccess();

//		} catch (Exception e) {
//			log.error("tcTradeServiceRef.closeOrder(id);" + e);

		}catch (Exception e){
			log.error("id={}" ,id, e);
			return false;
		}
	}

	@Override
	public boolean updateOrderInfo(long id,String remark) {
		if (id <= 0 || StringUtils.isBlank(remark)) {
			return false;
		}
		try {
			UpdateBizOrderExtFeatureDTO bizOrderExtFeatureDTO = new UpdateBizOrderExtFeatureDTO();
			bizOrderExtFeatureDTO.setBizOrderId(id);
			Map<BizOrderExtFeatureKey, Object> addOrderMap = new HashMap<BizOrderExtFeatureKey, Object>();
			addOrderMap.put(BizOrderExtFeatureKey.SELLER_MEMO, remark);
			bizOrderExtFeatureDTO.setAddOrUpdateBizOrderExtFeatureKeyObjectMap(addOrderMap);
			ResultSupport updateResult = tcTradeServiceRef.updateBizOrderExtFeature(bizOrderExtFeatureDTO);
			return updateResult.isSuccess();
		} catch (Exception e) {
			log.error("id={},remark={}",id,remark,e);

			return false;
		}
	}
	
	@Override
	public boolean confirmCheckIn(long id,long sellerId) {
		try {
			SellerConfirmCheckInDTO sellerConfirmCheckInDTO = new SellerConfirmCheckInDTO();
			sellerConfirmCheckInDTO.setBizOrderId(id);
			sellerConfirmCheckInDTO.setSellerId(sellerId);
			//卖家确认完成
			sellerConfirmCheckInDTO.setSource(FinishOrderSource.SELLER.getBizType());
			
			SellerConfirmCheckInResult result = tcTradeServiceRef.sellerConfirmCheckIn(sellerConfirmCheckInDTO);
			return result.isSuccess();
		} catch (Exception e) {
			log.error("tcTradeServiceRef.sellerConfirmCheckIn()" + e);
			return false;
		}
	}
	
	@Override
	public boolean confirmCheckNot(long id,long sellerId) {
		try {
			SellerConfirmCheckInDTO sellerConfirmCheckInDTO = new SellerConfirmCheckInDTO();
			sellerConfirmCheckInDTO.setBizOrderId(id);
			sellerConfirmCheckInDTO.setSellerId(sellerId);
			//卖家确认完成
			sellerConfirmCheckInDTO.setSource(FinishOrderSource.SELLER.getBizType());
			
			SellerConfirmCheckInResult result = tcTradeServiceRef.sellerConfirmNotCheckIn(sellerConfirmCheckInDTO);
			return result.isSuccess();
		} catch (Exception e) {
			log.error("tcTradeServiceRef.sellerConfirmNotCheckIn()" + e);
			return false;
		}
	}

	@Override
	public PageVO<JXComRateResult> getAssessmentList(
			AssessmentListQuery assessmentListQuery) {
		List<JXComRateResult> resultList = new  ArrayList<JXComRateResult>();
		
		//封装RatePageListDTO
		RatePageListDTO ratePageListDTO = OrderConverter.assessmentListQueryToRatePageListDTO(assessmentListQuery);
		if(null == ratePageListDTO){
			PageVO<JXComRateResult> orderPageVO = new PageVO<JXComRateResult>(
					assessmentListQuery.getPageNo(), assessmentListQuery.getPageSize(),
					0, resultList);
			return orderPageVO;
		}
		//根据用户昵称查询idList
		if (StringUtils.isNotEmpty(assessmentListQuery.getNickName())) {
			BaseResult<List<UserDO>> users = userServiceRef
					.getUserByNickname(assessmentListQuery.getNickName());
			OrderConverter.getIdByUserName(users, ratePageListDTO);
			
		}
		
		BasePageResult<ComRateResult> comRateResults = comRateServiceRef.getRatePageList(ratePageListDTO);
		if(!comRateResults.isSuccess()){
			log.error("ratePageListDTO={},comRateResults={}", ratePageListDTO,comRateResults);
			throw new BaseException("查询评价列表失败 ");
		}
		List<ComRateResult> comRateResultList = comRateResults.getList();
		
		if(null == comRateResultList || comRateResultList.size() < 1){
			PageVO<JXComRateResult> orderPageVO = new PageVO<JXComRateResult>(
					assessmentListQuery.getPageNo(), assessmentListQuery.getPageSize(),
					0, resultList);
			return orderPageVO;
		}else{
			List<JXComRateResult> jxresultList = new ArrayList<JXComRateResult>();
			for(int i=0;i<comRateResultList.size();i++){
				ComRateResult comRateResult = comRateResultList.get(i);
				JXComRateResult jxComRateResult = OrderConverter.changeComRateToJX(comRateResult);
				UserDO userDO = userServiceRef.getUserDOById(jxComRateResult.getUserId());
				jxComRateResult.setNickName(userDO.getNickname());
				jxComRateResult.setUserPhoto(userDO.getAvatar());
				jxComRateResult.setContent(comRateResult.getContent());
				//根据商品ids获取商品列表
				List<Long> ids = new ArrayList<Long>();
				ids.add(comRateResult.getItemId());
				ICResult<List<ItemDO>> item = itemQueryService.getItemByIds(ids);
				if(null!=item.getModule() && item.getModule().size()>0){
					jxComRateResult.setItemName(item.getModule().get(0).getTitle());
				}
				jxresultList.add(jxComRateResult);
			}
			resultList = jxresultList;
			PageVO<JXComRateResult> orderPageVO = new PageVO<JXComRateResult>(
					assessmentListQuery.getPageNo(), assessmentListQuery.getPageSize(),
					comRateResults.getTotalCount(), resultList);
			return orderPageVO;
		}
	}
	
	public List<ExpressCodeRelationDO> selectAllExpressCode(){
		List<ExpressCodeRelationDO> list = lgService.selectAllExpressCode();
		return list;
	}
	
	public boolean sendGoods(SellerSendGoodsDTO sellerSendGoodsDTO){
		SellerSendGoodsResult result = tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);
		if(null == result || !result.isSuccess()){
			log.error("tcTradeServiceRef.sellerSendGoods is error;param=" + JSON.toJSONString(sellerSendGoodsDTO)+"|||result="+JSON.toJSONString(result));
			return false;
		}
		return true;
	}
}

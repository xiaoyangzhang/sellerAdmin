package com.yimayhd.sellerAdmin.service;

import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.JXComRateResult;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;

/**
 * 订单服务接口
 * 
 * @author zhaozhaonan
 *
 */
public interface OrderService {
	/**
	 * 通过ID获取订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OrderDetails getOrderById(long id) throws Exception;

	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception;


	boolean buyerConfirmGoods(long id);

	boolean sellerSendGoods(long id);

	boolean refundOrder(long id);

	boolean closeOrder(long id);
	/**
	 * 确认入住
	 * @param id
	 * @return
	 */
	boolean confirmCheckIn(long id,long sellerId);
	/**
	 * 确认未入住
	 * @param id
	 * @return
	 */
	boolean confirmCheckNot(long id,long sellerId);
	/**
	 * 获取评论列表
	 * @param assessmentListQuery
	 * @return
	 */
	PageVO<JXComRateResult> getAssessmentList(AssessmentListQuery assessmentListQuery);

}

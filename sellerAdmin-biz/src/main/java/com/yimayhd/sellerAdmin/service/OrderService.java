package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.tradecenter.client.model.param.order.UpdateBizOrderExtFeatureDTO;

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
	 * 更新卖家备注
	 * @param dto
	 * @return
	 */
	boolean updateOrderInfo(long id,String remark);
}

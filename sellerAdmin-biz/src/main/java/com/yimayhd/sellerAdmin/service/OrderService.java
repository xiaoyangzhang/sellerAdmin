package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.lgcenter.client.domain.ExpressCodeRelationDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.OrderListVO;
import com.yimayhd.sellerAdmin.model.order.OrderPriceQuery;
import com.yimayhd.sellerAdmin.model.order.OrderPrizeDTO;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.JXComRateResult;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.sellerAdmin.util.excel.domain.BizOrderExportDomain;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.param.order.SellerBatchSendGoodsDTO;
import com.yimayhd.tradecenter.client.model.param.order.SellerSendGoodsDTO;
import com.yimayhd.tradecenter.client.model.result.order.TcSingleQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.metaq.OrderInfoTO;

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
	 * 根据主订单id获取子订单列表
	 * @parameter
	 * @return
	 * @throws
	 */
	List<OrderListVO> getOrderListByMainOrderId(long bizOrderId, OrderQueryOption orderQueryOption);
	
	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception;

	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	List<BizOrderExportDomain> exportOrderList(OrderListQuery orderListQuery, List<OrderInfoTO> orderInfoTOList, List<MainOrder> mainOrderList) throws Exception;



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

	/**
	 * 更新卖家备注
	 * @param dto
	 * @return
	 */
	boolean updateOrderInfo(long id,String remark);
	/**
	 * 发货
	 * @param sg
	 * @return
	 */
	boolean sendGoods(SellerSendGoodsDTO sg);
	
	/**
	 * 批量发货
	 * @parameter
	 * @return
	 * @throws
	 */
	boolean batchSendGoods(SellerBatchSendGoodsDTO sellerBatchSendGoodsDTO);
	/**
	 * 查看物流公司
	 * @return
	 */
	List<ExpressCodeRelationDO> selectAllExpressCode();
	
	/**
	 *  根据订单号查询订单
	 * @param bizOrderId
	 * @return
	 */
	TcSingleQueryResult searchOrderById(long bizOrderId);

	/**
	 * 订单改价
	 * @param orderPriceQuery
	 * @return
     */
	 WebResult<OrderPrizeDTO> orderChangePrice( OrderPriceQuery orderPriceQuery);


}

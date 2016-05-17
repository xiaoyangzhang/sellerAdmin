package com.yimayhd.sellerAdmin.controller;

import javax.servlet.http.HttpServletRequest;

import com.yimayhd.sellerAdmin.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.sellerAdmin.service.OrderService;

/**
 * 订单管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;


	/**
	 * 退款
	 */
	@RequestMapping(value = "/refundOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo refundOrderById(long orderId) throws Exception {
		boolean flag = orderService.refundOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/buyerConfirmGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo buyerConfirmGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.buyerConfirmGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 发货+确认
	 */
	@RequestMapping(value = "/sellerSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo sellerSendGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.sellerSendGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}


	/**
	 * 关闭、取消订单
	 */
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo closeOrder(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.closeOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}







	/**
	 * 根据ID获取路线订单详情
	 * @return 路线订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		long currentUserId= getCurrentUserId();
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			long sellerId = orderDetails.getSellerId();
			if (sellerId>0 && currentUserId == sellerId){
				model.addAttribute("order", orderDetails);
				return "/system/order/routeOrderInfo";
			}else{
				return "/system/error/lackPermission";
			}
		}
		return "/system/error/404";

	}

	/**
	 * 路线订单列表
	 * @return 路线订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String orderList(Model model, OrderListQuery orderListQuery) throws Exception {
		long userId = getCurrentUserId();
		if (userId > 0){
			orderListQuery.setSellerId(userId);
			orderListQuery.setDomain(Constant.DOMAIN_JIUXIU);
			PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("orderList", pageVo.getResultList());
			model.addAttribute("orderListQuery", orderListQuery);
		}
		return "/system/order/routeOrderList";
	}
	
	/**
	 * 评价列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/assessmentList", method = RequestMethod.GET)
	public String addessmentList(Model model, AssessmentListQuery assessmentListQuery) throws Exception {
		long userId = getCurrentUserId();
//		if (userId > 0){
//			assessmentListQuery.setSellerId(userId);
//			assessmentListQuery.setDomain(Constant.DOMAIN_JIUXIU);
//			PageVO<MainOrder> pageVo = orderService.getAssessmentList(assessmentListQuery);
//			model.addAttribute("pageVo", pageVo);
//			model.addAttribute("assessmentList", pageVo.getResultList());
//			model.addAttribute("assessmentListQuery", assessmentListQuery);
//		}
		return "/system/order/assessmentList";
	}













/*



	*/
/**
	 * 根据ID获取景区订单详情
	 * 
	 * @return 景区订单详情
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/scenicSpotOrder/{id}", method = RequestMethod.GET)
	public String getScenicSpotOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/scenicSpotOrderInfo";
	}

	*/
/**
	 * 景区订单列表
	 * 
	 * @return 景区订单列表
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/scenicSpotOrderList", method = RequestMethod.GET)
	public String scenicSpotOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.SPOTS.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/scenicSpotOrderList";
	}

	*/
/**
	 * 根据ID获取实物商品订单详情
	 * 
	 * @return 实物商品订单详情
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/goodsOrder/{id}", method = RequestMethod.GET)
	public String getGoodOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/goodsOrderInfo";
	}

	*/
/**
	 * 实物商品订单列表
	 * 
	 * @return 实物商品订单列表
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/goodsOrderList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.NORMAL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/goodsOrderList";
	}

	*/
/**
	 * 根据ID获取活动订单详情
	 * 
	 * @return 活动订单详情
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/activityOrder/{id}", method = RequestMethod.GET)
	public String getActivityOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/activityOrderInfo";
	}

	*/
/**
	 * 活动订单
	 * 
	 * @return 活动订单
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/activityOrderList", method = RequestMethod.GET)
	public String activityOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.ACTIVITY.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/activityOrderList";
	}

	*/
/**
	 * 根据ID获取会员卡订单详情
	 * 
	 * @return 活动会员卡详情
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/membershipCardOrder/{id}", method = RequestMethod.GET)
	public String getMembershipCardOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/membershipCardOrderInfo";
	}

	*/
/**
	 * 会员卡订单列表
	 * 
	 * @return 会员卡订单列表
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/membershipCardOrderList", method = RequestMethod.GET)
	public String membershipCardOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.MEMBER_RECHARGE.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/membershipCardOrderList";
	}



	*/
/**
	 * 根据ID获取路线订单详情
	 * @return 路线订单详情
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/hotelOrder/{id}", method = RequestMethod.GET)
	public String getHotelOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/hotelOrderInfo";
	}

	*/
/**
	 * 路线订单列表
	 * @return 路线订单列表
	 * @throws Exception
	 *//*

	@RequestMapping(value = "/hotelOrderList", method = RequestMethod.GET)
	public String hotelOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.HOTEL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/hotelOrderList";
	}

	*/
/**
	 * @Title: GFOrderList
	 * @Description:(GF订单列表)
	 * @author create by yushengwei @ 2016年2月26日
	 * @throws
	 *//*

	@RequestMapping(value = "/gfOrderList")//, method = RequestMethod.GET
	public String gfOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		orderListQuery.setDomain(1100);//TODO:enum类
		//int [] orderBizTypes = {OrderBizType.HOTEL.getBizType()};
		//orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getResultList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/gfOrderList";
	}

	*/
/**
	 * @Title: gfOrderDetailById
	 * @Description:(根据ID获取GF订单详情)
	 * @author create by yushengwei @ 2016年2月26日
	 * @throws
	 *//*

	@RequestMapping(value = "/gfOrder/{id}", method = RequestMethod.GET)
	public String gfOrderDetailById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/gfOrderInfo";
	}

*/

}

package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yimayhd.lgcenter.client.domain.ExpressCodeRelationDO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyQuery;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyVO;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.service.AppraiseMessageReplyService;
import com.yimayhd.sellerAdmin.constant.Constant;

import com.yimayhd.sellerAdmin.converter.ExcelExportConverer;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.BizOrderVO;
import com.yimayhd.sellerAdmin.model.order.OrderPriceQuery;
import com.yimayhd.sellerAdmin.model.order.OrderPrizeDTO;
import com.yimayhd.sellerAdmin.model.query.TradeListQuery;
import com.yimayhd.sellerAdmin.util.excel.JxlFor2003;
import com.yimayhd.sellerAdmin.util.excel.domain.BizOrderExportDomain;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.metaq.OrderInfoTO;
import org.apache.commons.collections.CollectionUtils;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
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
import com.yimayhd.sellerAdmin.enums.BizItemType;
import com.yimayhd.sellerAdmin.enums.OrderSearchType;
import com.yimayhd.sellerAdmin.model.query.AssessmentListQuery;
import com.yimayhd.sellerAdmin.model.query.OrderListQuery;
import com.yimayhd.sellerAdmin.model.trade.JXComRateResult;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.OrderDetails;
import com.yimayhd.sellerAdmin.service.OrderService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.tradecenter.client.model.enums.BizOrderExtFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.FinishOrderSource;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.SellerSendGoodsDTO;
import com.yimayhd.tradecenter.client.model.param.order.UpdateBizOrderExtFeatureDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.TcSingleQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;


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
	@Autowired
	private AppraiseMessageReplyService appraiseMessageReplyService;

	private final static int MONTH = -2;
	private final static int DAY = 62;

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
		long sellerId = getCurrentUserId();
		OrderDetails order= orderService.getOrderById(orderId);
		if (order.getSellerId() != sellerId) {
			return new ResponseVo(ResponseStatus.PARAM_ERROR);
		}
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
				model.addAttribute("finishOrderSource", FinishOrderSource.values());
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
			model.addAttribute("orderTypeList", OrderSearchType.values());
		}
		return "/system/order/routeOrderList";
	}

	/**
	 * 导出交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void orderListExport(HttpServletRequest request,HttpServletResponse response,OrderListQuery orderListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		orderListQuery.setSellerId(sellerId);
		orderListQuery.setDomain(Constant.DOMAIN_JIUXIU);
		orderListQuery.setPageNo(0);
		orderListQuery.setPageSize(Integer.MAX_VALUE);
		List<BizOrderExportDomain> bizOrderExportDomains = orderService.exportOrderList(orderListQuery);
		if(CollectionUtils.isNotEmpty(bizOrderExportDomains)) {
            List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("bizOrderId", "订单编号"));
			headList.add(new BasicNameValuePair("parentBizOrderId", "父订单编号"));
			headList.add(new BasicNameValuePair("commodityName", "商品名称"));
			headList.add(new BasicNameValuePair("resourceName", "资源名称"));
			headList.add(new BasicNameValuePair("commodityId", "商品id"));
			headList.add(new BasicNameValuePair("unitPrice", "单价"));
			headList.add(new BasicNameValuePair("itemNum", "数量"));
			headList.add(new BasicNameValuePair("itemType", "类型"));
			headList.add(new BasicNameValuePair("buyerName", "买家昵称"));
			headList.add(new BasicNameValuePair("buyerTel", "买家手机号"));
			headList.add(new BasicNameValuePair("bizOrderType", "订单状态"));
			headList.add(new BasicNameValuePair("discount", "已优惠"));
			headList.add(new BasicNameValuePair("bizOrderTotalPrice", "订单总价"));
			headList.add(new BasicNameValuePair("realCollection", "实收款"));
			headList.add(new BasicNameValuePair("payScore", "支付积分"));
			headList.add(new BasicNameValuePair("bizOrderCreateTime", "创建订单时间"));
			headList.add(new BasicNameValuePair("bizOrderPayTime", "支付订单时间"));
			headList.add(new BasicNameValuePair("buyerNotes", "买家备注"));
			headList.add(new BasicNameValuePair("sellerNotes", "卖家备注"));
			headList.add(new BasicNameValuePair("consigneeName", "收货人姓名"));
			headList.add(new BasicNameValuePair("consigneeTel", "收货人手机号码"));
			headList.add(new BasicNameValuePair("receivingProvince", "收货省"));
			headList.add(new BasicNameValuePair("receivingCity", "收货市"));
			headList.add(new BasicNameValuePair("receivingArea", "收货地区"));
			headList.add(new BasicNameValuePair("receivingAdress", "收货详细地址"));
			headList.add(new BasicNameValuePair("contactsName", "联系人姓名"));
			headList.add(new BasicNameValuePair("contactsTel", "联系人手机"));
			headList.add(new BasicNameValuePair("contactsEmail", "联系人邮箱"));
			headList.add(new BasicNameValuePair("travelListStr", "游客信息"));
			headList.add(new BasicNameValuePair("closeBizReason", "关单原因"));
			JxlFor2003.exportExcel(response, "交易记录.xls", bizOrderExportDomains, headList);
		}
	}
	
	/**
	 * 确认入住
	 */
	@RequestMapping(value = "/confirmCheckIn", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo confirmCheckIn(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.confirmCheckIn(orderId,getCurrentUserId());
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
	/**
	 * 确认未入住
	 */
	@RequestMapping(value = "/confirmCheckNot", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo confirmCheckNot(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.confirmCheckNot(orderId,getCurrentUserId());
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
	/**
	 * 评价列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/assessmentList")
	public String addessmentList(Model model, AssessmentListQuery assessmentListQuery) throws Exception {
		long userId = getCurrentUserId();
		if (userId > 0){
			assessmentListQuery.setSellerId(userId);
			assessmentListQuery.setDomain(Constant.DOMAIN_JIUXIU);
			PageVO<JXComRateResult> pageVo = orderService.getAssessmentList(assessmentListQuery);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("assessmentList", pageVo.getResultList());
			model.addAttribute("assessmentListQuery", assessmentListQuery);
		}
		return "/system/order/assessmentList";
	}



	/**
	 * 更新卖家备注
	 * @param orderId
	 * @param sellerRemark
	 * @return 
	 */
	@RequestMapping(value="/updateOrderDetail",method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateOrderDetail(long orderId,String sellerRemark) {
		boolean updateResult = orderService.updateOrderInfo(orderId,sellerRemark);
		if(updateResult) {
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
		
	}
	/**
	 * 查询物流公司
	 * @param model
	 * @param bizOrderId
	 * @return
	 */
	@RequestMapping(value = "/sendGoodsSearch", method = RequestMethod.GET)
	public String sendGoodsSearch(Model model,long bizOrderId){
		List<ExpressCodeRelationDO> list = orderService.selectAllExpressCode();//查询物流公司接口
		//log.info("sendGoodsSearch--:"+ JSON.toJSONString(list));
		List<ExpressCodeRelationDO> list2 = new ArrayList<ExpressCodeRelationDO>();
		for (ExpressCodeRelationDO expressCodeRelationDO : list) {
			if(expressCodeRelationDO.getName().contains("顺丰速运")){
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().contains("圆通")) {
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().contains("韵达快递")) {
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().contains("申通快递")) {
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().contains("中通快递")) {
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().contains("百世汇通")) {
				list2.add(expressCodeRelationDO);
			}else if (expressCodeRelationDO.getName().equals("EMS")) {
				list2.add(expressCodeRelationDO);
			}else if(expressCodeRelationDO.getName().equals("天天快递")){
				list2.add(expressCodeRelationDO);
			}
		}
		model.addAttribute("listExpress",list2);
		model.addAttribute("bizOrderId",bizOrderId);
		return "/system/order/routeOrderSendGoods";
	}
	
	/**
	 * 发货
	 * @param bizOrderId
	 * @param expressCompany
	 * @param expressNo
	 * @return
	 */
	@RequestMapping(value = "/sendGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo sendGoods(long bizOrderId,String expressCompany,String expressNo){
		if(0==bizOrderId || StringUtils.isEmpty(expressCompany) || StringUtils.isEmpty(expressNo)){
			return new ResponseVo(ResponseStatus.INVALID_DATA);
		}
		//权限校验
		TcSingleQueryResult result = orderService.searchOrderById(bizOrderId);
		if(result == null || !result.isSuccess() || null==result.getTcMainOrder()){
			return new ResponseVo(ResponseStatus.NOT_FOUND);
		}
		TcBizOrder bizOrder = result.getTcMainOrder().getBizOrder();
		if (null == bizOrder) {
			return new ResponseVo(ResponseStatus.NOT_FOUND);
		}
		if(bizOrder.getSellerId() != sessionManager.getUserId()){
			return new ResponseVo(ResponseStatus.PARAM_ERROR);
		}
		//权限校验end
		
		long userId = sessionManager.getUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" time=").append(DateUtil.format(new Date()))
		  .append(" userid=").append(userId)
		  .append(" bizOrderId=").append(bizOrderId)
		  .append(" expressCompany=").append(expressCompany)
		  .append(" expressNo=").append(expressNo);
		LOG.info(sb.toString());
		SellerSendGoodsDTO sg = new SellerSendGoodsDTO();
		sg.setBizOrderId(bizOrderId);
		sg.setExpressCompany(expressCompany);
		sg.setExpressNo(expressNo);
		boolean flag = orderService.sendGoods(sg);
		return new ResponseVo(flag);
	}


	/**
	 * 订单改价
	 * @param model
	 * @param orderPriceQuery
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/orderChangePrice",method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> orderChangePrice(Model model, OrderPriceQuery orderPriceQuery) throws Exception {
		WebResult<String> result = new WebResult<String>();
		log.info("orderChangePrice controller");
		if(orderPriceQuery==null||StringUtils.isBlank(orderPriceQuery.getOrderJson())){
			result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
			return result;
		}
		WebResult<OrderPrizeDTO> orderResult = orderService.orderChangePrice(orderPriceQuery);
		if(!orderResult.isSuccess()||orderResult.getValue()==null){
			log.error(orderResult.getWebReturnCode().getErrorMsg());
			result.setWebReturnCode(orderResult.getWebReturnCode());
			return result;
		}
		OrderPrizeDTO orderPrizeDTO = orderResult.getValue();
		result.setValue(orderPrizeDTO.getOrderJson());
		return result;
	}

	/**
	 * 改价浮层
	 * @param model
	 * @param orderid
     * @return
     */
	@RequestMapping(value = "/changepriceView",method = RequestMethod.GET)
	public String changepriceView(Model model,long orderid){
		if(orderid==0){
			return "/system/error/404";
		}
		model.addAttribute("orderid",orderid);
		return "/system/order/changeprice";
	}

	@RequestMapping(value = "/setUpReplyMsg",method = RequestMethod.POST)
	@ResponseBody
	public SellerResult<String> setUpReplyMsg(Model model, ReplyQuery replyQuery) throws Exception{
		log.info("setUpReplyMsg start param="+ JSON.toJSONString(replyQuery));
		long sellerId = getCurrentUserId();
		if(sellerId==0){
			log.error("商家id不能为空");
			return SellerResult.failure("商家id不能为空");
		}
		replyQuery.setSellerId(sellerId);
		SellerResult<ReplyVO> callResult  = appraiseMessageReplyService.addAppraiseMessageReply(replyQuery);
		if(callResult==null||!callResult.isSuccess()){
			log.error("评价回复失败,errMsg={}",callResult.getMsg());
			return SellerResult.failure(callResult.getMsg());
		}
		ReplyVO replyVO = callResult.getResultObject();
		String json = CommonJsonUtil.objectToJson(replyVO,ReplyVO.class);
		log.info("setUpReplyMsg result:"+json);
		return SellerResult.success(json);
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

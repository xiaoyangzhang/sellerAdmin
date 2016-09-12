package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.BizOrderExportVO;
import com.yimayhd.sellerAdmin.model.BizOrderVO;
import com.yimayhd.sellerAdmin.model.PayOrderExportVO;
import com.yimayhd.sellerAdmin.model.query.PayListQuery;
import com.yimayhd.sellerAdmin.model.query.TradeListQuery;
import com.yimayhd.sellerAdmin.service.TradeService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.excel.JxlFor2003;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 交易管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/tradeManage")
public class TradeManageController extends BaseController {
	private final static int MONTH = -2;
	private final static int DAY = 62;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 交易列表
	 * 
	 * @return 交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/list", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public String orderList(Model model, TradeListQuery tradeListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId = 1;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(tradeListQuery.getEndDate())){
			tradeListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(tradeListQuery.getBeginDate())) {
			tradeListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}

		PageVO<BizOrderVO> pageVo = tradeService.getOrderList(sellerId,tradeListQuery);
		List<BizOrderVO> bizOrderVOList = pageVo.getResultList();
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("tradeListQuery", tradeListQuery);
		model.addAttribute("orderList", bizOrderVOList);
		return "/system/trade/order/list";
	}
	/**
	 * 导出交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/export", method = RequestMethod.GET)
	public void orderListExport(HttpServletRequest request,HttpServletResponse response,TradeListQuery tradeListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId = 1;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(tradeListQuery.getEndDate())){
			tradeListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(tradeListQuery.getBeginDate())){
			tradeListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}
		if(DAY < DateUtil.daySubtraction(tradeListQuery.getBeginDate(),tradeListQuery.getEndDate())){
			throw new NoticeException("导出日期不能超过两个月");
		}
		List<BizOrderExportVO> bizOrderExportVOList = tradeService.exportOrderList(sellerId, tradeListQuery);
		if(CollectionUtils.isNotEmpty(bizOrderExportVOList)) {
			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("bizOrderId", "交易号"));
			headList.add(new BasicNameValuePair("number", "单号"));
			headList.add(new BasicNameValuePair("dt", "部门"));
			headList.add(new BasicNameValuePair("jn", "工号"));
			headList.add(new BasicNameValuePair("buyerNick", "会员名"));
			headList.add(new BasicNameValuePair("payChannelName", "支付方式"));
			headList.add(new BasicNameValuePair("payStatusName", "交易状态"));
			headList.add(new BasicNameValuePair("pn", "手机号"));
			headList.add(new BasicNameValuePair("actualTotalFeeY", "付款金额(单位：分)"));
			headList.add(new BasicNameValuePair("usePoint", "使用积分"));
			headList.add(new BasicNameValuePair("givePoint", "赠送积分"));
			headList.add(new BasicNameValuePair("gmtCreated", "交易时间"));
			headList.add(new BasicNameValuePair("sttDate", "小票时间"));
			JxlFor2003.exportExcel(response, "交易记录.xls", bizOrderExportVOList, headList);
		}
	}

	/**
	 * 交易详情
	 * 
	 * @return 交易详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public String getById(Model model,@PathVariable(value = "orderId") long orderId) throws Exception {
		List<BizOrderDO> bizOrderDOList = tradeService.getOrderByOrderId(orderId);
		model.addAttribute("orderDetailList",bizOrderDOList);
		return "/system/trade/order/detail";
	}

	/**
	 * 支付记录列表
	 *
	 * @return 支付记录列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/list", method = RequestMethod.GET)
	public String payList(Model model, PayListQuery payListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId =10000000;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(payListQuery.getEndDate())){
			payListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(payListQuery.getBeginDate())){
			payListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}

		PageVO<PayOrderDO> pageVo  = tradeService.getPayOrderList(sellerId,payListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("payListQuery", payListQuery);
		model.addAttribute("payList", pageVo.getResultList());
		return "/system/trade/pay/list";
	}
	/**
	 * 导出支付记录列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/export", method = RequestMethod.GET)
	public void payListExport(HttpServletRequest request,HttpServletResponse response,PayListQuery payListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId =10000000;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(payListQuery.getEndDate())){
			payListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(payListQuery.getBeginDate())){
			payListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}
		if(DAY < DateUtil.daySubtraction(payListQuery.getBeginDate(),payListQuery.getEndDate())){
			throw new NoticeException("导出日期不能超过两个月");
		}
		List<PayOrderExportVO> payOrderExportVOList = tradeService.exportPayOrderList(sellerId,payListQuery);
		if(CollectionUtils.isNotEmpty(payOrderExportVOList)) {
			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("tradeNo", "支付编号"));
			headList.add(new BasicNameValuePair("id", "交易号"));
			headList.add(new BasicNameValuePair("subject", "商品信息"));
			headList.add(new BasicNameValuePair("buyerAccount", "对方账号"));
			headList.add(new BasicNameValuePair("totalAmountY", "交易金额(单位：分)"));
			headList.add(new BasicNameValuePair("payStatusName", "状态"));
			headList.add(new BasicNameValuePair("gmtPayment", "支付时间"));
			JxlFor2003.exportExcel(response, "支付历史.xls", payOrderExportVOList, headList);
		}
	}

}

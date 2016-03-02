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

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.IMallRefundRecordExportVO;
import com.yimayhd.sellerAdmin.model.query.RefundListQuery;
import com.yimayhd.sellerAdmin.service.RefundService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.excel.JxlFor2003;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundDetailDO;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 退款管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/refundManage")
public class RefundManageController extends BaseController {
	private final static int MONTH = -2;
	private final static int DAY = 62;

	@Autowired
	private RefundService refundService;

	@Autowired
	private SessionManager sessionManager;
	/**
	 * 退款列表
	 * 
	 * @return 退款列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String refundList(Model model, RefundListQuery refundListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId = 1;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(refundListQuery.getEndDate())){
			refundListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(refundListQuery.getBeginDate())){
			refundListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}
		PageVO<IMallRefundRecordDO> pageVO = refundService.getList(sellerId,refundListQuery);
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("refundListQuery", refundListQuery);
		model.addAttribute("refundList", null == pageVO ? null : pageVO.getItemList());
		return "/system/refund/list";
	}

	/**
	 * 交易详情(退款)
	 *
	 * @return 交易详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public String getById(Model model,@PathVariable(value = "orderId") long refundId) throws Exception {
		long sellerId = sessionManager.getUserId();
		List<IMallRefundDetailDO> iMallRefundDetailDOList = refundService.getOrderByRecordId(sellerId,refundId);
		model.addAttribute("refundDetailList",iMallRefundDetailDOList);
		return "/system/refund/detail";
	}

	/**
	 * 导出退款列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/refund/export", method = RequestMethod.GET)

	public String refundListExport(HttpServletRequest request,HttpServletResponse response,RefundListQuery refundListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		//long sellerId = 1;
		//初始化和未填日期的时候，默认最近两个月
		if(StringUtils.isBlank(refundListQuery.getEndDate())){
			refundListQuery.setEndDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		if(StringUtils.isBlank(refundListQuery.getBeginDate())){
			refundListQuery.setBeginDate(DateUtil.getMonthAgo(new Date(), MONTH));
		}
		if(DAY < DateUtil.daySubtraction(refundListQuery.getBeginDate(),refundListQuery.getEndDate())){
			throw new NoticeException("导出日期不能超过两个月");
		}
		List<IMallRefundRecordExportVO> iMallRefundRecordExportVOList = refundService.exportRefundList(sellerId,refundListQuery);
		if(CollectionUtils.isNotEmpty(iMallRefundRecordExportVOList)) {
			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("tradeId", "交易编号"));
			headList.add(new BasicNameValuePair("number", "单号"));
			headList.add(new BasicNameValuePair("department", "部门"));
			headList.add(new BasicNameValuePair("jobNumber", "工号"));
			headList.add(new BasicNameValuePair("refundPaymentY", "实际退款金额"));
			headList.add(new BasicNameValuePair("refundTime", "退款时间"));
			headList.add(new BasicNameValuePair("receiptTime", "小票时间"));
			JxlFor2003.exportExcel(response, "退款列表.xls", iMallRefundRecordExportVOList, headList);
			return null;
		}else{
			return "/error";
		}
	}
}

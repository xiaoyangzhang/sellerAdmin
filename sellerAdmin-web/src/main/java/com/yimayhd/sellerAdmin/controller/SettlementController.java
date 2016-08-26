package com.yimayhd.sellerAdmin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.SettlementDetailVO;
import com.yimayhd.sellerAdmin.model.SettlementVO;
import com.yimayhd.sellerAdmin.model.query.SettlementQuery;
import com.yimayhd.sellerAdmin.service.SettlementService;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
 * @author hongfei.guo
 *
 */
@Controller
@RequestMapping("/settlement")
public class SettlementController extends BaseController {

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private SettlementService settlementService;
	
	/**
	 * 已结算查询
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET) 
	public String list(Model model, SettlementQuery query) throws Exception {
		
		long userId = sessionManager.getUserId();
		PageVO<SettlementVO> pageVo = settlementService.queryMerchantSettlements(query, userId);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		return "/system/settlement/list";
	}
	
	/**
	 * 结算单详情
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET) 
	public String details(Model model, SettlementQuery query) throws Exception {
		
		long userId = sessionManager.getUserId();
		PageVO<SettlementDetailVO> pageVo = settlementService.queryMerchantSettlementDetails(query, userId);
		model.addAttribute("pageVo", pageVo);
		
		String reqDate = query.getReqDate();
		int subIndexNum = 6;
		if(StringUtils.isNotEmpty(reqDate) && reqDate.length() > subIndexNum){
			String reqDateWithOutYear = reqDate.substring(subIndexNum, reqDate.length());
			query.setReqDateWithOutYear(reqDateWithOutYear.replace("-", "月")+"日");
		}
		
		model.addAttribute("query", query);
		return "/system/settlement/detail";
	}
	
	/**
	 * 待结算查询
	 */
	@RequestMapping(value = "/pendingList", method = RequestMethod.GET) 
	public String unList(Model model, SettlementQuery query) throws Exception {
		long userId = sessionManager.getUserId();
		PageVO<SettlementDetailVO> pageVo = settlementService.queryMerchantUnsettlements(query, userId);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		return "/system/settlement/pendingList";
	}
}

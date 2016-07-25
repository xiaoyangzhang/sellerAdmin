package com.yimayhd.sellerAdmin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/settlement")
public class SettlementController extends BaseController {

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private SettlementService settlementService;
	
	/**
	 * 已结算查询
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST) 
	public String list(Model model, SettlementQuery query) throws Exception {
		long userId = sessionManager.getUserId();
		PageVO<SettlementVO> pageVo = settlementService.queryMerchantSettlements(query, userId);
		model.addAttribute("pageVo", pageVo);
		return "/system/settlement/list";
	}
	
	/**
	 * 结算单详情
	 */
	@RequestMapping(value = "/details", method = RequestMethod.POST) 
	public String details(Model model, SettlementQuery query) throws Exception {
		long userId = sessionManager.getUserId();
		PageVO<SettlementDetailVO> pageVo = settlementService.queryMerchantSettlementDetails(query, userId);
		model.addAttribute("pageVo", pageVo);
		return "/system/settlement/detail";
	}
	
	/**
	 * 待结算查询
	 */
	@RequestMapping(value = "/pendingList", method = RequestMethod.POST) 
	public String unList(Model model, SettlementQuery query) throws Exception {
		long userId = sessionManager.getUserId();
		PageVO<SettlementVO> pageVo = settlementService.queryMerchantUnsettlements(query, userId);
		model.addAttribute("pageVo", pageVo);
		return "/system/settlement/pendingList";
	}
}

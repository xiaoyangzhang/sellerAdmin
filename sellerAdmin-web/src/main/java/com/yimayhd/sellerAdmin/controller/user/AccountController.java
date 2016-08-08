package com.yimayhd.sellerAdmin.controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.pay.client.model.enums.TransType;
import com.yimayhd.pay.client.model.enums.eleaccount.EleAccountType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.UserBiz;
import com.yimayhd.sellerAdmin.checker.UserChecker;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.EleAccountBillVO;
import com.yimayhd.sellerAdmin.model.EleAccountInfoVO;
import com.yimayhd.sellerAdmin.model.WithdrawalVO;
import com.yimayhd.sellerAdmin.model.query.AccountQuery;
import com.yimayhd.sellerAdmin.model.vo.user.ModifyPasswordVo;
import com.yimayhd.sellerAdmin.service.AccountService;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
 * @author wzf
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

	@Autowired
	private UserBiz userBiz;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private AccountService accountService;
	
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET) 
	public ModelAndView modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/modifyPassword");
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> modifyPassword(HttpServletRequest request, ModifyPasswordVo modifyPasswordVo) {
		WebResult<String> result = new WebResult<String>();
		WebResultSupport checkResult = UserChecker.checkModifyPasswordPassword(modifyPasswordVo);
		if ( !checkResult.isSuccess() ) {
			result.setWebReturnCode(checkResult.getWebReturnCode());
			return result ;
		}
		long userId = sessionManager.getUserId(request);
		ChangePasswordDTO changePasswordDTO = UserConverter.toModifyPasswordDTO(modifyPasswordVo, userId);
		
		WebResultSupport modifyResult = userBiz.modifyPassword(changePasswordDTO);
		if( modifyResult == null || !modifyResult.isSuccess() ){
			if( modifyResult == null ){
				result.setWebReturnCode(WebReturnCode.SYSTEM_ERROR);
			}else{
				result.setWebReturnCode(modifyResult.getWebReturnCode());
			}
			return result ;
		}
		String url = UrlHelper.getUrl(rootPath, "/account/modifyPasswordSuccess");
		result.setValue(url);
		return result;
	}
	
	@RequestMapping(value = "/modifyPasswordSuccess", method = RequestMethod.GET) 
	public ModelAndView modifyPasswordSuccess(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/modifyPasswordSuccess");
	}
	
	/**
	 * 我的钱包
	 */
	@RequestMapping(value = "/toMyWallet", method = RequestMethod.GET) 
	public ModelAndView toMyWallet(Model model) throws Exception {
		
		
		long userId = sessionManager.getUserId();
		EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);
		model.addAttribute("accountInfo", accountInfo);
		return new ModelAndView("/system/account/myWallet");
	}
	
	/**
	 * 到提现页面
	 */
	@RequestMapping(value = "/toWithdrawal", method = RequestMethod.GET) 
	public ModelAndView toWithdrawal(Model model) throws Exception {
		long userId = sessionManager.getUserId();
		EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);
		model.addAttribute("accountInfo", accountInfo);
		return new ModelAndView("/system/account/withdrawal");
	}
	
	/**
	 * 提现
	 */
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST) 
	@ResponseBody
	public ResponseVo withdrawal(Model model){
		try {
			
			long userId = sessionManager.getUserId();
			EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);
			
			WithdrawalVO vo = new WithdrawalVO();
			vo.setUserId(userId);
			vo.setWithdrawalAmount(accountInfo.getAccountBalance());
			vo.setEleAccountType(EleAccountType.UNION_ELE_ACCOUNT.getType());
			if(accountInfo.getAccountBalance() <= 0 ){
				throw new NoticeException(Constant.WITHDRAWAL_ACCOUNT_BALANCE_IS_ZERO);
			}
			
			accountService.withdrawal(vo);
			return ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
	/**
	 * 提现结果
	 */
	@RequestMapping(value = "/withdrawalResult", method = RequestMethod.GET) 
	@ResponseBody
	public ModelAndView withdrawalResult(Model model){
		return new ModelAndView("/system/account/withdrawalResult");
	}
	
	/**
	 * 收支明细
	 */
	@RequestMapping(value = "/billDetail", method = RequestMethod.GET) 
	public ModelAndView billDetail(Model model, AccountQuery query) throws Exception {
		long userId = sessionManager.getUserId();
		PageVO<EleAccountBillVO> pageVo = accountService.queryEleAccBillDetail(query, userId);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		model.addAttribute("WITHDRAW", TransType.WITHDRAW.getType());
		model.addAttribute("SETTLEMENT", TransType.SETTLEMENT.getType());
		return new ModelAndView("/system/account/billDetail");
	}
}

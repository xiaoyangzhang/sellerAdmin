package com.yimayhd.sellerAdmin.controller.user;


import com.alibaba.fastjson.JSON;
import com.yimayhd.pay.client.model.enums.msg.VerifyCodeType;
import com.yimayhd.pay.client.model.param.eleaccount.password.SettingSellerPayPwdDTO;
import com.yimayhd.pay.client.model.param.eleaccount.verify.VerifySellerAdminDTO;
import com.yimayhd.pay.client.model.param.verifycode.VerifyCodeDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.VerifySellerAdminResult;
import com.yimayhd.sellerAdmin.error.BizErrorCode;
import com.yimayhd.sellerAdmin.repo.AccountRepo;
import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.user.session.manager.SessionHelper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.pay.client.model.enums.TransType;
import com.yimayhd.pay.client.model.enums.eleaccount.EleAccountStatus;
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
	@Autowired
	private AccountRepo accountRepo;

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
		if(accountInfo.getStatus() == EleAccountStatus.NOT_VERIFIED.getStatus()){
			accountInfo.setTipMessage(Constant.WITHDRAWAL_COMPLETE_ACCOUNT_INFO);
		}
		if (accountInfo.isExistPayPwd()) {
			model.addAttribute("accountInfo", accountInfo);
			model.addAttribute("ACCOUNT_NOT_VERIFIED", EleAccountStatus.NOT_VERIFIED.getStatus());
			return new ModelAndView("/system/account/myWallet");
		}else {
			model.addAttribute("mobileKey", accountInfo.getMobilePhone());
			return new ModelAndView("/system/account/initPayPassword");
		}
	}

	/**
	 * 到提现页面
	 */
	@RequestMapping(value = "/toWithdrawal", method = RequestMethod.GET)
	public ModelAndView toWithdrawal(Model model) throws Exception {
		long userId = sessionManager.getUserId();
		EleAccountInfoVO accountInfo = this.judgeAccountStatus(userId);
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
			ResponseVo responseVo=new ResponseVo();
			long userId = sessionManager.getUserId();
			String payPwd=get("password");
			EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);

			WithdrawalVO vo = new WithdrawalVO();
			vo.setUserId(userId);
			vo.setWithdrawalAmount(accountInfo.getAccountBalance());
			vo.setPayPwd(payPwd);
			vo.setEleAccountType(EleAccountType.UNION_ELE_ACCOUNT.getType());
			if(accountInfo.getAccountBalance() <= 0 ){
				throw new NoticeException(Constant.WITHDRAWAL_ACCOUNT_BALANCE_IS_ZERO);
			}
			//Constant.WITHDRAWAL_FAIL

			ResultSupport support=accountService.withdrawalByPwd(vo);
			if (!support.isSuccess()){
				responseVo.setStatus(support.getErrorCode().getCode());
				responseVo.setMessage(support.getResultMsg());
			}
			return responseVo;
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

		//如果账户未认证，则抛出异常
		this.judgeAccountStatus(userId);

		PageVO<EleAccountBillVO> pageVo = accountService.queryEleAccBillDetail(query, userId);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		model.addAttribute("WITHDRAW", TransType.WITHDRAW.getType());
		model.addAttribute("SETTLEMENT", TransType.SETTLEMENT.getType());
		return new ModelAndView("/system/account/billDetail");
	}

	private EleAccountInfoVO judgeAccountStatus(long userId) throws Exception {
		EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);
		if(accountInfo.getStatus() == EleAccountStatus.NOT_VERIFIED.getStatus()){
			throw new NoticeException(Constant.WITHDRAWAL_COMPLETE_ACCOUNT_INFO);
		}
		return accountInfo;
	}

	/**
	 * 设置支付密码 发送短信
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String sendMessage(Model model) throws Exception {
		Map<String,Object> map=new HashMap<>();
		VerifyCodeDTO dto=new VerifyCodeDTO();
		try {
			dto.setUserId(sessionManager.getUserId());
			dto.setVerifyCodeType(VerifyCodeType.VERIFY_SELLER_ADMIN.getType());
			ResultSupport resultSupport=accountRepo.sendVerifyCode(dto);
			if (!resultSupport.isSuccess()){
				map.put("code",resultSupport.getResultCode());
				map.put("msg",resultSupport.getResultMsg());
			}
		}catch (Exception e){
			map.put("code",BizErrorCode.SYSTEM_ERROR.getCode());
			map.put("msg",e.getMessage());
		}
		return JSON.toJSONString(map);
	}

	/**
	 * 验证短信验证码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/verifyCode", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String verifyCode(Model model)  {
		Map<String, Object> map = new HashMap<>();
		String code = get("verifyCode");
		if (StringUtils.isBlank(code)) {
			map.put("code", BizErrorCode.PARAMS_NO_FULL.getCode());
			map.put("msg", BizErrorCode.PARAMS_NO_FULL.getMsg());
			return JSON.toJSONString(map);
		}
		try {
			VerifySellerAdminDTO dto = new VerifySellerAdminDTO();
			dto.setUserId(sessionManager.getUserId());
			dto.setVerifyCode(code);
			VerifySellerAdminResult result = accountRepo.verifySellerAdmin(dto);
			if (!result.isSuccess()) {
				map.put("code", result.getResultCode());
				map.put("msg", result.getResultMsg());
			}
			SessionHelper.getRequest().getSession().setAttribute(sessionManager.getUserId() + "", result.getVerifyIdentityCode());
		}catch (Exception e){
			map.put("code", BizErrorCode.SYSTEM_ERROR.getCode());
			map.put("msg", BizErrorCode.SYSTEM_ERROR.getMsg()+":"+e.getMessage());
		}
		return JSON.toJSONString(map);
	}

	/**
	 * 设置支付密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/settPayPwd" , method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public String settingSellerPayPwd() throws Exception{
		Map<String, Object> map = new HashMap<>();
		SettingSellerPayPwdDTO dto = new SettingSellerPayPwdDTO();
		try {
			String payPwd = get("payPwd");
			if (StringUtils.isBlank(payPwd)) {
				map.put("code", BizErrorCode.PARAMS_NO_FULL.getCode());
				map.put("msg", BizErrorCode.PARAMS_NO_FULL.getMsg());
				return JSON.toJSONString(map);
			}
			Object verifyIdentityCode = SessionHelper.getRequest().getSession().getAttribute(sessionManager.getUserId() + "");
			if (verifyIdentityCode == null) {
				map.put("code", "002");
				map.put("msg", "身份认证已失效");
				return JSON.toJSONString(map);
			}
			dto.setPayPwd(payPwd);
			dto.setUserId(sessionManager.getUserId());
			dto.setVerifyIdentityCode(verifyIdentityCode.toString());
			ResultSupport resultSupport = accountRepo.settingSellerPayPwd(dto);
			SessionHelper.getRequest().getSession().removeAttribute(sessionManager.getUserId() + "");
			if (!resultSupport.isSuccess()) {
				map.put("code", resultSupport.getResultCode());
				map.put("msg", resultSupport.getResultMsg());
			}
		}catch (Exception e){
			map.put("code", BizErrorCode.SYSTEM_ERROR.getCode());
			map.put("msg", BizErrorCode.SYSTEM_ERROR.getMsg()+":"+e.getMessage());
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "/toFindPayPwd" )
	public ModelAndView toFindPayPwd(Model model) throws Exception{
		long userId = sessionManager.getUserId();
		EleAccountInfoVO accountInfo = accountService.querySingleEleAccount(userId);
		if(accountInfo.getStatus() == EleAccountStatus.NOT_VERIFIED.getStatus()){
			accountInfo.setTipMessage(Constant.WITHDRAWAL_COMPLETE_ACCOUNT_INFO);
		}
		model.addAttribute("mobileKey", accountInfo.getMobilePhone());
		return new ModelAndView("/system/account/findPayPassword");
	}
}

package com.yimayhd.sellerAdmin.repo;

import com.yimayhd.pay.client.model.param.eleaccount.password.SettingSellerPayPwdDTO;
import com.yimayhd.pay.client.model.param.eleaccount.verify.VerifySellerAdminDTO;
import com.yimayhd.pay.client.model.param.verifycode.VerifyCodeDTO;
import com.yimayhd.pay.client.model.result.eleaccount.VerifySellerAdminResult;
import com.yimayhd.pay.client.service.verifycode.VerifyCodeService;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.pay.client.model.param.eleaccount.WithdrawalDTO;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccountSingleQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccountInfoResult;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;
import com.yimayhd.pay.client.service.eleaccount.EleAccBillService;
import com.yimayhd.pay.client.service.eleaccount.EleAccHandlerService;
import com.yimayhd.pay.client.service.eleaccount.EleAccInfoService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 
 * @author hongfei.guo
 *
 */
public class AccountRepo {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private EleAccInfoService eleAccInfoServiceRef;
	
	@Resource
	private EleAccHandlerService eleAccHandlerServiceRef;
	
	@Resource
	private EleAccBillService eleAccBillServiceRef;

	@Resource
	private VerifyCodeService verifyCodeServiceRef;
	/**
     * 查询电子账户的信息
     * @param query
     * @return
     */
	public EleAccountInfoResult querySingleEleAccount(EleAccountSingleQuery query){
		RepoUtils.requestLog(log, "eleAccInfoServiceRef.querySingleEleAccount", query);
		EleAccountInfoResult result = eleAccInfoServiceRef.querySingleEleAccount(query);
		RepoUtils.resultLog(log, "eleAccInfoServiceRef.querySingleEleAccount", result);
		return result;
	}
    
    /**
     * 账户提现
     * @param dto
     * @return
     */
	public ResultSupport withdrawal(WithdrawalDTO dto){
		RepoUtils.requestLog(log, "eleAccHandlerServiceRef.withdrawal", dto);
		ResultSupport result = eleAccHandlerServiceRef.withdrawal(dto);
		RepoUtils.resultLog(log, "eleAccHandlerServiceRef.withdrawal", result);
		return result;
	}
    
    /**
     * 用户收支明细的查询
     * @param query
     * @return
     */
	public PayPageResultDTO<EleAccountBillDTO> queryEleAccBillDetail(EleAccBillDetailQuery query){
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", query);
		PayPageResultDTO<EleAccountBillDTO> result = eleAccBillServiceRef.queryEleAccBillDetail(query);
		RepoUtils.resultLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", result);
		return result;
	}

	/**
	 * 支付密码修改 发送验证码
	 * @param dto
	 * @return
	 */
	public ResultSupport sendVerifyCode(VerifyCodeDTO dto){
		RepoUtils.requestLog(log, "eleAccHandlerServiceRef.sendVerifyCode", dto);
		ResultSupport result = verifyCodeServiceRef.sendVerifyCode(dto);
		log.info("eleAccHandlerServiceRef.sendVerifyCode:{}", com.alibaba.fastjson.JSON.toJSONString(result));
		return result;
	}

	/**
	 * 验证验证码是否正确的方法
	 * @param dto
	 * @return
	 */
	public VerifySellerAdminResult verifySellerAdmin(VerifySellerAdminDTO dto){
		RepoUtils.requestLog(log, "eleAccHandlerServiceRef.verifySellerAdmin", dto);
		VerifySellerAdminResult result=eleAccInfoServiceRef.verifySellerAdmin(dto);
		log.info("eleAccHandlerServiceRef.sendVerifyCode:{}", com.alibaba.fastjson.JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	public ResultSupport settingSellerPayPwd(SettingSellerPayPwdDTO dto){
		RepoUtils.requestLog(log, "eleAccHandlerServiceRef.settingSellerPayPwd", dto);
		ResultSupport result=eleAccInfoServiceRef.settingSellerPayPwd(dto);
		log.info("eleAccHandlerServiceRef.sendVerifyCode:{}", com.alibaba.fastjson.JSON.toJSONString(result));
		return result;
	}
}

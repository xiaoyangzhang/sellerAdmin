package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.pay.client.model.param.eleaccount.WithdrawalDTO;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccountSingleQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccountInfoResult;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.EleAccountBillVO;
import com.yimayhd.sellerAdmin.model.EleAccountInfoVO;
import com.yimayhd.sellerAdmin.model.WithdrawalVO;
import com.yimayhd.sellerAdmin.model.query.AccountQuery;
import com.yimayhd.sellerAdmin.repo.AccountRepo;
import com.yimayhd.sellerAdmin.service.AccountService;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * Created by hongfei.guo on 2016/07/25.
 */
public class AccountServiceImpl implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public EleAccountInfoVO querySingleEleAccount(AccountQuery query) throws Exception {
		EleAccountSingleQuery queryDO = AccountQuery.getEleAccountSingleQuery(query);
		queryDO.setUserId(sessionManager.getUserId());
		
		EleAccountInfoResult result = accountRepo.querySingleEleAccount(queryDO);
		if(result == null){
			log.error("accountRepo.querySingleEleAccount return value is null and param: {}", JSON.toJSONString(query));
			throw new BaseException("查询用户账户，返回结果错误");
		}

		EleAccountInfoVO infoVO = EleAccountInfoVO.getEleAccountInfoVO(result);
		return infoVO;
	}

	@Override
	public boolean withdrawal(WithdrawalVO withdrawalVO) throws Exception {
		
		WithdrawalDTO dto = WithdrawalVO.getWithdrawalDTO(withdrawalVO);
		dto.setUserId(sessionManager.getUserId());
		
		ResultSupport result = accountRepo.withdrawal(dto);
		if(null == result){
            log.error("AccountServiceImpl.withdrawal--accountRepo.withdrawal return value is null and parame: {}", JSON.toJSONString(dto));
            throw new BaseException("提现失败");
        }else if(!result.isSuccess()){
            log.error("AccountServiceImpl.withdrawal--accountRepo.withdrawal return value error ! returnValue : {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(dto));
            throw new NoticeException(result.getResultMsg());
        }
		
		return result.isSuccess();
	}

	@Override
	public PageVO<EleAccountBillVO> queryEleAccBillDetail(AccountQuery query) throws Exception {
		
		EleAccBillDetailQuery queryDO = AccountQuery.getEleAccBillDetailQuery(query);
		queryDO.setUserId(sessionManager.getUserId());
		
		PayPageResultDTO<EleAccountBillDTO> result = accountRepo.queryEleAccBillDetail(queryDO);
		if(null == result){
            log.error("AccountServiceImpl.queryEleAccBillDetail--accountRepo.queryEleAccBillDetail return value is null and parame: {}", JSON.toJSONString(queryDO));
            throw new BaseException("查询用户账户，返回结果错误");
        }else if(!result.isSuccess()){
            log.error("AccountServiceImpl.queryEleAccBillDetail--accountRepo.queryEleAccBillDetail return value error ! returnValue : {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(queryDO));
            throw new NoticeException(result.getResultMsg());
        }
		
		List<EleAccountBillVO> retList = new ArrayList<EleAccountBillVO>();
		List<EleAccountBillDTO> list = result.getList();
		if(CollectionUtils.isNotEmpty(list)){
			EleAccountBillDTO dto = null;
			for(int i = 0; i < list.size(); i++){
				dto = list.get(i);
				retList.add(EleAccountBillVO.getWithdrawalDTO(dto));
			}
		}
		
		return new PageVO<EleAccountBillVO>(query.getPageNo(), query.getPageSize(), result.getTotalCount(), retList);
	}

	
}

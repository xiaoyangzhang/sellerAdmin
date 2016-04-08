package com.yimayhd.sellerAdmin.repo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;

public class MerchantRepo {
	private static Logger LOGGER = LoggerFactory.getLogger(MerchantRepo.class);
	
	@Resource
	private MerchantService merchantService;
	
	@Autowired
	private UserService userService;
	
	@Resource
	private ExamineDealService examineDealService;
	
	public MemResult<ExamineInfoDTO> queryMerchantExamineInfoBySellerId(InfoQueryDTO info){
		return examineDealService.queryMerchantExamineInfoBySellerId(info);
	}
	
	
	/**
	 * 更新用户信息
	 * @param userDTO
	 * @return
	 */
	public BaseResult<UserDO> updateUser (UserDTO userDTO){
		return userService.updateUser(userDTO);
	}
	
	/**
	 * 保存商户基本信息
	 * @param merchantDO
	 * @return
	 */
	public BaseResult<MerchantDO> saveMerchant(MerchantDO merchantDO) {
		return merchantService.saveMerchant(merchantDO);
	}
	
	/**
	 * 更新商户基本信息
	 * @param merchantDO
	 * @return
	 */
	public BaseResult<Boolean> updateMerchantInfo(MerchantDTO merchantDTO) {
		return merchantService.updateMerchantInfo(merchantDTO);
	}
	
	/**
	 * 新增或修改商户入驻资料
	 * @param examineInfoDTO
	 * @return
	 */
	public MemResult<Boolean> saveUserdata(ExamineInfoDTO examineInfoDTO){
		return examineDealService.submitMerchantExamineInfo(examineInfoDTO);
	}
	
	/**
	 * 修改商户入驻状态
	 * @param examineInfoDTO
	 * @return
	 */
	public MemResult<Boolean> changeExamineStatusIntoIng(InfoQueryDTO nfoQueryDTO){
		return examineDealService.changeExamineStatusIntoIng(nfoQueryDTO);
	}
	
	
}

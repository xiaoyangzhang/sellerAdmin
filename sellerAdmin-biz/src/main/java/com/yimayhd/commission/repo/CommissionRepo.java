package com.yimayhd.commission.repo;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commission.client.base.BaseResult;
import com.yimayhd.commission.client.base.PageResult;
import com.yimayhd.commission.client.param.AmountObtainDTO;
import com.yimayhd.commission.client.param.AmountTotalDetailDTO;
import com.yimayhd.commission.client.param.CommissionDetailDTO;
import com.yimayhd.commission.client.param.UserInfoDTO;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.client.result.comm.AmountNumDTO;
import com.yimayhd.commission.client.result.comm.AmountTotalDTO;
import com.yimayhd.commission.service.CommissionService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class CommissionRepo {

    private static final Logger logger = LoggerFactory.getLogger(CommissionRepo.class);

    @Autowired
    private CommissionService commissionService;

    /**
     * 查询会员佣金
     * @param detailDTODetailDTO
     * @return
     */
    public PageResult<AmountTotalDTO> queryRebateAmt(AmountTotalDetailDTO detailDTODetailDTO){

        try{
            PageResult<AmountTotalDTO>  pageResult =  commissionService.queryAmountTotalDetail(detailDTODetailDTO);
            if(pageResult.isSuccess()){
                return pageResult;
            }
        }catch (Exception e){
            logger.error("CommissionRepo.queryRebateAmt error!params:{}", JSONObject.toJSONString(detailDTODetailDTO),e);
        }
        return null;
    }

    /**
     * 查询会员提现流水记录
     * @param userId
     */
    public PageResult<AmountDetailDTO> queryExtractDetailByUserId(long userId,int pageNo,int pageSize){

        try{
            CommissionDetailDTO commissionDetailDTO = new CommissionDetailDTO();
            commissionDetailDTO.setUserId(userId);
            commissionDetailDTO.setDomainId(1000);
            commissionDetailDTO.setPageNo(pageNo);
            commissionDetailDTO.setPageSize(pageSize);
            PageResult<AmountDetailDTO> pageResult = commissionService.queryExtractDetailByUserId(commissionDetailDTO);
            if(pageResult.isSuccess()){
                return pageResult;
            }
        }catch (Exception e){
            logger.error("CommissionRepo.queryExtractDetailByUserId error,params:{}",userId,e);
        }
        return null;
    }


    /**
     * 佣金提现
     * @param amountObtainDTO
     * @return
     */
    public BaseResult<AmountNumDTO> amountExtract(AmountObtainDTO amountObtainDTO){
    	BaseResult<AmountNumDTO> baseResult = null;
        try{
             baseResult = commissionService.amountExtract(amountObtainDTO);
            if(baseResult == null || !baseResult.isSuccess()){
            	logger.error("CommissionRepo.amountExtract request commissionService failure,param:{},result:{}",
            			JSON.toJSONString(amountObtainDTO), baseResult);
            }
        }catch (Exception e){
            logger.error("CommissionRepo.amountExtract error!,params:{}",amountObtainDTO,e);
        }
        return baseResult;
    }
    
    /**
     * 批量更新用户信息
     * @param list
     */
    public void batchUpdateUserInfo(List<UserInfoDTO> list){
    	if(CollectionUtils.isEmpty(list)){
    		return ;
    	}
    	try{
    		
    		BaseResult<List<UserInfoDTO>> remoteBaseResult = commissionService.batchUpdateUserInfo(list);
    		if (remoteBaseResult == null || !remoteBaseResult.isSuccess()) {
    			logger.error("CommissionRepo.batchUpdateUserInfo unexpected result,param:{},result:{}", 
    					JSON.toJSONString(list), JSON.toJSONString(remoteBaseResult));
    		}
    		
    	}catch(Exception e){
    		logger.error("CommissionRepo.batchUpdateUserInfo exceptions occur,param:{},ex:{}", 
    				JSON.toJSONString(list), e);
    	}
    	
    }

}

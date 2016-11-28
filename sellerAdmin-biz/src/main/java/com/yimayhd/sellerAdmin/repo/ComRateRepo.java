package com.yimayhd.sellerAdmin.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.RateReployDTO;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComRateService;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/11/15.
 */
public class ComRateRepo {
    private Logger log = LoggerFactory.getLogger(ComRateRepo.class);

    @Autowired
    private ComRateService comRateServiceRef;

    /**
     * 评论回复
     * @param rateReployDTO
     * @return
     */
    public SellerResult<ComRateDO> addRateReploy(RateReployDTO rateReployDTO){
        log.info("addRateReploy repo ,rateReployDTO="+ JSON.toJSONString(rateReployDTO));
        try{
            BaseResult<ComRateDO> callResult =  comRateServiceRef.addRateReploy(rateReployDTO);
            if(callResult==null||!callResult.isSuccess()){
                return SellerResult.failure(WebReturnCode.REMOTE_CALL_FAILED.getErrorCode(),callResult.getResultMsg());
            }
            log.info("addRateReploy repo result ,callResult="+ JSON.toJSONString(callResult));
            return SellerResult.success(callResult.getValue());
        }catch (Exception e){
            log.error("addRateReploy repo exception ",e);
            return SellerResult.failure(WebReturnCode.REMOTE_CALL_FAILED.getErrorCode(),"调用repo异常");
        }

    }
}

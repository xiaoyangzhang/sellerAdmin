package com.yimayhd.sellerAdmin.service.reply;

import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyQuery;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyVO;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.service.AppraiseMessageReplyService;
import com.yimayhd.sellerAdmin.converter.ReplyConverter;
import com.yimayhd.sellerAdmin.repo.ComRateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/11/15.
 */
public class AppraiseMessageReplyServiceImpl implements AppraiseMessageReplyService {
    private Logger log = LoggerFactory.getLogger(ComRateRepo.class);


    @Autowired
    private ComRateRepo comRateRepo;

    /**
     * 添加回复信息
     * @param replyQuery
     * @return
     */
    @Override
    public SellerResult<ReplyVO> addAppraiseMessageReply(ReplyQuery replyQuery) {
        ReplyConverter conver = new ReplyConverter(replyQuery);
        SellerResult checkResult = conver.checkAddParam();
        if(!checkResult.isSuccess()){
            return checkResult;
        }
        try{
            SellerResult<ComRateDO> result =comRateRepo.addRateReploy(conver.getRateReployDTO());
            if(!result.isSuccess()){
                return SellerResult.failure(result.getCode(),result.getMsg());
            }
            ReplyVO vo =  conver.getReplyVO(result.getResultObject());
            return SellerResult.success(vo);
        }catch (Exception e){
            log.error("addAppraiseMessageReply 异常",e);
            return SellerResult.failure(WebReturnCode.SYSTEM_ERROR.getErrorMsg());
        }

    }
}

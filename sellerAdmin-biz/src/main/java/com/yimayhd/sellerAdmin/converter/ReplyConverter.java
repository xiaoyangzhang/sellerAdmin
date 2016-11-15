package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.RateReployDTO;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyQuery;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyVO;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;


/**
 * Created by wangdi on 16/11/15.
 */
public class ReplyConverter {

    private static final Logger logger = LoggerFactory.getLogger(ReplyConverter.class);
    private ReplyQuery replyQuery;
    private BeanCopier voCopier = BeanCopier.create(ComRateDO.class, ReplyVO.class, false);


    public ReplyConverter(ReplyQuery replyQuery){
        this.replyQuery = replyQuery;
    }

    public SellerResult checkAddParam(){
        if(replyQuery==null){
            return SellerResult.failure(WebReturnCode.PARAM_ERROR.getErrorCode(),WebReturnCode.PARAM_ERROR.getErrorMsg());
        }
        if(replyQuery.getSellerId()==0){
            return SellerResult.failure(WebReturnCode.PARAM_ERROR.getErrorCode(),"商户id不能为空");
        }
        if(replyQuery.getId()==0){
            return SellerResult.failure(WebReturnCode.PARAM_ERROR.getErrorCode(),"回复pid不能为空");
        }
        if(StringUtils.isNotBlank(replyQuery.getBackContent())){
            return SellerResult.failure(WebReturnCode.PARAM_ERROR.getErrorCode(),"回复内容不能为空");
        }
        return  SellerResult.success();
    }

    public RateReployDTO getRateReployDTO(){
        if(replyQuery==null){
            return null;
        }
        RateReployDTO dto = new RateReployDTO();
        dto.setSellerId(replyQuery.getSellerId());
        dto.setId(replyQuery.getId());
        dto.setBackContent(replyQuery.getBackContent());
        dto.setGmtModified(DateUtil.getCurrentTimeStamp());
        return  dto;
    }

    public ReplyVO getReplyVO(ComRateDO comRateDO){
        ReplyVO vo = new ReplyVO();
        if(comRateDO==null){
            return null;
        }
        try{
            voCopier.copy(comRateDO,voCopier,null);
        }catch (Exception e){
            logger.error("bean copy fail",e);
            return null;
        }
        return vo;

    }

    public ReplyQuery getReplyQuery() {
        return replyQuery;
    }

    public void setReplyQuery(ReplyQuery replyQuery) {
        this.replyQuery = replyQuery;
    }
}

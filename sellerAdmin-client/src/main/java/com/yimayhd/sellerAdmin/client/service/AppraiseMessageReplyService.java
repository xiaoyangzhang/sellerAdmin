package com.yimayhd.sellerAdmin.client.service;

import com.yimayhd.sellerAdmin.client.model.reply.ReplyQuery;
import com.yimayhd.sellerAdmin.client.model.reply.ReplyVO;
import com.yimayhd.sellerAdmin.client.result.SellerResult;

/**
 * Created by wangdi on 16/11/15.r
 */
public interface AppraiseMessageReplyService {
    /**
     * 添加回复信息
     * @param replyQuery
     * @return
     */
    public SellerResult<ReplyVO> addAppraiseMessageReply(ReplyQuery replyQuery);

}

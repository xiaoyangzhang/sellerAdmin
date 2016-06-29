package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.IMallPointRuleVO;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface PointRuleService {
    /**
     * 获取正在使用的积分发放规则
     * @param sellerId 商家ID
     * @return 赠送积分规则
     * @throws Exception
     */
    IMallPointRuleResult getSendPointRuleNow(long sellerId)throws Exception;

    /**
     * 获取赠送积分规则的历史记录
     * @param sellerId 商家ID
     * @return 赠送积分列表
     * @throws Exception
     */
    PageVO<IMallPointRuleResult> getSendPointRuleHistory(long sellerId,BaseQuery baseQuery)throws Exception;

    /**
     * 新增积分赠送规则
     * @param sellerId 商家ID
     * @param iMallPointRuleVO
     * @return
     * @throws Exception
     */
    boolean add(long sellerId,IMallPointRuleVO iMallPointRuleVO)throws Exception;
}

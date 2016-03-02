package com.yimayhd.commission.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class CommissionDetailQuery extends BaseQuery {


    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

package com.yimayhd.commission.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2016/1/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class ItemRebateQuery extends BaseQuery {

    private String itemTitle;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}

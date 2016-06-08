package com.yimayhd.sellerAdmin.base.template;

import com.yimayhd.sellerAdmin.base.result.CallResult;

/**
 * Created by wangdi on 16/6/6.
 */
public interface ServiceTemplate {
    /**
     * 无事物执行
     * @param action
     * @param <T>
     * @return
     */
    public <T> CallResult<T> exeWithoutTransaction(TemplateAction<T> action);
}

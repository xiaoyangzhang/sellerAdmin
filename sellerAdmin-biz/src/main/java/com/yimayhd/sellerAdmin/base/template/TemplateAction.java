package com.yimayhd.sellerAdmin.base.template;

import com.yimayhd.sellerAdmin.base.result.CallResult;

/**
 * Created by wangdi on 16/6/6.
 */
public interface TemplateAction<T> {
    /**
     * 参数验证
     * @return
     */
    public  CallResult<T> checkParam();

    /**
     * 业务参数验证
     * @return
     */
    public CallResult<T> checkBiz();

    /**
     * 业务执行器
     * @return
     */
    public CallResult<T> doAction();

    /**
     * 最后执行
     * @param callResult
     */

    public void finishUp(CallResult<T> callResult);
}

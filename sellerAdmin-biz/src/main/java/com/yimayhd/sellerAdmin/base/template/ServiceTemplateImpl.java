package com.yimayhd.sellerAdmin.base.template;

import com.yimayhd.sellerAdmin.base.result.CallResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by wangdi on 16/6/7.
 */
public class ServiceTemplateImpl implements ServiceTemplate{
    private static final Log logger = LogFactory.getLog(ServiceTemplateImpl.class);

    /**
     * 无事物处理
     * @param action
     * @param <T>
     * @return
     */
    @Override
    public <T> CallResult<T> exeWithoutTransaction(final TemplateAction<T> action) {
            try{
                //选择数据库
                // DataSourceContextHolder.setDataSourceType(MSDataSourceType.MASTER);
                //非业务参数验证
                CallResult<T> callResult = action.checkParam();
                if(callResult==null){
                    logger.warn("exeInTransaction: Null result while checkParam");
                    return CallResult.failure("Null result while checkParam!");
                }
                if(!callResult.isSuccess()){
                    return callResult;
                }
                //业务参数验证
                callResult = action.checkBiz();
                if(callResult==null){
                    logger.warn("exeInTransaction: Null result while checkBiz");
                    return CallResult.failure("Null result while checkBiz!");
                }
                if(!callResult.isSuccess()){
                    return callResult;
                }
                //业务操作处理
                callResult = action.doAction();
                if(callResult==null){
                    logger.warn("exeInTransaction: Null result while doAction");
                    callResult = CallResult.failure("Null result while doAction!");
                }
                if(!callResult.isSuccess()){
                    return callResult;	//业务执行失败
                }
                //回调方法
                if(callResult.isSuccess()){
                    action.finishUp(callResult);
                }
                return callResult;
            }catch(Exception e){
                logger.error("Error while exeInTransaction!", e);
                return CallResult.failure("Error while exeInTransaction! msg="+e.getMessage());
            }
    }
}

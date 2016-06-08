package com.yimayhd.sellerAdmin.base.result; /**
 *
 */


import java.io.Serializable;

/**
 * 返回参数模板
 * @param <T>
 */

public class CallResult<T> extends SellerBaseDO  {
    public static final int CODE_FAILURE = -1;
    public static final int CODE_SUCCESS = 1;


    private final boolean success;
    private final int code;
    private final String msg;
    private final T resultObject;

    private String stringValue;

    public CallResult(boolean isSuccess, int code, String msg, T resultObject){
        this.success = isSuccess;
        this.code = code;
        this.msg = msg;
        this.resultObject = resultObject;
    }


    public static <T> CallResult<T> success(){
        return new CallResult<T>(true,CODE_SUCCESS,"default success",null);
    }

    public static <T> CallResult<T> success(T resultObject){
        return new CallResult<T>(true,CODE_SUCCESS,"default success",resultObject);
    }

    public static <T> CallResult<T> success(int code, String msg, T resultObject){
        return new CallResult<T>(true,code,msg,resultObject);
    }

    public static <T> CallResult<T> failure(){
        return new CallResult<T>(false,CODE_FAILURE,"default failure",null);
    }

    public static <T> CallResult<T> failure(String msg){
        return new CallResult<T>(false,CODE_FAILURE,msg,null);
    }


    public static <T> CallResult<T> failure(int code, String msg){
        return new CallResult<T>(false,code,msg,null);
    }

    @Override
    public String toString(){
        String result = stringValue;
        if(result != null){
            return result;
        }
        result = new StringBuilder()
                .append('{')
                .append("\"success\":").append(success).append(',')
                .append("\"code\":").append(code).append(',')
                .append("\"msg\":\"").append(msg).append("\",")
                .append("\"resultObject\":").append(resultObject)
                .append('}')
                .toString();
        stringValue = result;
        return result;
    }

	/* -----------------------------getters&setters------------------------------- */


    public boolean hasData(){
        return resultObject!=null;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getResultObject() {
        return resultObject;
    }
}
